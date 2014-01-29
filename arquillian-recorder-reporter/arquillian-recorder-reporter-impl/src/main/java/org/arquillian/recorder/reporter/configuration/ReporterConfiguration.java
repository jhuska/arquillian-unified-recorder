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
package org.arquillian.recorder.reporter.configuration;

import java.io.File;
import java.util.logging.Logger;

import org.arquillian.extension.recorder.Configuration;

/**
 * @author <a href="smikloso@redhat.com">Stefan Miklosovic</a>
 *
 */
public class ReporterConfiguration extends Configuration<ReporterConfiguration> {

    private static final Logger logger = Logger.getLogger(ReporterConfiguration.class.getName());

    private static final String DEFAULT_TYPE = "xml";

    private String report = DEFAULT_TYPE;

    private String file = "export";

    public String getReport() {
        return getProperty("report", report).toLowerCase();
    }

    public File getFile() {
        return new File(getProperty("file", file) + "." + getProperty("report", report));
    }

    @Override
    public void validate() throws ReporterConfigurationException {
        if (report.isEmpty()) {
            logger.info("Report type can not be empty string! Choosing default type \"xml\"");
            report = DEFAULT_TYPE;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-40s %s\n", "report", getReport()));
        sb.append(String.format("%-40s %s\n", "file", getFile().getAbsolutePath()));
        return sb.toString();
    }

}
