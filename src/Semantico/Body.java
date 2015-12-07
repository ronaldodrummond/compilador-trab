/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Lexico.Tag;
import java.io.IOException;

/**
   public void body() throws IOException {
        //body ::= [ decl-list] start stmt-list stop
        //duvidas quanto a parte [decl-list]
        switch (token.tag) {
            case Tag.INT:
            case Tag.REAL:
            case Tag.FLOAT://na implementação que eu vi ele olha se é inteiro ou real com 2 cases no caso 
                decList();
                if (token.tag==Tag.START) {
                	eat(Tag.START); //eat START
                } else {
                	System.out.println("Erro ;");
                	erro();
                }
                stmtList();
                eat(Tag.STOP); //eat STOP 
                break;
            default:
                erro();
        }
    }

 */
public class Body {
    vvvv
}
