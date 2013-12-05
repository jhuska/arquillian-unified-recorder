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
package org.arquillian.extension.recorder.video;

import java.io.File;

import org.arquillian.extension.recorder.Configuration;

/**
 * Video configuration for every recorder extension implementation.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public abstract class VideoConfiguration extends Configuration<VideoConfiguration> {

    private String rootFolder = "target";

    private String videoType = VideoType.AVI.toString();

    private String startBeforeTest = "false";

    private String startBeforeClass = "false";

    private String startBeforeSuite = "false";

    private String takeOnlyOnFail = "true";

    private String videoName = "record";

    private String testTimeout = "1800"; //30 minutes


    /**
     * By default set to true
     *
     * @return "true" if video should be taken only for the failed tests
     */
    public String getTakeOnlyOnFail() {
        return getProperty("takeOnlyOnFail", takeOnlyOnFail);
    }

    /**
     * By default set to 1800, i.e. 30 minutes
     *
     * @return timeout for each test in order to stop recording and save the video file if the test gets stucked
     */
    public int getTestTimeout() {
        return Integer.parseInt(getProperty("testTimeout", testTimeout));
    }
    /**
     * By default set to "record"
     *
     * @return video name which will be used in case you want to record whole test suite into one file.
     */
    public String getVideoName() {
        return getProperty("videoName", videoName);
    }

    /**
     * By default set to "target"
     *
     * @return root folder where all videos will be placed. Directory structure is left on the extension itself.
     */
    public File getRootFolder() {
        return new File(getProperty("rootFolder", rootFolder));
    }

    /**
     * By default set to "MPEG".
     *
     * @return type of video we want our screenshots to be of
     */
    public String getVideoType() {
        return getProperty("videoType", videoType);
    }

    /**
     * By default set to "false".
     *
     * @return "true" if video recording should start before test, "false" otherwise
     */
    public String getStartBeforeTest() {
        return getProperty("startBeforeTest", startBeforeTest);
    }

    /**
     * By default set to "false".
     *
     * @return "true" if video should be taken before class, "false" otherwise
     */
    public String getStartBeforeClass() {
        return getProperty("startBeforeClass", startBeforeClass);
    }

    /**
     * By default set to "false".
     *
     * @return "true" if screenshot should be taken before suite, "false" otherwise
     */
    public String getStartBeforeSuite() {
        return getProperty("startBeforeSuite", startBeforeSuite);
    }

    @Override
    public void validate() throws VideoConfigurationException {
        try {
            VideoType.valueOf(VideoType.class, getVideoType().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new VideoConfigurationException(
                "Video type you specified in arquillian.xml is not valid video type."
                    + "Supported video types are: " + VideoType.getAll());
        }
    }
}
