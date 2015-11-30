package compiler;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;


public class Compiler {

    public static void main(String[] args) throws IOException {

        try {
            JFileChooser chooser = new JFileChooser();
            int retorno = chooser.showOpenDialog(null);
            String token = "";
            if (retorno == JFileChooser.APPROVE_OPTION) {
                Lexer lexer = new Lexer(chooser.getSelectedFile());
                
//                //for para percorrer o arquivo
//                Scanner input = new Scanner(chooser.getSelectedFile());
//                while (input.hasNextLine()) {
//                    token = lexer.scan().toString();
//                    System.out.println(token);
//                }
//                
//                if(token.equalsIgnoreCase("stop") != true){
//                    System.out.println("Erro na linha " + Lexer.line);
//                }
                Sintatico sint = new Sintatico(lexer);
                sint.programa();//ai olha se o primeiro token é app
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        

    }
}
