package gestioneOrdini;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrdineCliente {
	String name;
	List<LineaOrdineCliente> linee = new ArrayList<>();
	private Stato stato = Stato.INSERITO;

	public Stato getStato() {
		return stato;
	}

	public List<LineaOrdineCliente> getLinee() {
		return linee;
	}

	public OrdineCliente(String codice) {
		super();
		this.name = codice;
	}

	public void addLinea(LineaOrdineCliente linea) {
		linee.add(linea);
	}

	enum Stato {
		INSERITO("Inserito"), CONSEGNATO("Consegnato");
		
		private String text;
		
		private Stato(String text) {
			this.text = text;
		}
		
		public String toString() {
			return text;
			
		}
		
	}

	public void checkConsegnato() {
		long nonconsegnati = linee.stream()
				.filter(l -> l.getStatoNonConsegnato() == true)
				.collect(Collectors.counting());
		if (nonconsegnati == 0)
			stato = Stato.CONSEGNATO;

	}

	public boolean isStatoconsgenato() {
		return stato == Stato.CONSEGNATO;
	}

	public String getName() {
		return name;
	}

	public long getSomma() {
		return linee.stream().map(LineaOrdineCliente::getProdotto)
				.map(Prodotto::getPrice)
				.collect(Collectors.summingLong(l -> l));

	}

}
