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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.arquillian.recorder.reporter.model.entry.FileEntry;
import org.arquillian.recorder.reporter.model.entry.KeyValueEntry;
import org.arquillian.recorder.reporter.model.entry.ScreenshotEntry;
import org.arquillian.recorder.reporter.model.entry.VideoEntry;
import org.arquillian.recorder.reporter.spi.PropertyEntry;
import org.arquillian.recorder.reporter.spi.ReportEntry;
import org.jboss.arquillian.test.spi.TestResult.Status;

/**
 * Reports test method which belongs to some {@link TestClassReport}<br>
 * <br>
 * Must hold:
 * <ul>
 * <li>name</li>
 * <li>result status</li>
 * <li>duration</li>
 * </ul>
 * Can hold:
 * <ul>
 * <li>exception message</li>
 * <li>list of {@link KeyValueEntry}</li>
 * <li>list of {@link FileEntry}</li>
 * <li>list of {@link VideoEntry}</li>
 * <li>list of {@link ScreenshotEntry}</li>
 * </ul>
 *
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
@XmlRootElement(name = "method")
@XmlType(propOrder = { "name", "status", "duration", "operatesOnDeployment", "exception", "propertyEntries" })
public class TestMethodReport implements ReportEntry {

    private String name;

    private Status status;

    private long duration = -1;

    private String exception;

    private String operatesOnDeployment;

    @XmlElements({
        @XmlElement(name = "property", type = KeyValueEntry.class),
        @XmlElement(name = "file", type = FileEntry.class),
        @XmlElement(name = "video", type = VideoEntry.class),
        @XmlElement(name = "screenshot", type = ScreenshotEntry.class)
    })
    private final List<PropertyEntry> propertyEntries = new ArrayList<PropertyEntry>();

    public String getName() {
        return name;
    }

    @XmlAttribute(required = true)
    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    @XmlAttribute(name = "result", required = true)
    public void setStatus(Status status) {
        this.status = status;
    }

    public long getDuration() {
        return duration;
    }

    @XmlAttribute(required = true)
    public void setDuration(long duration) {
        if (duration > 0) {
            this.duration = duration;
        }
    }

    @XmlElement(required = false)
    public void setException(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }

    @XmlAttribute(name = "operatesOnDeployment")
    public void setOperatesOnDeployment(String operatesOnDeployment) {
        this.operatesOnDeployment = operatesOnDeployment;
    }

    public String getOperatesOnDeployment() {
        return operatesOnDeployment;
    }

    @Override
    public List<PropertyEntry> getPropertyEntries() {
        return propertyEntries;
    }

}
