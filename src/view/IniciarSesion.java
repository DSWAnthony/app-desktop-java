package view;


import controller.UsuarioController;
import model.Usuario;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class IniciarSesion extends JFrame {

    private final UsuarioController controller;

    public IniciarSesion() {
        super("Login");
        this.controller = new UsuarioController();
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        configurarInterfaz();
        this.setVisible(true);
    }

    private void configurarInterfaz() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(180, 210, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lblIcono = new JLabel(new ImageIcon(getClass().getResource("/images/user.png")));
        lblIcono.setBounds(150, 20, 100, 100);
        panel.add(lblIcono);

        JLabel lblUsuario = new JLabel("Username");
        lblUsuario.setBounds(50, 140, 100, 25);
        panel.add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(50, 165, 300, 30);
        panel.add(txtUsuario);

        JLabel lblPass = new JLabel("Password");
        lblPass.setBounds(50, 210, 100, 25);
        panel.add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(50, 235, 300, 30);
        panel.add(txtPass);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(140, 290, 120, 40);
        panel.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String password = new String(txtPass.getPassword());

            Usuario user = new Usuario();
            user.setUser(usuario);
            user.setPassword(password);

            boolean acceso = controller.validarCuenta(user);

            if (acceso) {
                JOptionPane.showMessageDialog(this, "¡Bienvenido!");
                this.dispose();

                SwingUtilities.invokeLater(() -> {
                    AppMenu appMenu = new AppMenu();
                    appMenu.setVisible(true);
                });

            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            }
        });

        this.add(panel);
    }
}
