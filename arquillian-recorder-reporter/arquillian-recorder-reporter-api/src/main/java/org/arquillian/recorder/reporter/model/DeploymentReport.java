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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.arquillian.recorder.reporter.ReportEntry;

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
@XmlRootElement(name = "deployment")
public class DeploymentReport implements ReportEntry {

    @XmlAttribute
    private String name;

    @XmlElement(name = "archive")
    private String archiveName;

    @XmlElement
    private int order;

    @XmlElement
    private String protocol;

    @XmlTransient
    private String target;

    public void setName(String name) {
        this.name = name;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @XmlTransient
    public String getName() {
        return name;
    }

    @XmlTransient
    public String getArchiveName() {
        return archiveName;
    }

    @XmlTransient
    public int getOrder() {
        return order;
    }

    @XmlTransient
    public String getProtocol() {
        return protocol;
    }

    @XmlTransient
    public String getTarget() {
        return target;
    }

}
