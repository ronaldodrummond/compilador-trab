import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import compiler.Lexer;
import compiler.Token;
import java.io.File;
import java.util.Scanner;


public class Compiler {

    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        try {
            JFileChooser chooser = new JFileChooser();
            int retorno = chooser.showOpenDialog(null);
            String token = "";
            if (retorno == JFileChooser.APPROVE_OPTION) {
                Lexer lexer = new Lexer(chooser.getSelectedFile());
                //for para percorrer o arquivo
                Scanner input = new Scanner(chooser.getSelectedFile());
                while (input.hasNextLine()) {
                    token = lexer.scan().toString();
                    System.out.println(token);
                }
                if(token.equalsIgnoreCase("stop") != true){
                    System.out.println("Erro na linha " + Lexer.line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
