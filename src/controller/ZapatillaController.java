package controller;

import dao.ZapatillaDAO;
import java.util.List;
import model.Zapatilla;

public class ZapatillaController {
    private final ZapatillaDAO zapatillaDao;

    public ZapatillaController() {
        this.zapatillaDao = new ZapatillaDAO();
    }
    
    public List<Zapatilla> listarZapatillas() {
        return zapatillaDao.listar();
    }
    
    public boolean registrarZapatilla(Zapatilla zapatilla){
        return zapatillaDao.registrar(zapatilla);
    }
    
    public boolean actualizarZapatilla(Zapatilla zapatilla){
        return zapatillaDao.actualizar(zapatilla);
    }
    
    public boolean eliminarZapatilla(String sku){
        return zapatillaDao.eliminarPorSku(sku);
    }
}
 