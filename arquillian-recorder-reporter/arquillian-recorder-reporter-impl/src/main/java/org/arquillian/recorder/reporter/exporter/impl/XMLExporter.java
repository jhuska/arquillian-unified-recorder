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
package org.arquillian.recorder.reporter.exporter.impl;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.arquillian.extension.recorder.Configuration;
import org.arquillian.recorder.reporter.Exporter;
import org.arquillian.recorder.reporter.JAXBContextFactory;
import org.arquillian.recorder.reporter.configuration.ReporterConfiguration;
import org.arquillian.recorder.reporter.impl.type.XMLReport;
import org.arquillian.recorder.reporter.model.Report;
import org.arquillian.recorder.reporter.spi.ReportType;
import org.arquillian.recorder.reporter.spi.Reportable;

/**
 * Exports reports to XML file. Output is formatted.
 *
 * @see {@link XMLReport}
 *
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class XMLExporter implements Exporter {

    private ReporterConfiguration configuration;

    private JAXBContext context = JAXBContextFactory.initContext(Report.class);

    @Override
    public File export(Reportable report) throws Exception {
        File export = configuration.getFile();
        getMarshaller().marshal(report, export);
        return export;
    }

    private Marshaller getMarshaller() throws JAXBException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        return marshaller;
    }

    @Override
    public Class<? extends ReportType> getReportType() {
        return XMLReport.class;
    }

    @Override
    public void setConfiguration(Configuration<?> configuration) {
        this.configuration = (ReporterConfiguration) configuration;
    }

}
