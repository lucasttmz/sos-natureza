package apresentacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class frmChat extends JFrame {

    private final String nomeExibicao;
    private final List<JLabel> topicos;

    private JPanel pnlPrincipal;
    private JPanel pnlLateral;
    private JPanel pnlChat;
    private JPanel pnlMensagens;
    private JPanel pnlEntrada;
    private JPanel pnlTopicos;
    private Layout layoutPrincipal;
    private Layout layoutLateral;
    private Layout layoutMensagens;
    private Layout layoutChat;

    public frmChat(String nome) {
        this.nomeExibicao = nome;
        this.topicos = new ArrayList<>();
        iniciarComponentes();

        this.setTitle("SOS Natureza");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void iniciarComponentes() {
        configurarPainelLateral();
        configurarPainelEntrada();
        configurarPainelMensagens();
        configurarPainelPrincipal();
    }

    private void configurarPainelLateral() {
        pnlLateral = new JPanel();
        pnlLateral.setPreferredSize(new Dimension(274, 718));
        pnlLateral.setMinimumSize(new Dimension(274, 718));
        pnlLateral.setBorder(BorderFactory.createEtchedBorder());
        layoutLateral = new Layout(pnlLateral);
        layoutLateral.preencherHorizontalmente(true);

        // Logo
        JLabel lblLogo = new JLabel("SOS Natureza");
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLogo.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblLogo.setFont(new Font("Arial", Font.BOLD, 30));

        // Tópicos
        pnlTopicos = new JPanel();
        pnlTopicos.setPreferredSize(new Dimension(274, 50));
        pnlTopicos.setMinimumSize(new Dimension(274, 50));
        pnlTopicos.setLayout(new FlowLayout());

        // Mostrar tópicos disponíveis
        adicionarTopico("#geral");
        adicionarTopico("#outros");
        atualizarTopicos();

        // Criar tópico
        JButton btnCriarTopico = new JButton("Criar Novo Tópico");
        btnCriarTopico.addActionListener((e) -> {
            frmNovoTopico frmNT = new frmNovoTopico();
            frmNT.setVisible(true);
            adicionarTopico("#hehe");
            atualizarTopicos();
            revalidate();

        });
        btnCriarTopico.setPreferredSize(new Dimension(135, 25));
        btnCriarTopico.setHorizontalAlignment(SwingConstants.LEFT);
        btnCriarTopico.setHorizontalTextPosition(SwingConstants.LEFT);

        JSeparator separador = new JSeparator();
        separador.setForeground(Color.LIGHT_GRAY);

        layoutLateral.posicionarComponente(lblLogo, 0, 0, new Insets(20, 0, 20, 0));
        layoutLateral.posicionarComponente(pnlTopicos, 1, 0);
        layoutLateral.posicionarComponente(separador, 11, 0, new Insets(0, 0, 20, 0));
        layoutLateral.preencherHorizontalmente(false);
        layoutLateral.posicionarComponente(btnCriarTopico, 12, 0);
        layoutLateral.descentralizar();
    }

    private void configurarPainelEntrada() {
        pnlEntrada = new JPanel();
        pnlEntrada.setBorder(BorderFactory.createEtchedBorder());
        pnlEntrada.setMinimumSize(new Dimension(1092, 38));
        pnlEntrada.setPreferredSize(new Dimension(1092, 38));

        // Entrada e envio
        JTextField txfEntrada = new JTextField();
        txfEntrada.setPreferredSize(new Dimension(1000, 25));
        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.setPreferredSize(new Dimension(75, 25));

        pnlEntrada.add(txfEntrada);
        pnlEntrada.add(btnEnviar);

    }

    private void configurarPainelMensagens() {
        pnlMensagens = new JPanel();
        pnlMensagens.setBorder(BorderFactory.createEtchedBorder());
        pnlMensagens.setMinimumSize(new Dimension(1092, 680));
        pnlMensagens.setPreferredSize(new Dimension(1092, 680));
        layoutMensagens = new Layout(pnlMensagens);
        layoutMensagens.preencherHorizontalmente(true);

    }

    private void configurarPainelPrincipal() {
        pnlPrincipal = new JPanel();
        pnlPrincipal.setPreferredSize(new Dimension(1366, 718));
        layoutPrincipal = new Layout(pnlPrincipal);

        pnlChat = new JPanel();
        pnlChat.setMinimumSize(new Dimension(1092, 718));
        pnlChat.setPreferredSize(new Dimension(1092, 718));
        layoutChat = new Layout(pnlChat);

        layoutChat.posicionarComponente(pnlMensagens, 0, 0);
        layoutChat.posicionarComponente(pnlEntrada, 1, 0);
        layoutPrincipal.posicionarComponente(pnlLateral, 0, 0);
        layoutPrincipal.posicionarComponente(pnlChat, 0, 1);

        this.add(pnlPrincipal);
    }

    private void adicionarTopico(String hashtag) {
        JLabel lbl = new JLabel(hashtag);
        lbl.setBorder(BorderFactory.createSoftBevelBorder(0));
        lbl.setPreferredSize(new Dimension(274, 25));
        lbl.setMinimumSize(new Dimension(274, 25));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        topicos.add(lbl);
    }

    private void atualizarTopicos() {
        int altura = (topicos.size() + 1) * 30;
        pnlTopicos.setPreferredSize(new Dimension(274, altura));
        pnlTopicos.setMinimumSize(new Dimension(274, altura));

        for (JLabel topico : topicos) {
            pnlTopicos.add(topico);
        }

    }
}
