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
package org.arquillian.extension.recorder.droidium.configuration;

import org.arquillian.extension.recorder.screenshot.ScreenshooterConfiguration;
import org.arquillian.extension.recorder.screenshot.ScreenshooterConfigurationException;
import org.arquillian.extension.recorder.screenshot.ScreenshooterConfigurator;
import org.arquillian.extension.recorder.screenshot.event.ScreenshotExtensionConfigured;
import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.config.descriptor.api.ExtensionDef;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;

/**
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class DroidiumScreenshooterConfigurator extends ScreenshooterConfigurator {

    @Inject
    @ApplicationScoped
    private InstanceProducer<ScreenshooterConfiguration> configuration;

    @Inject
    private Event<ScreenshotExtensionConfigured> screenshotExtensionConfigured;

    @Override
    public void configureExtension(@Observes ArquillianDescriptor descriptor) {

        DroidiumScreenshooterConfiguration configuration = new DroidiumScreenshooterConfiguration();

        for (ExtensionDef extension : descriptor.getExtensions()) {
            if (extension.getExtensionName().equals(EXTENSION_NAME)) {
                configuration.setConfiguration(extension.getExtensionProperties());
                validate(configuration);
            }
        }

        this.configuration.set(configuration);

        screenshotExtensionConfigured.fire(new ScreenshotExtensionConfigured());
    }

    @Override
    public void validate(ScreenshooterConfiguration configuration) throws ScreenshooterConfigurationException {
        configuration.validate();
    }

}
