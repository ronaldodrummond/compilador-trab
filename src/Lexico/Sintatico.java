package Lexico;

import Lexico.Token;
import Lexico.Tag;
import Lexico.Lexer;
import java.io.IOException;
import java.util.Hashtable;

public class Sintatico {

    private Lexer lexer;
    private Token token;
    private Tag tag;
    Hashtable<String, Token> sint;
    int i = 0;//para o for que vai rodar a hashtable

    //implementação baseada no livro
    public Sintatico(Lexer lexer) throws IOException {
        this.lexer = lexer;
        avanca();
    }

    public void avanca() throws IOException {
    	token = lexer.scan();  
    	System.out.println();
    }

    private void erro() {
        System.out.println("Erro  na linha " + Lexer.line + " perto do token " + token.toString());
        System.exit(0);
    }

    private void eat(int tag) throws IOException {
        if (token.tag == tag) {
            System.out.println("eat " + token);
            avanca();
        } else {
            erro();
        }
    }

    //produção do símbolo inicial da gramática
    public void programa() throws IOException {
        //program ::= app identifier body
        switch (token.tag) {
            case Tag.APP:
                eat(Tag.APP);
                identifier();
                body();
                break;
            default:
                erro();
        }
    }

    public void body() throws IOException {
        //body ::= [ decl-list] start stmt-list stop
        //duvidas quanto a parte [decl-list]
        switch (token.tag) {
            case Tag.INT:
            case Tag.REAL:
            case Tag.FLOAT://na implementação que eu vi ele olha se é inteiro ou real com 2 cases no caso 
                decList();
                if (token.tag==Tag.START) {
                	eat(Tag.START); //eat START
                } else {
                	System.out.println("Erro ;");
                	erro();
                }
                stmtList();
                eat(Tag.STOP); //eat STOP 
                break;
            default:
                erro();
        }
    }

    public void decList() throws IOException {
        //decl-list ::= decl {";" decl}
    	decl();
        while(token.tag == ';') {
            eat(';');
            decl();
        } 
    }

    public void decl() throws IOException {
        //decl ::= type ident-list
    	//adicionar aqui o tipo
        switch (token.tag) {
            case Tag.INT:
            case Tag.REAL:
            case Tag.FLOAT:
            	type();
                identList();
                break;
            default:
                erro();
        }
    }
    
    public void type() throws IOException {
        if (token.tag == Tag.INT) {
            eat(Tag.INT);
        } else if (token.tag == Tag.FLOAT) {
            eat(Tag.FLOAT);
        } else {
            System.out.println("Faltou tipo na linha " + Lexer.line);
        }
    }

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

    public void stmt() throws IOException {
        //stmt ::= assign-stmt | if-stmt | while-stmt | repeat-stmt| read-stmt | write-stmt
        switch (token.tag) {
            case Tag.ID:
                assignStmt();
                break;
            case Tag.IF:
                ifStmt();
                break;
            case Tag.WHILE:
                WHILEstmt();
                break;
            case Tag.REPEAT:
                repeatStmt();
                break;
            case Tag.READ:
                readStmt();
                break;
            case Tag.WRITE:
                writeStmt();
                break;
            default:
                erro();

        }
    }

    public void assignStmt() throws IOException {
        //assign-stmt ::= identifier ":=" simple_expr 
        switch (token.tag) {
            case Tag.ID:
//                eat(Tag.ID);
                identifier();
                eat(Tag.ASGN);
                simpleExpr();
                break;
            default:
                erro();

        }
    }

    public void ifStmt() throws IOException {
        //if-stmt ::= if condition then stmt-list else-stmt
        switch (token.tag) {
            case Tag.IF:
                eat(Tag.IF);
                condition();
                eat(Tag.THEN);
                stmtList();
                esleStmt();
                break;
            default:
                erro();

        }
    }

    public void esleStmt() throws IOException {
        //else-stmt ::= end | else stmt-list end
        switch (token.tag) {
            case Tag.END:
                eat(Tag.END);
                break;
            case Tag.ELSE:
                eat(Tag.ELSE);
                stmtList();
                eat(Tag.END);
                break;
            default:
                erro();

        }

    }

    public void condition() throws IOException {
        //condition ::= expression 
        expression();

    }

    public void repeatStmt() throws IOException {
        //repeat-stmt ::= repeat stmt-list stmt-suffix
        switch (token.tag) {
            case Tag.REPEAT:
                eat(Tag.REPEAT);
                stmtList();
                stmtSuffix();
                break;
            default:
                erro();

        }
    }

    public void stmtSuffix() throws IOException {
        //stmt-suffix ::= until condition 
        switch (token.tag) {
            case Tag.UNTIL:
                eat(Tag.UNTIL);
                condition();
                break;
            default:
                erro();

        }

    }

    public void WHILEstmt() throws IOException {
        //while-stmt ::= stmt-prefix stmt-list end 
        switch (token.tag) {
            case Tag.WHILE:
                stmtPrefix();
                stmtList();
                eat(Tag.END);
                break;
            default:
                erro();
        }
    }

    public void stmtPrefix() throws IOException {
        //stmt-prefix ::= while condition do 
        switch (token.tag) {
            case Tag.WHILE:
                eat(Tag.WHILE);
                condition();
                eat(Tag.DO);
                break;
            default:
                erro();
        }
    }

    public void readStmt() throws IOException {
//read-stmt ::= read "(" identifier ")" 
        switch (token.tag) {
            case Tag.READ:
                eat(Tag.READ);
                eat('(');
                identifier();
                eat(')');
                break;
            default:
                erro();
        }
    }

    public void writeStmt() throws IOException {
        //write-stmt ::= write "(" writable ")" 
        switch (token.tag) {
            case Tag.WRITE:
                eat(Tag.WRITE);
                eat('(');
                WRITABLE();
                eat(')');
                break;
            default:
                erro();
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
            case Tag.FLOAT:
            case '(':
            case '{':
            case '!':
            case '-':
                simpleExpr();
                break;
            default:
                erro();
        }
    }

    public void expression() throws IOException {
        //expression ::= simple-expr | simple-expr relop simple-expr 
        switch (token.tag) {
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
            case Tag.FLOAT:
            case '(':
            case '{':
            case '!':
            case '-':
                simpleExpr();
                if (token.tag == Tag.EQ || token.tag == Tag.NE || token.tag == Tag.GE
                        || token.tag == Tag.LE || token.tag == '>' || token.tag == '<') {
                    relop();
                    simpleExpr();
                }
                break;
            default:
                erro();
        }
    }

    public void simpleExpr() throws IOException {
        //simple-expr ::= term simple-expr’
        switch (token.tag) {
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
            case Tag.FLOAT:
            case '(':
            case '{':
            case '!':
            case '-':
                term();
                simpleExpr2();
                break;
            default:
                erro();
        }
    }

    public void simpleExpr2() throws IOException {
        //simple-expr’ ::= λ | addop term simple-expr’ 
        if (token.tag == Tag.OR || token.tag == '+' || token.tag == '-') {
            addop();
            term();
            simpleExpr2();
        }
    }

    public void term() throws IOException {
        //term ::= factor-a term’
        switch (token.tag) {
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
            case Tag.FLOAT:
            case '(':
            case '{':
            case '!':
            case '-':
                factorA();
                term2();
                break;
            default:
                erro();
        }

    }

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
//                eat('(');
                factor();
                break;
            case Tag.ID:
            case Tag.INT:
            case Tag.REAL:
            case Tag.FLOAT:
                factor();
                break;
            default:
                erro();

        }

    }

    public void factor() throws IOException {
        //factor ::= identifier | constant | "(" expression ")" 
        switch (token.tag) {
            case Tag.ID:
                identifier();
                break;
            case Tag.FLOAT:
            case Tag.INT:
                constant();
                break;
            case ('('):
                eat('(');
                expression();
                eat(')');
                break;
            default:
                erro();

        }
    }

    public void relop() throws IOException {
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
                erro();

        }
    }

    public void addop() throws IOException {
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
                erro();

        }
    }

    public void mulop() throws IOException {
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
                erro();

        }
    }

    public void constant() throws IOException {
        //constant ::= integer_const | float_const 
        switch (token.tag) {
            case Tag.INT:
                integerConst();
                break;
            case Tag.FLOAT:
                floatConst();
                break;
            default:
                erro();

        }
    }

    public void integerConst() throws IOException {
        // integer_const ::= digit {digit} 
        switch (token.tag) {
            case Tag.INT:
                eat(Tag.INT);
//                if (token.tag == Tag.INT) //se for seguido de mais numeros já olha
//                {
//                    integerConst();
//                }
                break;
            default:
                erro();

        }
    }

    public void floatConst() throws IOException {
        // float_const ::= digit {digit} “.” digit {digit} 
        switch (token.tag) {
            case Tag.FLOAT:
                eat(Tag.FLOAT);
//                if (token.tag == Tag.FLOAT) //se for seguido de mais numeros já olha
//                {
//                    floatConst();
//                }
//                eat('.');
//                eat(Tag.INT);
//                if (token.tag == Tag.FLOAT) //se for seguido de mais numeros já olha
//                {
//                    floatConst();
//                }
                break;
            default:
                erro();

        }

    }

    public void literal() throws IOException {
        //literal ::= " {" {caractere} "}" 
        switch (token.tag) {
            case ('{'):
                eat('{');
//                if (token.tag == '{') //se for seguido de mais } já olha
//                {
//                    literal();
//                }
                caracter();
                eat('}');
//                 if (token.tag == '{') //se for seguido de mais } já olha
//                {
//                    literal();
//                }                
                break;
            default:
                erro();
        }
    }

    public void identifier() throws IOException {
        //Não sei se é isso mesmo, DUVIDA
        //identifier ::= letter |“_” {letter | digit | “_”} 
        switch (token.tag) {
            case (Tag.ID):
                eat(Tag.ID);
                break;
            default:
                erro();

        }
        
    }

    public void letter() throws IOException {
        // letter ::= [A-Za-z] 
        
       
    }

    public void digit() throws IOException {
        //digit ::= [0-9]
        if(token.tag == Tag.FLOAT){
            eat(Tag.FLOAT);
        }else if(token.tag == Tag.REAL){
            eat(Tag.REAL);
        }else if(token.tag == Tag.INT){
            eat(Tag.INT);
        }
        
    }
    public void caracter() throws IOException {
        //caractere ::= um dos 256 caracteres
        switch(token.tag){
            case '{':
            case '\n':
                erro();
            default:
                eat(token.tag);
                break;
        }
    }
}
