package apresentacao;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.HyperlinkEvent;
import modelo.Controle;

public class frmChat extends JFrame {

    private JTextField txfEntrada;
    private JButton btnEmoji;
    private JButton btnLocalizacao;
    private JButton btnEnviar;
    private JPanel pnlPrincipal;
    private JPanel pnlLateral;
    private JPanel pnlChat;
    private JPanel pnlMensagens;
    private JPanel pnlEntrada;
    private JPanel pnlTopicos;
    private JPanel pnlTopicoGeral;
    private Layout layoutPrincipal;
    private Layout layoutLateral;
    private Layout layoutChat;
    private CardLayout layoutMensagens;

    private final Map<String, String> msgSalva;
    private final Map<JLabel, JPanel> paineisTopicos;
    private final Map<String, JEditorPane> mensagensTopicos;
    private final Controle controle;
    private final ImageIcon icone;

    public frmChat(Controle controle) {
        this.controle = controle;
        this.paineisTopicos = new LinkedHashMap<>(); // Preserva a ordem
        this.mensagensTopicos = new HashMap<>();
        this.msgSalva = new HashMap<>();
        this.icone = new ImageIcon(getClass().getResource("/resources/icon.png"));
        
        iniciarComponentes();

        this.setTitle("SOS Natureza");
        this.setIconImage(icone.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void iniciarComponentes() {
        // Configura os paineis
        configurarPainelLateral();
        configurarPainelEntrada();
        configurarPainelMensagens();
        configurarPainelPrincipal();

        // Inicializa os tópicos
        adicionarTopicoGeral();
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
        lblLogo.setIcon(icone);
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
        btnCriarTopico.addActionListener((e) -> criarNovoTopico());
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
        txfEntrada = new JTextField("Digite sua mensagem aqui");
        txfEntrada.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        txfEntrada.setPreferredSize(new Dimension(825, 30));
        txfEntrada.setForeground(Color.WHITE);

        // Envio da mensagem
        txfEntrada.addActionListener((e) -> enviarMensagem());

        // Placeholder da mensagem
        txfEntrada.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txfEntrada.getText().equals("Digite sua mensagem aqui")) {
                    txfEntrada.setText("");
                }
                txfEntrada.setForeground(Color.WHITE);

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txfEntrada.getText().isEmpty()) {
                    txfEntrada.setForeground(Color.GRAY);
                    txfEntrada.setText("Digite sua mensagem aqui");
                }
            }
        });

        // Emoji
        btnEmoji = new JButton("🌳");
        btnEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        btnEmoji.setPreferredSize(new Dimension(75, 30));
        btnEmoji.addActionListener((e) -> mostrarListaEmojis());

        // Localizacao
        btnLocalizacao = new JButton("🌎");
        btnLocalizacao.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        btnLocalizacao.setPreferredSize(new Dimension(75, 30));
        btnLocalizacao.addActionListener((e) -> enviarLocalizacao());

        // Enviar
        btnEnviar = new JButton("Enviar");
        btnEnviar.setPreferredSize(new Dimension(75, 30));
        btnEnviar.addActionListener((e) -> enviarMensagem());

        pnlEntrada.add(txfEntrada);
        pnlEntrada.add(btnLocalizacao);
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

        // Concatena o emoji com o texto digitado
        lstEmoji.addListSelectionListener(e -> {
            String selectedEmoji = lstEmoji.getSelectedValue();
            if (txfEntrada.getText().equals("Digite sua mensagem aqui")) {
                txfEntrada.setText(selectedEmoji);
            } else {
                txfEntrada.setText(txfEntrada.getText() + selectedEmoji);
            }
            mnuEmoji.setVisible(false);
        });

        JScrollPane pnlEmojis = new JScrollPane(lstEmoji);
        mnuEmoji.add(pnlEmojis);

        int espacamento = 15;
        int x = btnEmoji.getWidth() - pnlEmojis.getPreferredSize().width;
        int y = btnEmoji.getY() - (mnuEmoji.getPreferredSize().height + espacamento);
        mnuEmoji.show(btnEmoji, x, y);
    }

    private void enviarMensagem() {
        String textoDigitado = txfEntrada.getText();
        if (textoDigitado.isBlank()) {
            return;
        }

        // Checa se foi um comando de chat
        if (textoDigitado.startsWith("/")) {
            if (textoDigitado.equals("/localizacao")) {
                enviarLocalizacao();
            } else {
                controle.executarComando(textoDigitado);
            }
        } else {
            controle.enviarMensagem(textoDigitado + "\n");
        }
        txfEntrada.setText("");
    }

    private void enviarLocalizacao() {
        // Pede permissão para compartilhar localização
        int permissao = JOptionPane.showOptionDialog(
                this,
                "Deseja compartilhar sua localização?",
                "Aviso de Privacidade",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Sim", "Não"},
                "Sim");

        if (permissao == JOptionPane.YES_OPTION) {
            controle.executarComando("/localizacao");
        }
    }
    
    public void adicionarMensagem(String msgFormatada, String canal) {
        // Mensagens do #hashtag
        JEditorPane edpMsg = mensagensTopicos.get(canal);
        String conteudo = edpMsg.getText();
        edpMsg.setText(conteudo.replace("</body>\n</html>", msgFormatada + "<br></body>\n</html>"));
        edpMsg.setCaretPosition(edpMsg.getDocument().getLength());

        // Mensagens do #geral
        edpMsg = mensagensTopicos.get(canal + "_geral");
        conteudo = edpMsg.getText();
        // Remove a imagem na hora de exibir no #geral
        msgFormatada = msgFormatada.replace("<br><img src='", "").replace("'/>", "");
        edpMsg.setText(conteudo.replace("</body>\n</html>", msgFormatada + "<br></body>\n</html>"));
        edpMsg.setCaretPosition(edpMsg.getDocument().getLength());
    }

    private List<JPanel> criarNovoTopicoGeral(List<String> infoTopico) {
        final int larguraMaxima = 1085;
        final int tamanhoImagem = 200;

        JPanel pnlDetalhes = new JPanel();
        Layout layoutDetalhes = new Layout(pnlDetalhes);
        pnlDetalhes.setPreferredSize(new Dimension(larguraMaxima, tamanhoImagem + 10));
        pnlDetalhes.setMaximumSize(new Dimension(larguraMaxima, tamanhoImagem + 10));

        // Título do tópico
        JLabel lblTitulo = new JLabel(infoTopico.get(0));
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 52));
        lblTitulo.setBackground(Color.white);
        lblTitulo.setPreferredSize(new Dimension(larguraMaxima - tamanhoImagem - 10, 50));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Descrição do tópico
        JLabel lblDesc = new JLabel(infoTopico.get(1));
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblDesc.setBackground(Color.CYAN);
        lblDesc.setPreferredSize(new Dimension(larguraMaxima - tamanhoImagem - 10, 100));
        lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
        lblDesc.setVerticalAlignment(SwingConstants.TOP);

        // Imagem do tópico
        JLabel lblImagem = new JLabel();
        lblImagem.setPreferredSize(new Dimension(tamanhoImagem, tamanhoImagem));
        if (!infoTopico.get(2).isEmpty()) {
            lblImagem.setBorder(BorderFactory.createBevelBorder(0));
            lblImagem.setVerticalTextPosition(SwingConstants.BOTTOM);
            lblImagem.setHorizontalTextPosition(SwingConstants.CENTER);
            lblImagem.setHorizontalAlignment(SwingConstants.CENTER);
            lblImagem.setIcon(new ImageIcon(infoTopico.get(2)));
        }

        layoutDetalhes.posicionarComponente(lblImagem, 0, 0, 1, 3);
        layoutDetalhes.posicionarComponente(lblTitulo, 0, 1, 1, 1);
        layoutDetalhes.posicionarComponente(lblDesc, 2, 1, 1, 1);

        // Painel mensagens
        JPanel pnlMsg = new JPanel();
        pnlMsg.setPreferredSize(new Dimension(larguraMaxima, 70));
        pnlMsg.setMaximumSize(new Dimension(larguraMaxima, 70));
        pnlMsg.setBorder(BorderFactory.createSoftBevelBorder(0));

        // Mensagens do tópico
        JEditorPane edpMensagens = new JEditorPane();
        edpMensagens.setContentType("text/html");
        edpMensagens.setText("<html><body></body></html>");
        edpMensagens.setPreferredSize(new Dimension(larguraMaxima - 20, 54));
        edpMensagens.setMaximumSize(new Dimension(larguraMaxima - 20, 54));
        edpMensagens.setEditable(false);
        edpMensagens.setFocusable(false);
        edpMensagens.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                mostrarTopico(infoTopico.get(3));
            }
        });

        // Adiciona o scroll
        JScrollPane scrollMensagens = new JScrollPane();
        scrollMensagens.setPreferredSize(new Dimension(larguraMaxima - 20, 54));
        scrollMensagens.setMaximumSize(new Dimension(larguraMaxima - 20, 54));
        scrollMensagens.setViewportView(edpMensagens);
        pnlMsg.add(scrollMensagens);

        List<String> todasMensagens = controle.todasMensagens(infoTopico.get(3));
        String juntos = String.join("<br>", todasMensagens);
        if (!juntos.isBlank()) {
            juntos += "<br>";
        }
        edpMensagens.setText("<html><body>" + juntos + "</body></html>");
        mensagensTopicos.put(infoTopico.get(3) + "_geral", edpMensagens);

        return List.of(pnlDetalhes, pnlMsg);

    }

    private void adicionarTopicoGeral() {
        pnlTopicoGeral = new JPanel();
        pnlTopicoGeral.setLayout(new BoxLayout(pnlTopicoGeral, BoxLayout.Y_AXIS));
        JScrollPane scrollTopicos = new JScrollPane(pnlTopicoGeral);
        scrollTopicos.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        scrollTopicos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JLabel lblAba = adicionarAba("#geral");

        // Salva o estado do #geral
        paineisTopicos.put(lblAba, pnlTopicoGeral);
        pnlMensagens.add(scrollTopicos, lblAba.getText());
        atualizarTopicos();
    }

    public void adicionarNovoTopico(String hashtag) {
        JPanel pnlTopico = new JPanel();
        JLabel lblAba = adicionarAba(hashtag);

        final int larguraMaxima = 1085;
        final int tamanhoImagem = 200;
        List<String> infoTopico = controle.informacoesTopico(hashtag);

        // Painel dos detalhes do tópico
        JPanel pnlDetalhes = new JPanel();
        Layout layoutDetalhes = new Layout(pnlDetalhes);
        pnlDetalhes.setPreferredSize(new Dimension(larguraMaxima, tamanhoImagem + 10));

        // Título do tópico
        JLabel lblTitulo = new JLabel(infoTopico.get(0));
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 52));
        lblTitulo.setPreferredSize(new Dimension(larguraMaxima - tamanhoImagem - 10, 50));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Descrição do tópico
        JLabel lblDesc = new JLabel(infoTopico.get(1));
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblDesc.setPreferredSize(new Dimension(larguraMaxima - tamanhoImagem - 10, 100));
        lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
        lblDesc.setVerticalAlignment(SwingConstants.TOP);

        // Imagem do tópico
        JLabel lblImagem = new JLabel();
        lblImagem.setPreferredSize(new Dimension(tamanhoImagem, tamanhoImagem));
        if (!infoTopico.get(2).isEmpty()) {
            lblImagem.setBorder(BorderFactory.createBevelBorder(0));
            lblImagem.setVerticalTextPosition(SwingConstants.BOTTOM);
            lblImagem.setHorizontalTextPosition(SwingConstants.CENTER);
            lblImagem.setHorizontalAlignment(SwingConstants.CENTER);
            lblImagem.setIcon(new ImageIcon(infoTopico.get(2)));
        }

        layoutDetalhes.posicionarComponente(lblImagem, 0, 0, 1, 3);
        layoutDetalhes.posicionarComponente(lblTitulo, 0, 1, 1, 1);
        layoutDetalhes.posicionarComponente(lblDesc, 2, 1, 1, 1);

        // Painel mensagens
        JPanel pnlMsg = new JPanel();
        pnlMsg.setPreferredSize(new Dimension(larguraMaxima, 450));
        pnlMsg.setBorder(BorderFactory.createSoftBevelBorder(0));

        // Mensagens do tópico
        JEditorPane edpMensagens = new JEditorPane();
        edpMensagens.setPreferredSize(new Dimension(larguraMaxima - 20, 430));
        edpMensagens.setContentType("text/html");
        edpMensagens.setEditable(false);
        
        // Abre o link no navegador
        edpMensagens.addHyperlinkListener((e) -> {
            HyperlinkEvent.EventType eventType = e.getEventType();
            if (eventType.equals(HyperlinkEvent.EventType.ACTIVATED)) {
                URL url = e.getURL();
                try {
                    Desktop.getDesktop().browse(url.toURI());
                } catch (IOException | URISyntaxException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao abrir o link", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Adiciona o scroll
        JScrollPane scrollMensagens = new JScrollPane();
        scrollMensagens.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollMensagens.setViewportView(edpMensagens);
        pnlMsg.add(scrollMensagens);

        List<String> todasMensagens = controle.todasMensagens(hashtag);
        String juntos = String.join("<br>", todasMensagens);
        if (!juntos.isBlank()) {
            juntos += "<br>";
        }
        edpMensagens.setText("<html><body>" + juntos + "</body></html>");

        pnlTopico.add(pnlDetalhes);
        pnlTopico.add(pnlMsg);
        paineisTopicos.put(lblAba, pnlTopico);
        pnlMensagens.add(pnlTopico, lblAba.getText());
        atualizarTopicos();

        // Salva o estado do lado do cliente
        mensagensTopicos.put(hashtag, edpMensagens);
        msgSalva.put(hashtag, "");

        List<JPanel> novoTopico = criarNovoTopicoGeral(controle.informacoesTopico(hashtag));
        pnlTopicoGeral.add(novoTopico.get(0));
        pnlTopicoGeral.add(novoTopico.get(1));
        revalidate();
    }

    private JLabel adicionarAba(String hashtag) {
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
        msgSalva.put(controle.getCanalAtual(), txfEntrada.getText());
        controle.setCanalAtual(hashtag);
        txfEntrada.setText(msgSalva.get(controle.getCanalAtual()));

        if (hashtag.equals("#geral")) {
            txfEntrada.setText("Não é possível enviar mensagens no #geral");
            txfEntrada.setEnabled(false);
            btnEnviar.setEnabled(false);
            btnLocalizacao.setEnabled(false);
            btnEmoji.setEnabled(false);
        } else {
            JEditorPane edpMsg = mensagensTopicos.get(hashtag);
            edpMsg.setText(edpMsg.getText());
            txfEntrada.setEnabled(true);
            btnEnviar.setEnabled(true);
            btnLocalizacao.setEnabled(true);
            btnEmoji.setEnabled(true);
        }

        String textoDigitado = txfEntrada.getText();
        if (textoDigitado.isEmpty() || textoDigitado.equals("Digite sua mensagem aqui")) {
            txfEntrada.setForeground(Color.GRAY);
            txfEntrada.setText("Digite sua mensagem aqui");
        } else {
            txfEntrada.setForeground(Color.BLACK);
        }

        // Tira o foco do input ao mudar de canal
        txfEntrada.setFocusable(false);
        try {
            Thread.sleep(1);
            txfEntrada.setFocusable(true);
        } catch (InterruptedException ex) {
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

    private void criarNovoTopico() {
        frmNovoTopico frmNT = new frmNovoTopico(this.controle);
        frmNT.setVisible(true);
    }
}
