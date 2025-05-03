package view;

import controller.ZapatillaController;
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
import model.Zapatilla;
import report.ReporteService;

public class IZapatillas extends JFrame {

    private JTextField txtColor, txtCosto, txtSku, txtPrecio, txtTalla;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnGenerarReporte;
    private JTextField txtBuscar;
    private JTable tbZapatillas;
    private DefaultTableModel modeloTabla;
    private ZapatillaController zapatillaController;
    private ReporteService reporteServicio = new ReporteService();

    public IZapatillas() {
        super("Inventario de Zapatillas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        zapatillaController = new ZapatillaController();
        
        add(crearFormulario(), BorderLayout.NORTH);
        add(crearBuscador(), BorderLayout.CENTER);
        add(crearTabla(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);  
    }


    private JPanel crearFormulario() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(800, 140));

        int x = 30, y = 25, labelW = 50, fieldW = 120, h = 25, gap = 10;
        int col2X = x + labelW + fieldW + 40;

        // Campos columna 1
        JLabel lblColor = new JLabel("Color:");
        lblColor.setBounds(x, y, labelW, h);
        txtColor = new JTextField();
        txtColor.setBounds(x + labelW, y, fieldW, h);

        JLabel lblCosto = new JLabel("Costo:");
        lblCosto.setBounds(x, y + (h + gap), labelW, h);
        txtCosto = new JTextField();
        txtCosto.setBounds(x + labelW, y + (h + gap), fieldW, h);

        JLabel lblTalla = new JLabel("Talla:");
        lblTalla.setBounds(x, y + 2*(h + gap), labelW, h);
        txtTalla = new JTextField();
        txtTalla.setBounds(x + labelW, y + 2*(h + gap), fieldW, h);

        // Campos columna 2
        JLabel lblSku = new JLabel("SKU:");
        lblSku.setBounds(col2X, y, labelW, h);
        txtSku = new JTextField();
        txtSku.setBounds(col2X + labelW, y, fieldW, h);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(col2X, y + (h + gap), labelW, h);
        txtPrecio = new JTextField();
        txtPrecio.setBounds(col2X + labelW, y + (h + gap), fieldW, h);

        // Botón Registrar
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(col2X, y + 2*(h + gap), 100, h);
        btnGenerarReporte = new JButton("Generar Reporte");
        btnGenerarReporte.setBounds(col2X + 120, y + 2*(h + gap), 140, h);
        btnGenerarReporte.addActionListener(this::onGenerarReporte);

        
        panel.add(lblColor);
        panel.add(txtColor);
        panel.add(lblCosto);
        panel.add(txtCosto);
        panel.add(lblTalla);
        panel.add(txtTalla);
        panel.add(lblSku);
        panel.add(txtSku);
        panel.add(lblPrecio);
        panel.add(txtPrecio);
        panel.add(btnRegistrar);
        panel.add(btnGenerarReporte);

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
}
