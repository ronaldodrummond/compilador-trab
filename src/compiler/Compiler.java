import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import compiler.Lexer;
import compiler.Token;


public class Compiler {

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        try {
     JFileChooser chooser = new JFileChooser();
     int retorno = chooser.showOpenDialog(null);
 
     if (retorno == JFileChooser.APPROVE_OPTION) {
       Lexer lexer = new Lexer(chooser.getSelectedFile());
        
        String token = lexer.scan().toString();
        System.out.println(token);
       
     }
   } catch (FileNotFoundException e) {
     e.printStackTrace();
   }

    }
}
