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

import org.arquillian.droidium.container.api.AndroidDevice;
import org.arquillian.extension.recorder.droidium.configuration.DroidiumScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshot.Screenshooter;
import org.arquillian.extension.recorder.screenshot.ScreenshotType;
import org.jboss.arquillian.core.spi.Validate;

/**
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class DroidiumScreenshooter implements Screenshooter<DroidiumScreenshot, DroidiumScreenshooterConfiguration> {

    private DroidiumScreenshooterConfiguration configuration;

    private AndroidDevice androidDevice;

    public void setDevice(AndroidDevice androidDevice) {
        Validate.notNull(androidDevice, "Android device you are trying to set for DroidiumScreenshooter is a null object!");
        this.androidDevice = androidDevice;
    }

    @Override
    public void setConfiguration(DroidiumScreenshooterConfiguration configuration) {
        Validate.notNull(configuration, "Droidium screenshooter configuration for DroidiumScreenshooter implementation "
            + "is a null object!");
        this.configuration = configuration;
    }

    @Override
    public Class<DroidiumScreenshooterConfiguration> getConfigurationClass() {
        return DroidiumScreenshooterConfiguration.class;
    }

    @Override
    public DroidiumScreenshot takeScreenshot() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DroidiumScreenshot takeScreenshot(ScreenshotType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DroidiumScreenshot takeScreenshot(String fileName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DroidiumScreenshot takeScreenshot(String fileName, ScreenshotType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setScreenshotTargetDir(String screenshotTargetDir) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setScreensthotType(ScreenshotType type) {
        // TODO Auto-generated method stub

    }

}
