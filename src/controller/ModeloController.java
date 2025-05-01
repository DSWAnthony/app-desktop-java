package controller;

import dao.ModeloDAO;
import model.Modelo;
import model.Categoria;
import model.Marca;
import view.FrmModelo;

import java.awt.event.*;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ModeloController implements ActionListener {

    private FrmModelo vista;
    private ModeloDAO modeloDAO = new ModeloDAO();

    public ModeloController(FrmModelo vista) {
        this.vista = vista;
        this.vista.getBtnAgregar().addActionListener(this);
        this.vista.getBtnActualizar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);
        
        this.vista.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                cargarDatosDesdeTabla();
            }
        });

        listarModelos();
        cargarCategorias();
        cargarMarcas();
    }

    private void listarModelos() {
        List<Modelo> lista = modeloDAO.listar();
        DefaultTableModel tableModel = vista.getTableModel();
        tableModel.setRowCount(0);

        for (Modelo modelo : lista) {
            tableModel.addRow(new Object[] {
                modelo.getId(),
                modelo.getNombre(),
                modelo.getGenero(),
                modelo.getCategoria().getNombre(),
                modelo.getMarca().getNombre()
            });
        }
    }

    private void cargarCategorias() {
        vista.getCboCategoria().removeAllItems();
        for (Categoria c : modeloDAO.listarCategorias()) {
            vista.getCboCategoria().addItem(c);
        }
    }

    private void cargarMarcas() {
        vista.getCboMarca().removeAllItems();
        for (Marca m : modeloDAO.listarMarcas()) {
            vista.getCboMarca().addItem(m);
        }
    }

    private void cargarDatosDesdeTabla() {
        int fila = vista.getTable().getSelectedRow();
        if (fila != -1) {
            vista.getTxtID().setText(vista.getTableModel().getValueAt(fila, 0).toString());
            vista.getTxtNombre().setText(vista.getTableModel().getValueAt(fila, 1).toString());

            String genero = vista.getTableModel().getValueAt(fila, 2).toString();
            vista.setGenero(genero);

            String categoriaNombre = vista.getTableModel().getValueAt(fila, 3).toString();
            String marcaNombre = vista.getTableModel().getValueAt(fila, 4).toString();

            for (int i = 0; i < vista.getCboCategoria().getItemCount(); i++) {
                if (vista.getCboCategoria().getItemAt(i).getNombre().equals(categoriaNombre)) {
                    vista.getCboCategoria().setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < vista.getCboMarca().getItemCount(); i++) {
                if (vista.getCboMarca().getItemAt(i).getNombre().equals(marcaNombre)) {
                    vista.getCboMarca().setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private boolean validarCampos(String nombre, Categoria categoria, Marca marca) {
        return !(nombre == null || nombre.isEmpty() || categoria == null || marca == null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnAgregar()) {
            guardar();
        } else if (e.getSource() == vista.getBtnActualizar()) {
            actualizar();
        } else if (e.getSource() == vista.getBtnEliminar()) {
            eliminar();
        } else if (e.getSource() == vista.getBtnLimpiar()) {
            vista.limpiarCampos();
        }
    }

    private void guardar() {
        String nombre = vista.getTxtNombre().getText().trim();
        String genero = vista.getGeneroSeleccionado(); // ← Nuevo método
        Categoria categoria = (Categoria) vista.getCboCategoria().getSelectedItem();
        Marca marca = (Marca) vista.getCboMarca().getSelectedItem();

        if (!validarCampos(nombre, categoria, marca)) {
            vista.mostrarMensaje("Complete todos los campos.");
            return;
        }

        Modelo modelo = new Modelo();
        modelo.setNombre(nombre);
        modelo.setGenero(genero);
        modelo.setCategoria(categoria);
        modelo.setMarca(marca);

        if (modeloDAO.registrar(modelo)) {
            vista.mostrarMensaje("Modelo guardado correctamente.");
            listarModelos();
            vista.limpiarCampos();
        } else {
            vista.mostrarMensaje("Error al guardar el modelo.");
        }
    }

    private void actualizar() {
        try {
            int id = Integer.parseInt(vista.getTxtID().getText());
            String nombre = vista.getTxtNombre().getText().trim();
            String genero = vista.getGeneroSeleccionado(); // ← Nuevo método
            Categoria categoria = (Categoria) vista.getCboCategoria().getSelectedItem();
            Marca marca = (Marca) vista.getCboMarca().getSelectedItem();

            if (!validarCampos(nombre, categoria, marca)) {
                vista.mostrarMensaje("Complete todos los campos.");
                return;
            }

            Modelo modelo = new Modelo(id, genero, nombre, categoria, marca);

            if (modeloDAO.actualizar(modelo)) {
                vista.mostrarMensaje("Modelo actualizado correctamente.");
                listarModelos();
                vista.limpiarCampos();
            } else {
                vista.mostrarMensaje("Error al actualizar el modelo.");
            }
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("ID inválido.");
        }
    }

    private void eliminar() {
        try {
            int id = Integer.parseInt(vista.getTxtID().getText());
            if (modeloDAO.eliminar(id)) {
                vista.mostrarMensaje("Modelo eliminado correctamente.");
                listarModelos();
                vista.limpiarCampos();
            } else {
                vista.mostrarMensaje("Error al eliminar el modelo.");
            }
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("ID inválido.");
        }
    }
}
