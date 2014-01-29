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
package org.arquillian.recorder.reporter.impl;

import org.arquillian.recorder.reporter.Reporter;
import org.arquillian.recorder.reporter.event.ExportReport;
import org.arquillian.recorder.reporter.event.ReportEvent;
import org.arquillian.recorder.reporter.model.ContainerReport;
import org.arquillian.recorder.reporter.model.DeploymentReport;
import org.arquillian.recorder.reporter.model.TestClassReport;
import org.arquillian.recorder.reporter.model.TestMethodReport;
import org.arquillian.recorder.reporter.model.TestSuiteReport;
import org.arquillian.recorder.reporter.spi.Reportable;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.spi.event.container.BeforeDeploy;
import org.jboss.arquillian.container.spi.event.container.BeforeStart;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.event.suite.After;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;

/**
 * Observes events from Arquillian and delegates them to {@link Reporter} implementation.
 *
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class ReporterLifecycleObserver {

    @Inject
    private Instance<Reporter> reporter;

    @Inject
    private Event<ExportReport> exportReportEvent;

    public void observeBeforeSuite(@Observes BeforeSuite event) {
        TestSuiteReport testSuiteReport = new TestSuiteReport();

        reporter.get().getReport().getTestSuiteReports().add(testSuiteReport);
        reporter.get().setTestSuiteReport(testSuiteReport);
    }

    public void observeBeforeStart(@Observes BeforeStart event, ContainerRegistry registry) {
        ContainerReport containerReport = new ContainerReport();

        for (Container container : registry.getContainers()) {
            if (container.getDeployableContainer().getConfigurationClass() == event.getDeployableContainer()
                .getConfigurationClass()) {

                containerReport.setQualifier(container.getName());
                containerReport.setConfiguration(container.getContainerConfiguration().exportAsString());

                reporter.get().getLastTestSuiteReport().getContainerReports().add(containerReport);
                reporter.get().setContainer(containerReport);
                break;
            }
        }

    }

    public void observeBeforeDeploy(@Observes BeforeDeploy event) {
        DeploymentReport deploymentReport = new DeploymentReport();

        DeploymentDescription description = event.getDeployment();

        deploymentReport.setArchiveName(description.getArchive().toString());
        deploymentReport.setName(description.getName());
        deploymentReport.setOrder(description.getOrder());
        deploymentReport.setProtocol(description.getProtocol().getName());
        deploymentReport.setTarget(description.getTarget().getName());

        for (ContainerReport containerReport : reporter.get().getLastTestSuiteReport().getContainerReports()) {
            if (containerReport.getQualifier().equals(deploymentReport.getTarget())) {
                containerReport.getDeploymentReports().add(deploymentReport);
                break;
            }
        }

    }

    public void observeBeforeClass(@Observes BeforeClass event) {
        TestClassReport testClassReport = new TestClassReport();
        testClassReport.setTestClassName(event.getClass().getName());

        reporter.get().getLastTestSuiteReport().getTestClassReports().add(testClassReport);
        reporter.get().setTestClassReport(testClassReport);
    }

    public void observeBeforeTest(@Observes Before event) {
        TestMethodReport testMethodReport = new TestMethodReport();
        testMethodReport.setName(event.getTestMethod().getName());

        reporter.get().getLastTestClassReport().getTestMethodReports().add(testMethodReport);
        reporter.get().setTestMethodReport(testMethodReport);
    }

    public void observeAfterTest(@Observes After event, TestResult result) {
        reporter.get().getLastTestMethodReport().setStatus(result.getStatus());
        reporter.get().getLastTestMethodReport().setDuration(result.getEnd() - result.getStart());
    }

    public void observeAfterSuite(@Observes AfterSuite event) {
        Reportable report = reporter.get().report();
        exportReportEvent.fire(new ExportReport(report));
    }

    public void observeReportEvent(@Observes ReportEvent event) {
        // TODO
    }
}
