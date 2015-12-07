/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Lexico.Tag;
import java.io.IOException;

/**
 //produção do símbolo inicial da gramática
    public void programa() throws IOException {
        //program ::= app identifier body
        switch (token.tag) {
            case Tag.APP:
                eat(Tag.APP);
                identifier();
                body();
                break;
            default:
                erro();
        }
    }
 */
public class Program extends Sintatico2{
    
    Identifier identifier;
    Body body;
    
    public Program(Sintatico2 head){
        super(head);
    }
 
    public void analise(){
        switch(token.tag){
            case Tag.APP:
        {
            try {
                eat(Tag.APP);
            } catch (IOException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                this.tipo = "app";
                identifier = new Identifier(this);
                identifier.declaracao = true;
                identifier.analise();
                body = new Body(this);
                body.analise();
                break;                  
            default:
                System.out.println("Erro sintatico na linha " + Lexer.numLinha 
                + ":\n" + "Declaracao <app> esperada, porém não encontrada.");
                erro();            
        }
    }
    
}
