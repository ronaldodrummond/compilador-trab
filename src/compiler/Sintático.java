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

/**
 *
 * @author 'Loraine
 */
public class Sintático {
     private Lexer lexer;
    private Token token;
    Hashtable<String, Token> sint;
    int i=0;//para o for que vai rodar a hashtable
    
    //implementação baseada no livro
    public Sintático(Lexer lexer) throws IOException {
        this.lexer = lexer;
        token = lexer.scan();
    }
    
    private void avanca() throws IOException {
        //DUVIDA
        //teria que fazer um for pra pegar o proximo token da lista de words, então fiz uma variavel I global que vai fazendo isso
        //MELHOR CONFERIR   
        token = lexer.words.elements(i);
        i++;
    }
    
    private void SintaticoErro() {
        System.out.println("Erro  na linha "+Lexer.line+" perto do token "+token.toString());
        System.exit(0);
    }
    
    private void eat(int tag) throws IOException {
        if (token.tag == tag){
            System.out.println("eat " +token);
            avanca();
        }
        else
            SintaticoErro();
    }
    
    public void verifica()
    {
        
    }
    
}
