package org.arquillian.recorder.reporter;

import java.util.List;

import org.arquillian.recorder.reporter.spi.ReportType;

public interface ExporterRegister {

    void add(Exporter reporter);

    Exporter get(Class<? extends ReportType> reportType);

    void clear();

    List<Exporter> getAll();

    boolean isSupported(Class<? extends ReportType> reportType);

}