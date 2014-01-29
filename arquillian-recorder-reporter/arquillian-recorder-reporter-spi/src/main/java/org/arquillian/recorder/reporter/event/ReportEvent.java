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
package org.arquillian.recorder.reporter.event;

import org.arquillian.recorder.reporter.spi.ReportType;
import org.arquillian.recorder.reporter.spi.Reportable;
import org.jboss.arquillian.core.spi.Validate;

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class ReportEvent {

    private Class<? extends ReportType> reportType;

    private Reportable report;

    /**
     *
     * @param report
     * @param clazz runtime class of {@code report}
     * @throws IllegalArgumentException if some argument is a null object
     */
    public ReportEvent(Reportable report, Class<? extends ReportType> reportType) {
        Validate.notNull(report, "report can not be a null object");
        Validate.notNull(reportType, "reportType can not be a null object");
        this.report = report;
        this.reportType = reportType;
    }

    public Class<? extends ReportType> getReportType() {
        return reportType;
    }

    public Reportable getReport() {
        return report;
    }
}
