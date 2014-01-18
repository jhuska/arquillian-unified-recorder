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
package org.arquillian.extension.recorder.screenshooter.browser.impl;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.arquillian.extension.recorder.DefaultFileNameBuilder;
import org.arquillian.extension.recorder.RecorderFileUtils;
import org.arquillian.extension.recorder.screenshooter.Screenshooter;
import org.arquillian.extension.recorder.screenshooter.ScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshooter.Screenshot;
import org.arquillian.extension.recorder.screenshooter.ScreenshotType;
import org.jboss.arquillian.core.spi.Validate;
import org.jboss.arquillian.drone.api.annotation.Default;
import org.jboss.arquillian.drone.webdriver.factory.remote.reusable.ReusableRemoteWebDriver;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.proxy.GrapheneProxyInstance;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 *
 */
public class BrowserScreenshooter implements Screenshooter {

    private File screenshotTargetDir = new File("target" + System.getProperty("file.separator"));
    private ScreenshotType screenshotType = ScreenshotType.PNG;
    private TakeScreenshotOnEveryActionInterceptor takeScreenshotOnEveryActionInterceptor;
    private TakeScreenshotBeforeTestInterceptor takeScreenshotBeforeTestInterceptor;
    private DefaultFileNameBuilder idGenerator = new DefaultFileNameBuilder();
    private ScreenshooterConfiguration configuration;

    public void setTakeScreenshoOnEveryActionInterceptor(
            TakeScreenshotOnEveryActionInterceptor takeScreenshotOnEveryActionInterceptor) {
        this.takeScreenshotOnEveryActionInterceptor = takeScreenshotOnEveryActionInterceptor;
    }

    public void setTakeScreenshotBeforeTestInterceptor(TakeScreenshotBeforeTestInterceptor takeScreenshotBeforeTestInterceptor) {
        this.takeScreenshotBeforeTestInterceptor = takeScreenshotBeforeTestInterceptor;
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
        return takeScreenshot(new File(fileName));
    }

    @Override
    public Screenshot takeScreenshot(File file) {
        Validate.notNull(file, "File is a null object!");
        return takeScreenshot(file, screenshotType);
    }

    private File getJustTargetDir(File screenshotToTake) {
        return new File(screenshotToTake.getPath().substring(0,
                screenshotToTake.getPath().lastIndexOf(System.getProperty("file.separator"))));
    }

    @Override
    public Screenshot takeScreenshot(String fileName, ScreenshotType type) {
        Validate.notNullOrEmpty(fileName, "File name is a null object or an empty string!");
        Validate.notNull(type, "Type of screenshot is a null object!");
        return takeScreenshot(new File(fileName), type);
    }

    @Override
    public Screenshot takeScreenshot(File screenshotToTake, ScreenshotType type) {
        ifNecessaryUnregisterInterceptors();

        WebDriver browser = getTakingScreenshotsBrowser();

        screenshotToTake = new File(screenshotTargetDir, screenshotToTake.getPath());
        File targetDir = getJustTargetDir(screenshotToTake);
        RecorderFileUtils.createDirectory(targetDir);
        try {
            FileUtils.copyFile(((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE), screenshotToTake);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ifNecessaryRegisterInterceptorsBack();

        Screenshot screenshoot = new BrowserScreenshot();
        screenshoot.setResource(screenshotToTake);
        return screenshoot;
    }

    private WebDriver getTakingScreenshotsBrowser() {
        WebDriver result = ((GrapheneProxyInstance) (GrapheneContext.getContextFor(Default.class)
                .getWebDriver(TakesScreenshot.class))).unwrap();
        if (result instanceof ReusableRemoteWebDriver) {
            result = new Augmenter().augment(result);
        }
        return result;
    }

    private WebDriver getBrowser() {
        return GrapheneContext.getContextFor(Default.class).getWebDriver();
    }

    private void ifNecessaryUnregisterInterceptors() {
        if (takeScreenshotOnEveryActionInterceptor != null) {
            takeScreenshotOnEveryActionInterceptor.unregisterThis(getBrowser());
        } else if (takeScreenshotBeforeTestInterceptor != null) {
            takeScreenshotBeforeTestInterceptor.unregisterThis(getBrowser());
        }
    }

    private void ifNecessaryRegisterInterceptorsBack() {

        if (takeScreenshotOnEveryActionInterceptor != null) {
            takeScreenshotOnEveryActionInterceptor.registerThis(getBrowser());
        } else if (takeScreenshotBeforeTestInterceptor != null) {
            takeScreenshotBeforeTestInterceptor.registerThis(getBrowser());
        }
    }

    @Override
    public void setScreenshotTargetDir(String screenshotTargetDir) {
        Validate.notNullOrEmpty(screenshotTargetDir, "Screenshot target directory can not be a null object or an empty string");
        setScreenshotTargetDir(new File(screenshotTargetDir));
    }

    @Override
    public void setScreenshotTargetDir(File screenshotTargetDir) {
        Validate.notNull(screenshotTargetDir, "File is a null object!");
        RecorderFileUtils.createDirectory(screenshotTargetDir);
        this.screenshotTargetDir = screenshotTargetDir;
    }

    @Override
    public void setScreensthotType(ScreenshotType screenshotType) {
        Validate.notNull(screenshotType, "Screenshot type is a null object!");
        this.screenshotType = screenshotType;
    }

    @Override
    public void init(ScreenshooterConfiguration configuration) {
        if (this.configuration == null) {
            if (configuration != null) {
                this.configuration = configuration;
                File root = new File(this.configuration.getRootFolder(), this.configuration.getBaseFolder());
                setScreenshotTargetDir(root);
                setScreensthotType(ScreenshotType.valueOf(this.configuration.getScreenshotType()));
            }
        }
    }

    @Override
    public ScreenshotType getScreenshotType() {
        return screenshotType;
    }
}