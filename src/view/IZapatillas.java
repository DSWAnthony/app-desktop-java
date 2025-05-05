package view;

import controller.ZapatillaController;
import dao.CategoriaDAO;
import dao.MarcaDAO;
import dao.ModeloDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Categoria;
import model.Marca;
import model.Modelo;
import model.Zapatilla;
import report.ReporteService;

public class IZapatillas extends JFrame {

    private JTextField txtColor, txtSku, txtPrecio, txtTalla;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnGenerarReporte, btnNuevo;
    private JTextField txtBuscar;
    private JTable tbZapatillas;
    private JComboBox<Categoria> cmbCategoria;
    private JComboBox<Modelo> cmbModelo;
    private JComboBox<Marca> cmbMarca;
    private DefaultTableModel modeloTabla;
    private ZapatillaController zapatillaController;
    private ReporteService reporteServicio = new ReporteService();
    private ModeloDAO modeloController ;
    private CategoriaDAO categoriaController ;
    private MarcaDAO marcaController ;

    public IZapatillas() {
        super("Inventario de Zapatillas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        zapatillaController = new ZapatillaController();
        modeloController = new ModeloDAO();
        categoriaController = new CategoriaDAO();
        marcaController = new MarcaDAO();
        
        add(crearFormulario(), BorderLayout.NORTH);
        add(crearBuscador(), BorderLayout.CENTER);
        add(crearTabla(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);  
    }


    private JPanel crearFormulario() {
     JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(800, 180));

        int x = 30, y = 25, labelW = 60, fieldW = 120, h = 25, gapV = 10, gapH = 50;
        // Columna 1: SKU, Modelo, Marca
        JLabel lblSku = new JLabel("SKU:");
        lblSku.setBounds(x, y, labelW, h);
        txtSku = new JTextField();
        txtSku.setBounds(x + labelW, y, fieldW, h);

        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setBounds(x, y + (h + gapV), labelW, h);
        cmbModelo = new JComboBox<>();
        cmbModelo.setBounds(x + labelW, y + (h + gapV), fieldW, h);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(x, y + 2 * (h + gapV), labelW, h);
        cmbMarca = new JComboBox<>();
        cmbMarca.setBounds(x + labelW, y + 2 * (h + gapV), fieldW, h);

        // Columna 2: Precio, Color, Talla
        int col2X = x + labelW + fieldW + gapH;
        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(col2X, y, labelW, h);
        txtPrecio = new JTextField();
        txtPrecio.setBounds(col2X + labelW, y, fieldW, h);

        JLabel lblColor = new JLabel("Color:");
        lblColor.setBounds(col2X, y + (h + gapV), labelW, h);
        txtColor = new JTextField();
        txtColor.setBounds(col2X + labelW, y + (h + gapV), fieldW, h);

        JLabel lblTalla = new JLabel("Talla:");
        lblTalla.setBounds(col2X, y + 2 * (h + gapV), labelW, h);
        txtTalla = new JTextField();
        txtTalla.setBounds(col2X + labelW, y + 2 * (h + gapV), fieldW, h);

        // Columna 3: Categoria
        int col3X = col2X + labelW + fieldW + gapH;
        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(col3X, y, labelW + 10, h);
        cmbCategoria = new JComboBox<>();
        cmbCategoria.setBounds(col3X + labelW + 20, y, fieldW, h);
        
        cmbCategoria.setSelectedIndex(-1);
        cmbMarca.setSelectedIndex(-1);
        cmbModelo.setSelectedIndex(-1)
                ;
        
        cargarCombobox();

        // Botones
        int btnY = y + 3 * (h + gapV) + 10;
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(x, btnY, 100, h);
        btnRegistrar.addActionListener(this::registrarZapatilla);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(x + 110, btnY, 100, h);
        btnActualizar.addActionListener(this::actualizarZapatilla);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(x + 220, btnY, 100, h);
        btnEliminar.addActionListener(this::eliminarZapatilla);

        btnGenerarReporte = new JButton("Generar Reporte");
        btnGenerarReporte.setBounds(x + 330, btnY, 140, h);
        btnGenerarReporte.addActionListener(this::onGenerarReporte);

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(x + 500, btnY, 80, h);
        btnNuevo.addActionListener((e) -> {
            txtSku.setText("");
            txtSku.setEnabled(true);
            txtPrecio.setText("");
            txtColor.setText("");
            txtTalla.setText("");
            if (cmbModelo.getItemCount() > 0) cmbModelo.setSelectedIndex(-1);
            if (cmbCategoria.getItemCount() > 0) cmbCategoria.setSelectedIndex(-1);
            if (cmbMarca.getItemCount() > 0) cmbMarca.setSelectedIndex(-1);
        });
        
        panel.add(lblSku); panel.add(txtSku);
        panel.add(lblModelo); panel.add(cmbModelo);
        panel.add(lblMarca); panel.add(cmbMarca);
        panel.add(lblPrecio); panel.add(txtPrecio);
        panel.add(lblColor); panel.add(txtColor);
        panel.add(lblTalla); panel.add(txtTalla);
        panel.add(lblCategoria); panel.add(cmbCategoria);
        panel.add(btnRegistrar); panel.add(btnActualizar);
        panel.add(btnEliminar); panel.add(btnGenerarReporte);
        panel.add(btnNuevo);

        panel.setBorder(BorderFactory.createTitledBorder("Registrar Zapatilla"));
        return panel;
    }

//dalete
    private JPanel crearBuscador() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setPreferredSize(new Dimension(800, 50));

        JLabel lblBuscar = new JLabel("Buscar:");
        txtBuscar = new JTextField(20);
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panel.add(lblBuscar);
        panel.add(txtBuscar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);

        // Filtro en tiempo real
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable(txtBuscar.getText());
            }
        });

        return panel;
    }

    private JScrollPane crearTabla() {
        modeloTabla = new DefaultTableModel(new String[]{"Sku","Modelo","Marca","Color","Precio","Talla","Categoria"}, 0);
        tbZapatillas = new JTable(modeloTabla);
            
        cargarTabla(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tbZapatillas);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        return scrollPane;
    }

    private void filterTable(String text) {
        System.out.println(text);
    }


    private void cargarTabla(DefaultTableModel model) {
        model.setRowCount(0);
        
        List<Zapatilla> zapatillas = zapatillaController.listarZapatillas();
        
        for(Zapatilla z : zapatillas) {
            model.addRow(new Object[]{z.getSku(), z.getModelo().getNombre(), z.getModelo().getMarca().getNombre()
                    , z.getColor(),z.getPrecio(), z.getTalla(),z.getModelo().getCategoria().getNombre()});
        }
        
        
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IZapatillas::new);
    }

    private void onGenerarReporte(ActionEvent e) {
       JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte como PDF");
        chooser.setSelectedFile(new File("Inventario.pdf"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File destino = chooser.getSelectedFile();
            Map<String,Object> params = new HashMap<>();
            InputStream logo = getClass().getResourceAsStream("/images/logo-elite.png");
            params.put("LOGO_IMAGE", logo);

            SwingUtilities.invokeLater(() -> {
                try {
                    reporteServicio.exportarPdf(
                        "/report/reporte_inventario.jasper",
                        params,
                        destino
                    );
                    JOptionPane.showMessageDialog(this,
                        "Reporte guardado en: " + destino.getAbsolutePath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                        "Error al generar el reporte: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }
    
    private void seleccionarFila(){
        int fila = tbZapatillas.getSelectedRow();
        
        System.out.println("selesciono :" + fila);
         if (fila != -1) {
             
            txtSku.setText(modeloTabla.getValueAt(fila, 0).toString());
            String modeloNombre = modeloTabla.getValueAt(fila, 1).toString();
            String marcaNombre = modeloTabla.getValueAt(fila, 2 ).toString();
            txtColor.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtPrecio.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtTalla.setText(modeloTabla.getValueAt(fila, 5).toString());
            String categoriaNombre = modeloTabla.getValueAt(fila, 6).toString();

            for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
                if (cmbCategoria.getItemAt(i).getNombre().equals(categoriaNombre)) {
                    cmbCategoria.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < cmbModelo.getItemCount(); i++) {
                if (cmbModelo.getItemAt(i).getNombre().equals(modeloNombre)) {
                    cmbModelo.setSelectedIndex(i);
                    break;
                }
            }
             for (int i = 0; i < cmbMarca.getItemCount() ; i++) {
                if (cmbMarca.getItemAt(i).getNombre().equals(marcaNombre)) {
                    cmbMarca.setSelectedIndex(i);
                    break;
                } 
             }
             
             txtSku.setEnabled(false);
        }
    }

    private void cargarCombobox() {

        for (Categoria categoria : categoriaController.listar()) {
           cmbCategoria.addItem(categoria);
        }
        for (Modelo modelo : modeloController.listar()) {
            cmbModelo.addItem(modelo);
        }
        for (Marca marca : marcaController.listar()) {
            cmbMarca.addItem(marca);
        }
        
    }
    
     private void registrarZapatilla(ActionEvent e) {
        System.out.println("Click en Registrar");
        
        zapatillaController.registrarZapatilla(capturarDatos());
            JOptionPane.showMessageDialog(this, "Zapatilla registrada correctamente.");
            limpiarFormulario();
            cargarTabla(modeloTabla);
    }
     
      private void limpiarFormulario() {
        txtSku.setText("");
        txtPrecio.setText("");
        txtColor.setText("");
        txtTalla.setText("");
        if (cmbModelo.getItemCount() > 0) cmbModelo.setSelectedIndex(-1);
        if (cmbCategoria.getItemCount() > 0) cmbCategoria.setSelectedIndex(-1);
        if (cmbMarca.getItemCount() > 0) cmbMarca.setSelectedIndex(-1);
    }

    private void actualizarZapatilla(ActionEvent e) {
        System.out.println("actualizar");
        zapatillaController.actualizarZapatilla(capturarDatos());
        JOptionPane.showMessageDialog(this, "Zapatilla Actualizada correctamente.");
            limpiarFormulario();
            cargarTabla(modeloTabla);
    }
    
    private void eliminarZapatilla(ActionEvent e) {
        System.out.println("eliminando");
        zapatillaController.eliminarZapatilla(capturarDatos().getSku());
        JOptionPane.showMessageDialog(this, "Zapatilla eliminada correctamente.");
            limpiarFormulario();
            cargarTabla(modeloTabla);
    }
    
    private Zapatilla capturarDatos(){
         try {
            String sku = txtSku.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            String color = txtColor.getText().trim();
            double talla = Double.parseDouble(txtTalla.getText().trim());
            Modelo modelo = (Modelo) cmbModelo.getSelectedItem();
            Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();
            Marca marca = (Marca) cmbMarca.getSelectedItem();

            if (sku.isEmpty() || modelo == null || categoria == null || marca == null) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
            }

            Zapatilla z = new Zapatilla();
            z.setSku(sku);
            z.setPrecio(precio);
            z.setColor(color);
            z.setTalla(talla);
            
            modelo.setCategoria(categoria);
            modelo.setMarca(marca);
            
            z.setModelo(modelo);
            
            return z;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage());
        }
         return null;
    }
    

}
