package gestioneOrdini;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GestioneOrdini {
	Map<String, Prodotto> prodotti = new TreeMap<>();
	Map<String, OrdineCliente> ordiniCliente = new TreeMap<>();
	Map<String, LineaOrdineCliente> lineeordine = new TreeMap<>();
	Map<String, OrdineFornitore> ordiniFornitore = new TreeMap<>();

	/**
	 * Il metodo void addProdotti (String s) throws Exception inserisce i
	 * prodotti (nome, prezzo int), segnalando la duplicazione del nome. Come si
	 * vede dall'esempio l'elenco dei prodotti è dato mediante una stringa.
	 * g.addProdotti("divano2pT,200,divano2pP,400,poltronaT,150,poltronaP,250,
	 * libreria,300,scaffale,120"); void addProdotti (String s) throws Exception
	 * {
	 */
	public void addProdotti(String s) throws Exception {
		if (s == null)
			throw new Exception("Null string");
		try (Scanner scanner = new Scanner(s)) {
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				String w = scanner.next();
				int i = Integer.valueOf(scanner.next());
				if (prodotti.containsKey(w))
					throw new Exception("Prodotto duplicato");
				Prodotto p = new Prodotto(w, i);
				prodotti.put(w, p);
			}
		} catch (Exception e) {
			System.out.println("scanner problem");
			if (e.getMessage().equals("Prodotto duplicato"))
				throw e;
		}
	}

	/**
	 * Il metodo void addFornitore (String nomeF, String prodotti) throws
	 * Exception inserisce un fornitore con i prodotti trattati, segnalando la
	 * mancanza di un prodotto. Come si vede dall'esempio l'elenco dei nomi dei
	 * prodotti trattati è dato mediante una stringa.
	 * g.addFornitore("AlfaMobili", "divano2pT,poltronaT,libreria"); Nota: le
	 * stringhe prodotti contengono nomi di prodotti separati da virgole.
	 */
	public void addFornitore(String nomeF, String prodotti) throws Exception {
		if (nomeF == null)
			throw new Exception("Null string");
		if (prodotti == null)
			throw new Exception("Null string");

		Fornitore f = new Fornitore(nomeF, null);
		try (Scanner scanner = new Scanner(prodotti)) {
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				f.addProdotto(this.prodotti.get(scanner.next()));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * Il metodo void addOrdineCliente (String codice, String cliente, String
	 * prodotti) throws Exception inserisce un ordine cliente; il codice è
	 * univoco, quindi è segnalata la duplicazione. Un ordine cliente si compone
	 * di linee, una per prodotto; i prodotti sono distinti (altrimenti è
	 * segnalata la duplicazione). Ciascuna linea punta al prodotto di cui è
	 * dato il nome nella stringa prodotti. g.addOrdineCliente("quercia:1",
	 * "quercia", "divano2pT,poltronaT"); //importo = 350 Note: quercia:1 è il
	 * codice dell'ordine; Si segnala un'eccezione se il codice esiste già, i
	 * prodotti non sono distinti o non sono registrati nel sistema.
	 **/
	public void addOrdineCliente(String codice, String cliente, String prodotti)
			throws Exception {
		if (ordiniCliente.containsKey(codice))
			throw new Exception();

		Set<String> setOfP = new TreeSet<>();
		try (Scanner scanner = new Scanner(prodotti)) {
			while (scanner.hasNext()) {
				String p = scanner.next();
				if (!this.prodotti.containsKey(p))
					throw new Exception("Prodotto non presente");
				if (setOfP.contains(p))
					throw new Exception("Prodotto duplicato in ordine cliente");
				else
					setOfP.add(p);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		OrdineCliente oc = new OrdineCliente(cliente);
		try (Scanner scanner = new Scanner(prodotti)) {
			while (scanner.hasNext()) {
				String p = scanner.next();
				LineaOrdineCliente loc = new LineaOrdineCliente(cliente,
						codice, this.prodotti.get(p));
				oc.addLinea(loc);
				lineeordine.put(codice, loc);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Il metodo int getImportoOC throws Exception dà l'importo in base al
	 * codice, con segnalazione di errore se non esiste l'ordine.
	 **/
	public int getImportoOC(String codice) throws Exception {
		if (ordiniCliente.containsKey(codice))
			throw new Exception();
		return ordiniCliente.get(codice).getLinee().values().stream()
				.map(LineaOrdineCliente::getProdotto)
				.collect(Collectors.summingInt(Prodotto::getPrice));
	}

	/**
	 * Il metodo void addOrdineFornitore (String codice, String fornitore,
	 * String linee) throws Exception inserisce un ordine fornitore; il codice è
	 * univoco, quindi è segnalata la duplicazione.
	 * g.addOrdineFornitore("alfa:1", "AlfaMobili",
	 * "quercia:1 divano2pT, faggio:1 poltronaP"); //importo = 450 Un ordine
	 * fornitore aggrega linee di ordini cliente: ogni linea è indicata dal
	 * codice dell'ordine cliente e dal nome del prodotto. L'ordine fornitore
	 * mostrato aggrega quindi la linea divano2pT dell'ordine cliente quercia:1
	 * e la linea poltronaP dell'ordine cliente faggio:1. Il metodo lancia
	 * un'eccezione se il fornitore non fornisce i prodotti corrispondenti alle
	 * linee e se le linee indicate sono inesistenti (errore nel codice ordine
	 * cliente o nel nome del prodotto) o sono già state aggregate in un altro
	 * ordine fornitore.
	 * 
	 * 
	 **/
	public void addOrdineFornitore(String codice, String fornitore, String linee)
			throws Exception {

		OrdineFornitore of = new OrdineFornitore(codice, fornitore);

		try (Scanner scanner = new Scanner(linee)) {
			scanner.useDelimiter("[, ]");
			while (scanner.hasNext()) {
				String codiceOrdine = scanner.next();
				String nomeProdotto = scanner.next();
				LineaOrdineCliente loc = ordiniCliente.get(codiceOrdine)
						.getLinee().get(nomeProdotto);
				of.addLineaOrdineCliente(loc);
				loc.changeStatoInOrdine();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * Il metodo void consegnaFornitore (String codice) throws Exception porta
	 * nello stato consegnato l'ordine fornitore e le linee associate. Verifica
	 * lo stato degli ordini cliente che non sono ancora nello stato consegnato:
	 * se tutte le linee sono nello stato consegnata, porta l'ordine cliente
	 * nello stato consegnato. Dà errore se il codice è errato o l'ordine
	 * fornitore è già nello stato consegnato.
	 * 
	 **/
	public void consegnaFornitore(String codice) throws Exception {
		ordiniFornitore.get(codice).consegnato();
		ordiniFornitore.get(codice).getLinee().forEach(l -> {
			l.changeStatoConsegnata();
			ordiniCliente.get(l.getNomeOrdine()).checkConsegnato();
		});
	}

	/**
	 * Il metodo List<String> getOrdiniConsegnati dà l'elenco dei codici
	 * (ordinati alfabeticamente) degli ordini consegnati.
	 * 
	 **/
	public List<String> getOrdiniConsegnati() {
		return ordiniCliente.values().stream()
				.filter(o -> o.isStatoconsgenato()).map(OrdineCliente::getName)
				.sorted().collect(Collectors.toList());
	}

	/**
	 * SortedMap <String, Long> nOrdiniCliente () dà il numero di ordini per
	 * cliente (nome) con nomi ordinati.
	 */

	public SortedMap<String, Long> nOrdiniCliente() {
		return ordiniCliente
				.values()
				.stream()
				.collect(
						Collectors.groupingBy(OrdineCliente::getName,
								TreeMap::new, Collectors.counting()));
	}

	/**
	 * int maxOrdineCliente () dà l'importo dell'ordine cliente più elevato
	 **/
	public int maxOrdineCliente() {
		Optional<Long> ol = ordiniCliente.values().stream()
				.map(OrdineCliente::getSomma)
				.collect(Collectors.maxBy(Comparator.naturalOrder()));

		if (ol.isPresent())
			return Math.toIntExact(ol.get());
		else
			return -1;
	}

	/**
	 * SortedMap <Integer, TreeSet<String>> clientiTotaleOrdini(); raggruppa i
	 * clienti in base all'importo totale dei loro ordini; gli importi sono
	 * decrescenti e dei clienti si danno i nomi distinti e ordinati.
	 *
	 **/
	public SortedMap<Integer, TreeSet<String>> clientiTotaleOrdini() {
		return ordiniCliente
				.values()
				.stream()
				.collect(
						Collectors.groupingBy(OrdineCliente::getName,
								Collectors.<OrdineCliente> summingInt(o -> Math
										.toIntExact(o.getSomma()))))
				.entrySet()
				.stream()
				.collect(
						Collectors.groupingBy(e -> e.getValue(), () -> {
							return new TreeMap<>(Comparator.reverseOrder());
						}, Collectors.mapping(e -> e.getKey(),
								Collectors.toCollection(TreeSet::new))));

	}

	/**
	 * 
	 * SortedMap <Long, TreeSet<String>> prodottiNLinee () raggruppa i prodotti
	 * per n. di linee (degli ordini cliente); i n. di linee sono decrescenti e
	 * dei prodotti si danno i nomi distinti e ordinati
	 **/
	public SortedMap<Long, TreeSet<String>> prodottiNLinee() {
		return lineeordine
				.values()
				.stream()
				.collect(
						Collectors.groupingBy(l -> l.getProdotto().getName(),
								Collectors.counting()))
				.entrySet()
				.stream()
				.collect(
						Collectors.groupingBy(e -> e.getValue(), () -> {
							return new TreeMap<>(Comparator.reverseOrder());
						}, Collectors.mapping(e -> e.getKey(),
								Collectors.toCollection(TreeSet::new))));
	}

	public String getStatoOC(String codice) {
		return ordiniCliente.get(codice).getStato().toString();
	}

	public String getImportoOF(String codice) {
		return ordiniFornitore
				.get(codice)
				.getLinee()
				.stream()
				.collect(Collectors.summingInt(l -> l.getProdotto().getPrice()))
				.toString();
	}

}