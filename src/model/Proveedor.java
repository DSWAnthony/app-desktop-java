/* MProveedor de Tabla Proveedor */
/* Grupo G2 - ELITE SAC */
/* Modificado por: Alarcon Ruiz */

//Importando paquetes
package model;


//Clase MProveedor
public class Proveedor {

    //Creacion de variables "plantilla"
    private int proveedorId;
    private String nombre;
    private String contacto;
    private String direccion;
    private String email;
    private String telefono;
    private String ruc;
    
    //Constructor
    public Proveedor(){
        //
    }
    
    public Proveedor(String xnombre, String xcontacto, String xdireccion, 
                      String xemail, String xtelefono, String xruc){
        
        //Importando los datos a las variables
        this.nombre      = xnombre;
        this.contacto    = xcontacto;
        this.direccion   = xdireccion;
        this.email       = xemail;
        this.telefono    = xtelefono;
        this.ruc         = xruc;
                
    }
    
    //Nota: ID es autoincrementable
    public Proveedor(int xproveedorid, String xnombre, String xcontacto, String xdireccion, 
                      String xemail, String xtelefono, String xruc){
        
        //Importando los datos a las variables
        this.proveedorId = xproveedorid;
        this.nombre      = xnombre;
        this.contacto    = xcontacto;
        this.direccion   = xdireccion;
        this.email       = xemail;
        this.telefono    = xtelefono;
        this.ruc         = xruc;
                
    }
    
    //Getters y Setters
    /***************************************/
    
    //PROVEEDOR ID
    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int xproveedorid) {
        this.proveedorId = xproveedorid;
    }


    
    //NOMBRE
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String xnombre) {
        this.nombre = xnombre;
    }

    

    //CONTACTO
    public String getContacto() {
        return contacto;
    }

    public void setContacto(String xcontacto) {
        this.contacto = xcontacto;
    }


    
    //DIRECCION
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String xdireccion) {
        this.direccion = xdireccion;
    }


    
    //EMAIL
    public String getEmail() {
        return email;
    }

    public void setEmail(String xemail) {
        this.email = xemail;
    }


    
    //TELEFONO
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String xtelefono) {
        this.telefono = xtelefono;
    }

    
    
    //RUC
    public String getRuc() {
        return ruc;
    }
    
        public void setRuc(String xruc) {
        this.ruc = xruc;
    }
        
    /***************************************/
    
}
