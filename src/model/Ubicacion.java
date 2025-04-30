package model;
public class Ubicacion {
    private int ubicacionId;
    private String contenedor;
    private String estante;
    private String pasillo;


    public Ubicacion() {}

    // Constructor lleno (con ID)
    public Ubicacion(int ubicacionId, String contenedor, String estante, String pasillo) {
        this.ubicacionId = ubicacionId;
        this.contenedor = contenedor;
        this.estante = estante;
        this.pasillo = pasillo;
    }

    // Constructor sin ID (para agregar nuevas ubicaciones)
    public Ubicacion(String contenedor, String estante, String pasillo) {
        this.contenedor = contenedor;
        this.estante = estante;
        this.pasillo = pasillo;
    }

    // Getters y Setters
    public int getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(int ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public String getContenedor() {
        return contenedor;
    }

    public void setContenedor(String contenedor) {
        this.contenedor = contenedor;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public String getPasillo() {
        return pasillo;
    }

    public void setPasillo(String pasillo) {
        this.pasillo = pasillo;
    }
}



