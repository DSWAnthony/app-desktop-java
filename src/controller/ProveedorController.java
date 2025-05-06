package controller;

import dao.ProveedorDAO;
import java.util.List;
import model.Proveedor;

public class ProveedorController {
    private final ProveedorDAO proveedor;

    public ProveedorController() {
        this.proveedor = new ProveedorDAO();
    }

    public List<Proveedor> listarProveedores() {
        return proveedor.listar();
    }

    public List<Proveedor> filtrarProveedores(String nombre) {
        return proveedor.filtrar(nombre);
    }

    public Proveedor obtenerProveedorPorId(int id) {
        return proveedor.traerEntidad(id);
    }

    public boolean registrarProveedor(Proveedor u) {
        return proveedor.registrar(u);
    }

    public boolean actualizarProveedor(Proveedor u) {
        return proveedor.actualizar(u);
    }

    public boolean eliminarProveedor(int id) {
        return proveedor.eliminar(id);
    }
}
