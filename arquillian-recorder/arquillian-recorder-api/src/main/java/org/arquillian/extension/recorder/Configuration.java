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

import java.util.HashMap;
import java.util.Map;

import org.jboss.arquillian.core.spi.Validate;

/**
 * The base class for all configurations for all implemented extensions.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public abstract class Configuration<T extends Configuration<T>> {

    private Map<String, String> configuration = new HashMap<String, String>();

    /**
     * Gets configuration from Arquillian descriptor and creates instance of it.
     *
     * @param properties properties of extension from arquillian.xml
     * @return configuration of screenshooter extension
     * @throws if {@code configuration} is a null object
     */
    @SuppressWarnings("unchecked")
    public T setConfiguration(Map<String, String> configuration) {
        Validate.notNull(configuration, "Properties for configuration of recorder extension can not be a null object.");
        this.configuration = configuration;
        return (T) this;
    }

    public Map<String, String> getConfiguration() {
        return this.configuration;
    }

    /**
     * Gets value of {@code name} property. In case a value for such name does not exist or is null or empty string,
     * {@code defaultValue} is returned.
     *
     * @param name name of property you want to get a value of
     * @param defaultValue value returned in case {@code name} is a null string or it is empty
     * @return value of a {@code name} property
     * @throws IllegalArgumentException if either arguments are null or empty strings
     */
    public String getProperty(String name, String defaultValue) throws IllegalStateException {
        Validate.notNullOrEmpty(name, "unable to get configuration value of null configuration key");
        Validate.notNullOrEmpty(defaultValue, "unable to set configuration value of " + name + " to null");

        String found = getConfiguration().get(name);
        if (found == null || found.isEmpty()) {
            return defaultValue;
        } else {
            return found;
        }
    }

    /**
     * Validates configuration.
     *
     * @throws ScreenshooterConfigurationException when configuration is not valid
     */
    public abstract void validate() throws RecorderConfigurationException;
}
