package org.sssw.client;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

/**
 * 
 * @author acaparros
 * 
 * Class that represents a SPARQL query
 */
public class SparqlQuery {
	private String urlDataset;
	private String urlOntology;
	private String query;

	public SparqlQuery() {

	}

	public SparqlQuery(String urlDataset, String urlOntology, String query) {
		this.urlDataset = urlDataset;
		this.urlOntology = urlOntology;
		this.query = query;
	}

	public String getUrlDataset() {
		return urlDataset;
	}

	public void setUrlDataset(String urlDataset) {
		this.urlDataset = urlDataset;
	}

	public String getUrlOntology() {
		return urlOntology;
	}

	public void setUrlOntology(String urlOntology) {
		this.urlOntology = urlOntology;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Executes a SPARQL query
	 */
	public ResultSet runQuery() {
		Query sparqlQuery = QueryFactory.create(this.query);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				this.urlDataset, sparqlQuery);

		ResultSet results = qexec.execSelect();
		ResultSetFormatter.out(System.out, results, sparqlQuery);

		qexec.close();
		
		return results;
	}

}
