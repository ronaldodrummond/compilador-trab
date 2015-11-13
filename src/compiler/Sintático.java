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
        //PEGAR O QUE ESTA SENDO ANALISADO NO LEXICO
        this.lexer = lexer;
        token = lexer.words();
    }

    private void avanca() throws IOException {
        //DUVIDA
        //teria que fazer um for pra pegar o proximo token da lista de words, então fiz uma variavel I global que vai fazendo isso
        //MELHOR CONFERIR   
        token = lexer.words.elements(i);
        i++;
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
            case : //tag APP
                eat(270);
                identifier();
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
            case : //na implementação que eu vi ele olha se é inteiro ou real com 2 cases no caso 
                DECLlist();
                eat(); //eat START
                STMTlist();
                eat(); //eat STOP     
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
            case : //case TYPE
                IDENTlist();
            default:
                SintaticoErro();
        }
    }

    public void IDENTlist() throws IOException {
        //ident-list ::= identifier {"," identifier}
        switch (token.tag) {
            case : //case ID
                Identifier();
            // aqui colocaria um if pra virgula e mais uma chamada pra Identifier()?
            default:
                SintaticoErro();
        }
    }

    public void Identifier() throws IOException {

    }

    public void STMTlist() throws IOException {
    }

}
