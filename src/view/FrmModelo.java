package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Categoria;
import model.Marca;

public class FrmModelo extends JFrame {
//modelo
    private JTextField txtID, txtNombre;
    private JRadioButton rbtnMasculino, rbtnFemenino, rbtnUnisex;
    private ButtonGroup grupoGenero;
    private JComboBox<Categoria> cboCategoria;
    private JComboBox<Marca> cboMarca;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmModelo() {
        setTitle("Gestión de Modelos");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Labels
        JLabel lblID = new JLabel("ID:");
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblGenero = new JLabel("Género:");
        JLabel lblCategoria = new JLabel("Categoría:");
        JLabel lblMarca = new JLabel("Marca:");

        
        txtID = new JTextField();
        txtID.setEditable(false);
        txtNombre = new JTextField();

        
        rbtnMasculino = new JRadioButton("Masculino");
        rbtnFemenino = new JRadioButton("Femenino");
        rbtnUnisex = new JRadioButton("Unisex");

        grupoGenero = new ButtonGroup();
        grupoGenero.add(rbtnMasculino);
        grupoGenero.add(rbtnFemenino);
        grupoGenero.add(rbtnUnisex);

        
        cboCategoria = new JComboBox<>();
        cboMarca = new JComboBox<>();

        
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Nuevo");

        
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Género", "Categoría", "Marca"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        
        lblID.setBounds(50, 20, 100, 30);
        txtID.setBounds(150, 20, 200, 30);

        lblNombre.setBounds(50, 60, 100, 30);
        txtNombre.setBounds(150, 60, 200, 30);

        lblGenero.setBounds(50, 100, 100, 30);
        rbtnMasculino.setBounds(150, 100, 100, 30);
        rbtnFemenino.setBounds(260, 100, 100, 30);
        rbtnUnisex.setBounds(370, 100, 100, 30);

        lblCategoria.setBounds(50, 140, 100, 30);
        cboCategoria.setBounds(150, 140, 200, 30);

        lblMarca.setBounds(50, 180, 100, 30);
        cboMarca.setBounds(150, 180, 200, 30);

        scrollPane.setBounds(50, 230, 740, 250);

        btnAgregar.setBounds(50, 500, 120, 30);
        btnActualizar.setBounds(200, 500, 120, 30);
        btnEliminar.setBounds(350, 500, 120, 30);
        btnLimpiar.setBounds(500, 500, 120, 30);

        
        add(lblID); add(txtID);
        add(lblNombre); add(txtNombre);
        add(lblGenero); add(rbtnMasculino); add(rbtnFemenino); add(rbtnUnisex);
        add(lblCategoria); add(cboCategoria);
        add(lblMarca); add(cboMarca);
        add(scrollPane);
        add(btnAgregar); add(btnActualizar); add(btnEliminar); add(btnLimpiar);

        setLocationRelativeTo(null);
        setVisible(true);

        SwingUtilities.invokeLater(() -> txtNombre.requestFocusInWindow());
    }

    
    public JTextField getTxtID() {
        return txtID;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JComboBox<Categoria> getCboCategoria() {
        return cboCategoria;
    }

    public JComboBox<Marca> getCboMarca() {
        return cboMarca;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
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

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    
    public String getGeneroSeleccionado() {
        if (rbtnMasculino.isSelected()) return "Masculino";
        if (rbtnFemenino.isSelected()) return "Femenino";
        if (rbtnUnisex.isSelected()) return "Unisex";
        return "";
    }

    
    public void setGenero(String genero) {
        switch (genero) {
            case "Masculino" -> rbtnMasculino.setSelected(true);
            case "Femenino" -> rbtnFemenino.setSelected(true);
            case "Unisex" -> rbtnUnisex.setSelected(true);
        }
    }

    
    public void limpiarCampos() {
        txtID.setText("");
        txtNombre.setText("");
        grupoGenero.clearSelection();
        cboCategoria.setSelectedIndex(0);
        cboMarca.setSelectedIndex(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
