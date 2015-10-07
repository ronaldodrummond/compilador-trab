/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;
import compiler.Lexer;
import compiler.Token;
import java.util.Scanner; 

public class Compiler {

   
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        String diretorio; 
        System.out.println("Compilador");
        System.out.println("Digite diretório com o nome do arquivo a ser analisado");
        diretorio = ler.next();
        
        System.out.println("Análise Léxica");
        Token tok = null;
        Lexer lexico = null;
       //chamaria classe pra ler o arquivo primeiro e depois chamaria o scan,mas todas as formas que tentei deu erro
       
    }
    
}
