package gestioneOrdini;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Fornitore {
	private String name;
	private List<Prodotto> prodottiF;
	
	public Fornitore(String name, List<Prodotto> prodottiF) {
		super();
		this.name = name;
		this.prodottiF = prodottiF;
		if (prodottiF == null) 
			prodottiF = new ArrayList<>();
	}
	
	public void addProdotto(Prodotto p) throws Exception
	{
		if (prodottiF.contains(p)) throw new Exception();
		prodottiF.add(p);
	}	
	
	

}
