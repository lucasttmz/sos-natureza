package Comandos;

import modelo.Comando;

public class Telefones implements Comando{

    String todosTelefone =  "SAMU: 192" + "<br>" + 
                            "BOMBEIROS: 193"+ "<br>" + 
                            "POLICIA: 190"+ "<br>" + 
                            "DENUNCIA: 181"+ "<br>" + 
                            "ANVISA: 0800-642-9782";
    /*
        SAMU: 192
        BOMBEIROS: 193
        POLICIA: 190
        DENUNCIA: 181
        ANVISA: 0800-642-9782
    */
    @Override
    public String executar(String parametro) {

        String Mensagem;
        
        switch (parametro) {
            case "":
                Mensagem = todosTelefone;
                break;
            case "samu":
                Mensagem = "SAMU : 192";
                break;
            case "bombeiros":
                Mensagem = "BOMBEIROS: 193";
                break;
            case "policia":
                Mensagem = "POLICIA: 190";
                break;
            case "denuncia":
                Mensagem = "DENUNCIA: 181";
                break;
            case "anvisa":
                Mensagem = "ANVISA: 0800-642-9782";
                break;
        
            default:
                Mensagem = "Telefone Não encontrado";
        }


        return "<br><span style='color: #fff;font-weight: bold'>" + Mensagem + "</span>";
    }

}
