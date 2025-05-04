package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import java.awt.BorderLayout;


public class AppMenu extends JFrame implements ActionListener {
    // Controles
    private JDesktopPane dsk_escritorio;

    private JMenuBar mb_principal;

    private JMenu mn_caso,mn_caso2;

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
        this.setSize(900, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void ConfigurarControles() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        dsk_escritorio = new JDesktopPane() {
            private Image imagenFondo = new ImageIcon(getClass().getResource("/images/fondo.jpg")).getImage();
            private Image imagenCentro = new ImageIcon(getClass().getResource("/images/logo-elite.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }

                if (imagenCentro != null) {

                    int x = (getWidth() - imagenCentro.getWidth(this)) / 2;
                    int y = (getHeight() - imagenCentro.getHeight(this)) / 2;
                    g.drawImage(imagenCentro, x, y, this);
                }
            }
        };

        JPanel panelMenu = new JPanel();
        panelMenu.setOpaque(false);
        panelMenu.setLayout(new FlowLayout(FlowLayout.LEFT));

        String[] nombres = {
                "Categoría", "Marca", "Cliente", "Proveedor",
                "Venta", "Usuario", "mipescuezo"
        };

        for (String nombre : nombres) {
            JButton btn = new JButton(nombre);
            btn.setPreferredSize(new Dimension(120, 40));
            btn.setBackground(new Color(30, 144, 255));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            btn.addActionListener(this);
            panelMenu.add(btn);
        }

        panelPrincipal.add(panelMenu, BorderLayout.NORTH);
        panelPrincipal.add(dsk_escritorio, BorderLayout.CENTER);

        this.setContentPane(panelPrincipal);
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
        String comando = e.getActionCommand();

        switch (comando) {
            case "Categoría":
                AbrirFrm(FrmCategoria.getInstancia());
                break;
            case "Marca":
                AbrirFrm(FrmMarca.getInstancia());
                break;
            case "Cliente":
                break;
            case "Proveedor":
                break;
            case "Venta":
                break;
            case "Usuario":
                break;
        }
    }



    public static void main(String[] args) {
        AppMenu app_menu = new AppMenu();
        app_menu.setVisible(true);
    }
}

