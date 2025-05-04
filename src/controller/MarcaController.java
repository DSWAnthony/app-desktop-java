package controller;

import dao.CategoriaDAO;
import dao.MarcaDAO;
import model.Categoria;
import model.Marca;

import java.util.List;

public class MarcaController {

    private final MarcaDAO marcaDAO = new MarcaDAO();

    public List<Marca> listarMarcas() {
        return marcaDAO.listar();
    }

    public boolean registrarMarca(Marca marca) {
        return marcaDAO.registrar(marca);
    }

    public boolean actualizarMarca(Marca marca) {
        return marcaDAO.actualizar(marca);
    }

    public boolean eliminarMarca(int id) {
        return marcaDAO.eliminar(id);
    }

    public List<Marca> filtrarCategoria(String busqueda) {
        return marcaDAO.filtrar(busqueda);
    }

    public Marca traerCategoria(int id) {
        return marcaDAO.traerEntidad(id);
    }

}
