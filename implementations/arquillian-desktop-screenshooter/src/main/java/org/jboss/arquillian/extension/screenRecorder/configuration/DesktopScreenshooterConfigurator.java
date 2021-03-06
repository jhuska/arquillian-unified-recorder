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

package org.jboss.arquillian.extension.screenRecorder.configuration;

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
 *
 * @author <a href="mailto:pmensik@redhat.com">Petr Mensik</a>
 */
public class DesktopScreenshooterConfigurator extends ScreenshooterConfigurator {

    @Inject
    @ApplicationScoped
    private InstanceProducer<ScreenshooterConfiguration> configuration;

    @Inject
    private Event<ScreenshotExtensionConfigured> extensionConfiguredEvent;

    @Override
    public void configureExtension(@Observes ArquillianDescriptor descriptor) {
        ScreenshooterConfiguration conf = new ScreenshooterConfiguration();
        for (ExtensionDef extension : descriptor.getExtensions()) {
            if (extension.getExtensionName().equals(EXTENSION_NAME)) {
                conf.setConfiguration(extension.getExtensionProperties());
                validate(conf);
                break;
            }
        }
        configuration.set(conf);
        extensionConfiguredEvent.fire(new ScreenshotExtensionConfigured());
    }

    @Override
    public void validate(ScreenshooterConfiguration configuration) throws ScreenshooterConfigurationException {
        configuration.validate();
    }

}
