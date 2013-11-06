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
package org.arquillian.extension.recorder.droidium.impl;

import org.arquillian.extension.recorder.droidium.configuration.DroidiumScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshot.Screenshooter;
import org.arquillian.extension.recorder.screenshot.event.ScreenshotExtensionConfigured;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;

/**
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class DroidiumScreenshooterCreator {

    @SuppressWarnings("rawtypes")
    @Inject
    private InstanceProducer<Screenshooter> screenshooter;

    public void onScreenshooterExtensionConfigured(@Observes ScreenshotExtensionConfigured event) {


        Screenshooter<DroidiumScreenshot, DroidiumScreenshooterConfiguration> screenshooter = new DroidiumScreenshooter();

        this.screenshooter.set(screenshooter);
    }
}
