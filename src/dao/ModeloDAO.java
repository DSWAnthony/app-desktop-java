package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import config.Conexion;
import java.util.List;
import model.Modelo;
import repository.IRepositoryCRUD;

public class ModeloDAO extends Conexion implements IRepositoryCRUD<Modelo>{
    private static final String insertarModelo = "INSERT INTO modelo(nombre) VALUES(?)";

    @Override
    public boolean registrar(Modelo modelo) {
        try(
                Connection cn = getConexion();
                CallableStatement cstm = cn.prepareCall(insertarModelo);
        ) {
            
            cstm.setString(1, modelo.getNombre());
            
        } catch (Exception e) {
        }
        
        return false;
    }

    @Override
    public boolean actualizar(Modelo entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Modelo> listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Modelo> filtrar(String params) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Modelo traerEntidad(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
