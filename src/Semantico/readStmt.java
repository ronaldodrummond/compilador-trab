/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Lexico.Tag;
import java.io.IOException;

/**
  public void readStmt() throws IOException {
//read-stmt ::= read "(" identifier ")" 
        switch (token.tag) {
            case Tag.READ:
                eat(Tag.READ);
                eat('(');
                identifier();
                eat(')');
                break;
            default:
                erro();
        }
    }
 */
public class readStmt {
    vvvvvvvv
}
