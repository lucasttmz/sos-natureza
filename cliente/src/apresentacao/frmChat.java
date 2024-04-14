package apresentacao;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import modelo.Controle;
import modelo.Topico;

public class frmChat extends JFrame {

    private JTextField txfEntrada;
    private JButton btnEmoji;
    private JButton btnEnviar;
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

    private String canalAtual = "#geral"; // Mover para o modelo
    private final String nomeExibicao;
    private final Map<String, String> msgSalva;
    private final Map<JLabel, JPanel> paineisTopicos;
    private final Map<String, JTextArea> mensagensTopicos;

    public frmChat(String nome) {
        this.nomeExibicao = nome;
        this.paineisTopicos = new LinkedHashMap<>(); // Preserva a ordem
        this.mensagensTopicos = new HashMap<>();
        this.msgSalva = new HashMap<>();

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
        adicionarTopicoGeral();
        adicionarDemaisTopicos();
        mostrarTopico("#geral");
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
                adicionarNovoTopico(hashtag);
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
        txfEntrada.addActionListener((e) -> {
            if (txfEntrada.getText().isBlank()) {
                return;
            }
            JTextArea txaMsg = mensagensTopicos.get(canalAtual);
            txaMsg.append(nomeExibicao + ": " + txfEntrada.getText() + "\n");
            txfEntrada.setText("");
        });

        // Emoji
        btnEmoji = new JButton("🌳");
        btnEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        btnEmoji.setPreferredSize(new Dimension(75, 30));
        btnEmoji.addActionListener((e) -> mostrarListaEmojis());

        // Enviar
        btnEnviar = new JButton("Enviar");
        btnEnviar.setPreferredSize(new Dimension(75, 30));

        pnlEntrada.add(txfEntrada);
        pnlEntrada.add(btnEmoji);
        pnlEntrada.add(btnEnviar);

    }

    private void configurarPainelMensagens() {
        // Mover para dentro do configurarPainelPrincipal?
        pnlMensagens = new JPanel();
        pnlMensagens.setBorder(BorderFactory.createEtchedBorder());
        pnlMensagens.setMinimumSize(new Dimension(1092, 675));
        pnlMensagens.setPreferredSize(new Dimension(1092, 675));

        layoutMensagens = new CardLayout();
        pnlMensagens.setLayout(layoutMensagens);
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

    private void adicionarTopicoGeral() {
        JPanel pnlTopico = new JPanel();
        JLabel lblAba = adicionarAba("#geral", pnlTopico);
        JPanel pnlDetalhes = new JPanel();
        Layout layoutDetalhes = new Layout(pnlDetalhes);
        pnlDetalhes.setPreferredSize(new Dimension(1085, 210));

        final int larguraMaxima = 1085;
        final int tamanhoImagem = 200;
        Controle controle = new Controle();

        List<String> infoTopico = controle.informacoesTopico("#outros");

        // Título do tópico
        JLabel lblTitulo = new JLabel(infoTopico.get(0));
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 52));
        lblTitulo.setBackground(Color.white);
        lblTitulo.setPreferredSize(new Dimension(larguraMaxima - tamanhoImagem, 50));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Descrição do tópico
        JLabel lblDesc = new JLabel(infoTopico.get(1));
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblDesc.setBackground(Color.CYAN);
        lblDesc.setPreferredSize(new Dimension(larguraMaxima - tamanhoImagem, 150));
        lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
        lblDesc.setVerticalAlignment(SwingConstants.TOP);

        // Imagem do tópico
        JLabel lblImagem = new JLabel(infoTopico.get(2));
        lblImagem.setBackground(Color.WHITE);
        lblImagem.setBorder(BorderFactory.createBevelBorder(0));
        lblImagem.setPreferredSize(new Dimension(tamanhoImagem, tamanhoImagem));

        layoutDetalhes.posicionarComponente(lblImagem, 0, 0, 1, 3);
        layoutDetalhes.posicionarComponente(lblTitulo, 0, 1, 1, 1);
        layoutDetalhes.posicionarComponente(new JSeparator(), 1, 1, 1, 1);
        layoutDetalhes.posicionarComponente(lblDesc, 2, 1, 1, 1);

        JPanel pnlMsg = new JPanel();
        pnlMsg.setPreferredSize(new Dimension(larguraMaxima, 100));
        pnlMsg.setBorder(BorderFactory.createSoftBevelBorder(0));

        // Mensagens do tópico
        JLabel lblUltimasMensagens = new JLabel("Últimas Mensagens");
        JScrollPane scrollMensagens = new JScrollPane();
        JTextArea txaMensagens = new JTextArea();
        txaMensagens.setLineWrap(true);
        txaMensagens.setWrapStyleWord(true);
        txaMensagens.setColumns(97);
        txaMensagens.setRows(3);
        txaMensagens.setEditable(false);
        scrollMensagens.setViewportView(txaMensagens);
        pnlMsg.add(lblUltimasMensagens);
        pnlMsg.add(scrollMensagens);

        // Mostra apenas as três últimas mensagens
        List<String> todasMensagens = controle.todasMensagens("#outros");
        for (int i = todasMensagens.size() - 3; i < todasMensagens.size(); i++) {
            String mensagem = todasMensagens.get(i);
            if (i == todasMensagens.size() - 1) {
                mensagem = mensagem.strip();
            }
            txaMensagens.append(mensagem);
        }

        pnlTopico.add(pnlDetalhes);
        pnlTopico.add(pnlMsg);

        // Salva o estado do #geral
        paineisTopicos.put(lblAba, pnlTopico);
        pnlMensagens.add(pnlTopico, lblAba.getText());
        atualizarTopicos();
    }

    private void adicionarDemaisTopicos() {
        Controle controle = new Controle();
        HashMap<String, Topico> todosTopicos = controle.getTodosTopicos();
        for (Map.Entry<String, Topico> topico : todosTopicos.entrySet()) {
            adicionarNovoTopico(topico.getKey());
        }
    }

    private void adicionarNovoTopico(String hashtag) {
        JPanel pnlTopico = new JPanel();
        JLabel lblAba = adicionarAba(hashtag, pnlTopico);
        JPanel pnlDetalhes = new JPanel();
        Layout layoutDetalhes = new Layout(pnlDetalhes);
        pnlDetalhes.setPreferredSize(new Dimension(1085, 210));

        final int larguraMaxima = 1085;
        final int tamanhoImagem = 200;
        Controle controle = new Controle();

        List<String> infoTopico = controle.informacoesTopico(hashtag);

        // Título do tópico
        JLabel lblTitulo = new JLabel(infoTopico.get(0));
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 52));
        lblTitulo.setBackground(Color.white);
        lblTitulo.setPreferredSize(new Dimension(larguraMaxima - tamanhoImagem, 50));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Descrição do tópico
        JLabel lblDesc = new JLabel(infoTopico.get(1));
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblDesc.setBackground(Color.CYAN);
        lblDesc.setPreferredSize(new Dimension(larguraMaxima - tamanhoImagem, 150));
        lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
        lblDesc.setVerticalAlignment(SwingConstants.TOP);

        // Imagem do tópico
        JLabel lblImagem = new JLabel(infoTopico.get(2));
        lblImagem.setBackground(Color.WHITE);
        lblImagem.setBorder(BorderFactory.createBevelBorder(0));
        lblImagem.setPreferredSize(new Dimension(tamanhoImagem, 200));

        layoutDetalhes.posicionarComponente(lblImagem, 0, 0, 1, 3);
        layoutDetalhes.posicionarComponente(lblTitulo, 0, 1, 1, 1);
        layoutDetalhes.posicionarComponente(new JSeparator(), 1, 1, 1, 1);
        layoutDetalhes.posicionarComponente(lblDesc, 2, 1, 1, 1);

        JPanel pnlMsg = new JPanel();
        pnlMsg.setPreferredSize(new Dimension(larguraMaxima, 450));
        pnlMsg.setBorder(BorderFactory.createSoftBevelBorder(0));

        // Mensagens do tópico
        JScrollPane scrollMensagens = new JScrollPane();
        JTextArea txaMensagens = new JTextArea();
        txaMensagens.setLineWrap(true);
        txaMensagens.setWrapStyleWord(true);
        txaMensagens.setColumns(97);
        txaMensagens.setRows(27);
        txaMensagens.setEditable(false);
        scrollMensagens.setViewportView(txaMensagens);
        pnlMsg.add(scrollMensagens);

        // Mostra todas as mensagens
        List<String> todasMensagens = controle.todasMensagens(hashtag);
        for (String mensagem : todasMensagens) {
            txaMensagens.append(mensagem);
        }

        pnlTopico.add(pnlDetalhes);
        pnlTopico.add(pnlMsg);
        paineisTopicos.put(lblAba, pnlTopico);
        pnlMensagens.add(pnlTopico, lblAba.getText());
        atualizarTopicos();

        // Salva o estado do lado do cliente
        mensagensTopicos.put(hashtag, txaMensagens);
        msgSalva.put(hashtag, "");
    }

    private JLabel adicionarAba(String hashtag, JPanel painel) {
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
                mostrarTopico(lbl.getText());
            }
        });

        return lbl;
    }

    private void mostrarTopico(String hashtag) {
        layoutMensagens.show(pnlMensagens, hashtag);
        for (JLabel lblTopico : paineisTopicos.keySet()) {
            if (lblTopico.getText().equals(hashtag)) {
                lblTopico.setBorder(BorderFactory.createBevelBorder(0));
            } else {
                lblTopico.setBorder(BorderFactory.createSoftBevelBorder(0));
            }
        }
        msgSalva.put(canalAtual, txfEntrada.getText());
        canalAtual = hashtag;
        txfEntrada.setText(msgSalva.get(canalAtual));

        if (hashtag.equals("#geral")) {
            txfEntrada.setEnabled(false);
            btnEnviar.setEnabled(false);
            btnEmoji.setEnabled(false);
        } else {
            txfEntrada.setEnabled(true);
            btnEnviar.setEnabled(true);
            btnEmoji.setEnabled(true);
        }

    }

    private void atualizarTopicos() {
        int altura = (paineisTopicos.size() + 1) * 30;
        pnlTopicos.setPreferredSize(new Dimension(274, altura));
        pnlTopicos.setMinimumSize(new Dimension(274, altura));

        for (Map.Entry<JLabel, JPanel> topico : paineisTopicos.entrySet()) {
            pnlTopicos.add(topico.getKey());
        }

    }

    // Temporario para não ter que digitar o ip toda vez que for testar
    public static void main(String[] args) {
        frmChat frmChat = new frmChat("teste");
        frmChat.setVisible(true);
    }
}
