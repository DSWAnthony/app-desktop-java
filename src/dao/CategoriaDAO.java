/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.Conexion;
import java.util.List;
import repository.IRepositoryCRUD;
import model.Categoria;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.sql.ResultSet;

public class CategoriaDAO extends Conexion implements IRepositoryCRUD<Categoria> {

    private Connection con;
    private CallableStatement cs;
    private ResultSet rs;

    @Override
    public boolean registrar(Categoria entidad) {

        String sql = "{call sp_crear_categoria(?,?)}";

        try {

            con = this.getConexion();
            cs = con.prepareCall(sql);

            cs.setString(1, entidad.getNombre());
            cs.setString(2, entidad.getDescripcion());

            cs.execute();

            return true;

        } catch (SQLException e) {

            System.out.println("Error registrar: " + e);

            return false;
        }

    }

    @Override
    public boolean actualizar(Categoria entidad) {

        String sql = "{call sp_actualizar_categoria(?,?,?)}";

        try {

            con = getConexion();

            cs = con.prepareCall(sql);

            cs.setInt(1, entidad.getId());
            cs.setString(2, entidad.getNombre());
            cs.setString(3, entidad.getDescripcion());

            cs.execute();

            return true;
        } catch (SQLException e) {

            System.out.println("Error actualizar: " + e);

            return false;
        }

    }

    @Override
    public boolean eliminar(int id) {

        String sql = "{call sp_eliminar_categoria(?)}";

        try {

            con = getConexion();

            cs = con.prepareCall(sql);

            cs.setInt(1, id);

            cs.execute();

            return true;

        } catch (SQLException e) {

            System.out.println("Error en eliminar categoria: " + e);

            return false;
        }

    }

    @Override
    public List<Categoria> listar() {

        String sql = "{call sp_listar_categorias()";
        List<Categoria> lista = new ArrayList<>();

        try {

            con = getConexion();
            cs = con.prepareCall(sql);
            rs = cs.executeQuery();

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("categoria_id"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));

                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error listar grupos: " + e);
        }

        return lista;
    }

    @Override
    public List<Categoria> filtrar(String params) {

        String sql = "{call sp_filtrar_categoria(?)}";
        List<Categoria> lista = new ArrayList<>();

        try {

            con = getConexion();
            cs = con.prepareCall(sql);
            cs.setString(1, params);
            rs = cs.executeQuery();

            while (rs.next()) {

                Categoria c = new Categoria();

                c.setId(rs.getInt("categoria_id"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));

                lista.add(c);

            }

        } catch (SQLException e) {

            System.out.println("Error filtrar: " + e);

        }

        return lista;
    }

    @Override
    public Categoria traerEntidad(int id) {

        String sql = "{call sp_traer_entidad(?)}";
        Categoria categoria = null;

        try {
            con = getConexion();
            cs = con.prepareCall(sql);
            cs.setInt(1, id);

            rs = cs.executeQuery();

            while (rs.next()) {
                categoria = new Categoria();

                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));

            }

        } catch (SQLException e) {

            System.out.println("Error en traer entidad");
        }

        return categoria;
    }

}
