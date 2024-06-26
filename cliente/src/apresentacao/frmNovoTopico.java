package apresentacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import modelo.Controle;

public class frmNovoTopico extends JDialog {

    private Layout layout;
    private JTextArea txaDesc;
    private JTextField txfNome;
    private JTextField txfFoto;

    private final Controle controle;
    private String hashtag;

    public frmNovoTopico(Controle controle) {
        this.controle = controle;
        this.setTitle("Criar novo tópico");
        this.setModal(true);
        this.setSize(400, 370);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.iniciarComponentes();
    }

    public void iniciarComponentes() {
        // Nome do tópico
        JLabel lblNome = new JLabel("Nome do tópico");
        txfNome = new JTextField();
        txfNome.setPreferredSize(new Dimension(350, 30));

        // Descrição do tópico
        JLabel lblDesc = new JLabel("Descrição do tópico");
        txaDesc = new JTextArea();
        txaDesc.setLineWrap(true);
        txaDesc.setWrapStyleWord(true);
        txaDesc.setBorder(BorderFactory.createLineBorder(new Color(29, 62, 48)));
        txaDesc.setPreferredSize(new Dimension(350, 75));

        // Foto
        JLabel lblFoto = new JLabel("Foto");
        txfFoto = new JTextField();
        txfFoto.setPreferredSize(new Dimension(350, 30));
        txfFoto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                escolherFoto();
            }
        });

        // Botão Criar
        JButton btnCriar = new JButton("Criar");
        btnCriar.addActionListener(e -> criarTopico());
        btnCriar.setPreferredSize(new Dimension(160, 30));
        btnCriar.setHorizontalAlignment(SwingConstants.CENTER);
        btnCriar.setHorizontalTextPosition(SwingConstants.CENTER);

        // Botão Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        btnCancelar.setPreferredSize(new Dimension(160, 30));
        btnCancelar.setHorizontalAlignment(SwingConstants.CENTER);
        btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);

        // Layout
        JPanel pnlPrincipal = new JPanel();
        pnlPrincipal.setPreferredSize(new Dimension(400, 450));
        Insets margemCampos = new Insets(5, 0, 5, 0);

        layout = new Layout(pnlPrincipal);
        layout.preencherHorizontalmente(true);
        layout.posicionarComponente(lblNome, 0, 0, 2, 1, margemCampos);
        layout.posicionarComponente(txfNome, 1, 0, 2, 1, margemCampos);
        layout.posicionarComponente(lblDesc, 2, 0, 2, 1, margemCampos);
        layout.posicionarComponente(txaDesc, 3, 0, 2, 1, margemCampos);
        layout.posicionarComponente(lblFoto, 4, 0, 2, 1, margemCampos);
        layout.posicionarComponente(txfFoto, 5, 0, 2, 1, margemCampos);
        layout.posicionarComponente(btnCriar, 6, 0, new Insets(30, 0, 0, 30));
        layout.posicionarComponente(btnCancelar, 6, 1, new Insets(30, 0, 0, 0));

        this.add(pnlPrincipal);
    }

    private void criarTopico() {
        if (controle.validarCriacaoTopico(txfNome.getText())) {
            this.hashtag = controle.criarNovoTopico(List.of(txfNome.getText(), txaDesc.getText(), txfFoto.getText()));
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, controle.getMensagem(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void escolherFoto() {
        JFileChooser fileChooser = new JFileChooser();
        int resposta = fileChooser.showOpenDialog(this);
        if (resposta == JFileChooser.APPROVE_OPTION) {
            String caminho = fileChooser.getSelectedFile().getPath();
            txfFoto.setText(caminho);
            txfFoto.setEditable(false);
        }
    }
}
