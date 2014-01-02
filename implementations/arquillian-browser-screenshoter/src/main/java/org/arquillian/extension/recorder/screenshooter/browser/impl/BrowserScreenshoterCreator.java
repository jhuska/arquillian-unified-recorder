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
package org.arquillian.extension.recorder.screenshooter.browser.impl;

import org.arquillian.extension.recorder.screenshooter.Screenshooter;
import org.arquillian.extension.recorder.screenshooter.ScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshooter.event.ScreenshotExtensionConfigured;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.drone.api.annotation.Default;
import org.jboss.arquillian.graphene.context.GrapheneContext;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 *
 */
public class BrowserScreenshoterCreator {

    @Inject
    @ApplicationScoped
    private InstanceProducer<Screenshooter> screenshooter;

    @Inject
    @ApplicationScoped
    private InstanceProducer<TakeScreenshotOnEveryActionInterceptor> takeScreenshotOnEveryActionInterceptor;

    @Inject
    @ApplicationScoped
    private InstanceProducer<TakeScreenshotBeforeTestInterceptor> takeScreenshotBeforeTestInterceptor;
    
    @Inject
    @ApplicationScoped
    private InstanceProducer<GrapheneContext> grapheneContext;


    @Inject
    private Instance<ScreenshooterConfiguration> configuration;

    /**
     * Creates {@link Screenshooter} instance.
     * 
     * @param event
     */
    public void onScreenshooterExtensionConfigured(@Observes ScreenshotExtensionConfigured event) {
        TakeScreenshotOnEveryActionInterceptor takeScreenshotOnEveryActionInterceptor = new TakeScreenshotOnEveryActionInterceptor();
        TakeScreenshotBeforeTestInterceptor takeScreenshotBeforeTestInterceptor = new TakeScreenshotBeforeTestInterceptor();
        BrowserScreenshooter screenshooter = new BrowserScreenshooter();

        screenshooter.setScreenshotTargetDir(configuration.get().getRootFolder());
        if (configuration.get().getTakeOnEveryAction()) {
            screenshooter.setTakeScreenshoOnEveryActionInterceptor(takeScreenshotOnEveryActionInterceptor);
        } else if(configuration.get().getTakeBeforeTest()) {
            screenshooter.setTakeScreenshotBeforeTestInterceptor(takeScreenshotBeforeTestInterceptor);
        }

        this.grapheneContext.set(GrapheneContext.getContextFor(Default.class));
        this.takeScreenshotOnEveryActionInterceptor.set(takeScreenshotOnEveryActionInterceptor);
        this.takeScreenshotBeforeTestInterceptor.set(takeScreenshotBeforeTestInterceptor);
        this.screenshooter.set(screenshooter);
    }
}