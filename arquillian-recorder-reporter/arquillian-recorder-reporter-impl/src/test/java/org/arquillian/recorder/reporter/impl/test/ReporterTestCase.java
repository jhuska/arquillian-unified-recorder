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
package org.arquillian.recorder.reporter.impl.test;

import org.arquillian.recorder.reporter.Exporter;
import org.arquillian.recorder.reporter.Reporter;
import org.arquillian.recorder.reporter.configuration.ReporterConfiguration;
import org.arquillian.recorder.reporter.exporter.impl.XMLExporter;
import org.arquillian.recorder.reporter.impl.ReporterImpl;
import org.arquillian.recorder.reporter.model.ContainerReport;
import org.arquillian.recorder.reporter.model.DeploymentReport;
import org.arquillian.recorder.reporter.model.Report;
import org.arquillian.recorder.reporter.model.TestClassReport;
import org.arquillian.recorder.reporter.model.TestMethodReport;
import org.arquillian.recorder.reporter.model.TestSuiteReport;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.TestResult.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
@RunWith(JUnit4.class)
public class ReporterTestCase {

    @Test
    public void testReporter() throws Exception {
        Reporter reporter = new ReporterImpl();
        reporter.setReport(new Report());
        TestSuiteReport testSuiteReport = new TestSuiteReport();
        reporter.getReport().getTestSuiteReports().add(testSuiteReport);
        reporter.setTestSuiteReport(testSuiteReport);

        // containers
        ContainerReport containerReport = new ContainerReport();
        containerReport.setQualifier("wildfly");
        containerReport.setConfiguration("container configuration will be put here");
        reporter.getLastTestSuiteReport().getContainerReports().add(containerReport);
        reporter.setContainer(containerReport);

        // deployment
        DeploymentReport deploymentReport = new DeploymentReport();

        deploymentReport.setArchiveName("some.war");
        deploymentReport.setName("deploymentName");
        deploymentReport.setOrder(1);
        deploymentReport.setProtocol("someProtocol");
        deploymentReport.setTarget("wildfly");

        reporter.getLastContainerReport().getDeploymentReports().add(deploymentReport);

        TestClassReport testClassReport = new TestClassReport();
        testClassReport.setTestClassName(FakeTestClass.class.getName());
        reporter.getLastTestSuiteReport().getTestClassReports().add(testClassReport);
        reporter.setTestClassReport(testClassReport);

        TestMethodReport testMethodReport = new TestMethodReport();
        testMethodReport.setName("someTestMethod");
        TestResult testResult = new TestResult();
        testResult.setStatus(Status.PASSED);
        testResult.setStart(System.currentTimeMillis());
        testResult.setEnd(testResult.getStart() + 1000);
        testMethodReport.setStatus(testResult.getStatus());
        testMethodReport.setDuration(testResult.getEnd() - testResult.getStart());

        reporter.getLastTestClassReport().getTestMethodReports().add(testMethodReport);
        reporter.setTestMethodReport(testMethodReport);

        TestMethodReport testMethodReport2 = new TestMethodReport();
        testMethodReport2.setName("someTestMethod2");
        TestResult testResult2 = new TestResult();
        testResult2.setStatus(Status.FAILED);
        testResult2.setStart(System.currentTimeMillis());
        testResult2.setEnd(testResult2.getStart() + 2000);
        testMethodReport2.setStatus(testResult2.getStatus());
        testMethodReport2.setDuration(testResult2.getEnd() - testResult2.getStart());

        reporter.getLastTestClassReport().getTestMethodReports().add(testMethodReport2);
        reporter.setTestMethodReport(testMethodReport2);

        Exporter exporter = new XMLExporter();
        exporter.setConfiguration(new ReporterConfiguration());

        exporter.export(reporter.report());
    }
}
