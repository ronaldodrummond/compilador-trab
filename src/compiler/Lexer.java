/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;

/**
 *
 * @author pedrohbnp
 */
public class Lexer {
    public static int line = 1; //contador de linhas
    private char ch = ' ';
    private FileReader file;
    
    private Hashtable words = new Hashtable();
    
    private void reserve(Word w){
        words.put(w.getLexeme(), w);
    }
    
    public Lexer(String fileName) throws FileNotFoundException{
        try{
            file = new FileReader(fileName);
        }catch(FileNotFoundException e){
            System.out.println("Arquivo não Encontrado");
            throw e;
        }
        
        reserve(new Word(Tag.IF, "if"));
        reserve(new Word(Tag.THEN, "then"));
        reserve(new Word(Tag.ELSE, "else"));
        reserve(new Word(Tag.END, "end"));
        reserve(new Word(Tag.FLOAT, "float"));
        reserve(new Word(Tag.INT, "int"));
        reserve(new Word(Tag.STRING, "string"));
        reserve(new Word(Tag.START, "start"));
        reserve(new Word(Tag.REPEAT, "repeat"));
        reserve(new Word(Tag.UNTIL, "until"));
        reserve(new Word(Tag.WHILE, "while"));
        reserve(new Word(Tag.DO, "do"));
        reserve(new Word(Tag.READ, "read"));
        reserve(new Word(Tag.WRITE, "write"));
        reserve(new Word(Tag.APP, "app"));
        reserve(new Word(Tag.REAL, "real"));
    	//TALVEZ FALTE ALGUMA PALAVRA RESERVADA AINDA
    }   
}
