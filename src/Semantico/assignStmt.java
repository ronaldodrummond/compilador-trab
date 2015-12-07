/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Lexico.Tag;
import java.io.IOException;

/**
  public void assignStmt() throws IOException {
        //assign-stmt ::= identifier ":=" simple_expr 
        switch (token.tag) {
            case Tag.ID:
//                eat(Tag.ID);
                identifier();
                eat(Tag.ASGN);
                simpleExpr();
                break;
            default:
                erro();

        }
    }

 */
public class assignStmt {
    vvvvvv
}
