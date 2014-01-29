/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
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
package org.arquillian.recorder.reporter.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.arquillian.recorder.reporter.ReportEntry;
import org.arquillian.recorder.reporter.model.property.PropertyEntry;

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
@XmlRootElement(name = "suite")
public class TestSuiteReport implements ReportEntry {

    @XmlElement(name = "class")
    @XmlElementWrapper(name = "classes")
    private final List<TestClassReport> testClassReports = new ArrayList<TestClassReport>();

    private final List<PropertyEntry> propertyEntries = new ArrayList<PropertyEntry>();

    public List<TestClassReport> getTestClassReports() {
        return testClassReports;
    }

    public List<PropertyEntry> getPropertyEntries() {
        return propertyEntries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("testSuiteReport\n\t\t");

        for (TestClassReport testClassReport : testClassReports) {
            sb.append(testClassReport.toString());
            sb.append("\t\t");
        }

        return sb.toString();
    }

}
