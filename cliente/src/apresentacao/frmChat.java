package apresentacao;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class frmChat extends JFrame {

    private final String nomeExibicao;
    private final Map<JLabel, JPanel> topicos;

    private JTextField txfEntrada;
    private JButton btnEmoji;

    private JPanel pnlPrincipal;
    private JPanel pnlLateral;
    private JPanel pnlChat;
    private JPanel pnlMensagens;
    private JPanel pnlEntrada;
    private JPanel pnlTopicos;
    private Layout layoutPrincipal;
    private Layout layoutLateral;
    private Layout layoutChat;
    private CardLayout layoutMensagens;

    public frmChat(String nome) {
        this.nomeExibicao = nome;
        this.topicos = new LinkedHashMap<>(); // Preserva a ordem
        iniciarComponentes();

        // Mostrar tópicos disponíveis
        adicionarTopico("#geral");

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

        // Criar tópico
        JButton btnCriarTopico = new JButton("Criar Novo Tópico");
        btnCriarTopico.addActionListener((e) -> {
            frmNovoTopico frmNT = new frmNovoTopico();
            frmNT.setVisible(true);
            frmNT.getHashtag().ifPresent((hashtag) -> {
                adicionarTopico(hashtag);
//                atualizarTopicos();
                revalidate();
            });
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
        pnlEntrada.setMinimumSize(new Dimension(1092, 43));
        pnlEntrada.setPreferredSize(new Dimension(1092, 43));

        // Entrada
        txfEntrada = new JTextField();
        // TODO: Encontrar uma fonte melhor
        txfEntrada.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        txfEntrada.setPreferredSize(new Dimension(905, 30));

        // Emoji
        btnEmoji = new JButton("🌳");
        btnEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        btnEmoji.setPreferredSize(new Dimension(75, 30));
        btnEmoji.addActionListener((e) -> mostrarListaEmojis());

        // Enviar
        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.setPreferredSize(new Dimension(75, 30));

        pnlEntrada.add(txfEntrada);
        pnlEntrada.add(btnEmoji);
        pnlEntrada.add(btnEnviar);

    }

    private void configurarPainelMensagens() {
        pnlMensagens = new JPanel();
        pnlMensagens.setBorder(BorderFactory.createEtchedBorder());
        pnlMensagens.setMinimumSize(new Dimension(1092, 675));
        pnlMensagens.setPreferredSize(new Dimension(1092, 675));

        layoutMensagens = new CardLayout();
        pnlMensagens.setLayout(layoutMensagens);

        // TODO: Adicionar #geral
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

    private void mostrarListaEmojis() {
        JPopupMenu mnuEmoji = new JPopupMenu();
        String[] emojis = {"🌍", "🌳", "🌱", "♻️", "🗑️", "🚮", "🛢️", "🔥", "⚠️", "🚨", "🚒", "🧯"};
        JList<String> lstEmoji = new JList<>(emojis);

        lstEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        lstEmoji.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstEmoji.setLayoutOrientation(JList.VERTICAL_WRAP);
        lstEmoji.setVisibleRowCount(3);

        lstEmoji.addListSelectionListener(e -> {
            // Espera a seleção senão buga tudo
            if (!e.getValueIsAdjusting()) {
                String selectedEmoji = lstEmoji.getSelectedValue();
                txfEntrada.setText(txfEntrada.getText() + selectedEmoji);
                mnuEmoji.setVisible(false);
            }
        });

        JScrollPane pnlEmojis = new JScrollPane(lstEmoji);
        mnuEmoji.add(pnlEmojis);

        int espacamento = 15;
        int x = btnEmoji.getWidth() - pnlEmojis.getPreferredSize().width;
        int y = btnEmoji.getY() - (mnuEmoji.getPreferredSize().height + espacamento);
        mnuEmoji.show(btnEmoji, x, y);
    }

    private void adicionarTopico(String hashtag) {
        JPanel pnl = new JPanel();
        JLabel lbl = new JLabel(hashtag);
        lbl.setBorder(BorderFactory.createSoftBevelBorder(0));
        lbl.setPreferredSize(new Dimension(274, 25));
        lbl.setMinimumSize(new Dimension(274, 25));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));

        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(lbl.getText());
            }
        });

        topicos.put(lbl, pnl);

        atualizarTopicos();
    }

    private void atualizarTopicos() {
        int altura = (topicos.size() + 1) * 30;
        pnlTopicos.setPreferredSize(new Dimension(274, altura));
        pnlTopicos.setMinimumSize(new Dimension(274, altura));

        for (Map.Entry<JLabel, JPanel> topico : topicos.entrySet()) {
            pnlTopicos.add(topico.getKey());
        }

    }

    // Temporario para não ter que digitar o ip toda vez que for testar
    public static void main(String[] args) {
        frmChat frmChat = new frmChat("teste");
        frmChat.setVisible(true);
    }
}
