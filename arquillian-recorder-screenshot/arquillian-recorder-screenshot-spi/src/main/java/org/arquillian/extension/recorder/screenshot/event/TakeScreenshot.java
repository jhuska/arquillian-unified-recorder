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
package org.arquillian.extension.recorder.screenshot.event;

import org.arquillian.extension.recorder.screenshot.Screenshot;
import org.arquillian.extension.recorder.screenshot.ScreenshotMetaData;
import org.arquillian.extension.recorder.screenshot.ScreenshotType;
import org.jboss.arquillian.core.spi.Validate;

/**
 * Event fired when we want to take a screenshot. There should be observer on the implementation side which listens to this kind
 * of event and when observed, a screenshot is taken in desired way, significant for any particular screenshooter extension.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class TakeScreenshot {

    private When when;

    private Screenshot screenshot;

    private ScreenshotType screenshotType;

    private ScreenshotMetaData metaData;

    public TakeScreenshot(ScreenshotType screenshotType, ScreenshotMetaData metaData, When when) {
        Validate.notNull(screenshotType, "Screenshot type is a null object!");
        Validate.notNull(metaData, "Meta data is a null object!");
        Validate.notNull(when, "When is a null object!");
        this.screenshotType = screenshotType;
        this.metaData = metaData;
        this.when = when;
    }

    public Screenshot getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(Screenshot screenshot) {
        this.screenshot = screenshot;
    }

    public ScreenshotType getScreenshotType() {
        return screenshotType;
    }

    public void setScreenshotType(ScreenshotType screenshotType) {
        Validate.notNull(screenshotType, "Screenshot type is a null object!");
        this.screenshotType = screenshotType;
    }

    public ScreenshotMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(ScreenshotMetaData metaData) {
        Validate.notNull(metaData, "Meta data is a null object!");
        this.metaData = metaData;
    }

    public When getWhen() {
        return when;
    }

    public void setWhen(When when) {
        this.when = when;
    }
}
