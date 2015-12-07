/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Lexico.Tag;
import java.io.IOException;

/**
 public void expression() throws IOException {
        //expression ::= simple-expr | simple-expr relop simple-expr 
        switch (token.tag) {
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
            case Tag.FLOAT:
            case '(':
            case '{':
            case '!':
            case '-':
                simpleExpr();
                if (token.tag == Tag.EQ || token.tag == Tag.NE || token.tag == Tag.GE
                        || token.tag == Tag.LE || token.tag == '>' || token.tag == '<') {
                    relop();
                    simpleExpr();
                }
                break;
            default:
                erro();
        }
    }

 */
public class expession {
    vvvvv
}
