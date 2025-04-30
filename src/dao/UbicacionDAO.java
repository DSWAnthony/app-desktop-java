package dao;

import config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Ubicacion;
import repository.IRepositoryCRUD;

public class UbicacionDAO extends Conexion implements IRepositoryCRUD<Ubicacion>{
    Connection con;
    Conexion cn = new Conexion();
    CallableStatement cs;
    ResultSet rs;
    @Override
    public List<Ubicacion> listar() {
    List<Ubicacion> lista = new ArrayList<>();
    try (Connection con = cn.getConexion();
         CallableStatement cs = con.prepareCall("{call sp_ListarUbicaciones()}");
         ResultSet rs = cs.executeQuery()) {
        
        while (rs.next()) {
            Ubicacion u = new Ubicacion();
            u.setUbicacionId(rs.getInt("ubicacion_id"));
            u.setContenedor(rs.getString("contenedor"));
            u.setEstante(rs.getString("estante"));
            u.setPasillo(rs.getString("pasillo"));
            lista.add(u);
        }
    } catch (SQLException e) {
        System.out.println("Error al listar ubicaciones: " + e.getMessage());
    }
    return lista;
}

 
    public boolean insertar(Ubicacion ubicacion) {
        try {
            con = cn.getConexion();
            cs = con.prepareCall("{call sp_crearUbicacion(?,?,?)}");
            cs.setString(1, ubicacion.getContenedor());
            cs.setString(2, ubicacion.getEstante());
            cs.setString(3, ubicacion.getPasillo());
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar ubicación: " + e.getMessage());
            return false;
        }
    }

   @Override
    public boolean actualizar(Ubicacion ubicacion) {
        try {
            con = cn.getConexion();
            cs = con.prepareCall("{call sp_ActualizarUbicacion(?,?,?,?)}");
            cs.setInt(1, ubicacion.getUbicacionId());
            cs.setString(2, ubicacion.getContenedor());
            cs.setString(3, ubicacion.getEstante());
            cs.setString(4, ubicacion.getPasillo());
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar ubicación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        try {
            con = cn.getConexion();
            cs = con.prepareCall("{call sp_EliminarUbicacion(?)}");
            cs.setInt(1, id);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar ubicación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean registrar(Ubicacion ubicacion) {
      try {
            con = cn.getConexion();
            cs = con.prepareCall("{call sp_crearUbicacion(?,?,?)}");
            cs.setString(1, ubicacion.getContenedor());
            cs.setString(2, ubicacion.getEstante());
            cs.setString(3, ubicacion.getPasillo());
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar ubicación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Ubicacion> filtrar(String params) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Ubicacion traerEntidad(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}
