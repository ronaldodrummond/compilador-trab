/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Lexico.Tag;
import java.io.IOException;

/**
  public void factorA() throws IOException {
        //fator-a ::= factor | "!" factor | "-" factor 
        switch (token.tag) {
            case '!':
                eat('!');
                factor();
                break;
            case '-':
                eat('-');
                factor();
                break;
            case '(':
//                eat('(');
                factor();
                break;
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
            case Tag.FLOAT:
                factor();
                break;
            default:
                erro();

        }

    }
 */
public class factorA {
    vvvvvvvvvvvvvv
}
