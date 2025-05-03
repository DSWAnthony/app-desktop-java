package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
//    private static final String CONTROLADOR = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//    private static final String URL = "jdbc:sqlserver://localhost:1433;database=inventario_elite;"
//            + "user=sa;password=Aguilar939428950@;loginTimeout=30";
//
//    private static Connection cn;
//
//    static {
//        try {
//            Class.forName(CONTROLADOR);
//        } catch (ClassNotFoundException e) {
//            System.out.println("Error al cargar el controlador: " + e.getMessage());
//        }
//    }
//
//    public static Connection getConexion() {
//        if (cn == null) {
//            try {
//                cn = DriverManager.getConnection(URL);
//            } catch (SQLException e) {
//                System.out.println("Error al conectar: " + e.getMessage());
//            }
//        }
//        return cn;
//    }
    
    private static final String URL = "jdbc:mysql://localhost:3306/inventario";
    private static final String USER = "root";
    private static final String PASSWORD = "Xboxlive123";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver JDBC de MySQL", e);
        }
    }

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
