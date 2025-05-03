package controller;

import dao.CategoriaDAO;
import java.util.List;
import model.Categoria;

public class CategoriaController {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public List<Categoria> listarCategorias() {
        return categoriaDAO.listar();
    }

    public boolean registrarCategoria(Categoria categoria) {
        return categoriaDAO.registrar(categoria);
    }

    public boolean actualizarCategoria(Categoria categoria) {
        return categoriaDAO.actualizar(categoria);
    }

    public boolean eliminarCategoria(int id) {
        return categoriaDAO.eliminar(id);
    }

    public List<Categoria> filtrarCategoria(String busqueda) {
        return categoriaDAO.filtrar(busqueda);
    }

    public Categoria traerCategoria(int id) {
        return categoriaDAO.traerEntidad(id);
    }
}
