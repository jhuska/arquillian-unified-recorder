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

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
@XmlRootElement(name = "container")
public class ContainerReport implements ReportEntry {

    @XmlAttribute
    private String qualifier;

    @XmlElement
    private String configuration;

    @XmlElementWrapper(name = "deployments")
    @XmlElement(name = "deployment")
    private List<DeploymentReport> deploymentReports = new ArrayList<DeploymentReport>();

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    @XmlTransient
    public String getQualifier() {
        return qualifier;
    }

    public void setDeploymentReports(List<DeploymentReport> deploymentReports) {
        this.deploymentReports = deploymentReports;
    }

    @XmlTransient
    public List<DeploymentReport> getDeploymentReports() {
        return deploymentReports;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    @XmlTransient
    public String getConfiguration() {
        return configuration;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("container\n\t\t\t");
        sb.append("qualifier: ").append(qualifier != null ? qualifier : "unknown");
        sb.append("\n\t\t\t");

        for (DeploymentReport deploymentReport : deploymentReports) {
            sb.append(deploymentReport);
        }

        sb.append("configuration: ").append(configuration != null ? configuration : "unknown");
        sb.append("\n\t\t");
        return sb.toString();
    }
}
