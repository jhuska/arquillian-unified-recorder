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
package org.arquillian.extension.recorder.video.impl;

import org.arquillian.extension.recorder.video.VideoConfiguration;
import org.arquillian.extension.recorder.video.VideoMetaData;
import org.arquillian.extension.recorder.video.VideoStrategy;
import org.arquillian.extension.recorder.video.VideoType;
import org.arquillian.extension.recorder.video.event.AfterVideoStart;
import org.arquillian.extension.recorder.video.event.AfterVideoStop;
import org.arquillian.extension.recorder.video.event.BeforeVideoStart;
import org.arquillian.extension.recorder.video.event.BeforeVideoStop;
import org.arquillian.extension.recorder.video.event.StartRecordVideo;
import org.arquillian.extension.recorder.video.event.StopRecordVideo;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.jboss.arquillian.test.spi.event.suite.ClassLifecycleEvent;
import org.jboss.arquillian.test.spi.event.suite.TestLifecycleEvent;

/**
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class VideoRecorderLifecycleObserver {

    @Inject
    private Instance<VideoConfiguration> configuration;

    @Inject
    private Instance<VideoStrategy> strategy;

    @Inject
    private Event<BeforeVideoStart> beforeVideoStart;

    @Inject
    private Event<AfterVideoStart> afterVideoStart;

    @Inject
    private Event<BeforeVideoStop> beforeVideoStop;

    @Inject
    private Event<AfterVideoStop> afterVideoStop;

    @Inject
    private Event<StartRecordVideo> startRecordVideo;

    @Inject
    private Event<StopRecordVideo> stopRecordVideo;

    public void beforeSuite(@Observes BeforeSuite event) {
        if (strategy.get().isTakingAction(event)) {
            beforeVideoStart.fire(new BeforeVideoStart(getVideoType(), getSuiteMetaData(event)));

            startRecordVideo.fire(new StartRecordVideo());

            afterVideoStart.fire(new AfterVideoStart());
        }
    }

    public void beforeClass(@Observes BeforeClass event) {
        if (strategy.get().isTakingAction(event)) {
            beforeVideoStart.fire(new BeforeVideoStart(getVideoType(), getClassMetaData(event)));

            startRecordVideo.fire(new StartRecordVideo());

            afterVideoStart.fire(new AfterVideoStart());
        }
    }

    public void beforeTest(@Observes Before event) {
        if (strategy.get().isTakingAction(event)) {
            beforeVideoStart.fire(new BeforeVideoStart(getVideoType(), getMetaData(event)));

            startRecordVideo.fire(new StartRecordVideo());

            afterVideoStart.fire(new AfterVideoStart());
        }
    }

    public void afterTest(@Observes After event) {
        if (strategy.get().isTakingAction(event)) {
            beforeVideoStop.fire(new BeforeVideoStop());
            stopRecordVideo.fire(new StopRecordVideo());
            afterVideoStop.fire(new AfterVideoStop());
        }
    }

    public void afterClass(@Observes AfterClass event) {
        if (strategy.get().isTakingAction(event)) {
            beforeVideoStop.fire(new BeforeVideoStop());
            stopRecordVideo.fire(new StopRecordVideo());
            afterVideoStop.fire(new AfterVideoStop());
        }
    }

    public void afterSuite(@Observes AfterSuite event) {
        if (strategy.get().isTakingAction(event)) {
            beforeVideoStop.fire(new BeforeVideoStop());
            stopRecordVideo.fire(new StopRecordVideo());
            afterVideoStop.fire(new AfterVideoStop());
        }
    }

    private VideoMetaData getSuiteMetaData(BeforeSuite event) {
        VideoMetaData metaData = new VideoMetaData();
        metaData.setTimeStamp(System.currentTimeMillis());
        return metaData;
    }

    private VideoMetaData getMetaData(TestLifecycleEvent event) {
        VideoMetaData metaData = new VideoMetaData();

        metaData.setTestClassName(event.getTestClass().getName());
        metaData.setTestMethodName(event.getTestMethod().getName());
        metaData.setTimeStamp(System.currentTimeMillis());

        return metaData;
    }

    private VideoMetaData getClassMetaData(ClassLifecycleEvent event) {
        VideoMetaData metaData = new VideoMetaData();

        metaData.setTestClassName(event.getTestClass().getName());
        metaData.setTimeStamp(System.currentTimeMillis());

        return metaData;
    }

    private VideoType getVideoType() {
        return VideoType.valueOf(VideoType.class, configuration.get().getVideoType().toUpperCase());
    }
}
