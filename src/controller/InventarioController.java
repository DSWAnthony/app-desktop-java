package controller;

import dao.InventarioDAO;
import model.Inventario;
import java.util.List;

public class InventarioController {
    private final InventarioDAO inventarioDao;

    public InventarioController() {
        this.inventarioDao = new InventarioDAO();
    }


    public List<Inventario> listarInventarios() {
        return inventarioDao.listar();
    }

   
    public List<Inventario> filtrarInventarios(String parametro) {
        return inventarioDao.filtrar(parametro);
    }

 
    public Inventario obtenerInventarioPorId(int id) {
        return inventarioDao.traerEntidad(id);
    }

   
    public boolean registrarInventario(Inventario i) {
        return inventarioDao.registrar(i);
    }

 
    public boolean actualizarInventario(Inventario i) {
        return inventarioDao.actualizar(i);
    }

    
    public boolean eliminarInventario(int id) {
        return inventarioDao.eliminar(id);
    }
}
