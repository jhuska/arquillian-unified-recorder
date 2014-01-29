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

import org.arquillian.extension.recorder.Configuration;
import org.arquillian.recorder.reporter.Reporter;
import org.arquillian.recorder.reporter.configuration.ReporterConfiguration;
import org.arquillian.recorder.reporter.impl.type.XMLReport;
import org.arquillian.recorder.reporter.model.ContainerReport;
import org.arquillian.recorder.reporter.model.Report;
import org.arquillian.recorder.reporter.model.TestClassReport;
import org.arquillian.recorder.reporter.model.TestMethodReport;
import org.arquillian.recorder.reporter.model.TestSuiteReport;
import org.arquillian.recorder.reporter.spi.ReportType;
import org.arquillian.recorder.reporter.spi.Reportable;

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class ReporterImpl implements Reporter {

    private ReporterConfiguration configuration;

    private Report report = null;

    private TestSuiteReport testSuiteReport = null;

    private TestClassReport testClassReport = null;

    private TestMethodReport testMethodReport = null;

    private ContainerReport containerReport = null;

    private ReportType reportType = new XMLReport();

    @Override
    public Reportable report() {
        System.out.println(report.toString());
        return report;
    }

    @Override
    public ReportType getReportType() {
        return reportType;
    }

    @Override
    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public Report getReport() {
        return report;
    }

    @Override
    public void setTestSuiteReport(TestSuiteReport testSuiteReport) {
        this.testSuiteReport = testSuiteReport;
    }

    @Override
    public void setTestClassReport(TestClassReport testClassReport) {
        this.testClassReport = testClassReport;
    }

    @Override
    public void setTestMethodReport(TestMethodReport testMethodReport) {
        this.testMethodReport = testMethodReport;
    }

    @Override
    public void setContainer(ContainerReport containerReport) {
        this.containerReport = containerReport;
    }

    @Override
    public TestSuiteReport getLastTestSuiteReport() {
        return testSuiteReport;
    }

    @Override
    public TestClassReport getLastTestClassReport() {
        return testClassReport;
    }

    @Override
    public TestMethodReport getLastTestMethodReport() {
        return testMethodReport;
    }

    @Override
    public ContainerReport getLastContainerReport() {
        return containerReport;
    }

    @Override
    public void setConfiguration(Configuration<?> configuration) {
        this.configuration = (ReporterConfiguration) configuration;
    }

}
