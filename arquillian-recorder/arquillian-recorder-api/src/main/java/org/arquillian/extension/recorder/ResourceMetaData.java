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

import org.jboss.arquillian.core.spi.Validate;

/**
 * Metadata which are related to any resource.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public abstract class ResourceMetaData {

    private String testClassName;

    private String testMethodName;

    private long timeStamp;

    public String getTestClassName() {
        return testClassName;
    }

    /**
     * Test case class as string in which invocation method was called.
     *
     * @param testCase
     * @return
     * @throws IllegalArgumentException if {@code testCase} is null or empty string
     */
    public ResourceMetaData setTestClassName(String testClassName) {
        Validate.notNullOrEmpty(testClassName, "Name of test class is null or empty string.");
        this.testClassName = testClassName;
        return this;
    }

    /**
     * Test method in which invocation method was called.
     *
     * @param testMethod
     * @return
     * @throws IllegalArgumentException if {@code testMethod} is null or empty string
     */
    public ResourceMetaData setTestMethodName(String testMethodName) {
        Validate.notNullOrEmpty(testMethodName, "Name of test method is null or empty string.");
        this.testMethodName = testMethodName;
        return this;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Time stamp when this screenshot was taken.
     *
     * @param timeStamp
     * @return
     */
    public ResourceMetaData setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("test class\t\t:\n").append(getTestClassName())
            .append("test method\t\t:\n").append(getTestMethodName())
            .append("timestamp\t\t:\n").append(getTimeStamp());
        return sb.toString();
    }

}
