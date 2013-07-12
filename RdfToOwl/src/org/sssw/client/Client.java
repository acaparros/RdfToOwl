package org.sssw.client;

import java.util.Iterator;

import org.sssw.ontology.Ontology;

import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.ResultSet;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Ontology ont = new Ontology();
		ont.loadOntology(args[1]);
		for (Iterator<OntProperty> i = ont.getModel().listAllOntProperties();i.hasNext();){
			OntProperty cls = i.next();
			System.out.println(cls.getURI()+": ");
		}

		SparqlQuery client = new SparqlQuery(args[0], args[1], args[2]);
		ResultSet results = client.runQuery();
		
		
	}

}
