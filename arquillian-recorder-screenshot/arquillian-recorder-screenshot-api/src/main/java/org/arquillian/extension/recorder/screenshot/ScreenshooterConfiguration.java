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
package org.arquillian.extension.recorder.screenshot;

import java.io.File;

import org.arquillian.extension.recorder.Configuration;
import org.arquillian.extension.recorder.ResourceType;

/**
 * Screenshooter configuration for every screenshooter extension implementation.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public abstract class ScreenshooterConfiguration extends Configuration<ScreenshooterConfiguration> {

    private String rootFolder = "target";

    private String screenshoType = ScreenshotType.PNG.toString();

    private String takeBeforeTest = "false";

    private String takeAfterTest = "false";

    private String takeWhenTestFailed = "true";

    private String takeWhenTestPassed = "false";

    /**
     * By default set to "target"
     *
     * @return root folder where all screenshots will be placed. Directory structure is left on the extension itself
     */
    public File getRootFolder() {
        return new File(getProperty("rootFolder", rootFolder));
    }

    /**
     * By default set to PNG.
     *
     * @return type of image we want to have our screenshots of, consult {@link ScreenshotType}
     */
    public String getScreenshotType() {
        return getProperty("screenshoType", screenshoType);
    }

    /**
     * By default set to "false".
     *
     * @return "true" if screenshot should be taken before test, "false" otherwise
     */
    public String getTakeBeforeTest() {
        return getProperty("takeBeforeTest", takeBeforeTest);
    }

    /**
     * By default set to "false".
     *
     * @return "true" if screenshot should be taken after test, "false" otherwise
     */
    public String getTakeAfterTest() {
        return getProperty("takeAfterTest", takeAfterTest);
    }

    /**
     * By default set to "false".
     *
     * @return "true" if screenshot should be taken when test failed, "false" otherwise
     */
    public String getTakeWhenTestFailed() {
        return getProperty("takeWhenTestFailed", takeWhenTestFailed);
    }

    /**
     * By default set to "false".
     *
     * @return "true" if screenshot should be taken when test passes, "false" otherwise
     */
    public String getTakeWhenTestPassed() {
        return getProperty("takeWhenTestPassed", takeWhenTestPassed);
    }

    @Override
    public void validate() throws ScreenshooterConfigurationException {

        try {
            ScreenshotType.valueOf(ScreenshotType.class, getScreenshotType().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ScreenshooterConfigurationException(
                "Screenshot type you specified in arquillian.xml is not valid screenshot type."
                    + "Supported screenshot types are: " + ScreenshotType.getAll());
        }

        try {
            if (!getRootFolder().exists()) {
                getRootFolder().mkdir();
            } else {
                if (!getRootFolder().isDirectory()) {
                    throw new ScreenshooterConfigurationException("Root directory you specified is not a directory.");
                }
                if (!getRootFolder().canWrite()) {
                    throw new ScreenshooterConfigurationException(
                        "You can not write to '" + getRootFolder().getAbsolutePath() + "'.");
                }
            }
        } catch (SecurityException ex) {
            throw new ScreenshooterConfigurationException(
                "You are not permitted to operate on specified resource: " + getRootFolder().getAbsolutePath() + "'.");
        }
    }

}
