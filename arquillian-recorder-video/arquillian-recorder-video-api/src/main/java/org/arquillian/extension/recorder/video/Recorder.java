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

/**
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public interface Recorder {

    /**
     *
     * @return configuration class of this recorder implementation
     */
    public <T extends VideoConfiguration> Class<T> getConfigurationClass();

    /**
     * Starts to record your test.
     */
    public void startRecording();

    /**
     * Starts to record your test.
     *
     * @param videoType type of video you want to get
     */
    public void startRecording(VideoType videoType);

    /**
     * Starts to record your test.
     *
     * @param fileName name of video you want to start to record
     */
    public void startRecording(String fileName);

    /**
     * Starts to record your test.
     *
     * @param file file where to save recorded video
     */
    public void startRecording(File file);

    /**
     * Starts to record your test.
     *
     * @param fileName name of video you want to start to record
     * @param videoType type of video you want to start to record
     */
    public void startRecording(String fileName, VideoType videoType);

    /**
     * Starts to record your test.
     *
     * @param file file where to save recorder video
     * @param videoType type of video you want to start to record
     */
    public void startRecording(File file, VideoType videoType);

    /**
     *
     * @param videoTargetDir name of directory you want to save all videos to
     */
    public void setVideoTargetDir(String videoTargetDir);

    /**
     *
     * @param videoTargetDir directory you want to save all videos to
     */
    public void setVideoTargetDir(File videoTargetDir);

    /**
     *
     * @param videoType type of video you want all videos to be
     */
    public void setVideoType(VideoType videoType);

    /**
     *
     * Stops to record your video after you started it with {@link #startRecording()}
     *
     * @return recorded video
     */
    public <V extends Video> V stopRecording();
}
