package apresentacao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class frmInicial extends JFrame {

    private Layout layout;

    public frmInicial() {
        this.setTitle("SOS Natureza");
        this.setSize(400, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.iniciarComponentes();
    }

    public void iniciarComponentes() {
        // Titulo
        JLabel lblTitulo = new JLabel("SOS Natureza");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setHorizontalTextPosition(SwingConstants.CENTER);
        lblTitulo.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));

        // Nome exibição
        JLabel lblNome = new JLabel("Nome de exibição");
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        lblNome.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNome.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblNome.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField txfNome = new JTextField();
        txfNome.setHorizontalAlignment(SwingConstants.CENTER);

        // Entrar
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener((e) -> {
            // Validar nome
            frmChat frmC = new frmChat(txfNome.getText());
            frmC.setVisible(true);
            this.dispose();
        });

        // Layout
        JPanel pnlPrincipal = new JPanel();
        pnlPrincipal.setPreferredSize(new Dimension(400, 450));
        Insets margemLogo = new Insets(15, 0, 15, 0);
        Insets margemCampos = new Insets(3, 0, 3, 0);

        layout = new Layout(pnlPrincipal);
        layout.preencherHorizontalmente(true);
        layout.posicionarComponente(lblTitulo, 0, 0, margemLogo);
        layout.posicionarComponente(lblNome, 1, 0, margemCampos);
        layout.posicionarComponente(txfNome, 2, 0, margemCampos);
        layout.posicionarComponente(btnEntrar, 3, 0, margemCampos);

        this.add(pnlPrincipal);
    }
}
