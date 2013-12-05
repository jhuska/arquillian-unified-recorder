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

package org.jboss.arquillian.extension.videoRecorder.configuration;

import java.util.Map;
import org.arquillian.extension.recorder.RecorderConfigurationException;
import org.arquillian.extension.recorder.video.VideoConfiguration;
import org.arquillian.extension.recorder.video.VideoConfigurator;
import org.arquillian.extension.recorder.video.VideoType;
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
 */
public class DesktopVideoRecorderConfigurator extends VideoConfigurator {

    @Inject
    @ApplicationScoped
    private InstanceProducer<VideoConfiguration> configuration;
    
    @Inject
    private Event<VideoExtensionConfigured> extensionConfiguredEvent;
    
    @Override
    public void configureExtension(@Observes ArquillianDescriptor descriptor) {
        VideoConfiguration conf = new DesktopVideoRecorderConfiguration();
        for (ExtensionDef extension : descriptor.getExtensions()) {
            if (extension.getExtensionName().equals(EXTENSION_NAME)) {
                conf.setConfiguration(extension.getExtensionProperties());
                validate(conf);
                break;
            }
        }
        configuration.set(conf);
        extensionConfiguredEvent.fire(new VideoExtensionConfigured());
    }

    @Override
    public void validate(VideoConfiguration configuration) throws RecorderConfigurationException {
        configuration.validate();
        if(VideoType.valueOf(configuration.getVideoType()) != VideoType.AVI) {
            Map<String, String> conf = configuration.getConfiguration();
            conf.put("videoType", VideoType.AVI.toString());
            configuration.setConfiguration(conf);
        }
    }

}
