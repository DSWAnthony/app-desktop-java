package model;

public class Zapatilla {
    private int id;
    private String color;
    private double costo;
    private String sku;
    private double talla;
    private double precio;
    private Modelo modelo;

    public Zapatilla() {
    }

    public Zapatilla(int id, String color, double costo, String sku, double talla, double precio, Modelo modelo) {
        this.id = id;
        this.color = color;
        this.costo = costo;
        this.sku = sku;
        this.talla = talla;
        this.precio = precio;
        this.modelo = modelo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "Zapatilla{" + "id=" + id + ", color=" + color + ", costo=" + costo + ", sku=" + sku + ", talla=" + talla + ", precio=" + precio + ", modelo=" + modelo + '}';
    }
    
    
}
