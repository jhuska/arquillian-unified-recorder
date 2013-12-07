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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.arquillian.extension.recorder.DefaultFileNameBuilder;
import org.arquillian.extension.recorder.screenshot.ScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshot.event.ScreenshotExtensionConfigured;
import org.arquillian.extension.recorder.screenshot.event.TakeScreenshot;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.extension.screenRecorder.configuration.DesktopScreenshooterConfiguration;
import org.jboss.arquillian.test.spi.TestClass;

/**
 * Takes screenshots of whole desktop.
 * 
 * @author <a href="mailto:pmensik@redhat.com">Petr Mensik</a>
 */
public class DesktopScreenshotTaker {

    private static final Logger logger = Logger.getLogger(DesktopScreenshotTaker.class.getName());

    @Inject
    private Instance<ScreenshooterConfiguration> conf;

    private DesktopScreenshooterConfiguration configuration;
    private File root;

    /**
     * 
     * Observes {@link ScreenshotExtensionConfigured} event and creates directory where the screenshots are stored upon it
     * 
     * @param event
     */
    public void onRecorderConfigured(@Observes ScreenshotExtensionConfigured event) {
        configuration = (DesktopScreenshooterConfiguration) conf.get();
        root = new File(configuration.getRootFolder(), configuration.getScreenshotBaseFolder());
        boolean created = root.mkdirs();
        if (!created) {
            throw new RuntimeException("Unable to create root directory.");
        }
    }

    /**
     * Takes screenshot when {@link TakeScreenshot} event is fired
     * 
     * @param event
     */
    public void onTakeScreenshot(@Observes TakeScreenshot event) {
        String fileName = new DefaultFileNameBuilder()
            .withMetaData(event.getMetaData())
            .withStage(event.getWhen())
            .withFileType(event.getScreenshotType())
            .build();
        try {
            takeScreenshot(event.getMetaData().getTestClass(), fileName);
        } catch (AWTException ex) {
            logger.log(Level.WARNING, "Couldn''t take a screenshot because of java.awt.Robot creation failure {0}",
                ex.getMessage());
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Couldn't write an image to the hard drive ", ex.getMessage());
        }
    }

    /**
     * Takes screenshot by using java.awt.Robot class
     * 
     * @param {@link TestClass} contains metadata about test
     * @param fileName String which specifies where to store the image
     * @throws AWTException when java.awt.Robot is failed to create
     * @throws IOException when image file couldn't be saved on the drive
     */
    private void takeScreenshot(TestClass testClass, String fileName) throws AWTException, IOException {
        File testClassDirectory = new File(root, testClass.getName());
        if (!testClassDirectory.exists()) {
            boolean created = testClassDirectory.mkdirs();
            if (!created) {
                throw new RuntimeException("Unable to create test class directory " + testClassDirectory.getAbsolutePath());
            }
        }
        Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage image = new Robot().createScreenCapture(screenSize);

        File outputFile = FileUtils.getFile(testClassDirectory, fileName);
        ImageIO.write(image, configuration.getScreenshotType(), outputFile);
    }
}
