package apresentacao;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.JPanel;

public class Layout {

    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private JPanel painel;

    public Layout(JPanel painel) {
        this.painel = painel;
        this.layout = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.painel.setLayout(layout);
    }

    public void preencherHorizontalmente(boolean preencher) {
        if (preencher) {
            this.gbc.fill = GridBagConstraints.BOTH;
        } else {
            this.gbc.fill = GridBagConstraints.NONE;
        }
    }

    public void posicionarComponente(Component c, int linha, int coluna, int largura, int altura, Insets margem) {
        gbc.insets = margem;
        gbc.gridy = linha;
        gbc.gridx = coluna;
        gbc.gridheight = altura;
        gbc.gridwidth = largura;
        layout.setConstraints(c, gbc);
        painel.add(c);
    }

    public void posicionarComponente(Component c, int linha, int coluna) {
        posicionarComponente(c, linha, coluna, 1, 1);
    }

    public void posicionarComponente(Component c, int linha, int coluna, Insets margem) {
        posicionarComponente(c, linha, coluna, 1, 1, margem);
    }

    public void posicionarComponente(Component c, int linha, int coluna, int largura, int altura) {
        posicionarComponente(c, linha, coluna, largura, altura, new Insets(0, 0, 0, 0));
    }

    public void descentralizar() {
        // Tentar achar forma melhor?
        gbc.weighty = 1;
        posicionarComponente(Box.createHorizontalBox(), 999, 0, 1, 1);
        gbc.weightx = 0;
    }

    public JPanel getPainel() {
        return painel;
    }

}
