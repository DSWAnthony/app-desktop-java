package view;
import controller.UbicacionController;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        FrmUbicacion vista = new FrmUbicacion();
        vista.setVisible(true);
        UbicacionController controller = new UbicacionController(vista);
    }
}