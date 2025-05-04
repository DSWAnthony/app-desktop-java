package controller;

import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean validarCuenta(Usuario entidad) {
        return usuarioDAO.validarCuenta(entidad);
    }
}
