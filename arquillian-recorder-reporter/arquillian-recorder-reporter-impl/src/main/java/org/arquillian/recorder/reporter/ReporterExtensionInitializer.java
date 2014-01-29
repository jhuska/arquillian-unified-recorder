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
package org.arquillian.recorder.reporter;

import org.arquillian.recorder.reporter.configuration.ReporterConfiguration;
import org.arquillian.recorder.reporter.configuration.ReporterConfigurationException;
import org.arquillian.recorder.reporter.event.ExporterRegisterCreated;
import org.arquillian.recorder.reporter.event.ReporterExtensionConfigured;
import org.arquillian.recorder.reporter.exporter.DefaultExporterRegisterFactory;
import org.arquillian.recorder.reporter.impl.ReportTypeRegister;
import org.arquillian.recorder.reporter.impl.ReporterImpl;
import org.arquillian.recorder.reporter.impl.type.XMLReport;
import org.arquillian.recorder.reporter.spi.ReportType;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class ReporterExtensionInitializer {

    @Inject
    @ApplicationScoped
    private InstanceProducer<ExporterRegister> exporterRegister;

    @Inject
    @ApplicationScoped
    private InstanceProducer<ReportTypeRegister> reportTypeRegister;

    @Inject
    @ApplicationScoped
    private InstanceProducer<Exporter> exporter;

    @Inject
    @ApplicationScoped
    private InstanceProducer<Reporter> reporter;

    @Inject
    private Instance<ServiceLoader> serviceLoader;

    @Inject
    private Instance<ReporterConfiguration> configuration;

    @Inject
    private Event<ExporterRegisterCreated> exporterRegisterCreatedEvent;

    public void onExtensionConfigured(@Observes ReporterExtensionConfigured event) {

        // produce report type register and add default type
        reportTypeRegister.set(new ReportTypeRegister());
        reportTypeRegister.get().add(new XMLReport());

        // produce default reporter

        Reporter reporter = serviceLoader.get().onlyOne(Reporter.class, ReporterImpl.class);
        reporter.setConfiguration(configuration.get());
        this.reporter.set(reporter);

        // produce default exporter

        ExporterRegisterFactory registerFactory = serviceLoader.get().onlyOne(ExporterRegisterFactory.class,
            DefaultExporterRegisterFactory.class);

        ExporterRegister register = registerFactory.getExporterRegisterInstance();
        this.exporterRegister.set(register);

        // place to listen to where hooking of exporters will occur

        exporterRegisterCreatedEvent.fire(new ExporterRegisterCreated());

        // match reporter type from configuration to registered exporters

        String report = configuration.get().getReport();

        ReportType reportType = reportTypeRegister.get().get(report);

        if (reportType != null) {
            Exporter exporterToUse = exporterRegister.get().get(reportType.getClass());
            if (exporterToUse != null) {
                exporterToUse.setConfiguration(configuration.get());
                this.exporter.set(exporterToUse);
            } else {
                throw new ReporterConfigurationException("Unable to match required reporter type from configuration to some registered exporter.");
            }
        }
    }
}
