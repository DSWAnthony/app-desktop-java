package dao;

import config.Conexion;
import model.Categoria;
import model.Marca;
import model.Modelo;
import repository.IRepositoryCRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloDAO extends Conexion implements IRepositoryCRUD<Modelo> {
 
    @Override
    public boolean registrar(Modelo modelo) {
    String sql = "{CALL sp_registrar_modelo(?, ?, ?, ?)}";
    try (Connection cn = getConexion(); CallableStatement cs = cn.prepareCall(sql)) {
        cs.setString(1, modelo.getNombre());
        cs.setString(2, modelo.getGenero());
        cs.setInt(3, modelo.getCategoria().getId());
        cs.setInt(4, modelo.getMarca().getId());
        return cs.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
        }
    }

     @Override
    public boolean actualizar(Modelo modelo) {
        String sql = "{CALL sp_actualizar_modelo(?, ?, ?, ?, ?)}";
        try (Connection cn = getConexion(); CallableStatement cs = cn.prepareCall(sql)) {
            cs.setString(1, modelo.getNombre());
            cs.setString(2, modelo.getGenero());
            cs.setInt(3, modelo.getCategoria().getId());
            cs.setInt(4, modelo.getMarca().getId());
            cs.setInt(5, modelo.getId());
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "{CALL sp_eliminar_modelo(?)}";
        try (Connection cn = getConexion(); CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Modelo> listar() {
        List<Modelo> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_modelos()}";
        try (Connection cn = getConexion(); CallableStatement cs = cn.prepareCall(sql); ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Modelo modelo = new Modelo();
                modelo.setId(rs.getInt("modelo_id"));
                modelo.setNombre(rs.getString("nombre"));
                modelo.setGenero(rs.getString("genero"));

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNombre(rs.getString("categoria_nombre"));
                modelo.setCategoria(categoria);

                Marca marca = new Marca();
                marca.setId(rs.getInt("marca_id"));
                marca.setNombre(rs.getString("marca_nombre"));
                modelo.setMarca(marca);

                lista.add(modelo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Modelo traerEntidad(int id) {
        String sql = "{CALL sp_traer_modelo_por_id(?)}";
        try (Connection cn = getConexion(); CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Modelo modelo = new Modelo();
                    modelo.setId(rs.getInt("modelo_id"));
                    modelo.setNombre(rs.getString("nombre"));
                    modelo.setGenero(rs.getString("genero"));

                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("categoria_id"));
                    categoria.setNombre(rs.getString("categoria_nombre"));
                    modelo.setCategoria(categoria);

                    Marca marca = new Marca();
                    marca.setId(rs.getInt("marca_id"));
                    marca.setNombre(rs.getString("marca_nombre"));
                    modelo.setMarca(marca);

                    return modelo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Modelo> filtrar(String params) {
        return new ArrayList<>();
    }

    public List<Categoria> listarCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT categoria_id, nombre FROM categoria";
        try (Connection cn = getConexion(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("categoria_id"));
                c.setNombre(rs.getString("nombre"));
                categorias.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public List<Marca> listarMarcas() {
        List<Marca> marcas = new ArrayList<>();
        String sql = "SELECT marca_id, nombre FROM marca";
        try (Connection cn = getConexion(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Marca m = new Marca();
                m.setId(rs.getInt("marca_id"));
                m.setNombre(rs.getString("nombre"));
                marcas.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marcas;
    }
}