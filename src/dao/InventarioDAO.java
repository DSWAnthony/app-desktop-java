package dao;

import config.Conexion;
import model.Inventario;
import model.UbicacionCombo;
import repository.IRepositoryCRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO implements IRepositoryCRUD<Inventario> {

    @Override
    public boolean registrar(Inventario i) {
        String sql = "{CALL sp_RegistrarInventario(?, ?, ?)}";
        try (Connection con = Conexion.getConexion();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, i.getZapatoId());
            cs.setInt(2, i.getUbicacionId());
            cs.setInt(3, i.getCantidadActual());
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar inventario: " + e.getMessage());
        }
        return false;
    }

   @Override
public boolean actualizar(Inventario i) {
    String sql = "{CALL sp_EditarInventario(?, ?, ?)}"; 
    try (Connection con = Conexion.getConexion();
         CallableStatement cs = con.prepareCall(sql)) {
        cs.setInt(1, i.getInventarioId());  
        cs.setInt(2, i.getCantidadActual()); 
        cs.setInt(3, i.getUbicacionId());   
        cs.execute();
        return true;
    } catch (SQLException e) {
        System.err.println("Error al actualizar inventario: " + e.getMessage());
    }
    return false;
}


    @Override
    public boolean eliminar(int id) {
        String sql = "{CALL sp_EliminarInventario(?)}";
        try (Connection con = Conexion.getConexion();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, id);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar inventario: " + e.getMessage());
        }
        return false;
    }

@Override
public List<Inventario> listar() {
    List<Inventario> lista = new ArrayList<>();
    String sql = "{CALL sp_ListarInventario()}";
    try (Connection con = Conexion.getConexion();
         CallableStatement cs = con.prepareCall(sql);
         ResultSet rs = cs.executeQuery()) {
        while (rs.next()) {
            Inventario i = new Inventario();
            i.setInventarioId(rs.getInt("inventario_id"));
            
            i.setCantidadActual(rs.getInt("stock")); 
            i.setSku(rs.getString("sku"));
            i.setModelo(rs.getString("modelo"));
            i.setTalla(rs.getString("talla"));
            i.setColor(rs.getString("color"));
            i.setMarca(rs.getString("marca"));
            i.setAlmacen(rs.getString("almacen"));
            lista.add(i);
        }
    } catch (SQLException e) {
        System.err.println("Error al listar inventario: " + e.getMessage());
    }
    return lista;
}



    @Override
    public Inventario traerEntidad(int id) {
        String sql = "{CALL sp_ObtenerInventarioPorId(?)}";
        try (Connection con = Conexion.getConexion();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Inventario i = new Inventario();
                    i.setInventarioId(rs.getInt("inventario_id"));
                    
                    i.setUbicacionId(rs.getInt("ubicacion_id"));
                    i.setCantidadActual(rs.getInt("cantidad_actual"));
                    i.setSku(rs.getString("sku"));
                    i.setModelo(rs.getString("modelo"));
                    i.setTalla(rs.getString("talla"));
                    i.setColor(rs.getString("color"));
                    i.setMarca(rs.getString("marca"));
                    i.setAlmacen(rs.getString("almacen"));
                    return i;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al traer inventario: " + e.getMessage());
        }
        return null;
    }
public List<UbicacionCombo> obtenerUbicaciones() {
    List<UbicacionCombo> listaUbicaciones = new ArrayList<>();
    String sql = "SELECT ubicacion_id, CONCAT(pasillo, '-', estante, '-', contenedor) AS nombre FROM ubicacion_almacen";
    try (Connection con = Conexion.getConexion();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            int id = rs.getInt("ubicacion_id");
            String nombre = rs.getString("nombre");
            listaUbicaciones.add(new UbicacionCombo(id, nombre));
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ubicaciones: " + e.getMessage());
    }
    return listaUbicaciones;
}

public UbicacionCombo obtenerUbicacionPorId(int id) {
    String sql = "SELECT ubicacion_id, CONCAT(pasillo, '-', estante, '-', contenedor) AS nombre FROM ubicacion_almacen WHERE ubicacion_id = ?";
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int ubicacionId = rs.getInt("ubicacion_id");
                String nombre = rs.getString("nombre");
                return new UbicacionCombo(ubicacionId, nombre);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener ubicación: " + e.getMessage());
    }
    return null;
}


    @Override
    public List<Inventario> filtrar(String params) {
        throw new UnsupportedOperationException("Filtrado no implementado aún.");
    }
}
