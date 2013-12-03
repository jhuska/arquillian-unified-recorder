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

import org.arquillian.extension.recorder.AbstractFileNameBuilder;
import org.arquillian.extension.recorder.When;

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class ScreenshotFileNameBuilder extends AbstractFileNameBuilder {

    private ScreenshotMetaData metaData = null;

    private ScreenshotType screenshotType = null;

    private When when = null;

    public ScreenshotFileNameBuilder withMetaData(ScreenshotMetaData metaData) {
        this.metaData = metaData;
        return this;
    }

    public ScreenshotFileNameBuilder withStage(When when) {
        this.when = when;
        return this;
    }

    public ScreenshotFileNameBuilder withFileType(ScreenshotType screenshotType) {
        this.screenshotType = screenshotType;
        return this;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();

        if (metaData == null || metaData.getTestMethodName() == null) {
            sb.append("noNameMethod");
        } else {
            sb.append(metaData.getTestMethodName());
        }

        sb.append("_");

        if (when != null) {
            sb.append(when.toString());
        }

        if (screenshotType != null) {
            sb.append(".");
            sb.append(screenshotType.toString());
        }

        return sb.toString();
    }

}
