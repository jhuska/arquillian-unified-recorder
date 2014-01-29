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
package org.arquillian.extension.recorder.screenshooter.impl;

import java.io.File;

import org.arquillian.extension.recorder.screenshooter.Screenshooter;
import org.arquillian.extension.recorder.screenshooter.Screenshot;
import org.arquillian.extension.recorder.screenshooter.ScreenshotType;
import org.arquillian.extension.recorder.screenshooter.event.TakeScreenshot;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;

/**
 *
 * @author <a href="mailto:pmensik@redhat.com">Petr Mensik</a>
 */
public class ScreenshotTaker {

    @Inject
    private Instance<Screenshooter> screenshooter;

    @Inject
    private Instance<ServiceLoader> serviceLoader;

    public void onTakeScreenshot(@Observes TakeScreenshot event) {

        ScreenshotType type = screenshooter.get().getScreenshotType();

        File screenshotTarget = new File(event.getMetaData().getTestClassName() +
            System.getProperty("file.separator")
            + event.getMetaData().getTestMethodName(), event.getFileName());

        Screenshot screenshot = screenshooter.get().takeScreenshot(screenshotTarget, type);
        screenshot.setResourceMetaData(event.getMetaData());
    }
}
