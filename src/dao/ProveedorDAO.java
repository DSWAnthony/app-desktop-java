/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.Conexion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.ResultSet;

import model.Proveedor;
import repository.IRepositoryCRUD;

/**
 *
 * @author ajca
 */
public class ProveedorDAO extends Conexion implements IRepositoryCRUD<Proveedor>{
    private Connection cnx = null;
    private CallableStatement stm = null;
    private ResultSet rs = null;

    @Override
    public boolean registrar(Proveedor proveedor) {
           
        //Control de conexion
        try{
            
            stm = cnx.prepareCall("{CALL sp_insertar_proveedor(?, ?, ?, ?, ?, ?)}");
            stm.setString(1, proveedor.getNombre());
            stm.setString(2, proveedor.getContacto());
            stm.setString(3, proveedor.getDireccion());
            stm.setString(4, proveedor.getEmail());
            stm.setString(5, proveedor.getTelefono());
            stm.setString(6, proveedor.getRuc());
            stm.execute();
            return true;
            
        } catch(SQLException e){
            //Excepcion
        } finally {
            
            try{
                if(stm != null) stm.close();
            } catch(SQLException e2){
                //Manejo de errores
            }
            
        }
        return false;
    }

    @Override
    public boolean actualizar(Proveedor proveedor) {
          //Control de conexion
        try{
            
            stm = cnx.prepareCall("{CALL sp_editar_proveedor(?, ?, ?, ?, ?, ?, ?)}");
               stm.setInt(1, proveedor.getProveedorId());
            stm.setString(2, proveedor.getNombre());
            stm.setString(3, proveedor.getContacto());
            stm.setString(4, proveedor.getDireccion());
            stm.setString(5, proveedor.getEmail());
            stm.setString(6, proveedor.getTelefono());
            stm.setString(7, proveedor.getRuc());
            stm.execute();
            return true;
        } catch(SQLException e){
            //Excepcion
        } finally {
            
            try{
                if(stm != null) stm.close();
            } catch(SQLException e2){
                //Manejo de errores
            }
            
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
         
        //Control de conexion
        try{
            
            stm = cnx.prepareCall("{CALL sp_eliminar_proveedor(?)}");
            stm.setInt(1, id);
            stm.execute();
            return true;
            
        } catch(SQLException e){
            //Excepcion
        } finally {
            
            try{
                if(stm != null) stm.close();
            } catch(SQLException e2){
                //Manejo de errores
            }
            
        }
        return false;
    }

    @Override
    public List<Proveedor> listar() {
           //Genera una lista para datos SQL
        List<Proveedor> lista = new ArrayList<>();
        
        //Control de conexion
        try{
            cnx = getConexion();
            stm = cnx.prepareCall("{CALL sp_listar_proveedor()}");
            rs = stm.executeQuery();
            
            //Generacion de datos
            while(rs.next()){
                Proveedor proveedor = new Proveedor(
                        rs.getInt("proveedor_id"),
                        rs.getString("nombre"),
                        rs.getString("contacto"),
                        rs.getString("direccion"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("ruc")
                );
                
                lista.add(proveedor);
            }
            
        } catch(SQLException e){
            //Excepcion
        } finally {
            
            try{
                
                if(rs != null) rs.close();
                if(stm != null) stm.close();
                
            } catch(SQLException e2){
                //Manejo de errores
            }
           
        }
        
        return lista;
    }

    @Override
    public List<Proveedor> filtrar(String params) {
           //Creacion de vector
        List<Proveedor> lista = new ArrayList<>();
        
        //Control de conexion
        try{
            
            stm = cnx.prepareCall("{CALL sp_filtrar_proveedor_por_nombre(?)}");
            stm.setString(1, params);
            rs = stm.executeQuery();
            
            //Generacion de datos
            while(rs.next()){
                Proveedor proveedor = new Proveedor(
                    rs.getInt("proveedor_id"),
                    rs.getString("nombre"),
                    rs.getString("contacto"),
                    rs.getString("direccion"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("ruc")
                );
                
                lista.add(proveedor);
            }
            
        } catch(SQLException e){
            //Excepcion
        } finally {
            
            try{
                if(rs != null) rs.close();
                if(stm != null) stm.close();
            } catch(SQLException e2){
                //Manejo de errores
            }
            
        }
        
        return lista;
    }

    @Override
    public Proveedor traerEntidad(int id) {
         //Objeto de modelo
        Proveedor proveedor = null;
        
        //Control de conexion
        try{
            
            stm = cnx.prepareCall("{CALL sp_buscar_proveedor_por_ID(?)}");
            stm.setInt(1, id);
            rs = stm.executeQuery();
            
            //Generacion de datos
            if(rs.next()){
                proveedor = new Proveedor(
                    rs.getInt("proveedor_id"),
                    rs.getString("nombre"),
                    rs.getString("contacto"),
                    rs.getString("direccion"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("ruc")
                );
            }
            
        } catch(SQLException e){
            //Excepcion
        } finally {
            
            try{
                if(rs != null) rs.close();
                if(stm != null) stm.close();
            } catch(SQLException e2){
                //Manejo de errores
            }
            
        }
        
        return proveedor;
        
    }
    
}
