package view;

import controller.InventarioController;
import model.Inventario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import model.UbicacionCombo;
public class FrmInventario extends JInternalFrame {
    private static FrmInventario instancia;

    
    private JTextField txtSku, txtCantidad;
    private JComboBox<UbicacionCombo> cmbAlmacen;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar, btnNuevo;
    private JTable tbInventario;
    private DefaultTableModel modeloTabla;
    private InventarioController controller;

    // Constructor del formulario
    public FrmInventario() {
    super("Gestión de Inventario",false,true,false,false);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLayout(null);
    setSize(800, 600);

    controller = new InventarioController();

    add(crearFormulario());
    add(crearTabla());

    agregarEventos();

    
    txtSku.setEnabled(false);
}

     private void agregarEventos() {
    btnRegistrar.addActionListener((ActionEvent e) -> {
    if (camposVacios()) {
        mostrar("Por favor completa los campos.");
        return;
    }

    int idZapato = Integer.parseInt(txtSku.getText().trim()); 
    int cantidad = Integer.parseInt(txtCantidad.getText().trim());
    UbicacionCombo u = (UbicacionCombo) cmbAlmacen.getSelectedItem();

    Inventario inv = new Inventario(0, idZapato, u.getId(), cantidad, "", "", "", "", "", u.getNombre());
    if (controller.registrarInventario(inv)) {
        recargarTabla();
        limpiarCampos();
    } else {
        mostrar("Error al registrar :  EL ZAPATO YA EXISTE EN ESA UBICACION .");
    }
});
        
        tbInventario.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int fila = tbInventario.getSelectedRow();
        if (fila != -1) {
            

            String almacen = modeloTabla.getValueAt(fila, 6).toString();
            
            for (int i = 0; i < cmbAlmacen.getItemCount(); i++) {
                if (((UbicacionCombo) cmbAlmacen.getItemAt(i)).getNombre().equals(almacen)) {
                    cmbAlmacen.setSelectedIndex(i);
                    break;
                }
            }
            txtCantidad.setText(modeloTabla.getValueAt(fila, 7).toString());

            
            txtSku.setEnabled(false);
            cmbAlmacen.setEnabled(true);
            txtCantidad.setEnabled(true);
        }
    }
});

            btnActualizar.addActionListener(e -> {
            int fila = tbInventario.getSelectedRow();
            if (fila == -1 || camposVacios()) {
                mostrar("Selecciona una fila y completa los campos.");
                return;
            }
            int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            UbicacionCombo u = (UbicacionCombo) cmbAlmacen.getSelectedItem();

            Inventario inv = new Inventario(id, 0, u.getId(), cantidad, "", "", "", "", "", u.getNombre());
            if (controller.actualizarInventario(inv)) {
                recargarTabla();
                limpiarCampos();
                
            } else mostrar("Error al actualizar.: EL ZAPATO YA EXISTE EN ESA UBICACION");
        });

         btnEliminar.addActionListener((ActionEvent e) -> {
    int fila = tbInventario.getSelectedRow();
    if (fila != -1) {
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de que deseas eliminar esta ubicación?", 
                "Confirmación de eliminación", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            int inventarioId = (int) modeloTabla.getValueAt(fila, 0);
            controller.eliminarInventario(inventarioId);
            modeloTabla.setRowCount(0);
            cargarTabla();
            limpiarCampos();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
    }
});




        tbInventario.getSelectionModel().addListSelectionListener(e -> {
            int fila = tbInventario.getSelectedRow();
            if (fila != -1) {
                txtCantidad.setText(modeloTabla.getValueAt(fila, 7).toString());
                String almacen = modeloTabla.getValueAt(fila, 6).toString();
                txtSku.setEnabled(false);
                for (int i = 0; i < cmbAlmacen.getItemCount(); i++) {
                    if (cmbAlmacen.getItemAt(i).getNombre().equals(almacen)) {
                        cmbAlmacen.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnNuevo.addActionListener(e -> desbloquearCampos());
    }

    private boolean camposVacios() {
    return txtCantidad.getText().trim().isEmpty();
}


    private void mostrar(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void limpiarCampos() {
        txtSku.setText("");
        txtCantidad.setText("");
        cmbAlmacen.setSelectedIndex(0);
    }

    private void bloquearCampos() {
        txtSku.setEnabled(false);
        cmbAlmacen.setEnabled(false);
        txtCantidad.setEnabled(false);
    }

    private void desbloquearCampos() {
        txtSku.setEnabled(true);
        cmbAlmacen.setEnabled(true);
        txtCantidad.setEnabled(true);
    }

    private void recargarTabla() {
        modeloTabla.setRowCount(0);
        cargarTabla();
    }

   private void cargarTabla() {
        List<Inventario> inventarios = controller.listarInventarios();
        for (Inventario i : inventarios) {
            modeloTabla.addRow(new Object[]{i.getInventarioId(), i.getSku(), i.getModelo(), i.getTalla(), i.getColor(), i.getMarca(), i.getAlmacen(), i.getCantidadActual()});
        }
    }

    private JPanel crearFormulario() {
        JPanel p = new JPanel(null);
        p.setBounds(10, 10, 760, 250);
        int x = 30, y = 25, w = 120, h = 25, g = 10, x2 = x + 240;

        p.add(crearLabel("ID Zapato:", x, y));
        txtSku = crearTextField(x + 80, y); p.add(txtSku);

        p.add(crearLabel("Almacén:", x, y + h + g));
        cmbAlmacen = new JComboBox<>(); cmbAlmacen.setBounds(x + 80, y + h + g, w, h); p.add(cmbAlmacen);
        cargarUbicaciones();

        p.add(crearLabel("Cantidad:", x2, y));
        txtCantidad = crearTextField(x2 + 80, y); p.add(txtCantidad);

        btnRegistrar = crearBoton("Registrar", x2, y + 2 * (h + g));
        btnActualizar = crearBoton("Actualizar", x2, y + 3 * (h + g));
        btnEliminar = crearBoton("Eliminar", x2, y + 4 * (h + g));
        btnLimpiar = crearBoton("Limpiar", x2 + 120, y + 3 * (h + g));
        btnNuevo = crearBoton("Nuevo", x2 + 120, y + 2 * (h + g));

        for (JComponent b : new JComponent[]{btnRegistrar, btnActualizar, btnEliminar, btnLimpiar, btnNuevo}) p.add(b);
        p.setBorder(BorderFactory.createTitledBorder("Registro de Inventario"));
        return p;
    }

    private JLabel crearLabel(String txt, int x, int y) {
        JLabel l = new JLabel(txt); l.setBounds(x, y, 80, 25); return l;
    }

    private JTextField crearTextField(int x, int y) {
        JTextField t = new JTextField(); t.setBounds(x, y, 120, 25); return t;
    }

    private JButton crearBoton(String txt, int x, int y) {
        JButton b = new JButton(txt); b.setBounds(x, y, 100, 25); return b;
    }

    private void cargarUbicaciones() {
        List<UbicacionCombo> ubicaciones = controller.obtenerUbicaciones();
        for (UbicacionCombo u : ubicaciones) {
            cmbAlmacen.addItem(u);
        }
    }

    private JScrollPane crearTabla() {
        modeloTabla = new DefaultTableModel(new String[]{"Num", "SKU", "Modelo", "Talla", "Color", "Marca", "Almacén", "Cantidad"}, 0);
        tbInventario = new JTable(modeloTabla);
        cargarTabla();
        JScrollPane scroll = new JScrollPane(tbInventario);
        scroll.setBounds(10, 270, 760, 250);
        return scroll;
    }
public static FrmInventario getInstancia() {
        if (instancia == null || instancia.isClosed()) {
            instancia = new FrmInventario();
        }
        return instancia;
    }
   
}
