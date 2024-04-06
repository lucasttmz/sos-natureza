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

    private Layout layout;
    private JTextArea txaDesc;
    private JTextField txfNome;
    private JTextField txfFoto;

    public frmNovoTopico() {
        this.setTitle("Criar novo tópico");
        this.setModal(true);
        this.setSize(400, 350);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.iniciarComponentes();
    }

    public void iniciarComponentes() {
        // Nome do tópico
        JLabel lblNome = new JLabel("Nome do tópico");
        txfNome = new JTextField();
        txfNome.setPreferredSize(new Dimension(350, 25));

        // Descrição do tópico
        JLabel lblDesc = new JLabel("Descrição do tópico");
        txaDesc = new JTextArea();
        txaDesc.setLineWrap(true);
        txaDesc.setWrapStyleWord(true);
        txaDesc.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txaDesc.setPreferredSize(new Dimension(350, 75));

        // Foto
        JLabel lblFoto = new JLabel("Foto");
        txfFoto = new JTextField();
        txfFoto.setPreferredSize(new Dimension(350, 25));

        // Botão Criar
        JButton btnCriar = new JButton("Criar");
        btnCriar.addActionListener(e -> criarTopico());
        btnCriar.setPreferredSize(new Dimension(160, 25));
        btnCriar.setHorizontalAlignment(SwingConstants.CENTER);
        btnCriar.setHorizontalTextPosition(SwingConstants.CENTER);

        // Botão Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        btnCancelar.setPreferredSize(new Dimension(160, 25));
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
        Controle controle = new Controle();
        controle.criarNovoTopico(List.of(txfNome.getText(), txaDesc.getText(), txfFoto.getText()));
        this.dispose();
    }

    public static void main(String[] args) {
        frmNovoTopico frmNT = new frmNovoTopico();
        frmNT.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frmNT.setVisible(true);
    }

}
