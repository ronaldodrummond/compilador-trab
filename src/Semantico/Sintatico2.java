/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;
import Lexico.TabelaDeSimbolos;
import Lexico.Lexer;
import Lexico.Token;
import java.io.IOException;

public abstract class Sintatico2 {
       public Sintatico2 head; // PORQUE?
    public String tipo; // PORQUE?
    public boolean declaracao; // PORQUE?
    public static Lexer lexer;
    public static Token token;
   public static TabelaDeSimbolos ts = TabelaDeSimbolos.getInstance();
    
    protected Sintatico2(Sintatico2 head){
        this.head = head; // PORQUE?
        this.tipo = "void"; // PORQUE?
        this.declaracao = false; // PORQUE?
    }
    
    
    protected void erro() {
        System.out.println("Erro , programa sendo terminado");
        System.exit(0);
    }
    
    protected void eat(int tag) throws IOException{
        if (token.tag == tag) {
            //System.out.println("eat " + token);
            token = lexer.scan();
        } else {
            System.out.println("Erro Sintático na linha " + lexer.line + ":\n");
            erro();
        }
    }
    
    public abstract void analise();
}
