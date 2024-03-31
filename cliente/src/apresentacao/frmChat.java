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

public class frmChat extends JFrame {

    private String nomeExibicao;
    
    private JPanel pnlPrincipal;
    private JPanel pnlLateral;
    private JPanel pnlChat;
    private JPanel pnlMensagens;
    private JPanel pnlEntrada;
    
    private GridBagConstraints gbc;
    private GridBagLayout layoutPrincipal;
    private GridBagLayout layoutLateral;
    private GridBagLayout layoutChat;
    private GridBagLayout layoutMensagens;
    private GridBagLayout layoutEntrada;

    public frmChat(String nome) {
        this.nomeExibicao = nome;

        this.setTitle("SOS Natureza");
        this.setSize(1336, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        iniciarComponentes();
    }

    public void iniciarComponentes() {
        // Layout
        pnlPrincipal = new JPanel();
        layoutPrincipal = new GridBagLayout();
        layoutLateral = new GridBagLayout();
        layoutChat = new GridBagLayout();
        layoutMensagens = new GridBagLayout();
        layoutEntrada = new GridBagLayout();
        
        gbc = new GridBagConstraints();
        pnlPrincipal.setPreferredSize(new Dimension(1366, 718));
        pnlPrincipal.setLayout(layoutPrincipal);
        
        // Painel Lateral
        pnlLateral = new JPanel();
        pnlLateral.setBackground(Color.red);
        pnlLateral.setPreferredSize(new Dimension(274, 718));
        pnlLateral.setMinimumSize(new Dimension(274, 718));
        pnlLateral.setLayout(layoutLateral);

        // Painel Lateral
        pnlChat = new JPanel();
        pnlChat.setBackground(Color.green);
        pnlChat.setMinimumSize(new Dimension(1092, 718));
        pnlChat.setPreferredSize(new Dimension(1092, 718));

        posicionarComponente(layoutPrincipal, pnlPrincipal, pnlLateral, 0, 0, 1, 1);
        posicionarComponente(layoutPrincipal, pnlPrincipal, pnlChat, 0, 1, 1, 1);
        
        gbc.fill = GridBagConstraints.BOTH;
        
        // Logo
        JLabel lblLogo = new JLabel("SOS Natureza");
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLogo.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblLogo.setFont(new Font("Arial", Font.BOLD, 30));
        posicionarComponente(layoutLateral, pnlLateral, lblLogo, 0, 0, 1, 1);
        
        // Geral
        JLabel lblGeral = new JLabel("#geral");
        lblGeral.setHorizontalAlignment(SwingConstants.CENTER);
        lblGeral.setHorizontalTextPosition(SwingConstants.CENTER);
        lblGeral.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblGeral.setFont(new Font("Arial", Font.BOLD, 16));
        posicionarComponente(layoutLateral, pnlLateral, lblGeral, 1, 0, 1, 1);
        
        // Geral
        JLabel lblTopico = new JLabel("#topico1");
        lblTopico.setHorizontalAlignment(SwingConstants.CENTER);
        lblTopico.setHorizontalTextPosition(SwingConstants.CENTER);
        lblTopico.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblTopico.setFont(new Font("Arial", Font.BOLD, 16));
        
        posicionarComponente(layoutLateral, pnlLateral, lblTopico, 2, 0, 1, 1);
        
        // Painel Mensagens
        pnlMensagens = new JPanel();
        pnlMensagens.setBackground(Color.yellow);
        pnlMensagens.setMinimumSize(new Dimension(1092, 665));
        pnlMensagens.setPreferredSize(new Dimension(1092, 665));
        pnlMensagens.setLayout(layoutMensagens);
        
        posicionarComponente(layoutChat, pnlChat, pnlMensagens, 0, 0, 1, 1);
        
        // Painel Entrada
        pnlEntrada = new JPanel();
        pnlEntrada.setBackground(Color.blue);
        pnlEntrada.setMinimumSize(new Dimension(1092, 45));
        pnlEntrada.setPreferredSize(new Dimension(1092, 45));
        pnlEntrada.setLayout(layoutEntrada);
        
        JTextField txfEntrada = new JTextField();
        txfEntrada.setPreferredSize(new Dimension(800, 25));
        JButton btnEmoji = new JButton("\ueb54");
        btnEmoji.setPreferredSize(new Dimension(50, 25));
        btnEmoji.setHorizontalAlignment(SwingConstants.LEFT);
        btnEmoji.setHorizontalTextPosition(SwingConstants.LEFT);
        btnEmoji.setFont(new Font("FiraCode Nerd Font", 0, 20));
        JButton btnEnviar = new JButton("\udb84\udcdd");
        btnEnviar.setFont(new Font("FiraCode Nerd Font", 0, 20));
        btnEnviar.setPreferredSize(new Dimension(75, 25));
        
        pnlEntrada.add(txfEntrada);
        pnlEntrada.add(btnEmoji);
        pnlEntrada.add(btnEnviar);

        posicionarComponente(layoutChat, pnlChat, pnlEntrada, 1, 0, 1, 1);
        
        this.add(pnlPrincipal);
    }

    private void posicionarComponente(GridBagLayout layout, JPanel pnl, Component c, int linha, int coluna, int largura, int altura) {
        gbc.gridy = linha;
        gbc.gridx = coluna;
        gbc.gridheight = altura;
        gbc.gridwidth = largura;
        layout.setConstraints(c, gbc);
        pnl.add(c);
    }
}
