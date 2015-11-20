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
        EXPRESSION();

    }

    public void REPEATstmt() throws IOException {
        //repeat-stmt ::= repeat stmt-list stmt-suffix
        switch (token.tag) {
            case Tag.REPEAT:
                eat(Tag.REPEAT);
                STMTlist();
                STMTsuffix();
                break;
            default:
                SintaticoErro();

        }
    }

    public void STMTsuffix() throws IOException {
        //stmt-suffix ::= until condition 
        switch (token.tag) {
            case Tag.UNTIL:
                eat(Tag.UNTIL);
                Condition();
                break;
            default:
                SintaticoErro();

        }

    }

    public void WHILEstmt() throws IOException {
        //while-stmt ::= stmt-prefix stmt-list end 
        switch (token.tag) {
            case Tag.WHILE:
                STMTprefix();
                STMTlist();
                eat(Tag.END);
                break;
            default:
                SintaticoErro();
        }
    }

    public void STMTprefix() throws IOException {
        //stmt-prefix ::= while condition do 
        switch (token.tag) {
            case Tag.WHILE:
                eat(Tag.WHILE);
                Condition();
                eat(Tag.DO);
                break;
            default:
                SintaticoErro();
        }
    }

    public void READstmt() throws IOException {
//read-stmt ::= read "(" identifier ")" 
        switch (token.tag) {
            case Tag.READ:
                eat(Tag.READ);
                eat('(');
                Identifier();
                eat(')');
                break;
            default:
                SintaticoErro();
        }
    }

    public void WRITEstmt() throws IOException {
        //write-stmt ::= write "(" writable ")" 
        switch (token.tag) {
            case Tag.WRITE:
                eat(Tag.WRITE);
                eat('(');
                WRITABLE();
                eat(')');
                break;
            default:
                SintaticoErro();
        }

    }

    public void WRITABLE() throws IOException {
        //writable ::= simple-expr | literal 
        switch (token.tag) {
            case Tag.LIT:
                eat(Tag.LIT);
                break;
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
            case '(':
            case '{':
            case '!':
            case '-':
                SIMPLEexpr();
                break;
            default:
                SintaticoErro();
        }
    }

    public void EXPRESSION() throws IOException {
        //expression ::= simple-expr | simple-expr relop simple-expr 
        switch (token.tag) {
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
            case '(':
            case '{':
            case '!':
            case '-':
                SIMPLEexpr();
                if (token.tag == Tag.EQ || token.tag == Tag.NE || token.tag == Tag.GE
                        || token.tag == Tag.LE || token.tag == '>' || token.tag == '<') {
                    Relop();
                    SIMPLEexpr();
                }
                break;
            default:
                SintaticoErro();
        }
    }

    public void SIMPLEexpr() throws IOException {
        //simple-expr ::= term simple-expr’
        switch (token.tag) {
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
            case '(':
            case '{':
            case '!':
            case '-':
                Term();
                SIMPLEexpression();
                break;
            default:
                SintaticoErro();
        }
    }

    public void SIMPLEexpression() throws IOException {
        //simple-expr’ ::= λ | addop term simple-expr’ 
        if (token.tag == Tag.OR || token.tag == '+' || token.tag == '-') {
            ADDop();
            Term();
            SIMPLEexpression();
        }
    }

    public void Term() throws IOException {
        //term ::= factor-a term’
        switch (token.tag) {
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
            case '(':
            case '{':
            case '!':
            case '-':
                factorA();
                Termo();
                break;
            default:
                SintaticoErro();
        }

    }

    public void Termo() throws IOException {
        //term’ ::= λ | mulop factor-a term’  
        switch (token.tag) {
            case '*':
                eat('*');
                factorA();
                Termo();
                break;
            case ('/'):
                eat('/');
                factorA();
                Termo();
                break;
            case Tag.AND:
                eat(Tag.AND);
                factorA();
                Termo();
                break;
            //não coloquei default por poder ser Lambida, então ACHO que se fosse lambida ia dar erro
        }
    }

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
                eat('(');
                factor();
                break;
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
                factor();
                break;
            default:
                SintaticoErro();

        }

    }

    public void factor() throws IOException {
        //factor ::= identifier | constant | "(" expression ")" 
        switch (token.tag) {
            case Tag.ID:
                Identifier();
                break;
            case Tag.NUM:
                Constant();
                break;
            case ('('):
                eat('(');
                EXPRESSION();
                eat(')');
                break;
            default:
                SintaticoErro();

        }
    }

    public void Relop() throws IOException {
        //relop ::= "=" | ">" | ">=" | "<" | "<=" | "!=" 
        switch (token.tag) {
            case Tag.EQ:
                eat(Tag.EQ);
                break;
            case Tag.GR:
                eat(Tag.GR);
                break;
            case Tag.GE:
                eat(Tag.GE);
                break;
            case Tag.LS:
                eat(Tag.LS);
                break;
            case Tag.LE:
                eat(Tag.LE);
                break;
            case Tag.NE:
                eat(Tag.NE);
                break;
            default:
                SintaticoErro();

        }
    }

    public void ADDop() throws IOException {
        // addop ::= "+" | "-" | "||" 
        switch (token.tag) {
            case ('+'):
                eat('+');
                break;
            case ('-'):
                eat('-');
                break;
            case Tag.OR:
                eat(Tag.OR);
                break;
            default:
                SintaticoErro();

        }
    }

    public void MULop() throws IOException {
        //mulop ::= "*" | "/" | "&&" 
        switch (token.tag) {
            case ('*'):
                eat('*');
                break;
            case ('/'):
                eat('/');
                break;
            case Tag.AND:
                eat(Tag.AND);
                break;
            default:
                SintaticoErro();

        }
    }

    public void Constant() throws IOException {
        //constant ::= integer_const | float_const 
        switch (token.tag) {
            case Tag.INT:
                INTEGERconst();
                break;
            case Tag.FLOAT:
                FLOATconst();
                break;
            default:
                SintaticoErro();

        }
    }

    public void INTEGERconst() throws IOException {
        // integer_const ::= digit {digit} 
        switch (token.tag) {
            case Tag.INT:
                eat(Tag.INT);
                if (token.tag == Tag.INT) //se for seguido de mais numeros já olha
                {
                    INTEGERconst();
                }
                break;
            default:
                SintaticoErro();

        }
    }

    public void FLOATconst() throws IOException {
        // float_const ::= digit {digit} “.” digit {digit} 
        switch (token.tag) {
            case Tag.FLOAT:
                eat(Tag.FLOAT);
                if (token.tag == Tag.FLOAT) //se for seguido de mais numeros já olha
                {
                    FLOATconst();
                }
                eat('.');
                eat(Tag.FLOAT);
                if (token.tag == Tag.FLOAT) //se for seguido de mais numeros já olha
                {
                    FLOATconst();
                }
                break;
            default:
                SintaticoErro();

        }

    }

    public void Literal() throws IOException {
        //literal ::= " {" {caractere} "}" 
        switch (token.tag) {
            case ('{'):
                eat('{');
                if (token.tag == '{') //se for seguido de mais } já olha
                {
                    Literal();
                }
                Caractere();
                eat('}');
                 if (token.tag == '{') //se for seguido de mais } já olha
                {
                    Literal();
                }                
                break;
            default:
                SintaticoErro();
        }
    }

    public void Identifier() throws IOException {
        //Não sei se é isso mesmo, DUVIDA
        //identifier ::= letter |“_” {letter | digit | “_”} 
        switch (token.tag) {
            case (Tag.ID):
                eat(Tag.ID);
                break;
            default:
                SintaticoErro();

        }
        
    }

    public void WRITABLE() throws IOException {
        // letter ::= [A-Za-z] 
    }

    public void Digit() throws IOException {
        if(token.tag == Tag.REAL){
            eat(Tag.REAL);
        }else if(token.tag == Tag.INT){
            eat(Tag.INT);
        }
        //digit ::= [0-9] 
    }
    public void WRITABLE() throws IOException {
        //caractere ::= um dos 256 caracteres
    }
}
