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

import org.arquillian.extension.recorder.video.api.VideoConfiguration;
import org.arquillian.extension.recorder.video.api.VideoRecorderStrategy;
import org.jboss.arquillian.core.spi.Validate;
import org.jboss.arquillian.core.spi.event.Event;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;

/**
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class DefaultVideoRecorderStrategy implements VideoRecorderStrategy {

    private VideoConfiguration configuration;

    public DefaultVideoRecorderStrategy(VideoConfiguration configuration) {
        Validate.notNull(configuration, "Video configuration can not be a null object!");
        this.configuration = configuration;
    }

    @Override
    public boolean isTakingAction(Event event, TestResult result) {
        if (event instanceof After) {
            switch (result.getStatus()) {
                case SKIPPED:
                    return false;
                default:
                    break;
            }
            return Boolean.parseBoolean(configuration.getStartBeforeTest());
        }
        return false;
    }

    @Override
    public boolean isTakingAction(Event event) {
        if (event instanceof BeforeSuite) {
            return Boolean.parseBoolean(configuration.getStartBeforeSuite());
        } else if (event instanceof BeforeClass) {
            return Boolean.parseBoolean(configuration.getStartBeforeClass());
        } else if (event instanceof Before) {
            return Boolean.parseBoolean(configuration.getStartBeforeTest());
        } else if (event instanceof AfterSuite) {
            return Boolean.parseBoolean(configuration.getStartBeforeSuite());
        } else if (event instanceof AfterClass) {
            return Boolean.parseBoolean(configuration.getStartBeforeClass());
        }

        return false;
    }
}
