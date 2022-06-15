package it.polito.tdp.yelp.model;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {

	private YelpDao dao;

	private List<Business> businessList;

	private SimpleDirectedWeightedGraph<Business, DefaultWeightedEdge> grafo;

	public Model() {
		dao = new YelpDao();
	}

	public void creaGrafo(String city, int anno) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

		// VERTICI
		businessList = dao.listCityYear(city, anno);
		Graphs.addAllVertices(grafo, businessList);

		// ARCHI
		for (int i = 0; i < businessList.size(); i++) {
			Business b = businessList.get(i);
			for (int j = i + 1; j < businessList.size(); j++) {
				Business b1 = businessList.get(j);
				double peso = dao.getPeso(b, b1, anno);
				if (peso < 0) {
					peso *= -1;
					Graphs.addEdgeWithVertices(grafo, b1, b, peso);
				} else if (peso > 0) {
					Graphs.addEdgeWithVertices(grafo, b, b1, peso);
				}
			}
		}
	}

	public Business bestBusiness() {
		Business ris = null;
		int max = 0;
		for (Business b : businessList) {
			int entranti = 0;
			int uscenti = 0;
			for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(b)) {
				uscenti += grafo.getEdgeWeight(e);
			}
			for (DefaultWeightedEdge e : grafo.incomingEdgesOf(b)) {
				entranti += grafo.getEdgeWeight(e);
			}
			int totale = uscenti - entranti;
			System.out.println(b.getBusinessName() + ": " + totale);
			if (totale > max) {
				ris = b;
				max = totale;
			}
		}

		return ris;
	}

	public List<String> getCities() {
		return dao.getAllCity();
	}

	public Set<Business> getVertici() {
		return grafo.vertexSet();
	}

	public Set<DefaultWeightedEdge> getArchi() {
		return grafo.edgeSet();
	}
}
