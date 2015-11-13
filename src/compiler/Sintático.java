/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import compiler.Tag;

/**
 *
 * @author 'Loraine
 */
public class Sintático {

    private Lexer lexer;
    private Token token;
    private Tag tag;
    Hashtable<String, Token> sint;
    int i = 0;//para o for que vai rodar a hashtable

    //implementação baseada no livro
    public Sintático(Lexer lexer) throws IOException {
        this.lexer = lexer;
        token = lexer.words();
    }

    private void avanca() throws IOException {
        
        Token t = lexer.scan();
        if (t != null) {
            token = t;
        } else {
            //Else  no caso de ter acabado de ler
        }

    }

    private void SintaticoErro() {
        System.out.println("Erro  na linha " + Lexer.line + " perto do token " + token.toString());
        System.exit(0);
    }

    private void eat(int tag) throws IOException {
        if (token.tag == tag) {
            System.out.println("eat " + token);
            avanca();
        } else {
            SintaticoErro();
        }
    }

    public void Programa() throws IOException {
        //program ::= app identifier body
        switch (token.tag) {
            case Tag.APP:
                eat(Tag.APP);
                Identifier();
                body();
                break;
            default:
                SintaticoErro();
        }
    }

    public void body() throws IOException {
        //body ::= [ decl-list] start stmt-list stop
        //duvidas quanto a parte [decl-list]
        switch (token.tag) {
            case Tag.INT:
            case Tag.REAL: //na implementação que eu vi ele olha se é inteiro ou real com 2 cases no caso 
                DECLlist();
                eat(Tag.START); //eat START
                STMTlist();
                eat(Tag.STOP); //eat STOP 
                break;
            default:
                SintaticoErro();
        }
    }

    public void DECLlist() throws IOException {
        //decl-list ::= decl {";" decl}
        DECL();
        if (token.tag == ';') {
            eat(';');
            DECL();
        } else {
            SintaticoErro();
        }
    }

    public void DECL() throws IOException {
        //decl ::= type ident-list
        switch (token.tag) {
            case Tag.INT:
            case Tag.REAL:
                IDENTlist();
                break;
            default:
                SintaticoErro();
        }
    }

    public void IDENTlist() throws IOException {
        //ident-list ::= identifier {"," identifier}
        switch (token.tag) {
            case Tag.ID:
                Identifier();

                if (token.tag == ';') {
                    eat(';');
                    Identifier();
                }
                break;
            default:
                SintaticoErro();
        }
    }

    public void STMTlist() throws IOException {
        // stmt-list ::= stmt {";" stmt}
        switch (token.tag) {
            case Tag.IF:
            case Tag.ID:
            case Tag.REPEAT:
            case Tag.READ:
            case Tag.WHILE:
            case Tag.WRITE:
                STMT();

                if (token.tag == ';') {
                    eat(';');
                    STMT();
                }
                break;

            default:
                SintaticoErro();
        }

    }

    public void STMT() throws IOException {
        //stmt ::= assign-stmt | if-stmt | while-stmt | repeat-stmt| read-stmt | write-stmt
        switch (token.tag) {
            case Tag.ID:
                ASSIGNstmt();
                break;
            case Tag.IF:
                IFstmt();
                break;
            case Tag.WHILE:
                WHILEstmt();
                break;
            case Tag.REPEAT:
                REPEATstmt();
                break;
            case Tag.READ:
                READstmt();
                break;
            case Tag.WRITE:
                WRITEstmt();
                break;
            default:
                SintaticoErro();

        }
    }

    public void ASSIGNstmt() throws IOException {
        //assign-stmt ::= identifier ":=" simple_expr 
        switch (token.tag) {
            case Tag.ID:
                eat(Tag.ID);
                Identifier();
                eat(Tag.ASGN);
                SIMPLEexpr();
                break;
            default:
                SintaticoErro();

        }
    }

    public void IFstmt() throws IOException {
        //if-stmt ::= if condition then stmt-list else-stmt
        switch (token.tag) {
            case Tag.IF:
                eat(Tag.IF);
                Condition();
                eat(Tag.THEN);
                STMTlist();
                ELSEstmt();
                break;
            default:
                SintaticoErro();

        }
    }

    public void ELSEstmt() throws IOException {
     //else-stmt ::= end | else stmt-list end
        switch (token.tag) {
            case Tag.END:
                eat(Tag.END);
                break;
            case Tag.ELSE:
                eat(Tag.ELSE);
                STMTlist();
                eat(Tag.END);
                break;
            default:
                SintaticoErro();

        }
        
    }
 public void Condition() throws IOException {
    //condition ::= expression 
     Expresion();
     
 }
  public void REPEATstmt() throws IOException {
 //repeat-stmt ::= repeat stmt-list stmt-suffix
      
      
  }
    public void Identifier() throws IOException {

    }

    public void AssignStmt() throws IOException {

    }
/* O QUE FALTA 
    
stmt-suffix ::= until condition 

while-stmt ::= stmt-prefix stmt-list end 

stmt-prefix ::= while condition do 

read-stmt ::= read "(" identifier ")" 

write-stmt ::= write "(" writable ")" 

writable ::= simple-expr | literal 

expression ::= simple-expr | simple-expr relop simple-
expr 

simple-expr ::= term simple-expr’

simple-expr’ ::= λ | addop term simple-expr’  

term ::= factor-a term’

term’ ::= λ | mulop factor-a term’  
 

fator-a ::= factor | "!" factor | "-" factor 

factor ::= identifier | constant | "(" expression ")" 

relop ::= "=" | ">" | ">=" | "<" | "<=" | "!=" 

addop ::= "+" | "-" | "||" mulop ::= "*" | "/" | "&&" 

constant ::= integer_const | float_const 

integer_const ::= digit {digit} 

float_const ::= digit {digit} “.” digit {digit} 

literal ::= " {" {caractere} "}" 

identifier ::= letter |“_” {letter | digit | “_”} 

letter ::= [A-Za-z] 

digit ::= [0-9] 

caractere ::= um dos 256 caracteres

    */
}
