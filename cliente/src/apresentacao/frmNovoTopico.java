package apresentacao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import modelo.Controle;
import modelo.Topico;

public class frmNovoTopico extends JDialog {

    private JPanel pnlPrincipal;
    private GridBagConstraints gbc;
    private GridBagLayout layout;

    public frmNovoTopico() {
        this.setTitle("Criar novo tópico");
        this.setModal(true);
        this.setSize(400, 350);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        iniciarComponentes();
    }

    public void iniciarComponentes() {
        // Nome do tópico
        JLabel lblNome = new JLabel("Nome do tópico");
        JTextField txfNome = new JTextField();
        txfNome.setPreferredSize(new Dimension(350, 25));

        // Descrição do tópico
        JLabel lblDesc = new JLabel("Descrição do tópico");
        JTextArea txaDesc = new JTextArea();
        txaDesc.setLineWrap(true);
        txaDesc.setWrapStyleWord(true);
        txaDesc.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txaDesc.setPreferredSize(new Dimension(350, 75));

        // Foto
        JLabel lblFoto = new JLabel("Foto");

        // Input Text da foto
        JTextField txfFoto = new JTextField();
        txfFoto.setPreferredSize(new Dimension(350, 25));

        // Botão Criar
        JButton btnCriar = new JButton("Criar");
        btnCriar.addActionListener((e) -> {
            new Controle().criarNovoTopico(List.of(txfNome.getText(), txaDesc.getText(), txfFoto.getText()));
            this.dispose();
        });
        btnCriar.setPreferredSize(new Dimension(160, 25));
        btnCriar.setHorizontalAlignment(SwingConstants.CENTER);
        btnCriar.setHorizontalTextPosition(SwingConstants.CENTER);

        // Botão Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((e) -> {
            this.dispose();
        });
        btnCancelar.setPreferredSize(new Dimension(160, 25));
        btnCancelar.setHorizontalAlignment(SwingConstants.CENTER);
        btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);

        // Layout
        pnlPrincipal = new JPanel();
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.BOTH;
        pnlPrincipal.setPreferredSize(new Dimension(400, 450));
        pnlPrincipal.setLayout(layout);

        posicionarComponente(lblNome, 0, 0, 2, 1);
        posicionarComponente(txfNome, 1, 0, 2, 1);
        posicionarComponente(lblDesc, 2, 0, 2, 1);
        posicionarComponente(txaDesc, 3, 0, 2, 1);
        posicionarComponente(lblFoto, 4, 0, 2, 1);
        posicionarComponente(txfFoto, 5, 0, 2, 1);

        gbc.insets.right = 30;
        gbc.insets.top = 30;
        posicionarComponente(btnCriar, 6, 0, 1, 1);
        gbc.insets.right = 0;
        posicionarComponente(btnCancelar, 6, 1, 1, 1);
        this.add(pnlPrincipal);
    }

    private void posicionarComponente(Component c, int linha, int coluna, int largura, int altura) {
        gbc.gridy = linha;
        gbc.gridx = coluna;
        gbc.gridheight = altura;
        gbc.gridwidth = largura;
        layout.setConstraints(c, gbc);
        pnlPrincipal.add(c);
    }

    public static void main(String[] args) {
        frmNovoTopico frmNT = new frmNovoTopico();
        frmNT.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frmNT.setVisible(true);
    }

}
