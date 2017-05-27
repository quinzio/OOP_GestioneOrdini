package gestioneOrdini;

public class LineaOrdineCliente {
	private String cliente;
	private String nomeOrdine;
	private Prodotto prodotto;
	private Stato stato = Stato.INSERITA;

	public String getCliente() {
		return cliente;
	}

	public String getNomeOrdine() {
		return nomeOrdine;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}

	public LineaOrdineCliente(String cliente, String nomeOrdine,
			Prodotto prodotto) {
		super();
		this.cliente = cliente;
		this.nomeOrdine = nomeOrdine;
		this.prodotto = prodotto;
	}

	public boolean getStatoNonConsegnato() {
		return stato != Stato.CONSEGNATO;
	}

	private enum Stato {
		INSERITA, INORDINE, CONSEGNATO;
	}

	public void changeStatoInOrdine() {
		stato = Stato.INORDINE;
	}
	public void changeStatoConsegnata() {
		stato = Stato.CONSEGNATO;
	}

}
