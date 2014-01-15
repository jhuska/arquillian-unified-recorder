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

package org.arquillian.extension.videoRecorder.configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.arquillian.extension.recorder.RecorderConfigurationException;
import org.arquillian.extension.recorder.video.VideoConfiguration;
import org.arquillian.extension.recorder.video.VideoConfigurator;
import org.arquillian.extension.recorder.video.event.VideoExtensionConfigured;
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
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 */
public class DesktopVideoRecorderConfigurator extends VideoConfigurator {

    private static final Logger logger = Logger.getLogger(DesktopVideoRecorderConfigurator.class.getSimpleName());

    @Inject
    @ApplicationScoped
    private InstanceProducer<VideoConfiguration> configuration;

    @Inject
    private Event<VideoExtensionConfigured> extensionConfiguredEvent;

    @Override
    public void configureExtension(@Observes ArquillianDescriptor descriptor) {

        VideoConfiguration configuration = new VideoConfiguration();

        for (ExtensionDef extension : descriptor.getExtensions()) {
            if (extension.getExtensionName().equals(EXTENSION_NAME)) {
                configuration.setConfiguration(extension.getExtensionProperties());
                validate(configuration);
                break;
            }
        }

        this.configuration.set(configuration);
        extensionConfiguredEvent.fire(new VideoExtensionConfigured());
    }

    @Override
    public void validate(VideoConfiguration configuration) throws RecorderConfigurationException {
        configuration.validate();

        if ((configuration.getStartBeforeClass() || configuration.getStartBeforeSuite())
            && configuration.getStartBeforeTest()) {
            configuration.setProperty("startBeforeTest", "false");
            logger.log(Level.INFO, "You have set both startBeforeTest and startBeforeSuite or startBeforeClass to true - "
                + "startBeforeTest was set to false.");
        }

        if (configuration.getStartBeforeSuite() && configuration.getStartBeforeClass()) {
            logger.log(Level.INFO, "You have set startBeforeSuite and startBeforeClass both to true. You can not set them "
                + "simultaneously to true - startBeforeSuite has precedence so startBeforeClass was set to true.");
            configuration.setProperty("startBeforeClass", "false");
        }

        if (configuration.getTakeOnlyOnFail() && configuration.getStartBeforeTest()) {
            configuration.setProperty("startBeforeTest", "false");
            logger.log(Level.INFO, "You have set takeOnlyOnFail to true as well as startBeforeTest to true. You can not set "
                + "both to true - startBeforeTest was set to false. Videos of all tests will be recorded automatically "
                + "and some video of test will be preserved only if test fails.");
        }

        if ((configuration.getStartBeforeClass() || configuration.getStartBeforeSuite() || configuration.getStartBeforeTest())
            && configuration.getTakeOnlyOnFail()) {

            configuration.setProperty("startBeforeClass", "false");
            configuration.setProperty("startBeforeSuite", "false");
            configuration.setProperty("startBeforeTest", "false");

            logger.log(Level.INFO, "You have set one of startBeforeClass, startBeforeSuite or startBeforeTest in connection "
                + "with takeOnlyOnFail. All start* properties were set to false so every @Test will be recorded and "
                + "preserved only in case it has failed");
        }

        if (logger.isLoggable(Level.INFO)) {
            System.out.println("Configuration of Arquillian Desktop Video Recorder:");
            System.out.println(configuration.toString());
        }
    }

}
