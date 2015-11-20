package compiler;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TabelaDeSimbolos {
	private Hashtable tabela;
	private TabelaDeSimbolos prev;
	
	public TabelaDeSimbolos (TabelaDeSimbolos prev) {
		this.tabela = new Hashtable();
		this.prev = prev;
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
	
	public void imprimirTabela() {
		System.out.println("\nTabela de Símbolos:\n");
		Set set = tabela.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map<K, V>.Entry<Token, int> entry = (Map.Entry<Token, int>) it.next();
		}
	}
}
