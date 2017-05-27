package test;

import java.util.SortedMap;
import java.util.SortedSet;

import gestioneOrdini.GestioneOrdini;

public class Test {

	public static void main(String[] args) throws Exception {

		GestioneOrdini g = new GestioneOrdini();
		g.addProdotti("divano2pT,200,divano2pP,400,poltronaT,150,poltronaP,250,libreria,300,scaffale,120");
		// registra i prodotti nome prezzo (intero), segnala duplicazione
		g.addFornitore("AlfaMobili", "divano2pT,poltronaT,libreria");
		// registra un fornitore con i prodotti trattati, segnala mancanza
		// prodotto
		g.addFornitore("BetaMobili ", "divano2pP,poltronaP");
		g.addFornitore("TuttoMobili",
				"divano2pT,poltronaT,divano2pP,poltronaP,libreria,scaffale");

		g.addOrdineCliente("quercia:1", "quercia", "divano2pT,poltronaT"); // importo
																			// =
																			// 350
		g.addOrdineCliente("faggio:1", "faggio", "poltronaT,poltronaP,libreria"); // importo
																					// =
																					// 700
		g.addOrdineFornitore("alfa:1", "AlfaMobili",
				"quercia:1 divano2pT, faggio:1 poltronaP");
		// importo = 450
		g.addOrdineFornitore("tutto:1", "TuttoMobili",
				"quercia:1 poltronaT, faggio:1 libreria");
		g.consegnaFornitore("alfa:1");
		g.consegnaFornitore("tutto:1");
		g.addOrdineCliente("quercia:2", "quercia", "libreria,poltronaT"); // importo
																			// =
																			// 450
		// letture
		System.out.println(g.getStatoOC("quercia:1"));
		System.out.println(g.getStatoOC("faggio:1"));
		System.out.println(g.getImportoOC("quercia:1"));
		System.out.println(g.getImportoOF("alfa:1"));
		SortedMap<String, Long> mappaNOrdiniCliente = g.nOrdiniCliente();
		System.out.println("mappaNOrdiniCliente " + mappaNOrdiniCliente);
		int maxOrdineCliente = g.maxOrdineCliente();
		System.out.println("maxOrdineCliente " + maxOrdineCliente);
		SortedMap<Integer, ? extends SortedSet<String>> mappaClientiTotaleOrdini = g
				.clientiTotaleOrdini();
		System.out.println("mappaClientiTotaleOrdini "
				+ mappaClientiTotaleOrdini);
		SortedMap<Long, ? extends SortedSet<String>> mappaProdottiNLinee = g
				.prodottiNLinee();
		System.out.println("mappaProdottiNLinee " + mappaProdottiNLinee);
	}

}
