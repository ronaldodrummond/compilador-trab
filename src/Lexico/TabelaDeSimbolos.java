package Lexico;

import Lexico.Token;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TabelaDeSimbolos {
	private Hashtable tabela;
	private TabelaDeSimbolos prev;
	
	public TabelaDeSimbolos () {
		this.tabela = new Hashtable();
	}
	
	public void put (Token w, int id) {
		tabela.put(w, id);
	}
	
	public int get (Token w) {
		int aux;
		for (TabelaDeSimbolos s = this; s != null; s = s.prev) {
			if (s.tabela.get(w) != null) {
				aux = (int) s.tabela.get(w);
				return aux;
			}
		}
		return 0;
	}
	// falta casse getInstance
        public  TabelaDeSimbolos getInstance() {
        if (prev == null) {
            prev = new TabelaDeSimbolos();
            return prev;
        }
        return prev;
    }
	public void imprimirTabela() {
		System.out.println("\nTabela de Símbolos:\n");
		Set set = tabela.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
		//HashMap<K, V>.Entry<Token,String> entry = (HashMap.Entry<Token, String) it.next();
		}
	}
}
