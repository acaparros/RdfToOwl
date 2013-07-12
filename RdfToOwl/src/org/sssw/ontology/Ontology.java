package org.sssw.ontology;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.vocabulary.XSD;

public class Ontology {

	private OntModel model;	// model ontology
	
	public Ontology(){
		this.model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
	}
	
	public OntModel getModel() {
		return model;
	}

	public void setModel(OntModel model) {
		this.model = model;
	}

	/**
	 * Loads dataset's ontology
	 * 
	 * @param urlOntology
	 * @return
	 */
	public void loadOntology(String urlOntology){
		this.model.read(urlOntology);
		
		System.out.println("Loading ontology...");
		System.out.println("Classes");
		for (Iterator<OntClass> i = this.model.listClasses();i.hasNext();){
			OntClass cls = i.next();
			System.out.print(cls.getLocalName()+": ");
			for(Iterator it = cls.listInstances(true);it.hasNext();){
				Individual ind = (Individual)it.next();
				if(ind.isIndividual()){
					System.out.print(ind.getLocalName()+" ");
				}
			}

			System.out.println();
		}
		
		System.out.println("Properties");
		for (Iterator<OntProperty> i = this.model.listAllOntProperties();i.hasNext();){
			OntProperty cls = i.next();
			System.out.print(cls.getLocalName()+": ");
		}
		System.out.println();
		
		System.out.println("Ontology Loaded!");
		System.out.println();
	}
	
	/**
	 * Method to create the new ontology
	 * @param urlOntology
	 * @param name
	 * @return
	 */
	public Ontology buildOntology(String urlOntology, String name){

		//We add default NameSpace for our ontology
		String NS = name;
		model.setNsPrefix(NS, urlOntology);      
		
		//Classes
		String nameClass = "";
		OntClass ontologyClass = model.createClass(NS+":"+nameClass);


		//We create properties
		String nameProperty = "";
		DatatypeProperty property = model.createDatatypeProperty(NS+":"+nameProperty);
		property.addDomain(ontologyClass);//Class
		property.addRange(XSD.xint);//Type
		property.convertToFunctionalProperty();

		//We create instructions and assing weights.
		String nameIndividual="";
		Individual individual = model.createIndividual(NS+":"+nameIndividual, ontologyClass);
		individual.setPropertyValue(property, model.createTypedLiteral(250));

		//Almacenamos la ontología en un fichero OWL (Opcional)
		File file = new File("./"+name+".owl");
		//Hay que capturar las Excepciones
		if (!file.exists()){
		     try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			model.write(new PrintWriter(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}

}
