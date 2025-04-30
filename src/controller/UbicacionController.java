package controller;

import dao.UbicacionDAO;
import model.Ubicacion;
import view.FrmUbicacion;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class UbicacionController {

    private FrmUbicacion vista;  // Vista
    private UbicacionDAO dao;     // DAO

    public UbicacionController(FrmUbicacion vista) {
        this.vista = vista;
        this.dao = new UbicacionDAO();
        iniciarControl();
    }

    private void iniciarControl() {
        // Configurar el modelo de la tabla
        String[] columnas = {"ID", "Contenedor", "Estante", "Pasillo"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        vista.getTable().setModel(modelo);

        // Asociar los botones de la vista con sus respectivos métodos
        vista.getBtnAgregar().addActionListener(e -> agregarUbicacion());
        vista.getBtnActualizar().addActionListener(e -> actualizarUbicacion());
        vista.getBtnEliminar().addActionListener(e -> eliminarUbicacion());
        vista.getBtnLimpiar().addActionListener(e -> limpiarCampos());
        vista.getTable().addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int fila = vista.getTable().getSelectedRow();
        if (fila != -1) {
            vista.getTxtID().setText(vista.getTable().getValueAt(fila, 0).toString());
            vista.getTxtContenedor().setText(vista.getTable().getValueAt(fila, 1).toString());
            vista.getTxtEstante().setText(vista.getTable().getValueAt(fila, 2).toString());
            vista.getTxtPasillo().setText(vista.getTable().getValueAt(fila, 3).toString());
        }
    }
});
        // Cargar las ubicaciones cuando la ventana se inicia
        cargarUbicaciones();
        
    }

    private void agregarUbicacion() {
    // Obtener los datos de los campos de texto
    String contenedor = vista.getTxtContenedor().getText();
    String estante = vista.getTxtEstante().getText();
    String pasillo = vista.getTxtPasillo().getText();

    // Validar que no estén vacíos
    if (contenedor.isEmpty() || estante.isEmpty() || pasillo.isEmpty()) {
        vista.mostrarMensaje("Por favor, completa todos los campos.");
        return;
    }

    // Crear un objeto Ubicacion con el constructor adecuado (sin ID)
    Ubicacion ubicacion = new Ubicacion(contenedor, estante, pasillo);

   
    if (dao.insertar(ubicacion)) {
        vista.mostrarMensaje("Ubicación agregada correctamente");
        vista.limpiarCampos();  
        cargarUbicaciones();    // Recargar las ubicaciones en la tabla
    } else {
        vista.mostrarMensaje("Error al agregar la ubicación");
    }
}

    private void actualizarUbicacion() {
        int filaSeleccionada = vista.getTable().getSelectedRow();
        
        // Validar si se ha seleccionado una fila
        if (filaSeleccionada == -1) {
            vista.mostrarMensaje("Selecciona una ubicación para actualizar");
            return;
        }

        // Obtener el ID de la ubicación seleccionada de la tabla
        int id = Integer.parseInt(vista.getTable().getValueAt(filaSeleccionada, 0).toString());
        
        System.out.println(id);
        // Obtener los datos de los campos de texto
        String contenedor = vista.getTxtContenedor().getText();
        String estante = vista.getTxtEstante().getText();
        String pasillo = vista.getTxtPasillo().getText();

        // Validar que no estén vacíos
        if (contenedor.isEmpty() || estante.isEmpty() || pasillo.isEmpty()) {
            vista.mostrarMensaje("Por favor, completa todos los campos.");
            return;
        }

        // Crear un objeto Ubicacion con los nuevos datos
        Ubicacion ubicacion = new Ubicacion(id, contenedor, estante, pasillo);

        // Actualizar la ubicación a través del DAO
        if (dao.actualizar(ubicacion)) {
            vista.mostrarMensaje("Ubicación actualizada correctamente");
            vista.limpiarCampos();  // Limpiar los campos de texto
            cargarUbicaciones();    // Recargar las ubicaciones en la tabla
        } else {
            vista.mostrarMensaje("Error al actualizar la ubicación");
        }
    }

    private void eliminarUbicacion() {
        int filaSeleccionada = vista.getTable().getSelectedRow();
        
        // Validar si se ha seleccionado una fila
        if (filaSeleccionada == -1) {
            vista.mostrarMensaje("Selecciona una ubicación para eliminar");
            return;
        }

        // Obtener el ID de la ubicación seleccionada de la tabla
        int id = Integer.parseInt(vista.getTable().getValueAt(filaSeleccionada, 0).toString());

        // Eliminar la ubicación a través del DAO
        if (dao.eliminar(id)) {
            vista.mostrarMensaje("Ubicación eliminada correctamente");
            cargarUbicaciones();    // Recargar las ubicaciones en la tabla
        } else {
            vista.mostrarMensaje("Error al eliminar la ubicación");
        }
    }

    private void limpiarCampos() {
        
        vista.limpiarCampos();
    }

    private void cargarUbicaciones() {
        // Obtener la lista de ubicaciones desde el DAO
        List<Ubicacion> ubicaciones = dao.listar();

        // Obtener el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) vista.getTable().getModel();
        modelo.setRowCount(0); // Limpiar la tabla

        // Llenar la tabla con los datos de las ubicaciones
        for (Ubicacion ubicacion : ubicaciones) {
            modelo.addRow(new Object[] {
                ubicacion.getUbicacionId(),
                ubicacion.getContenedor(),
                ubicacion.getEstante(),
                ubicacion.getPasillo()
            });
        }
    }
}
