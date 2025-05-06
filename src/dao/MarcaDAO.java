package dao;

import config.Conexion;
import model.Categoria;
import model.Marca;
import repository.IRepositoryCRUD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO extends Conexion implements IRepositoryCRUD<Marca> {

    private Connection con;
    private CallableStatement cs;
    private ResultSet rs;

    @Override
    public boolean registrar(Marca entidad) {
        String sql = "{call sp_registrar_marca(?)}";

        try {

            con = this.getConexion();
            cs = con.prepareCall(sql);

            cs.setString(1, entidad.getNombre());

            cs.execute();

            return true;

        } catch (SQLException e) {

            System.out.println("Error registrar: " + e);

            return false;
        }
    }

    @Override
    public boolean actualizar(Marca entidad) {
        String sql = "{call sp_actualizar_marca(?,?)}";

        try {

            con = getConexion();

            cs = con.prepareCall(sql);

            cs.setInt(1, entidad.getId());
            cs.setString(2, entidad.getNombre());

            cs.execute();

            return true;
        } catch (SQLException e) {

            System.out.println("Error actualizar: " + e);

            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "{call sp_eliminar_marca(?)}";

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
    public List<Marca> listar() {
        String sql = "{call sp_listar_marcas()}";
        List<Marca> lista = new ArrayList<>();

        try {

            con = getConexion();
            cs = con.prepareCall(sql);
            rs = cs.executeQuery();

            while (rs.next()) {
                Marca c = new Marca();
                c.setId(rs.getInt("marca_id"));
                c.setNombre(rs.getString("nombre"));

                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error listar grupos: " + e);
        }

        return lista;
    }

    @Override
    public List<Marca> filtrar(String params) {
        String sql = "{call sp_filtrar_marca(?)}";
        List<Marca> lista = new ArrayList<>();

        try {

            con = getConexion();
            cs = con.prepareCall(sql);
            cs.setString(1, params);
            rs = cs.executeQuery();

            while (rs.next()) {

                Marca c = new Marca();

                c.setId(rs.getInt("marca_id"));
                c.setNombre(rs.getString("nombre"));

                lista.add(c);

            }

        } catch (SQLException e) {

            System.out.println("Error filtrar: " + e);

        }

        return lista;
    }

    @Override
    public Marca traerEntidad(int id) {
        String sql = "{call sp_traer_entidad_marca(?)}";
        Marca marca = null;

        try {
            con = getConexion();
            cs = con.prepareCall(sql);
            cs.setInt(1, id);

            rs = cs.executeQuery();

            while (rs.next()) {
                marca = new Marca();

                marca.setId(rs.getInt("marca_id"));
                marca.setNombre(rs.getString("nombre"));

            }

        } catch (SQLException e) {

            System.out.println("Error en traer entidad");
        }

        return marca;

    }
}
