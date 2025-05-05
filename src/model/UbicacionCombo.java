
package model;

public class UbicacionCombo {
    private int id;
    private String nombre;

    public UbicacionCombo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre; 
    }
}

