/*
* JBoss, Home of Professional Open Source
* Copyright 2013, Red Hat Middleware LLC, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.jboss.arquillian.extension.screenRecorder.impl;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.arquillian.extension.recorder.screenshot.ScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshot.ScreenshotMetaData;
import org.arquillian.extension.recorder.screenshot.event.ScreenshotExtensionConfigured;
import org.arquillian.extension.recorder.screenshot.event.TakeScreenshot;
import org.arquillian.extension.recorder.screenshot.event.When;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.TestResult;

/**
 *
 * @author <a href="mailto:pmensik@redhat.com">Petr Mensik</a>
 */
public class DesktopScreenshotTaker {
    
    @Inject
    private Instance<ScreenshooterConfiguration> configuration;
    private File root;
    
    public void onRecorderConfigured(@Observes ScreenshotExtensionConfigured event) {
        root = new File(configuration.get().getRootFolder(), "screenshots");
        root.mkdirs();
    }

    public void onTakeScreenshot(@Observes TakeScreenshot event) {
        takeScreenshot(event.getMetaData(), event.getWhen());
    }
    
    private void takeScreenshot(ScreenshotMetaData metaData, When when) {
        try {
            String appender = "_";
            if(metaData.getTestResult().getStatus() == TestResult.Status.FAILED) {
                appender += "failed";
            } else {
                appender += when.name();
            }
            takeScreenshot(metaData.getTestClass(), metaData.getTestMethod(), appender);
        } catch (AWTException ex) {
            Logger.getLogger(DesktopScreenshotTaker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DesktopScreenshotTaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void takeScreenshot(TestClass testClass, Method testMethod, String appender) throws AWTException, IOException {
        File testClassDirectory = new File(root, testClass.getName());
        if(!testClassDirectory.exists()) {
            testClassDirectory.mkdirs();
        }
        Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = new Robot().createScreenCapture(screenSize);
        String imageName = testMethod.getName() + "_" + appender + "." + configuration.get().getScreenshotType();

        File outputFile = FileUtils.getFile(testClassDirectory, imageName);
        ImageIO.write(image, configuration.get().getScreenshotType(), outputFile);
    }
}
