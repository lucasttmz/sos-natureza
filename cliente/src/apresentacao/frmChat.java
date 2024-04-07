package apresentacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class frmChat extends JFrame {

    private String nomeExibicao;

    private JPanel pnlPrincipal;
    private JPanel pnlLateral;
    private JPanel pnlChat;
    private JPanel pnlMensagens;
    private JPanel pnlEntrada;
    private Layout layoutPrincipal;
    private Layout layoutLateral;
    private Layout layoutEntrada;
    private Layout layoutMensagens;
    private Layout layoutChat;

    public frmChat(String nome) {
        this.nomeExibicao = nome;

        this.setTitle("SOS Natureza");
        this.setSize(1336, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        configurarPainelLateral();
        configurarPainelEntrada();
        configurarPainelMensagens();
        configurarPainelPrincipal();
    }

    private void configurarPainelLateral() {
        pnlLateral = new JPanel();
        pnlLateral.setBackground(Color.red);
        pnlLateral.setPreferredSize(new Dimension(274, 718));
        pnlLateral.setMinimumSize(new Dimension(274, 718));
        layoutLateral = new Layout(pnlLateral);
        layoutLateral.preencherHorizontalmente(true);
        Insets margemTopicos = new Insets(0, 0, 5, 0);
        Insets margemLogo = new Insets(20, 0, 20, 0);

        // Logo
        JLabel lblLogo = new JLabel("SOS Natureza");
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLogo.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblLogo.setFont(new Font("Arial", Font.BOLD, 30));

        // Geral
        JLabel lblGeral = new JLabel("#geral");
        lblGeral.setHorizontalAlignment(SwingConstants.CENTER);
        lblGeral.setHorizontalTextPosition(SwingConstants.CENTER);
        lblGeral.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblGeral.setFont(new Font("Arial", Font.BOLD, 16));

        // Topico Teste
        JLabel lblTopico = new JLabel("#teste");
        lblTopico.setHorizontalAlignment(SwingConstants.CENTER);
        lblTopico.setHorizontalTextPosition(SwingConstants.CENTER);
        lblTopico.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblTopico.setFont(new Font("Arial", Font.BOLD, 16));

        // Criar tópico
        JButton btnCriarTopico = new JButton("Criar Novo Tópico");
        btnCriarTopico.addActionListener((e) -> {
            frmNovoTopico frmNT = new frmNovoTopico();
            frmNT.setVisible(true);
        });
        btnCriarTopico.setPreferredSize(new Dimension(135, 25));
        btnCriarTopico.setHorizontalAlignment(SwingConstants.LEFT);
        btnCriarTopico.setHorizontalTextPosition(SwingConstants.LEFT);

        layoutLateral.posicionarComponente(lblLogo, 0, 0, margemLogo);
        layoutLateral.posicionarComponente(lblGeral, 1, 0, margemTopicos);
        layoutLateral.posicionarComponente(lblTopico, 2, 0, margemTopicos);
        layoutLateral.posicionarComponente(new JSeparator(), 11, 0, margemLogo);
        layoutLateral.preencherHorizontalmente(false);
        layoutLateral.posicionarComponente(btnCriarTopico, 12, 0, margemLogo);
        layoutLateral.descentralizar();
    }

    private void configurarPainelEntrada() {
        pnlEntrada = new JPanel();
        pnlEntrada.setBackground(Color.blue);
        pnlEntrada.setMinimumSize(new Dimension(1092, 45));
        pnlEntrada.setPreferredSize(new Dimension(1092, 45));
        layoutEntrada = new Layout(pnlEntrada);
        layoutEntrada.preencherHorizontalmente(true);

        // Entrada e envio
        JTextField txfEntrada = new JTextField();
        txfEntrada.setPreferredSize(new Dimension(900, 25));
        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.setPreferredSize(new Dimension(75, 25));

        layoutEntrada.posicionarComponente(txfEntrada, 0, 0, new Insets(0, 0, 0, 10));
        layoutEntrada.posicionarComponente(btnEnviar, 0, 1);

    }

    private void configurarPainelMensagens() {
        pnlMensagens = new JPanel();
        pnlMensagens.setBackground(Color.yellow);
        pnlMensagens.setMinimumSize(new Dimension(1092, 665));
        pnlMensagens.setPreferredSize(new Dimension(1092, 665));
        layoutMensagens = new Layout(pnlMensagens);
        layoutMensagens.preencherHorizontalmente(true);

    }

    private void configurarPainelPrincipal() {
        pnlPrincipal = new JPanel();
        pnlPrincipal.setPreferredSize(new Dimension(1366, 718));
        layoutPrincipal = new Layout(pnlPrincipal);

        pnlChat = new JPanel();
        pnlChat.setBackground(Color.green);
        pnlChat.setMinimumSize(new Dimension(1092, 718));
        pnlChat.setPreferredSize(new Dimension(1092, 718));
        layoutChat = new Layout(pnlChat);

        layoutChat.posicionarComponente(pnlMensagens, 0, 0);
        layoutChat.posicionarComponente(pnlEntrada, 1, 0);
        layoutPrincipal.posicionarComponente(pnlLateral, 0, 0);
        layoutPrincipal.posicionarComponente(pnlChat, 0, 1);

        this.add(pnlPrincipal);
    }
}
