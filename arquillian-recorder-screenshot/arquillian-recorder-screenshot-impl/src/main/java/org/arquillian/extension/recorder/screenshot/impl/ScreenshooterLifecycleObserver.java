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
package org.arquillian.extension.recorder.screenshot.impl;

import org.arquillian.extension.recorder.screenshot.ScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshot.ScreenshootingStrategy;
import org.arquillian.extension.recorder.screenshot.ScreenshotMetaData;
import org.arquillian.extension.recorder.screenshot.ScreenshotType;
import org.arquillian.extension.recorder.screenshot.event.AfterScreenshotTaken;
import org.arquillian.extension.recorder.screenshot.event.BeforeScreenshotTaken;
import org.arquillian.extension.recorder.screenshot.event.TakeScreenshot;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.TestLifecycleEvent;

/**
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class ScreenshooterLifecycleObserver {

    @Inject
    private Instance<ScreenshootingStrategy> strategy;

    @Inject
    private Instance<ScreenshooterConfiguration> configuration;

    @Inject
    private Event<BeforeScreenshotTaken> beforeScreenshotTaken;

    @Inject
    private Event<TakeScreenshot> takeScreenshot;

    @Inject
    private Event<AfterScreenshotTaken> afterScreenshotTaken;

    @Inject
    private Instance<TestResult> testResult;

    public void beforeTest(@Observes Before event) {
        if (strategy.get().isTakingAction(event)) {

            ScreenshotType screenshotType = getScreenshotType();
            ScreenshotMetaData metaData = getMetaData(event);

            beforeScreenshotTaken.fire(new BeforeScreenshotTaken(screenshotType, metaData));

            takeScreenshot.fire(new TakeScreenshot(screenshotType, metaData));

            afterScreenshotTaken.fire(new AfterScreenshotTaken(screenshotType, metaData));
        }
    }

    public void afterTest(@Observes After event) {
        if (strategy.get().isTakingAction(event, testResult.get())) {
            ScreenshotType screenshotType = getScreenshotType();
            ScreenshotMetaData metaData = getMetaData(event);

            beforeScreenshotTaken.fire(new BeforeScreenshotTaken(screenshotType, metaData));

            takeScreenshot.fire(new TakeScreenshot(screenshotType, metaData));

            afterScreenshotTaken.fire(new AfterScreenshotTaken(screenshotType, metaData));

        }
    }

    private ScreenshotMetaData getMetaData(TestLifecycleEvent event) {
        ScreenshotMetaData metaData = new ScreenshotMetaData();

        metaData.setTestClass(event.getTestClass());
        metaData.setTestMethod(event.getTestMethod());
        metaData.setTimeStamp(System.currentTimeMillis());

        return metaData;
    }

    private ScreenshotType getScreenshotType() {
        return ScreenshotType.valueOf(ScreenshotType.class, configuration.get().getScreenshotType().toUpperCase());
    }

}
