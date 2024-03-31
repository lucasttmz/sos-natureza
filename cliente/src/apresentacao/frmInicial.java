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

public class frmInicial extends JFrame {

    private JPanel pnlPrincipal;
    private GridBagConstraints gbc;
    private GridBagLayout layout;

    public frmInicial() {
        this.setTitle("SOS Natureza");
        this.setSize(400, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        iniciarComponentes();
    }

    public void iniciarComponentes() {
        // Titulo
        JLabel lblTitulo = new JLabel("SOS Natureza");
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
        
        // Nome
        JTextField txfNome = new JTextField();
        txfNome.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Entrar
        JButton btnEntrar = new JButton("Entrar");
        
        btnEntrar.addActionListener((e) -> {
            // Validar nome
            frmChat frmC = new frmChat(txfNome.getText());
            frmC.setVisible(true);
            this.dispose();
        });
        
        // Layout
        pnlPrincipal = new JPanel();
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.fill = GridBagConstraints.BOTH;
        
        pnlPrincipal.setPreferredSize(new Dimension(400, 450));
        pnlPrincipal.setLayout(layout);

        posicionarComponente(lblTitulo, 0, 0, 1, 1);
        gbc.insets = new Insets(3, 0, 3, 0);
        posicionarComponente(lblNome, 1, 0, 1, 1);
        posicionarComponente(txfNome, 2, 0, 1, 1);
        posicionarComponente(btnEntrar, 3, 0, 1, 1);
        
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

}
