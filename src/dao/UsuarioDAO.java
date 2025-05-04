package dao;

import config.Conexion;
import model.Usuario;
import repository.IRepositoryValidar;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UsuarioDAO implements IRepositoryValidar<Usuario> {

    private Connection con;
    private CallableStatement cs;


    @Override
    public boolean validarCuenta(Usuario entidad) {
        boolean existe = false;

        try {

            con = Conexion.getConexion();
            cs= con.prepareCall("{call verificar_usuario(?, ?, ?)}");

            cs.setString(1, entidad.getUser());
            cs.setString(2, entidad.getPassword());
            cs.registerOutParameter(3, Types.INTEGER);

            cs.execute();

            int resultado = cs.getInt(3);
            existe = resultado > 0;

        } catch (SQLException e) {
            System.out.println("Error al verificar el usuario: " + e.getMessage());
        }

        return existe;
    }
}
