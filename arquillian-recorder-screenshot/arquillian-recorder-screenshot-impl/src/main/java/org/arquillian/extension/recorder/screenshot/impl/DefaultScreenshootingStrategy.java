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
import org.jboss.arquillian.core.spi.Validate;
import org.jboss.arquillian.core.spi.event.Event;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.Before;

/**
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class DefaultScreenshootingStrategy implements ScreenshootingStrategy {

    private ScreenshooterConfiguration configuration;

    @Override
    public void setConfiguration(ScreenshooterConfiguration configuration) {
        Validate.notNull(configuration, "Screenshooter configuration is a null object!");
        this.configuration = configuration;
    }

    @Override
    public boolean isTakingAction(Event event, TestResult result) {
        if (event instanceof After) {
            if(configuration.getTakeAfterTest()) {
                return true;
            }
            switch (result.getStatus()) {
                case FAILED:
                    return configuration.getTakeWhenTestFailed();
                case PASSED:
                    return configuration.getTakeWhenTestPassed();
                case SKIPPED:
                    return false;
                default:
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean isTakingAction(Event event) {
        if (event instanceof Before) {
            return configuration.getTakeBeforeTest();
        }
        return false;
    }
}
