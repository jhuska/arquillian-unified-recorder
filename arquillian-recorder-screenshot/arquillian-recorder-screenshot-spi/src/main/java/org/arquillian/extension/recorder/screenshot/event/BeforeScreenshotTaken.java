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

import org.arquillian.extension.recorder.screenshot.ScreenshotMetaData;
import org.arquillian.extension.recorder.screenshot.ScreenshotType;
import org.jboss.arquillian.core.spi.Validate;

/**
 * This event is fired before some screenshot it taken.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class BeforeScreenshotTaken {

    private ScreenshotType screenshotType;

    private ScreenshotMetaData metaData;

    public BeforeScreenshotTaken(ScreenshotType screenshotType, ScreenshotMetaData metaData) {
        Validate.notNull(screenshotType, "Screenshot type is a null object!");
        Validate.notNull(metaData, "Meta data is a null object!");

        this.screenshotType = screenshotType;
        this.metaData = metaData;
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

}
