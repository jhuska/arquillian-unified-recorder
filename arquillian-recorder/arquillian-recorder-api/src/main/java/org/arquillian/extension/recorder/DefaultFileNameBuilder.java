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

import java.util.UUID;

/**
 * Builds a file name of some screenshot resource.
 *
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class DefaultFileNameBuilder extends AbstractFileNameBuilder {

    private ResourceMetaData metaData = null;

    private When when = null;

    private ResourceType resourceType = null;

    private ResourceIdentifier<ResourceType> resourceIdentifier;

    public DefaultFileNameBuilder() {
        setDefaultFileIdentifier();
    }

    public DefaultFileNameBuilder withMetaData(ResourceMetaData metaData) {
        this.metaData = metaData;
        return this;
    }

    public DefaultFileNameBuilder withStage(When when) {
        this.when = when;
        return this;
    }

    public DefaultFileNameBuilder withFileType(ResourceType resourceType) {
        if (resourceType != null) {
            this.resourceType = resourceType;
        }
        return this;
    }

    /**
     * Sets resource identifier for this file builder, suppresses the behavior of standard file name generation as specified in
     * {@link #build()} method.
     *
     * @param resourceIdentifier
     * @return this
     */
    public DefaultFileNameBuilder withResourceIdentifier(ResourceIdentifier<ResourceType> resourceIdentifier) {
        if (resourceIdentifier != null) {
            this.resourceIdentifier = resourceIdentifier;
        }
        return this;
    }

    /**
     * When generation of file name is not overridden by {@link #withResourceIdentifier(ResourceIdentifier)}, by default, every
     * file name will be of format: <br>
     * <br>
     * testMethodName_stage.screenshotType <br>
     * <br>
     * where stage is {@link When}.
     *
     * When you do not set meta data or name of test method is null or empty string, random {@link UUID} is generated instead of
     * that. If {@link When} is not set, it is not included into file name generation. When you do not specify resource type,
     * file name will be generated without file format suffix, excluding a dot as well.
     */
    @Override
    public String build() {
        return resourceIdentifier.getIdentifier(resourceType);
    }

    private void setDefaultFileIdentifier() {
        resourceIdentifier = new ResourceIdentifier<ResourceType>() {

            @Override
            public String getIdentifier(ResourceType resourceType) {
                StringBuilder sb = new StringBuilder();
                if (metaData == null || metaData.getTestMethodName() == null || metaData.getTestMethodName().isEmpty()) {
                    sb.append(UUID.randomUUID().toString());
                } else {
                    sb.append(metaData.getTestMethodName());
                }
                if (when != null) {
                    sb.append("_");
                    sb.append(when.toString());
                }
                if (resourceType != null) {
                    sb.append(".");
                    sb.append(resourceType.toString());
                }
                return sb.toString();
            }
        };
    }

}