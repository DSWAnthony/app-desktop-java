/* Vista de Gestion de Proveedores */
/* Grupo G2 - ELITE SAC */
/* Modificado por: Alarcon Ruiz */

//Importando paquetes
package view;

//Instancia Controlador

//Importando librerias
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

//Clase VProveedor
public class FrmProveedor extends JInternalFrame{

    private static FrmProveedor instancia;
    //Creacion de componentes
    private JTextField txtbuscar, txtid, txtnombre, txtcontacto, txtdireccion,
                       txtemail, txttelefono, txtruc;
    private JLabel lblbuscar, lblid, lblprov, lblnombre, lblcontacto, 
                   lbldireccion, lblemail, lbltelefono, lblruc;
    private JButton btnactualizar, btninsertar, btneditar, btneliminar, 
                    btnregresar, btnlimpiar, btnnuevo;
    private JSeparator separador1, separador2, separador3, separador4;
    public JTable tablaproveedor;
    private JScrollPane scrollTabla;
    private DefaultTableModel tabla;
    
    //Creacion de vector
    String[] columnas = {"ID", "Nombre", "Contacto", "Direccion", 
                         "Email", "Telefono", "RUC"};
    
    //Constructor
    public FrmProveedor(){
        super("Proveedor", false, true, false, false);
        //Configuracion ventana
        setLayout(null);
        setTitle("GESTION DE PROVEEDORES");
        setResizable(false);
        setSize(800,600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        /* ------------------------------ */
        /*            BOTONES             */
        
        //Boton Actualizar
        btnactualizar = new JButton("ACTUALIZAR");
        btnactualizar.setBounds(300, 10, 110, 25);
        add(btnactualizar);
        
        //Boton Limpiar
        btnlimpiar = new JButton("LIMPIAR");
        btnlimpiar.setBounds(30, 120, 120, 30);
        add(btnlimpiar);
        
        //Boton Nuevo
        btnnuevo = new JButton("NUEVO");
        btnnuevo.setBounds(30, 180, 120, 30);
        add(btnnuevo);
        
        //Boton Insertar
        btninsertar = new JButton("INSERTAR");
        btninsertar.setBounds(20, 515, 120, 30);
        add(btninsertar);
        
        //Boton Editar
        btneditar = new JButton("EDITAR");
        btneditar.setBounds(160, 515, 120, 30);
        add(btneditar);
        
        //Boton Eliminar
        btneliminar = new JButton("ELIMINAR");
        btneliminar.setBounds(300, 515, 120, 30);
        add(btneliminar);
        
        //Boton Regresar a Menu
        btnregresar = new JButton("REGRESAR");
        btnregresar.setBounds(645, 515, 120, 30);
        add(btnregresar);
        
        
        /* ------------------------------ */
        /*         CAMPOS TEXTO           */
        
        //Barra de Busqueda
        txtbuscar = new JTextField();
        txtbuscar.setBounds(75, 12, 220, 20);
        add(txtbuscar);
        
        //Campo ID
        txtid = new JTextField();
        txtid.setBounds(55, 60, 90, 25);
        txtid.setEditable(false); //ID no editable
        add(txtid);
        
        //Campo Nombre
        txtnombre = new JTextField();
        txtnombre.setBounds(280, 110, 160, 25);
        add(txtnombre);
        
        //Campo Contacto
        txtcontacto = new JTextField();
        txtcontacto.setBounds(470, 110, 160, 25);
        add(txtcontacto);
        
        //Campo Direccion
        txtdireccion = new JTextField();
        txtdireccion.setBounds(280, 160, 350, 25);
        add(txtdireccion);
        
        //Campo E-Mail
        txtemail = new JTextField();
        txtemail.setBounds(280, 210, 350, 25);
        add(txtemail);
        
        //Campo Telefono
        txttelefono = new JTextField();
        txttelefono.setBounds(280, 260, 160, 25);
        add(txttelefono);
        
        //Campo RUC
        txtruc = new JTextField();
        txtruc.setBounds(470, 260, 160, 25);
        add(txtruc);
        
        
        /* ------------------------------ */
        /*           ETIQUETAS            */
        
        //Buscar
        lblbuscar = new JLabel("BUSCAR:");
        lblbuscar.setBounds(20, 8, 60, 25);
        add(lblbuscar);
        
        //ID
        lblid = new JLabel("ID");
        lblid.setBounds(30, 60, 100, 25);
        add(lblid);
        
        //Proveedor
        lblprov = new JLabel("DATOS DE PROVEEDOR/ES");
        lblprov.setBounds(380, 55, 160, 25);
        add(lblprov);
        
        //NOMBRE
        lblnombre = new JLabel("NOMBRE:");
        lblnombre.setBounds(280, 90, 100, 25);
        add(lblnombre);
        
        //CONTACTO
        lblcontacto = new JLabel("CONTACTO:");
        lblcontacto.setBounds(470, 90, 100, 25);
        add(lblcontacto);
        
        //DIRECCION
        lbldireccion = new JLabel("DIRECCION:");
        lbldireccion.setBounds(280, 140, 100, 25);
        add(lbldireccion);
        
        //EMAIL
        lblemail = new JLabel("EMAIL:");
        lblemail.setBounds(280, 190, 100, 25);
        add(lblemail);
        
        //TELEFONO
        lbltelefono = new JLabel("TELEFONO:");
        lbltelefono.setBounds(280, 240, 100, 25);
        add(lbltelefono);
        
        //RUC
        lblruc = new JLabel("RUC:");
        lblruc.setBounds(470, 240, 100, 25);
        add(lblruc);
        
        
        /* ------------------------------ */
        /*          SEPARADORES           */
        
        //Separador superior
        separador1 = new JSeparator();
        separador1.setBounds(20, 45, 740, 10);
        add(separador1);
        
        //Separador inferior
        separador2 = new JSeparator();
        separador2.setBounds(20, 495, 740, 10);
        add(separador2);
        
        //Separador ID 1
        separador3 = new JSeparator(SwingConstants.VERTICAL);
        separador3.setBounds(160, 44, 1, 58);
        add(separador3);
        
        //Separador ID 2
        separador4 = new JSeparator();
        separador4.setBounds(20, 100, 140, 10);
        add(separador4);
        
        
        /* ------------------------------ */
        /*             TABLA              */
        
        //Tabla dentro de ScrollPane
        tabla = new DefaultTableModel(columnas, 0);
        tablaproveedor = new JTable();
        tablaproveedor.setModel(tabla);
        scrollTabla = new JScrollPane(getTablaproveedor());
        scrollTabla.setBounds(20, 300, 745, 180);
        add(scrollTabla);
        
        
    }
    
    //Getters y Setters -> Para obtener datos
    /***************************************/
        
        //Barra Buscar
    public JTextField getTxtbuscar() {
        return txtbuscar;
    }

    public void setTxtbuscar(JTextField txtbuscar) {
        this.txtbuscar = txtbuscar;
    }


    //Campo ID
    public JTextField getTxtid() {
        return txtid;
    }
    
    public void setTxtid(JTextField txtid) {
        this.txtid = txtid;
    }


    //Campo Nombre
    public JTextField getTxtnombre() {
        return txtnombre;
    }

    public void setTxtnombre(JTextField txtnombre) {
        this.txtnombre = txtnombre;
    }


    //Campo Contacto
    public JTextField getTxtcontacto() {
        return txtcontacto;
    }

    public void setTxtcontacto(JTextField txtcontacto) {
        this.txtcontacto = txtcontacto;
    }


    //Campo Direccion
    public JTextField getTxtdireccion() {
        return txtdireccion;
    }

    public void setTxtdireccion(JTextField txtdireccion) {
        this.txtdireccion = txtdireccion;
    }


    //Campo Email
    public JTextField getTxtemail() {
        return txtemail;
    }

    public void setTxtemail(JTextField txtemail) {
        this.txtemail = txtemail;
    }


    //Campo Telefono
    public JTextField getTxttelefono() {
        return txttelefono;
    }

    public void setTxttelefono(JTextField txttelefono) {
        this.txttelefono = txttelefono;
    }


    //Campo RUC
    public JTextField getTxtruc() {
        return txtruc;
    }

    public void setTxtruc(JTextField txtruc) {
        this.txtruc = txtruc;
    }


    //Boton Actualizar
    public JButton getBtnactualizar() {
        return btnactualizar;
    }

    public void setBtnactualizar(JButton btnactualizar) {
        this.btnactualizar = btnactualizar;
    }


    //Boton Insertar
    public JButton getBtninsertar() {
        return btninsertar;
    }

    public void setBtninsertar(JButton btninsertar) {
        this.btninsertar = btninsertar;
    }


    //Boton Editar
    public JButton getBtneditar() {
        return btneditar;
    }

    public void setBtneditar(JButton btneditar) {
        this.btneditar = btneditar;
    }


    //Boton Eliminar
    public JButton getBtneliminar() {
        return btneliminar;
    }

    public void setBtneliminar(JButton btneliminar) {
        this.btneliminar = btneliminar;
    }

    
    //Boton Regresar a Menu
    public JButton getBtnregresar() {
        return btnregresar;
    }

    public void setBtnregresar(JButton btnregresar) {
        this.btnregresar = btnregresar;
    }


    //Boton Limpiar
    public JButton getBtnlimpiar() {
        return btnlimpiar;
    }

    public void setBtnlimpiar(JButton btnlimpiar) {
        this.btnlimpiar = btnlimpiar;
    }


    //Boton Nuevo
    public JButton getBtnnuevo() {
        return btnnuevo;
    }

    public void setBtnnuevo(JButton btnnuevo) {
        this.btnnuevo = btnnuevo;
    }


    //Tabla
    public DefaultTableModel getTabla() {
        return tabla;
    }

    public void setTabla(DefaultTableModel tabla) {
        this.tabla = tabla;
    }
    
    //Tabla Proveedor
    public JTable getTablaproveedor() {
        return tablaproveedor;
    }

    public void setTablaproveedor(JTable tablaproveedor) {
        this.tablaproveedor = tablaproveedor;
    }

    public static FrmProveedor getInstancia() {
        if (instancia == null || instancia.isClosed()) {
            instancia = new FrmProveedor();
        }
        return instancia;
    }
    
}
