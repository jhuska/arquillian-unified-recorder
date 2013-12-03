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
package org.arquillian.extension.recorder.video.event;

import org.arquillian.extension.recorder.video.VideoMetaData;
import org.arquillian.extension.recorder.video.VideoType;
import org.jboss.arquillian.core.spi.Validate;

/**
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class StopRecordVideo {

    private VideoMetaData metaData;
    private VideoType videoType;

    public StopRecordVideo(VideoMetaData metaData, VideoType videoType) {
        Validate.notNull(videoType, "VideoType is a null object!");
        Validate.notNull(metaData, "Meta data is a null object!");
        this.metaData = metaData;
        this.videoType = videoType;
    }

    public VideoMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(VideoMetaData metaData) {
        Validate.notNull(metaData, "Meta data is a null object!");
        this.metaData = metaData;
    }

    public VideoType getVideo() {
        return videoType;
    }

    public void setVideo(VideoType videoType) {
        Validate.notNull(videoType, "VideoType is a null object!");
        this.videoType = videoType;
    }
}
