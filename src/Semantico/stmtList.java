/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Lexico.Tag;
import java.io.IOException;

/**
  public void stmtList() throws IOException {
        // stmt-list ::= stmt {";" stmt}
        switch (token.tag) {
            case Tag.IF:
            case Tag.ID:
            case Tag.REPEAT:
            case Tag.READ:
            case Tag.WHILE:
            case Tag.WRITE:
                stmt();

                while (token.tag == ';') {
                    eat(';');
                    stmt();
                }
                break;

            default:
                erro();
        }

    }
 */
public class stmtList {
    vvvvv
}
