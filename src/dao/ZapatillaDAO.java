package dao;

import java.util.List;
import config.Conexion;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Categoria;
import model.Marca;
import model.Modelo;
import model.Zapatilla;
import repository.IRepositoryCRUD;

public class ZapatillaDAO extends Conexion implements IRepositoryCRUD<Zapatilla>{
    private static final String listarZapatillas = "CALL ListarZapatillas()";
    private static final String insertarZapatilla = "CALL InsertarZapatilla(?, ?, ?, ?, ?)";
    
    @Override
    public boolean registrar(Zapatilla zapatilla) {
        try(
                Connection cn = getConexion();
                CallableStatement procedure = cn.prepareCall(insertarZapatilla);       
           ) 
        {
            procedure.setString(1, zapatilla.getSku());
            procedure.setString(2, zapatilla.getColor());
            procedure.setDouble(3, zapatilla.getCosto());
            procedure.setDouble(4, zapatilla.getTalla());
            
            if (zapatilla.getModelo().getId() != null) {
                
            } else{
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ZapatillaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean actualizar(Zapatilla entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Zapatilla> listar() {
         List<Zapatilla> zapatillas = new ArrayList<>();
        
        try (Connection cn = Conexion.getConexion();
             CallableStatement procedure = cn.prepareCall(listarZapatillas);
             ResultSet rs = procedure.executeQuery()) {
            
            while (rs.next()) {                
                Zapatilla zapatilla = new Zapatilla();
                zapatilla.setId(rs.getInt("zapato_id"));
                zapatilla.setColor(rs.getString("color"));
                zapatilla.setCosto(rs.getDouble("costo"));
                zapatilla.setSku(rs.getString("sku"));
                zapatilla.setPrecio(rs.getDouble("precio"));
                zapatilla.setTalla(rs.getDouble("talla"));
                
                Modelo modelo = new Modelo();
                modelo.setId(rs.getInt("modelo_id"));
                modelo.setGenero(rs.getString("genero"));
                modelo.setNombre(rs.getString("modelo"));
                
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNombre(rs.getString("categoria"));
                categoria.setDescripcion(rs.getString("descripcion"));
                
                Marca marca = new Marca();
                marca.setId(rs.getInt("marca_id"));
                marca.setNombre(rs.getString("marca"));
                
                modelo.setCategoria(categoria);
                modelo.setMarca(marca);
                
                zapatilla.setModelo(modelo);
                
                zapatillas.add(zapatilla);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al traer Zapatillas: " + e.getMessage());
        }
        
        return zapatillas;
    }

    @Override
    public List<Zapatilla> filtrar(String params) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Zapatilla traerEntidad(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
