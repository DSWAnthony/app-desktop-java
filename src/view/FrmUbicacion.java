package view;

import controller.UbicacionController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import model.Ubicacion;

public class FrmUbicacion extends JInternalFrame {

    private static FrmUbicacion instancia;
    private JTextField txtContenedor, txtEstante, txtPasillo, txtBuscar;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tbUbicaciones;
    private DefaultTableModel modeloTabla;
    private UbicacionController ubicacionController;
    private void agregarEventos() {
        btnRegistrar.addActionListener((ActionEvent e) -> {

            String contenedor = txtContenedor.getText();
            String estante = txtEstante.getText();
            String pasillo = txtPasillo.getText();

            Ubicacion u = new Ubicacion();
    u.setContenedor(contenedor);
    u.setEstante(estante);
    u.setPasillo(pasillo);

    ubicacionController.registrarUbicacion(u);
            modeloTabla.setRowCount(0); // Limpiar tabla
            cargarTabla(modeloTabla);   // Volver a cargar
            limpiarCampos();
        });

        btnActualizar.addActionListener((ActionEvent e) -> {

            int fila = tbUbicaciones.getSelectedRow();
            if (fila != -1) {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                String contenedor = txtContenedor.getText();
                String estante = txtEstante.getText();
                String pasillo = txtPasillo.getText();

                Ubicacion u = new Ubicacion();
                u.setUbicacionId(id);
                u.setContenedor(contenedor);
                u.setEstante(estante);
                u.setPasillo(pasillo);

                ubicacionController.actualizarUbicacion(u);

                modeloTabla.setRowCount(0);
                cargarTabla(modeloTabla);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            }
        });

        btnEliminar.addActionListener((ActionEvent e) -> {
            int fila = tbUbicaciones.getSelectedRow();
            if (fila != -1) {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                ubicacionController.eliminarUbicacion(id);
                modeloTabla.setRowCount(0);
                cargarTabla(modeloTabla);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            }
        });

        btnLimpiar.addActionListener((ActionEvent e) -> {
            limpiarCampos();
        });
    }

    private void limpiarCampos() {
        txtContenedor.setText("");
        txtEstante.setText("");
        txtPasillo.setText("");
    }

    public FrmUbicacion() {
        super("Ubicaciones", false, true, false, false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        ubicacionController = new UbicacionController();

        add(crearFormulario());
        add(crearTabla());

        agregarEventos(); 

        setSize(800, 600);
    }


    private JPanel crearFormulario() {
        JPanel panel = new JPanel(null);
        panel.setBounds(10, 10, 760, 160);

        int x = 30, y = 25, labelW = 80, fieldW = 120, h = 25, gap = 10;
        int col2X = x + labelW + fieldW + 40;

        // Campos columna 1
        JLabel lblContenedor = new JLabel("Contenedor:");
        lblContenedor.setBounds(x, y, labelW, h);
        txtContenedor = new JTextField();
        txtContenedor.setBounds(x + labelW, y, fieldW, h);

        JLabel lblEstante = new JLabel("Estante:");
        lblEstante.setBounds(x, y + (h + gap), labelW, h);
        txtEstante = new JTextField();
        txtEstante.setBounds(x + labelW, y + (h + gap), fieldW, h);

        JLabel lblPasillo = new JLabel("Pasillo:");
        lblPasillo.setBounds(x, y + 2*(h + gap), labelW, h);
        txtPasillo = new JTextField();
        txtPasillo.setBounds(x + labelW, y + 2*(h + gap), fieldW, h);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(col2X, y, 100, h);
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(col2X, y + (h + gap), 100, h);
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(col2X, y + 2*(h + gap), 100, h);
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(col2X + 120, y + 2*(h + gap), 100, h);

        panel.add(lblContenedor);
        panel.add(txtContenedor);
        panel.add(lblEstante);
        panel.add(txtEstante);
        panel.add(lblPasillo);
        panel.add(txtPasillo);
        panel.add(btnRegistrar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);

        panel.setBorder(BorderFactory.createTitledBorder("Registro de Ubicación"));
        return panel;
    }

    

    private JScrollPane crearTabla() {
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Contenedor", "Estante", "Pasillo"}, 0);
        tbUbicaciones = new JTable(modeloTabla);

        cargarTabla(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tbUbicaciones);
        scrollPane.setBounds(10, 240, 760, 300);  
        return scrollPane;
    }

    private void cargarTabla(DefaultTableModel model) {
        List<Ubicacion> ubicaciones = ubicacionController.listarUbicaciones();

        for (Ubicacion u : ubicaciones) {
            model.addRow(new Object[]{u.getUbicacionId(), u.getContenedor(), u.getEstante(), u.getPasillo()});
        }
    }

    private void filterTable(String text) {
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTextField getTxtContenedor() {
        return txtContenedor;
    }

    public JTextField getTxtEstante() {
        return txtEstante;
    }

    public JTextField getTxtPasillo() {
        return txtPasillo;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JTable getTbUbicaciones() {
        return tbUbicaciones;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
    
    public static FrmUbicacion getInstancia() {
        if (instancia == null || instancia.isClosed()) {
            instancia = new FrmUbicacion();
        }
        return instancia;
    }
}
