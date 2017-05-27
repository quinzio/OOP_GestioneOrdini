package gestioneOrdini;

import java.util.ArrayList;
import java.util.List;

public class OrdineFornitore {
	private String nameFornitore;
	private String codice;
	private List<LineaOrdineCliente> linee = new ArrayList<>();
	private Stato stato = Stato.INSERITO;
	
	public String getnameFornitore() {
		return nameFornitore;
	}

	public String getCodice() {
		return codice;
	}

	public List<LineaOrdineCliente> getLinee() {
		return linee;
	}

	public OrdineFornitore(String codice, String name) {
		super();
		this.nameFornitore = name;
		this.codice = codice;
	}
	
	public void addLineaOrdineCliente(LineaOrdineCliente loc) {
		linee.add(loc);
	}
	
	private enum Stato {
		INSERITO, CONSEGNATO;
	}
	
	public void consegnato() {
		stato = Stato.CONSEGNATO;
	}

	

}
