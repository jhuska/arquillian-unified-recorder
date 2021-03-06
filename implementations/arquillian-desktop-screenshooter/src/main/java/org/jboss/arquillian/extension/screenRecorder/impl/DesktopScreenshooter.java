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
import javax.imageio.ImageIO;

import org.arquillian.extension.recorder.DefaultFileNameBuilder;
import org.arquillian.extension.recorder.screenshot.Screenshooter;
import org.arquillian.extension.recorder.screenshot.ScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshot.Screenshot;
import org.arquillian.extension.recorder.screenshot.ScreenshotType;
import org.jboss.arquillian.core.spi.Validate;

/**
 * Takes screenshots of the whole desktop.
 *
 * @author <a href="mailto:pmensik@redhat.com">Petr Mensik</a>
 */
public class DesktopScreenshooter implements Screenshooter {

    private File screenshotTargetDir = new File("target" + System.getProperty("file.separator"));

    private File root;

    private ScreenshotType screenshotType = ScreenshotType.PNG;

    private ScreenshooterConfiguration configuration;

    private DefaultFileNameBuilder idGenerator = new DefaultFileNameBuilder();

    @Override
    public void init(ScreenshooterConfiguration configuration) {
        if (this.configuration == null) {
            if (configuration != null) {
                this.configuration = configuration;

                root = new File(this.configuration.getRootFolder(), this.configuration.getScreenshotBaseFolder());

                if (!root.exists()) {
                    if (!root.mkdirs()) {
                        throw new RuntimeException("Unable to create root directory.");
                    }
                }

                setScreenshotTargetDir(root);

                setScreensthotType(ScreenshotType.valueOf(this.configuration.getScreenshotType().toUpperCase()));
            }
        }
    }

    @Override
    public Screenshot takeScreenshot() {
        return takeScreenshot(screenshotType);
    }

    @Override
    public Screenshot takeScreenshot(ScreenshotType type) {
        Validate.notNull(type, "Screenshot type is a null object!");
        return takeScreenshot(new File(idGenerator.withFileType(type).build()), type);
    }

    @Override
    public Screenshot takeScreenshot(String fileName) {
        Validate.notNullOrEmpty(fileName, "File name is a null object or an empty string!");
        return takeScreenshot(fileName, screenshotType);
    }

    @Override
    public Screenshot takeScreenshot(File file) {
        Validate.notNull(file, "File is a null object!");
        return takeScreenshot(file, screenshotType);
    }

    @Override
    public Screenshot takeScreenshot(String fileName, ScreenshotType type) {
        Validate.notNullOrEmpty(fileName, "File name is a null object or an empty string!");
        Validate.notNull(type, "Type of screenshot is a null object!");
        return takeScreenshot(new File(fileName), type);
    }

    @Override
    public Screenshot takeScreenshot(File file, ScreenshotType type) {

        if (configuration == null) {
            throw new IllegalStateException("Screenshooter was not initialized. Please call init() method first.");
        }

        file = new File(screenshotTargetDir, file.getPath());
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException("Unable to create screenshot directory " + file.getAbsolutePath());
            }
        }

        Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            BufferedImage image = new Robot().createScreenCapture(screenSize);
            ImageIO.write(image, screenshotType.toString(), file);
        } catch (AWTException ex) {
            throw new RuntimeException("Could not take a screenshot because of java.awt.Robot creation failure {0}", ex);
        } catch (IOException ex) {
            throw new RuntimeException("Could not write an image", ex);
        }

        Screenshot screenshoot = new DesktopScreenshot();
        screenshoot.setResource(file);
        screenshoot.setResourceType(screenshotType);

        return screenshoot;
    }

    @Override
    public void setScreenshotTargetDir(String screenshotTargetDir) {
        Validate.notNullOrEmpty(screenshotTargetDir, "Screenshot target directory can not be a null object or an empty string");
        this.screenshotTargetDir = new File(screenshotTargetDir);
    }

    @Override
    public void setScreenshotTargetDir(File screenshotTargetDir) {
        Validate.notNull(screenshotType, "File is a null object!");
        this.screenshotTargetDir = screenshotTargetDir;
    }

    @Override
    public void setScreensthotType(ScreenshotType type) {
        Validate.notNull(screenshotType, "Screenshot type is a null object!");
        this.screenshotType = type;
    }

}
