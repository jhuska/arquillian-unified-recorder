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

import java.lang.reflect.Method;

import org.jboss.arquillian.core.spi.Validate;
import org.jboss.arquillian.test.spi.TestClass;

/**
 * Meta data which are related to any {@link Resource}.
 *
 * @author <a href="mailto:smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public abstract class ResourceMetaData {

    private String testClass;

    private String testMethod;

    private long timeStamp;

    public String getTestClass() {
        return testClass;
    }

    /**
     * Sets a class for some particular {@code Resource} where that resource was created.
     *
     * @param testCase name of test class where some resource was created
     * @return {@code this}
     * @throws IllegalArgumentException if {@code testClassName} is a null or an empty string
     */
    public ResourceMetaData setTestClass(String testClass) {
        Validate.notNullOrEmpty(testClass, "Name of test class is a null object or an empty string!");
        this.testClass = testClass;
        return this;
    }

    /**
     * Sets a class for some particular {@code Resource} where that resource was created.
     *
     * @param testClass test class where some resource was created
     * @return {@code this}
     * @throws IllegalArgumentException if {@code TestClass} is a null object
     */
    public ResourceMetaData setTestClass(TestClass testClass) {
        Validate.notNull(testClass, "Test class is a null object!");
        this.testClass = testClass.getName();
        return this;
    }

    public String getTestMethod() {
        return testMethod;
    }

    /**
     * Sets a test method for some particular {@code Resource} where that resource was created.
     *
     * @param testMethod name of test a method where some resource was created
     * @return {@code this}
     * @throws IllegalArgumentException if {@code testMethodName} is a null object or an empty string
     */
    public ResourceMetaData setTestMethod(String testMethod) {
        Validate.notNullOrEmpty(testMethod, "Name of test method is a null or an empty string!");
        this.testMethod = testMethod;
        return this;
    }

    /**
     * Sets a test method for some particular {@code Resource} where that resource was created.
     *
     * @param testMethod test method where some resource was created
     * @return {@code this}
     * @throws IllegalArgumentException if {@code testMethod} is a null object.
     */
    public ResourceMetaData setTestMethod(Method testMethod) {
        Validate.notNull(testMethod, "Method is a null object!");
        this.testMethod = testMethod.getName();
        return this;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Time stamp specifying when some particular {@code Resource} was created.
     *
     * @param timeStamp
     * @return {@code this}
     */
    public ResourceMetaData setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((testClass == null) ? 0 : testClass.hashCode());
        result = prime * result + ((testMethod == null) ? 0 : testMethod.hashCode());
        result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResourceMetaData other = (ResourceMetaData) obj;
        if (testClass == null) {
            if (other.testClass != null)
                return false;
        } else if (!testClass.equals(other.testClass))
            return false;
        if (testMethod == null) {
            if (other.testMethod != null)
                return false;
        } else if (!testMethod.equals(other.testMethod))
            return false;
        if (timeStamp != other.timeStamp)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("test class\t\t:\n").append(getTestClass())
            .append("test method\t\t:\n").append(getTestMethod())
            .append("timestamp\t\t:\n").append(getTimeStamp());
        return sb.toString();
    }

}
