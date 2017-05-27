package gestioneOrdini;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrdineCliente {
	String name;
	Map<String, LineaOrdineCliente> linee = new TreeMap<>();
	private Stato stato = Stato.INSERITO;

	public Map<String, LineaOrdineCliente> getLinee() {
		return linee;
	}

	public OrdineCliente(String codice) {
		super();
		this.name = codice;
	}

	public void addLinea(LineaOrdineCliente linea) {
		linee.put(linea.getNomeOrdine(), linea);
	}

	private enum Stato {
		INSERITO, CONSEGNATO;
	}

	public void checkConsegnato() {
		long nonconsegnati = linee.values().stream()
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
		return linee.values().stream().map(LineaOrdineCliente::getProdotto)
				.map(Prodotto::getPrice)
				.collect(Collectors.summingLong(l -> l));

	}

}
