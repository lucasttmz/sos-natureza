package sosnatureza;

import apresentacao.frmInicial;
import com.formdev.flatlaf.IntelliJTheme;


public class SOSNatureza {

    public static void main(String[] args) {
        IntelliJTheme.setup(SOSNatureza.class.getResourceAsStream("/resources/tema_flatlaf.json"));
        
        frmInicial frmI = new frmInicial();
        frmI.setVisible(true);
    }

}
