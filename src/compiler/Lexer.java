/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
   
    public Lexer(File fileName) throws FileNotFoundException{
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

    private void readch() throws IOException{
    	ch = (char) file.read();
    }

    private boolean readch(char c) throws IOException{
    	readch();
    	if(ch != c) return false;
    	ch = ' ';
    	return true;
    }  

    public Token scan() throws IOException{

    	for(;; readch()){
    		if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') {
    			continue;
    		}else if(ch == '\n'){
    			line++;
    		}else{
    			break;
    		}
    	}

    	switch(ch){
    		case '&':
    			if(readch('&')) return Word.and;
    			else return new Token('&');
    		case '|':
    			if(readch('|')) return Word.or;
    			else return new Token('|');
    		case '<':
    			if(readch('=')) return Word.le;
    			else return new Token('<');
    		case '>':
    			if(readch('=')) return Word.ge;
    			else return new Token('>');
    		case '!':
    			if(readch('=')) return Word.ne;
    			else return new Token('!');
    		case '=':
    			ch = ' ';
    			return new Token('=');
    		case '+':
    			ch = ' ';
    			return new Token('+');
    		case '-':
    			ch = ' ';
    			return new Token('-');
    		case '*':
    			ch = ' ';
    			return new Token('*');
    		case '/':
    			ch = ' ';
    			return new Token('/');
    		case '(':
    			ch = ' ';
    			return new Token('(');
    		case ')':
    			ch = ' ';
    			return new Token(')');
    		case '{':
    			ch = ' ';
    			return new Token('{');
    		case '}':
    			ch = ' ';
    			return new Token('}');
    	}

    	if(Character.isDigit(ch)){
    		int value = 0;
    		do{
                    value = 10*value + Character.digit(ch,10);
                    readch();
    		}while(Character.isDigit(ch));
                
                if(ch == '.'){
                    String val = value + ".";
                    do{
                        val += ch + "";
                    }while(Character.isDigit(ch));
//                    return new NumFloat(Float.parseFloat(val));
                }
    		return new NumInt(value);
    		//IMPLEMENTACAO DA CLASSE VALUE AINDA NAO DEFINIDA, E PRECISO PENSAR SE DEVEMOS CRIAR CLASSES DIFERENTES PARA FLOAT INT E REAL
    	}

    	if(Character.isLetter(ch)){
    		StringBuffer sb = new StringBuffer();
    		do{
    			sb.append(ch);
    			readch();
    		}while(Character.isLetterOrDigit(ch));

    		String s = sb.toString();
    		Word w = (Word) words.get(s);
    		if(w != null)
    			return w;
    		w = new Word(Tag.ID, s);
    		words.put(s,w);
    		return w;
    	}

    	Token t = new Token(ch);
    	ch = ' ';
    	return t;
    }

}
