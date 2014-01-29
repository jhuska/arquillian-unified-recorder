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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.arquillian.recorder.reporter.ReportEntry;
import org.arquillian.recorder.reporter.model.property.PropertyEntry;

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
@XmlRootElement(name = "class")
public class TestClassReport implements ReportEntry {

    @XmlAttribute(name = "name")
    private String testClassName;

    @XmlElement(name = "method")
    @XmlElementWrapper(name = "methods")
    private final List<TestMethodReport> testMethodReports = new ArrayList<TestMethodReport>();

    private final List<PropertyEntry> propertyEntries = new ArrayList<PropertyEntry>();

    @XmlTransient
    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public List<TestMethodReport> getTestMethodReports() {
        return testMethodReports;
    }

    public List<PropertyEntry> getPropertyEntries() {
        return propertyEntries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("testClassReport\n")
            .append("\t\t\tname: ");
        sb.append(testClassName != null ? testClassName : "unknown");
        sb.append("\n")
            .append("\t\t\t");

        for (TestMethodReport testMethodReport : testMethodReports) {
            sb.append(testMethodReport);
            sb.append("\t\t\t");
        }

        return sb.toString();
    }
}
