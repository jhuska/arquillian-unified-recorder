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
package org.arquillian.extension.recorder;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;

/**
 * Configures any extension.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public abstract class RecorderConfigurator<T extends Configuration<T>> {

    /**
     * When not shadowed in extended classes, an extension will be identified by this name.
     */
    public static final String EXTENSION_NAME = "recorder";

    /**
     * Observes {@link ArquillianDescriptor} event in order to configure some extension implementation.
     *
     * @param descriptor
     * @throws RecorderConfigurationException
     */
    public abstract void configureExtension(ArquillianDescriptor descriptor);

    /**
     * Validates configuration given by user from arquillian.xml. It is up to configurator to call this method. It is
     * recommanded to call this method in {@link #configureExtension(ArquillianDescriptor)}.
     *
     * @param configuration configuration for this particular extension
     * @throws RecorderConfigurationException when validation of some extension is not valid
     */
    public abstract void validate(T configuration) throws RecorderConfigurationException;
}
