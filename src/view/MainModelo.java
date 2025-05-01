
package view;

import controller.ModeloController;
import javax.swing.SwingUtilities;
//modelo
public class MainModelo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmModelo vista = new FrmModelo();
            new ModeloController(vista);
            vista.setVisible(true);
        });
    }
}
