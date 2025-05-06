/* Controlador de Gestion de Proveedores */
/* Grupo G2 - ELITE SAC */
/* Modificado por: Alarcon Ruiz */

//Importando paquetes
package controller;

//Importando librerias
import view.FrmProveedor;
import model.Proveedor;
import dao.ProveedorDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;

//Clase CProveedor
public class CProveedor implements ActionListener, MouseListener{

    //Instancia de Modelo y Vista
    private FrmProveedor vista;
    private ProveedorDAO dao;
    private DefaultTableModel modelotabla;
    
    //Constructor
    public CProveedor(FrmProveedor vista, ProveedorDAO dao){
        
        //Instancia vista
        this.vista = vista;
        
        this.dao = new ProveedorDAO();
        
        //Acciones de botones
        this.vista.getBtninsertar().addActionListener(this);
        this.vista.getBtneditar().addActionListener(this);
        this.vista.getBtneliminar().addActionListener(this);
        this.vista.getBtnlimpiar().addActionListener(this);
        this.vista.getBtnnuevo().addActionListener(this);
        this.vista.getBtnactualizar().addActionListener(this);
        this.vista.getBtnregresar().addActionListener(this);
        
        //Evento tabla
        this.vista.tablaproveedor.addMouseListener(this);
        
        listarProveedores();
        
        
        
        //Iniciando metodo de barra de busqueda
        this.vista.getTxtbuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarPorNombreOId();
            }
        });
        
        //Bloqueo de campos
        bloquearCampos();
        
    }
    
    
    
    
    /* ------------------------------ */
    /*            EVENTOS             */

    //Sobrecarga de eventos de botones
    //Accion en desarrollo
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.getBtninsertar()) {
            registrarProveedor();
        
        } else if (e.getSource() == vista.getBtneditar()) {
            editarProveedor();

        } else if (e.getSource() == vista.getBtneliminar()) {
            eliminarProveedor();

        } else if (e.getSource() == vista.getBtnlimpiar()) {
            limpiarCampos();

        } else if (e.getSource() == vista.getBtnnuevo()) {
            //Funcion
            prepararNuevo();
            
            //Desbloqueo
            desbloquearCampos();

        } else if (e.getSource() == vista.getBtnactualizar()) {
            actualizarBusqueda();

        } else if (e.getSource() == vista.getBtnregresar()) {
            vista.dispose(); //Lleva a MENU
        }
        
    }

    //Accion de click
    @Override
    public void mouseClicked(MouseEvent e) {
        
        int fila = vista.tablaproveedor.getSelectedRow();
        if (fila != -1) {
            
            vista.getTxtid().setText(vista.tablaproveedor.getValueAt(fila, 0).toString());
            vista.getTxtnombre().setText(vista.tablaproveedor.getValueAt(fila, 1).toString());
            vista.getTxtcontacto().setText(vista.tablaproveedor.getValueAt(fila, 2).toString());
            vista.getTxtdireccion().setText(vista.tablaproveedor.getValueAt(fila, 3).toString());
            vista.getTxtemail().setText(vista.tablaproveedor.getValueAt(fila, 4).toString());
            vista.getTxttelefono().setText(vista.tablaproveedor.getValueAt(fila, 5).toString());
            vista.getTxtruc().setText(vista.tablaproveedor.getValueAt(fila, 6).toString());
            
        }
        
        //Activa campos
        desbloquearCampos();
        
    }

    //Accion de seleccion
    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    //Accion de soltar opcion
    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    
    //Accion de entrada
    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
    
    
    
    /* ------------------------------ */
    /*           FUNCIONES            */
    
    //------ Funciones CRUD ------
    //Funcion CREATE
    private void registrarProveedor() {
        
        if (validarCampos()) {
            Proveedor nuevo = new Proveedor(
                    
                vista.getTxtnombre().getText(),
                vista.getTxtcontacto().getText(),
                vista.getTxtdireccion().getText(),
                vista.getTxtemail().getText(),
                vista.getTxttelefono().getText(),
                vista.getTxtruc().getText()
                    
            );
            
            dao.registrar(nuevo);
            JOptionPane.showMessageDialog(vista, "¡Proveedor registrado correctamente!");
            
            //Validacion
            listarProveedores();
            limpiarCampos();
            bloquearCampos();
            
        }
        
    }
    
    
    //Funcion READ
    private void listarProveedores(){
        
        modelotabla = (DefaultTableModel) vista.getTablaproveedor().getModel();
        modelotabla.setRowCount(0); //Limpiar tabla
        List<Proveedor> lista = dao.listar();
        
        for (Proveedor proveedor : lista) {
            Object[] fila = {
                proveedor.getProveedorId(),
                proveedor.getNombre(),
                proveedor.getContacto(),
                proveedor.getDireccion(),
                proveedor.getEmail(),
                proveedor.getTelefono(),
                proveedor.getRuc()
            };
            modelotabla.addRow(fila);
        }
  
    }
    
    
    //Funcion UPDATE
    private void editarProveedor() {
        
        if (vista.getTxtid().getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(vista, "Seleccione un proveedor de la tabla.");
            return;
            
        }
        
        if (validarCampos()) {
            
            Proveedor actualizado = new Proveedor(
                Integer.parseInt(vista.getTxtid().getText()),
                vista.getTxtnombre().getText(),
                vista.getTxtcontacto().getText(),
                vista.getTxtdireccion().getText(),
                vista.getTxtemail().getText(),
                vista.getTxttelefono().getText(),
                vista.getTxtruc().getText()
                    
            );
            
            dao.actualizar(actualizado);
            JOptionPane.showMessageDialog(vista, "¡Proveedor actualizado correctamente!");
            listarProveedores();
            limpiarCampos();
            bloquearCampos();
            
        }
    }
    
    
    //Funcion DELETE
    private void eliminarProveedor() {
        
        int fila = vista.tablaproveedor.getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un Proveedor a eliminar...");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(vista, "¿Está seguro/a de eliminar este Proveedor?");
        
        if (confirm == JOptionPane.YES_OPTION) {
            
            int id = Integer.parseInt(vista.getTxtid().getText());
            dao.eliminar(id);
            JOptionPane.showMessageDialog(vista, "¡Proveedor eliminado correctamente!");
            listarProveedores();
            limpiarCampos();
            bloquearCampos();
            
        }
 
    }
    
    
    //------ Funciones Adicionales ------
    //Funcion Limpiar
    private void limpiarCampos() {
        
        vista.getTxtid().setText("");
        vista.getTxtnombre().setText("");
        vista.getTxtcontacto().setText("");
        vista.getTxtdireccion().setText("");
        vista.getTxtemail().setText("");
        vista.getTxttelefono().setText("");
        vista.getTxtruc().setText("");
        
    }
    
    
    //Funcion Validacion
    private boolean validarCampos(){
        
        if (vista.getTxtnombre().getText().isEmpty()
         || vista.getTxtcontacto().getText().isEmpty()
         || vista.getTxtdireccion().getText().isEmpty()
         || vista.getTxtemail().getText().isEmpty()
         || vista.getTxttelefono().getText().isEmpty()
         || vista.getTxtruc().getText().isEmpty()) {

            JOptionPane.showMessageDialog(vista, "¡Debe completar todos los campos!");
            return false;
        }
        return true;
        
    }
    
    
    //Funcion Actualizar
    private void actualizarBusqueda() {
        
        filtrarPorNombreOId();
        listarProveedores();
        
    }
    
    
    //Funcion NUEVO
    private void prepararNuevo() {
        
        limpiarCampos();
        desbloquearCampos();
        JOptionPane.showMessageDialog(vista, "Formulario listo para nuevo proveedor.");
        
    }
    
    //Funcion Desbloquear Campos
    private void desbloquearCampos(){
        
        vista.getTxtnombre().setEnabled(true);
        vista.getTxtcontacto().setEnabled(true);
        vista.getTxtdireccion().setEnabled(true);
        vista.getTxtemail().setEnabled(true);
        vista.getTxttelefono().setEnabled(true);
        vista.getTxtruc().setEnabled(true);
        
    }
    
    //Funcion Bloquear Campos
    private void bloquearCampos(){
        
        vista.getTxtid().setEnabled(false);
        vista.getTxtnombre().setEnabled(false);
        vista.getTxtcontacto().setEnabled(false);
        vista.getTxtdireccion().setEnabled(false);
        vista.getTxtemail().setEnabled(false);
        vista.getTxttelefono().setEnabled(false);
        vista.getTxtruc().setEnabled(false);
        
    }
    
    
    //Funcion BUSCAR
    private void filtrarPorNombreOId() {
        String texto = vista.getTxtbuscar().getText().trim();
        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaproveedor().getModel();
        modelo.setRowCount(0); // Limpiar tabla

        //Interpretamos el texto a ID
        try {
            int id = Integer.parseInt(texto);
            Proveedor proveedor = dao.traerEntidad(id);

            if (proveedor != null) {
                modelo.addRow(new Object[]{
                    proveedor.getProveedorId(),
                    proveedor.getNombre(),
                    proveedor.getContacto(),
                    proveedor.getDireccion(),
                    proveedor.getEmail(),
                    proveedor.getTelefono(),
                    proveedor.getRuc()
                });
            }

        } catch (NumberFormatException e) {
            //En caso de que el ID sea un nombre
            List<Proveedor> lista = dao.filtrar(texto);

            for (Proveedor proveedor : lista) {
                modelo.addRow(new Object[]{
                    proveedor.getProveedorId(),
                    proveedor.getNombre(),
                    proveedor.getContacto(),
                    proveedor.getDireccion(),
                    proveedor.getEmail(),
                    proveedor.getTelefono(),
                    proveedor.getRuc()
                });
            }
        }
    }

    
}
