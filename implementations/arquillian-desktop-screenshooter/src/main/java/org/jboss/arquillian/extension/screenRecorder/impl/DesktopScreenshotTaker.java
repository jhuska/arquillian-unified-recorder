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

import java.io.File;
import org.arquillian.extension.recorder.DefaultFileNameBuilder;
import org.arquillian.extension.recorder.screenshot.Screenshooter;
import org.arquillian.extension.recorder.screenshot.ScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshot.Screenshot;
import org.arquillian.extension.recorder.screenshot.ScreenshotType;
import org.arquillian.extension.recorder.screenshot.event.ScreenshotExtensionConfigured;
import org.arquillian.extension.recorder.screenshot.event.TakeScreenshot;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.extension.screenRecorder.configuration.DesktopScreenshooterConfiguration;

/**
 * Takes screenshots of whole desktop.
 *
 * @author <a href="mailto:pmensik@redhat.com">Petr Mensik</a>
 */
public class DesktopScreenshotTaker {

    @Inject
    private Instance<ScreenshooterConfiguration> conf;
    
    @Inject
    private Instance<Screenshooter> screenshooter;
    
    private DesktopScreenshooterConfiguration configuration;
    
    private File root;
    
    private ScreenshotType screenshotType;

    /**
     *
     * Observes {@link ScreenshotExtensionConfigured} event and creates directory where the screenshots are stored upon it
     *
     * @param event
     */
    public void onRecorderConfigured(@Observes ScreenshotExtensionConfigured event) {
        configuration = (DesktopScreenshooterConfiguration) conf.get();
        root = new File(configuration.getRootFolder(), configuration.getScreenshotBaseFolder());
        screenshotType = ScreenshotType.valueOf(configuration.getScreenshotType().toUpperCase());
        screenshooter.get().setScreensthotType(screenshotType);
        screenshooter.get().setScreenshotTargetDir(root);
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
        
        File screenshotFolder = new File(event.getMetaData().getTestClassName(), fileName);
        Screenshot screenshot = screenshooter.get().takeScreenshot(screenshotFolder);
        screenshot.setResource(new File(fileName));
        screenshot.setResourceMetaData(event.getMetaData());
    }
}
