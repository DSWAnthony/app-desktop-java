package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmUbicacion extends JFrame {

    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    private JTextField txtContenedor, txtEstante, txtPasillo;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtID;
    
    
public FrmUbicacion() {
    setTitle("Gestión de Ubicaciones");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);

    // Crear etiquetas
    JLabel lblID = new JLabel("ID:");
    JLabel lblContenedor = new JLabel("Contenedor:");
    JLabel lblEstante = new JLabel("Estante:");
    JLabel lblPasillo = new JLabel("Pasillo:");

    // Crear campos de texto
    txtID = new JTextField();
    txtID.setEditable(false);  // Campo ID
    txtContenedor = new JTextField();
    
    txtEstante = new JTextField();
    txtPasillo = new JTextField();

    // Crear botones
    btnAgregar = new JButton("Agregar");
    btnActualizar = new JButton("Editar");
    btnEliminar = new JButton("Eliminar");
    btnLimpiar = new JButton("Nuevo");

    // Crear tabla con modelo
    tableModel = new DefaultTableModel(new String[]{"ID", "Contenedor", "Estante", "Pasillo"}, 0);
    table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    // Posicionar componentes
    lblID.setBounds(50, 20, 100, 30);
    txtID.setBounds(150, 20, 200, 30);

    lblContenedor.setBounds(50, 60, 100, 30);
    txtContenedor.setBounds(150, 60, 200, 30);

    lblEstante.setBounds(50, 100, 100, 30);
    txtEstante.setBounds(150, 100, 200, 30);

    lblPasillo.setBounds(50, 140, 100, 30);
    txtPasillo.setBounds(150, 140, 200, 30);

    scrollPane.setBounds(50, 190, 700, 300);

    btnAgregar.setBounds(50, 510, 120, 30);
    btnActualizar.setBounds(200, 510, 120, 30);
    btnEliminar.setBounds(350, 510, 120, 30);
    btnLimpiar.setBounds(500, 510, 120, 30);

    // Agregar componentes a la ventana
    add(lblID);
    add(txtID);
    add(lblContenedor);
    add(txtContenedor);
    add(lblEstante);
    add(txtEstante);
    add(lblPasillo);
    add(txtPasillo);
    add(scrollPane);
    add(btnAgregar);
    add(btnActualizar);
    add(btnEliminar);
    add(btnLimpiar);

    setLocationRelativeTo(null); // Centrar ventana
    
    setVisible(true);
    SwingUtilities.invokeLater(() -> txtContenedor.requestFocusInWindow());
}


    // Getters
    public JButton getBtnAgregar() {
        return btnAgregar;
    }
public JTextField getTxtID() {
    return txtID;
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

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    // Métodos útiles
    public void limpiarCampos() {
        txtID.setText("");
        txtContenedor.setText("");
        txtEstante.setText("");
        txtPasillo.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
