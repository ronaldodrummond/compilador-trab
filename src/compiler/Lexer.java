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

    private void reserve(Word w) {
        words.put(w.getLexeme(), w);
    }

    public Lexer(File fileName) throws FileNotFoundException {
        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
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
        reserve(new Word(Tag.AND, "and"));
        reserve(new Word(Tag.OR, "or"));
        reserve(new Word(Tag.NUM, "num"));
        reserve(new Word(Tag.ID, "id"));
    }

    private boolean lexicError(char ch) throws IOException {
        if (ch != '>' && ch != '<' && ch != '=' && ch != '+'
                && ch != '-' && ch != '*' && ch != '/'
                && ch != ';' && ch != '(' && ch != ')'
                && ch != ' ' && ch != '\t' && ch != '\r'
                && ch != '\b' && ch != '.') {
            // pelo que entendi falta uma execessão pra palavra ou caracter...não?
            System.out.println("Erro na linha "
                    + line);
            while (ch != ' ' && ch != '\n' && ch != '\t'
                    && ch != '\r' && ch != '\b') {
                readch();
            }
            return true;
        } else {
            return false;
        }
    }

    private void readch() throws IOException {
        ch = (char) file.read();
    }

    private boolean readch(char c) throws IOException {
        readch();
        if (ch != c) {
            return false;
        }
        ch = ' ';
        return true;
    }

    public Token scan() throws IOException {

        for (;; readch()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') {
                continue;
            } else if (ch == '\n') {
                line++;
            } else if (ch == '%')//para os comentarios, anulam o resto da linha
            {
                while (ch != '\n') {
                    readch();
                }
                continue;
            } else {
                break;
            }
        }

        switch (ch) {
            case '&':
                if (readch('&')) {
                    return Word.and;
                } else {
                    return new Token('&');
                }
            case '|':
                if (readch('|')) {
                    return Word.or;
                } else {
                    return new Token('|');
                }
            case '<':
                if (readch('=')) {
                    return Word.le;
                } else {
                    return new Token('<');
                }
            case '>':
                if (readch('=')) {
                    return Word.ge;
                } else {
                    return new Token('>');
                }
            case '!':
                if (readch('=')) {
                    return Word.ne;
                } else {
                    return new Token('!');
                }
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
//            case '{':
//                ch = ' ';
//                return new Token('{');
//            case '}':
//                ch = ' ';
//                return new Token('}');
            case ':':
                if (readch('=')) {
                    return Word.assign;
                } else {
                    return new Token(':');
                }
            case ',':
                ch = ' ';
                return new Token(',');

        }
//Numeros
        if (Character.isDigit(ch)) {
            int value = 0;
            do {
                value = 10 * value + Character.digit(ch, 10);
                readch();
            } while (Character.isDigit(ch));
            if (lexicError(ch)) {
                //return new Token(ERRO);
            } else if (ch == '.') {
                String valueR = value + ".";
                while (Character.isDigit(ch)) {
                    valueR += ch + "";
                }
                if (lexicError(ch)) {
                    //return new Token(ERRO);
                }
                float resFloat = Float.parseFloat(valueR);
                return new NumFloat(resFloat);
            } else {
                return new NumInt(value);
            }
        }
//Identificador
        if (Character.isLetter(ch) || ch == '_') {
            StringBuffer sb = new StringBuffer();
            do {
                sb.append(ch);
                readch();
            } while (Character.isLetterOrDigit(ch) || ch == '_');
            if (ch == '\n') {
                if (sb.toString().equalsIgnoreCase("app") || sb.toString().equalsIgnoreCase("start")) {
                    //System.out.println("");
                } else {
                    System.out.println("Erro na linhaa " + line);
                }
            } else {
                if (ch != ',') {
                    if (lexicError(ch)) {
                        return new Token(Tag.ERROR);
                    }
                }
            }
            String s = sb.toString();
            Word w = (Word) words.get(s);
            if (w != null) {
                return w;// Se ja existir na tabela de simbolos
            }
            w = new Word(Tag.ID, s);
            words.put(s, w);
//            System.out.println("Colocando "+w.toString()+"na tabela de simbolos.");
            return w;
        }

//Literal
        if (ch == '{') {
            readch();
            String literal = "";
            while (ch != '}') {
                if (ch == '\n') {
                    System.out.println("Erro na linha " + line);
                    return new Token(Tag.ERROR);
                }
                literal += ch;
                readch();
            }
            if (lexicError(ch)) {
                System.out.println("Erro na linha " + line);
                return new Token(Tag.ERROR);
            } else {
                ch = ' ';
                return new Word(Tag.LIT, literal);
            }
        }
        //Fazer a impressão de tudo que esta sendo inserido na tabela 
        for (int i = 0; i < words.size(); i++) {
            System.out.println(words.get(i));
        }

        Token t = new Token(ch);
        ch = ' ';
        return t;
    }

}
