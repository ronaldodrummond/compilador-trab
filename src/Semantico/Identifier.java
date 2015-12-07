/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Lexico.Token;
import Lexico.Tag;
import Lexico.Lexer;
import Lexico.Word;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * public void identifier() throws IOException { //Não sei se é isso mesmo,
 *  //identifier ::= letter |“_” {letter | digit | “_”} 
 * switch (token.tag)
 * { 
 * case (Tag.ID):
 * eat(Tag.ID);
 * break; 
 * default: 
 * erro();
 *
 * }
 *
 * }
 */
public class Identifier extends Sintatico2 {

    public Identifier(Sintatico2 head) {
        super(head);
    }

    public void analisa() {
        switch (token.tag) {
            case Tag.ID:
                if (this.declaracao)
                { // se o token for valido ou nao existir na ts, ele vai ser inserido na tabela de simbolos
                    this.tipo = head.tipo;
                    Token tok = ts.get(word.lexema);
                   
                    if (tok != null) {
                        tok = new Token(token.getLexema(), Tag.ID);
                         tok.tipo = this.tipo;
                        ts.put(tok, token.tag);
                    } else {
                        System.out.println("Erro semantico, linha: " + lexer.line + ":\n");
                        erro();
                    }
                } else {
                    // se o token for nulo, vai dar erro
                    Token tok = ts.get(word.getLexeme);
                    if (tok == null) {
                        System.out.println("Erro semantico, linha: "  + lexer.line + ":\n");
                        erro();
                    }
                    this.tipo = tok.tipo;
                } {
                try {
                    eat(Tag.ID);
                } catch (IOException ex) {
                    Logger.getLogger(Identifier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            default:
                //se der qualquer outra coisa fora disso da erro
                System.out.println("Erro semantico, linha: "  + lexer.line + ":\n");
                erro();
        }
    }

    @Override
    public void analise() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
