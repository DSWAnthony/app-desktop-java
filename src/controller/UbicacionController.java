package controller;

import dao.UbicacionDAO;
import java.util.List;
import model.Ubicacion;

public class UbicacionController {
    private final UbicacionDAO ubicacionDao;

    public UbicacionController() {
        this.ubicacionDao = new UbicacionDAO();
    }

    public List<Ubicacion> listarUbicaciones() {
        return ubicacionDao.listar();
    }

    public List<Ubicacion> filtrarUbicaciones(String nombre) {
        return ubicacionDao.filtrar(nombre);
    }

    public Ubicacion obtenerUbicacionPorId(int id) {
        return ubicacionDao.traerEntidad(id);
    }

    public boolean registrarUbicacion(Ubicacion u) {
        return ubicacionDao.registrar(u);
    }

    public boolean actualizarUbicacion(Ubicacion u) {
        return ubicacionDao.actualizar(u);
    }

    public boolean eliminarUbicacion(int id) {
        return ubicacionDao.eliminar(id);
    }
}