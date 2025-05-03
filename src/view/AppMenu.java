package view;

import controller.UbicacionController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import view.FrmCategoria;

public class AppMenu extends JFrame implements ActionListener {
    // Controles
    private JDesktopPane dsk_escritorio;

    private JMenuBar mb_principal;

    private JMenu mn_caso;

    private JMenuItem mi_personal;
    private JMenuItem mi_ubicacion;
    private JMenuItem mi_salir;

    private JSeparator msp;


    public AppMenu() {
        super();

        ConfigurarFormulario();
        ConfigurarControles();
    }

    private void ConfigurarFormulario() {
        this.setTitle("Ventana Principal");
        this.setSize(800, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void ConfigurarControles() {
        dsk_escritorio = new JDesktopPane();

        mb_principal = new JMenuBar();

        mn_caso = new JMenu("Mantenimiento");

        mi_personal = new JMenuItem("Personal");
        mi_personal.addActionListener(this);

        mi_ubicacion = new JMenuItem("Ubicacion");
        mi_ubicacion.addActionListener(this);

        msp = new JSeparator();

        mi_salir = new JMenuItem("Salir");
        mi_salir.addActionListener(this);

        mn_caso.add(mi_personal);
        mn_caso.add(mi_ubicacion);
        mn_caso.add(msp);
        mn_caso.add(mi_salir);

        mb_principal.add(mn_caso);
        setJMenuBar(mb_principal);

        this.add(dsk_escritorio);
    }

    public void AbrirFrm(JInternalFrame frm) {
        if (!frm.isVisible()) {
            dsk_escritorio.add(frm);
            frm.setLocation((dsk_escritorio.getWidth() - frm.getWidth()) / 2, (dsk_escritorio.getHeight() - frm.getHeight()) / 2);
            frm.setVisible(true);
        } else {
            frm.toFront();
            frm.requestFocus();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mi_personal) {
            FrmCategoria app_personal = new FrmCategoria();
            AbrirFrm(app_personal);
        } else if (e.getSource() == mi_ubicacion) {
            // Aquí va el código para mi_ubicacion
        } else if (e.getSource() == mi_salir) {
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        AppMenu app_menu = new AppMenu();
        app_menu.setVisible(true);
    }
}

