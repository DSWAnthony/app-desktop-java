package view;

import controller.InventarioController;
import model.Inventario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class IInventario extends JFrame {

    private JTextField txtSku, txtCantidad;
    private JComboBox<String> cmbAlmacen;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar, btnNuevo;
    private JTable tbInventario;
    private DefaultTableModel modeloTabla;
    private InventarioController inventarioController;

    // Constructor del formulario
    public IInventario() {
        super("Gestión de Inventario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setSize(800, 600);
        setLocationRelativeTo(null);

        inventarioController = new InventarioController();

        add(crearFormulario());
        add(crearTabla());

        agregarEventos();

        setVisible(true);
    }

    private void agregarEventos() {
        
        btnRegistrar.addActionListener((ActionEvent e) -> {
            String sku = txtSku.getText();
            String almacen = (String) cmbAlmacen.getSelectedItem();
            int cantidad = Integer.parseInt(txtCantidad.getText());

            Inventario inventario = new Inventario(0, 0, 0, cantidad, sku, "", "", "", "", almacen);
            inventarioController.registrarInventario(inventario);

            modeloTabla.setRowCount(0); 
            cargarTabla();   
            limpiarCampos();
        });
        
        tbInventario.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tbInventario.getSelectedRow();
                if (fila != -1) {
                    txtSku.setText(modeloTabla.getValueAt(fila, 1).toString());
                    cmbAlmacen.setSelectedItem(modeloTabla.getValueAt(fila, 6).toString());
                    txtCantidad.setText(modeloTabla.getValueAt(fila, 7).toString());

                    txtSku.setEnabled(false);
                    cmbAlmacen.setEnabled(true);
                    txtCantidad.setEnabled(true);
                }
            }
        });

        
        btnActualizar.addActionListener((ActionEvent e) -> {
            int fila = tbInventario.getSelectedRow();
            if (fila != -1) {
                int inventarioId = (int) modeloTabla.getValueAt(fila, 0);
                String sku = txtSku.getText();
                String almacen = (String) cmbAlmacen.getSelectedItem();
                int cantidad = Integer.parseInt(txtCantidad.getText());

                Inventario inventario = new Inventario(inventarioId, 0, 0, cantidad, sku, "", "", "", "", almacen);
                inventarioController.actualizarInventario(inventario);

                modeloTabla.setRowCount(0);
                cargarTabla();
                limpiarCampos();
                bloquearCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            }
        });

        
        btnEliminar.addActionListener((ActionEvent e) -> {
            int fila = tbInventario.getSelectedRow();
            if (fila != -1) {
                int inventarioId = (int) modeloTabla.getValueAt(fila, 0);
                inventarioController.eliminarInventario(inventarioId);
                modeloTabla.setRowCount(0);
                cargarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
            }
        });

        btnLimpiar.addActionListener((ActionEvent e) -> limpiarCampos());

        btnNuevo.addActionListener((ActionEvent e) -> desbloquearCampos());
    }

    private void limpiarCampos() {
        txtSku.setText("");
        cmbAlmacen.setSelectedIndex(0);
        txtCantidad.setText("");
    }

    private void bloquearCampos() {
        txtSku.setEnabled(false);
        cmbAlmacen.setEnabled(false);
        txtCantidad.setEnabled(false);
    }

    private void desbloquearCampos() {
        txtSku.setEnabled(false);
        cmbAlmacen.setEnabled(true);
        txtCantidad.setEnabled(true);
    }

    private void cargarTabla() {
        List<Inventario> inventarios = inventarioController.listarInventarios();
        for (Inventario i : inventarios) {
            modeloTabla.addRow(new Object[]{i.getInventarioId(), i.getSku(), i.getModelo(), i.getTalla(), i.getColor(), i.getMarca(), i.getAlmacen(), i.getCantidadActual()});
        }
    }

   private JPanel crearFormulario() {
    JPanel panel = new JPanel(null);
    panel.setBounds(10, 10, 760, 250);

    int x = 30, y = 25, labelW = 80, fieldW = 120, h = 25, gap = 10;
    int col2X = x + labelW + fieldW + 40;

    // Etiquetas y campos de entrada
    JLabel lblSku = new JLabel("SKU:");
    lblSku.setBounds(x, y, labelW, h);
    txtSku = new JTextField();
    txtSku.setBounds(x + labelW, y, fieldW, h);

    JLabel lblAlmacen = new JLabel("Almacen:");
    lblAlmacen.setBounds(x, y + (h + gap), labelW, h);
    cmbAlmacen = new JComboBox<>(new String[]{"Almacen 1", "Almacen 2", "Almacen 3"}); 
    cmbAlmacen.setBounds(x + labelW, y + (h + gap), fieldW, h);

    JLabel lblCantidad = new JLabel("Cantidad:");
    lblCantidad.setBounds(col2X, y, labelW, h);
    txtCantidad = new JTextField();
    txtCantidad.setBounds(col2X + labelW, y, fieldW, h);

    
    btnRegistrar = new JButton("Registrar");
    btnRegistrar.setBounds(col2X, y + 2 * (h + gap), 100, h);

    btnActualizar = new JButton("Actualizar");
    btnActualizar.setBounds(col2X, y + 3 * (h + gap), 100, h);

    btnEliminar = new JButton("Eliminar");
    btnEliminar.setBounds(col2X, y + 4 * (h + gap), 100, h);

    btnLimpiar = new JButton("Limpiar");
    btnLimpiar.setBounds(col2X + 120, y + 3 * (h + gap), 100, h);

    btnNuevo = new JButton("Nuevo");
    btnNuevo.setBounds(col2X + 120, y + 2 * (h + gap), 100, h);

   
    panel.add(lblSku);
    panel.add(txtSku);
    panel.add(lblAlmacen);
    panel.add(cmbAlmacen);
    panel.add(lblCantidad);
    panel.add(txtCantidad);
    panel.add(btnRegistrar);
    panel.add(btnActualizar);
    panel.add(btnEliminar);
    panel.add(btnLimpiar);
    panel.add(btnNuevo);

    
    panel.setBorder(BorderFactory.createTitledBorder("Registro de Inventario"));

    return panel;
}



    private JScrollPane crearTabla() {
        modeloTabla = new DefaultTableModel(new String[]{"Num Inventario", "SKU", "Modelo", "Talla", "Color", "Marca", "Almacen", "Cantidad"}, 0);
        tbInventario = new JTable(modeloTabla);

        cargarTabla(); 
        JScrollPane scrollPane = new JScrollPane(tbInventario);
        scrollPane.setBounds(10, 270, 760, 250);
        return scrollPane;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IInventario::new);
    }
}
