/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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

import java.io.File;

import org.arquillian.extension.recorder.DefaultFileNameBuilder;
import org.arquillian.extension.recorder.screenshot.Screenshooter;
import org.arquillian.extension.recorder.screenshot.Screenshot;
import org.arquillian.extension.recorder.screenshot.ScreenshotType;
import org.arquillian.extension.recorder.screenshot.event.TakeScreenshot;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;

/**
 *
 * @author <a href="mailto:pmensik@redhat.com">Petr Mensik</a>
 */
public class ScreenshotTaker {

    @Inject
    private Instance<Screenshooter> screenshooter;

    public void onTakeScreenshot(@Observes TakeScreenshot event) {

        ScreenshotType type = screenshooter.get().getScreenshotType();

        String fileName = new DefaultFileNameBuilder()
            .withMetaData(event.getMetaData())
            .withStage(event.getWhen())
            .withFileType(type)
            .build();

        File screenshotTarget = new File(event.getMetaData().getTestClassName(), fileName);

        Screenshot screenshot = screenshooter.get().takeScreenshot(screenshotTarget, type);
        screenshot.setResourceMetaData(event.getMetaData());

        // in case of reporting extension, we can fire event with taken screenshot
    }
}
