/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.CategoriaController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Categoria;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.JOptionPane;
import model.Categoria;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FrmCategoria extends JFrame {

    private JTextField txtNombre, txtDescrip;
    private JButton btnRegistrar, btnActualizar, btnEliminar;
    private JTextField txtBuscar;
    private JTable tbCategorias;
    private DefaultTableModel modeloTabla;

    private TableRowSorter<DefaultTableModel> filasClasificador;

    private CategoriaController categoriaController;

    private Categoria categoria;

    public FrmCategoria() {
        super("CATEGORIAS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        categoriaController = new CategoriaController();

        add(crearFormulario(), BorderLayout.NORTH);
        add(crearBuscador(), BorderLayout.WEST);
        add(crearTabla(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        agregarEventos();

    }

    private JPanel crearFormulario() {

        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(800, 140));

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 25, 70, 25);
        txtNombre = new JTextField();
        txtNombre.setBounds(130, 25, 120, 25);

        JLabel lblDescrip = new JLabel("Descripcion:");
        lblDescrip.setBounds(30, 80, 200, 25);
        txtDescrip = new JTextField();
        txtDescrip.setBounds(130, 60, 300, 50);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(575, 30, 100, 25);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(500, 80, 100, 25);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(650, 80, 100, 25);

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblDescrip);
        panel.add(txtDescrip);
        panel.add(btnRegistrar);
        panel.add(btnEliminar);
        panel.add(btnActualizar);

        return panel;

    }

    private JPanel crearBuscador() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(800, 80));

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setBounds(40, 20, 70, 30);

        txtBuscar = new JTextField(20);
        txtBuscar.setBounds(130, 20, 200, 30);

        panel.add(lblBuscar);
        panel.add(txtBuscar);

        return panel;
    }

    private JScrollPane crearTabla() {
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripcion"}, 0);
        tbCategorias = new JTable(modeloTabla);

        filasClasificador = new TableRowSorter<>(modeloTabla);
        tbCategorias.setRowSorter(filasClasificador);

        cargarTabla(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tbCategorias);
        scrollPane.setPreferredSize(new Dimension(800, 150));
        return scrollPane;

    }

    private void cargarTabla(DefaultTableModel model) {

        model.setRowCount(0);
        List<Categoria> categorias = categoriaController.listarCategorias();

        for (Categoria c : categorias) {
            model.addRow(new Object[]{c.getId(), c.getNombre(), c.getDescripcion()});
        }

    }

    private void agregarEventos() {
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String descripcion = txtDescrip.getText();
            
            
            if (nombre.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return; 
            }
            
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);

            if (categoriaController.registrarCategoria(categoria)) {
                JOptionPane.showMessageDialog(this, "Registrado correctamente");
                cargarTabla(modeloTabla);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tbCategorias.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tbCategorias.getValueAt(fila, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (categoriaController.eliminarCategoria(id)) {
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
            int fila = tbCategorias.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tbCategorias.getValueAt(fila, 0);
                String nombre = txtNombre.getText();
                String descripcion = txtDescrip.getText();
                categoria = new Categoria(id, nombre, descripcion);
                if (categoriaController.actualizarCategoria(categoria)) {
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

        tbCategorias.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int fila = tbCategorias.getSelectedRow();
                if (fila >= 0) {
                    int id = (int) tbCategorias.getValueAt(fila, 0);
                    txtNombre.setText(tbCategorias.getValueAt(fila, 1).toString());
                    txtDescrip.setText(tbCategorias.getValueAt(fila, 2).toString());

                    categoria = new Categoria(id, txtNombre.getText(), txtDescrip.getText());

                    btnRegistrar.setEnabled(false);
                }
            }
        });

        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String filtro = txtBuscar.getText();
                filasClasificador.setRowFilter(RowFilter.regexFilter("(?i)" + filtro));
            }
        });

        this.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (!tbCategorias.getBounds().contains(e.getPoint())) {
                    tbCategorias.clearSelection(); 
                    limpiarCampos(); 
                }
            }
        });

    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtDescrip.setText("");
        tbCategorias.clearSelection();
        btnRegistrar.setEnabled(true); 

    }

}
