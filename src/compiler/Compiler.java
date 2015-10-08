/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author pedrohbnp
 */
public class Compiler {

    /**
     * @param args the command line arguments
     */
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
