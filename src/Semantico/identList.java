/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Lexico.Tag;
import java.io.IOException;

/**
public void identList() throws IOException {
        //ident-list ::= identifier {"," identifier}
        switch (token.tag) {
            case Tag.ID:
                identifier();
                while (token.tag == ',') {
                    eat(',');
                    identifier();
                }
                break;
            default:
                erro();
        }
    }
 */
public class identList {
    vvvvv
}
