/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
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
package org.arquillian.extension.recorder.droidium.impl;

import java.io.File;

import org.arquillian.droidium.container.api.AndroidDevice;
import org.arquillian.extension.recorder.droidium.configuration.DroidiumScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshot.Screenshooter;
import org.arquillian.extension.recorder.screenshot.ScreenshotType;
import org.jboss.arquillian.core.spi.Validate;

/**
 * Takes screenshots for Android devices.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class DroidiumScreenshooter implements Screenshooter {

    private DroidiumScreenshooterConfiguration configuration;

    private File screenshotTargetDir = new File("target" + System.getProperty("file.separator"));

    private AndroidDevice device;

    private ScreenshotType screenshotType = ScreenshotType.PNG;

    private DroidiumScreenshotIdentifierGenerator idGenerator = new DroidiumScreenshotIdentifierGenerator();

    public void setConfigurationClass(DroidiumScreenshooterConfiguration configuration) {
        Validate.notNull(configuration, "Configuration is a null object!");
        this.configuration = configuration;
    }

    @Override
    public Class<DroidiumScreenshooterConfiguration> getConfigurationClass() {
        return DroidiumScreenshooterConfiguration.class;
    }

    @Override
    public DroidiumScreenshot takeScreenshot() {
        return takeScreenshot(screenshotType);
    }

    @Override
    public DroidiumScreenshot takeScreenshot(ScreenshotType type) {
        Validate.notNull(type, "Screenshot type is a null object!");
        return takeScreenshot(new File(idGenerator.getIdentifier(type)), type);
    }

    @Override
    public DroidiumScreenshot takeScreenshot(String fileName) {
        Validate.notNullOrEmpty(fileName, "File name is a null object or an empty string!");
        return takeScreenshot(new File(fileName));
    }

    @Override
    public DroidiumScreenshot takeScreenshot(File file) {
        Validate.notNull(file, "File is a null object!");
        return takeScreenshot(file, screenshotType);
    }

    @Override
    public DroidiumScreenshot takeScreenshot(String fileName, ScreenshotType type) {
        Validate.notNullOrEmpty(fileName, "File name is a null object or an empty string!");
        Validate.notNull(type, "Type of screenshot is a null object!");
        return takeScreenshot(new File(fileName), type);
    }

    @Override
    public DroidiumScreenshot takeScreenshot(File file, ScreenshotType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setScreenshotTargetDir(String screenshotTargetDir) {
        Validate.notNullOrEmpty(screenshotTargetDir, "Screenshot target dir is a null object or an empty string!");
        setScreenshotTargetDir(new File(screenshotTargetDir));
    }

    @Override
    public void setScreenshotTargetDir(File screenshotTargetDir) {
        Validate.notNull(screenshotTargetDir, "Screenshot target dir is a null object or an empty string!");
        this.screenshotTargetDir = screenshotTargetDir;
    }

    @Override
    public void setScreensthotType(ScreenshotType screenshotType) {
        Validate.notNull(screenshotType, "Screenshot type is a null object!");
        this.screenshotType = screenshotType;
    }

    public void setDevice(AndroidDevice device) {
        Validate.notNull(device, "Android device to set to take screenshots is a null object!");
        this.device = device;
    }

}
