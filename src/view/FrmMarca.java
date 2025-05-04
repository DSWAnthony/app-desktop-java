package view;

import controller.MarcaController;
import model.Marca;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmMarca extends JInternalFrame {

    private JTextField txtNombre;
    private JButton btnRegistrar, btnActualizar, btnEliminar;
    private JTextField txtBuscar;
    private JTable tbMarca;
    private DefaultTableModel modeloTabla;
    private JRadioButton opcion1;
    private static FrmMarca instancia;

    private TableRowSorter<DefaultTableModel> filasClasificador;

    private MarcaController marcaController;
    private Marca marca;

    public FrmMarca() {
        super("MARCAS", false, true, false, false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        marcaController = new MarcaController();

        add(crearFormulario(), BorderLayout.NORTH);
        add(crearBuscador(), BorderLayout.WEST);
        add(crearTabla(), BorderLayout.SOUTH);

        pack();
        setLocation(0, 0);
        agregarEventos();
        setResizable(false);
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(800, 100));

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 25, 70, 25);
        txtNombre = new JTextField();
        txtNombre.setBounds(130, 25, 120, 25);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(300, 25, 100, 25);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(420, 25, 100, 25);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(540, 25, 100, 25);

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(btnRegistrar);
        panel.add(btnEliminar);
        panel.add(btnActualizar);

        return panel;
    }

    private JPanel crearBuscador() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(800, 80));

        JLabel lblBuscar = new JLabel("Buscar por");
        lblBuscar.setBounds(40, 20, 70, 30);

        txtBuscar = new JTextField(20);
        txtBuscar.setBounds(250, 20, 200, 30);

        opcion1 = new JRadioButton("Nombre");
        opcion1.setBounds(120, 15, 100, 30);
        opcion1.setSelected(true);

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(opcion1);

        panel.add(opcion1);
        panel.add(lblBuscar);
        panel.add(txtBuscar);

        return panel;
    }

    private JScrollPane crearTabla() {
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tbMarca = new JTable(modeloTabla);

        filasClasificador = new TableRowSorter<>(modeloTabla);
        tbMarca.setRowSorter(filasClasificador);

        cargarTabla(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tbMarca);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        return scrollPane;
    }

    private void cargarTabla(DefaultTableModel model) {
        model.setRowCount(0);
        List<Marca> marcas = marcaController.listarMarcas();

        for (Marca c : marcas) {
            model.addRow(new Object[]{c.getId(), c.getNombre()});
        }
    }

    private void agregarEventos() {
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa el nombre", "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Marca marca = new Marca();
            marca.setNombre(nombre);

            if (marcaController.registrarMarca(marca)) {
                JOptionPane.showMessageDialog(this, "Registrado correctamente");
                cargarTabla(modeloTabla);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tbMarca.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tbMarca.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (marcaController.eliminarMarca(id)) {
                        JOptionPane.showMessageDialog(this, "Eliminado correctamente");
                        cargarTabla(modeloTabla);
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila");
            }
        });

        btnActualizar.addActionListener(e -> {
            int fila = tbMarca.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tbMarca.getValueAt(fila, 0);
                String nombre = txtNombre.getText();
                marca = new Marca(id, nombre);
                if (marcaController.actualizarMarca(marca)) {
                    JOptionPane.showMessageDialog(this, "Actualizado correctamente");
                    cargarTabla(modeloTabla);
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una fila");
            }
        });

        tbMarca.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int fila = tbMarca.getSelectedRow();
                if (fila >= 0) {
                    int id = (int) tbMarca.getValueAt(fila, 0);
                    txtNombre.setText(tbMarca.getValueAt(fila, 1).toString());
                    marca = new Marca(id, txtNombre.getText());
                    btnRegistrar.setEnabled(false);
                }
            }
        });

        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String filtro = txtBuscar.getText();

                if (opcion1.isSelected()) {
                    filasClasificador.setRowFilter(RowFilter.regexFilter("(?i)" + filtro, 1));
                } else {
                    filasClasificador.setRowFilter(null);
                }
            }
        });

        this.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!tbMarca.getBounds().contains(e.getPoint())) {
                    tbMarca.clearSelection();
                    limpiarCampos();
                }
            }
        });
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        tbMarca.clearSelection();
        btnRegistrar.setEnabled(true);
    }

    public static FrmMarca getInstancia() {
        if (instancia == null || instancia.isClosed()) {
            instancia = new FrmMarca();
        }
        return instancia;
    }
}
