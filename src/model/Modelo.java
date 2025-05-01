
package model;


public class Modelo {
    private Integer id;
    private String genero;
    private String nombre;
    private Categoria categoria;
    private Marca marca;

    public Modelo() {
    }
//modelo
    public Modelo(int id, String genero, String nombre, Categoria categoria, Marca marca) {
        this.id = id;
        this.genero = genero;
        this.nombre = nombre;
        this.categoria = categoria;
        this.marca = marca;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Modelo{" + "id=" + id + ", genero=" + genero + ", nombre=" + nombre + ", categoria=" + categoria + ", marca=" + marca + '}';
    }
    
    
}
