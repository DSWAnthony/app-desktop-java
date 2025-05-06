package model;

public class Inventario {
    private int inventarioId;
    private int zapatoId;
    private int ubicacionId;
    private int cantidadActual;

    
    private String sku;
    private String modelo;
    private String talla;
    private String color;
    private String marca;
    private String almacen; 

    public Inventario() {
    }

    public Inventario(int inventarioId, int zapatoId, int ubicacionId, int cantidadActual, String sku,
                  String modelo, String talla, String color, String marca, String almacen)
{
    this.inventarioId = inventarioId;
    this.zapatoId = zapatoId; 
    this.sku = sku;
    this.ubicacionId = ubicacionId;
    this.cantidadActual = cantidadActual;
    this.modelo = modelo;
    this.talla = talla;
    this.color = color;
    this.marca = marca;
    this.almacen = almacen;
}



    public int getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(int inventarioId) {
        this.inventarioId = inventarioId;
    }

    public int getZapatoId() {
        return zapatoId;
    }

    public void setZapatoId(int zapatoId) {
        this.zapatoId = zapatoId;
    }

    public int getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(int ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }
}
