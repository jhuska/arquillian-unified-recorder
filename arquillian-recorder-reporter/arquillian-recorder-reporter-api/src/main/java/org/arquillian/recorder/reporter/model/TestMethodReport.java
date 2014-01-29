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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.arquillian.recorder.reporter.ReportEntry;
import org.arquillian.recorder.reporter.model.property.PropertyEntry;
import org.jboss.arquillian.test.spi.TestResult.Status;

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
@XmlRootElement(name = "method")
public class TestMethodReport implements ReportEntry {

    @XmlAttribute
    private String name;

    @XmlAttribute(name = "result")
    private Status status;

    @XmlAttribute
    private long duration = -1;

    private final List<PropertyEntry> propertyEntries = new ArrayList<PropertyEntry>();

    @XmlTransient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @XmlTransient
    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        if (duration > 0) {
            this.duration = duration;
        }
    }

    public List<PropertyEntry> getPropertyEntries() {
        return propertyEntries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("testMethodReport\n")
            .append("\t\t\t\t\tname: ").append(name != null ? name : "unknown").append("\n")
            .append("\t\t\t\t\tduration: ").append(duration != -1 ? duration : "unknown").append("\n")
            .append("\t\t\t\t\tresult: ").append(status != null ? status : "unknown").append("\n");

        return sb.toString();

    }

}
