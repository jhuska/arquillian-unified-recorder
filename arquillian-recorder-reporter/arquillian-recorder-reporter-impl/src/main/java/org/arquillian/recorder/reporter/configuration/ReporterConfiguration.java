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
import java.util.UUID;
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

    private String file = getFileDefaultFileName();

    private String rootDir = "target";

    private String template = "src/test/resources/template.xsl";

    /**
     *
     * @return type of report we want to get, it defaults to "xml"
     */
    public String getReport() {
        return getProperty("report", report).toLowerCase();
    }

    /**
     *
     * @return file where to export a report
     */
    public File getFile() {
        return new File(getRootDir(), getProperty("file", file));
    }

    /**
     *
     * @return root directory which prepends {@link #getFile()}
     */
    public File getRootDir() {
        return new File(getProperty("rootDir", rootDir));
    }

    /**
     *
     * @return xsl template for transforming XML to HTML when using HTML report type, defaults to "template.xsl"
     */
    public File getTemplate() {
        return new File(getProperty("template", template));
    }

    private String getFileDefaultFileName() {
        return new StringBuilder()
            .append("arquillian_report_")
            .append(UUID.randomUUID())
            .toString();
    }

    @Override
    public void validate() throws ReporterConfigurationException {
        if (report.isEmpty()) {
            logger.info("Report type can not be empty string! Choosing default type \"xml\"");
            report = DEFAULT_TYPE;
        }

        String fileProperty = getProperty("file", file);
        String reportProperty = getProperty("report", report);

        if (!fileProperty.endsWith(reportProperty)) {
            file = fileProperty.concat(".").concat(reportProperty);
            setProperty("file", file);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-40s %s\n", "report", getReport()));
        sb.append(String.format("%-40s %s\n", "rootDir", getRootDir().getPath()));
        sb.append(String.format("%-40s %s\n", "file", getFile().getPath()));
        sb.append(String.format("%-40s %s\n", "template", getTemplate().getPath()));
        return sb.toString();
    }

}
