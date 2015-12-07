/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semantico;

import Lexico.Tag;
import java.io.IOException;

/**
  public void term2() throws IOException {
        //term’ ::= λ | mulop factor-a term’  
        switch (token.tag) {
            case '*':
                eat('*');
                factorA();
                term2();
                break;
            case ('/'):
                eat('/');
                factorA();
                term2();
                break;
            case Tag.AND:
                eat(Tag.AND);
                factorA();
                term2();
                break;
            default:
                break;
            //não coloquei default por poder ser Lambida, então ACHO que se fosse lambida ia dar erro
        }
    }
 */
public class term2 {
    vvvvvvvvvv
}
