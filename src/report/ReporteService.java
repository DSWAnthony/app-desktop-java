package report;

import config.Conexion;
import java.io.File;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;

public class ReporteService {

    public void exportarPdf(String jasperPath, Map<String, Object> parametros, File outFile) throws Exception {
        try (Connection conn = Conexion.getConexion()) {
            // Carga y rellena
            JasperReport report = JasperReportUtil.loadReport(jasperPath);
            JasperPrint print  = JasperReportUtil.fillReport(report, parametros, conn);

            // Exportador PDF
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outFile));

            SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(config);

            exporter.exportReport();
        }
    }
}
