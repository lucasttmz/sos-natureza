package apresentacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import modelo.Controle;

public class frmInicial extends JFrame {

    private Layout layout;

    private JTextField txfNome;
    private JTextField txfIP;
    private JTextField txfPorta;

    public frmInicial() {
        this.setTitle("SOS Natureza");
        this.setSize(400, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.iniciarComponentes();
    }

    public void iniciarComponentes() {
        // Ícone
        ImageIcon icone = new ImageIcon(getClass().getResource("/resources/icon.png"));
        ImageIcon logo = new ImageIcon(getClass().getResource("/resources/logo.png"));
        setIconImage(icone.getImage());

        // Titulo
        JLabel lblTitulo = new JLabel();
        lblTitulo.setIcon(logo);
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
        txfNome = new JTextField();
        txfNome.setHorizontalAlignment(SwingConstants.CENTER);

        // Endereço de IP
        JLabel lblIP = new JLabel("Endereço de IP");
        lblIP.setHorizontalAlignment(SwingConstants.CENTER);
        lblIP.setHorizontalTextPosition(SwingConstants.CENTER);
        lblIP.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblIP.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lblPorta = new JLabel("Porta");
        lblPorta.setHorizontalAlignment(SwingConstants.CENTER);
        lblPorta.setHorizontalTextPosition(SwingConstants.CENTER);
        lblPorta.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblPorta.setFont(new Font("Arial", Font.BOLD, 16));
        txfIP = new JTextField();
        txfIP.setText("127.0.0.1");
        txfIP.setPreferredSize(new Dimension(200, 25));
        txfIP.setHorizontalAlignment(SwingConstants.CENTER);
        txfPorta = new JTextField();
        txfPorta.setText("12345");
        txfPorta.setPreferredSize(new Dimension(100, 25));
        txfPorta.setHorizontalAlignment(SwingConstants.CENTER);

        // Separador
        JSeparator separador = new JSeparator();
        separador.setForeground(Color.LIGHT_GRAY);

        // Entrar
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener((e) -> this.conectar());

        // Layout
        JPanel pnlPrincipal = new JPanel();
        pnlPrincipal.setPreferredSize(new Dimension(400, 450));
        Insets margemGrande = new Insets(15, 0, 15, 0);
        Insets margemCampos = new Insets(3, 0, 3, 0);

        layout = new Layout(pnlPrincipal);
        layout.preencherHorizontalmente(true);
        layout.posicionarComponente(lblTitulo, 0, 0, 2, 1, margemGrande);
        layout.posicionarComponente(lblIP, 1, 0, 1, 1, margemCampos);
        layout.posicionarComponente(lblPorta, 1, 1, 1, 1, margemCampos);
        layout.posicionarComponente(txfIP, 2, 0, 1, 1, margemCampos);
        layout.posicionarComponente(txfPorta, 2, 1, 1, 1, margemCampos);
        layout.posicionarComponente(separador, 3, 0, 2, 1, margemGrande);
        layout.posicionarComponente(lblNome, 4, 0, 2, 1, margemCampos);
        layout.posicionarComponente(txfNome, 5, 0, 2, 1, margemCampos);
        layout.posicionarComponente(btnEntrar, 6, 0, 2, 1, margemGrande);

        this.add(pnlPrincipal);
    }

    private void conectar() {
        Controle controle = new Controle();
        if (controle.validarConexao(txfNome.getText(), txfIP.getText(), txfPorta.getText())) {
            controle.conectar(txfNome.getText());
            if (controle.getMensagem().isEmpty()) {
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        controle.getMensagem(),
                        "Erro de conexão",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    controle.getMensagem(),
                    "Erro de validação",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
