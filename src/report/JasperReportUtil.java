package report;

import java.sql.Connection;
import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperReportUtil {
    
    public static JasperReport loadReport(String reportPath) throws Exception {
        InputStream stream = JasperReportUtil.class.getResourceAsStream(reportPath);
        if (stream == null) {
            throw new IllegalArgumentException("No se encontró el reporte compilado: " + reportPath);
        }
        return (JasperReport) JRLoader.loadObject(stream);
    }

    public static JasperPrint fillReport(JasperReport report, Map<String, Object> params, Connection conn) throws Exception {
        return JasperFillManager.fillReport(report, params, conn);
    }
}
