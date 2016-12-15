package com.cricmantic.functions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;

import arq.query;

public class Insert {
	static String word=null;
	static ArrayList<String> list = new ArrayList<String>();
	static String defaultNameSpace = "http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
	public static void main(String news) {
		String s = news;
		tokens(s);
		Model model=null;
		
	    model=Analize(list);
		save(model);
	}
	public  static ArrayList<String> tokens(String query)
	{
		String[] words=query.split(" ");//splits the string based on string
		//using java foreach loop to print elements of string array
		for(String w:words){
		list.add(w);
		}
		return list;
	}
	public static Model Analize(ArrayList<String> arraylist)
	{
		Model model = ModelFactory.createOntologyModel();
		model.read("src/crickmantic.owl");
		Resource predicate;
		Property pred = null;
		int flag=0;
			word=(String) arraylist.get(1);
			predicate=model.getResource(defaultNameSpace+word);
			
			if(model.contains(predicate, RDF.type,OWL.ObjectProperty))
			{
			    pred=model.getProperty(defaultNameSpace+word);
				flag=1;
			}
			else if(model.contains(predicate, RDF.type,OWL.DatatypeProperty))
			{
				pred=model.getProperty(defaultNameSpace+word);
				flag=1;
			}
			if(flag==1)
			{
				word=(String) arraylist.get(0);
				Resource subject = model.createResource(defaultNameSpace+word);
				word=(String) arraylist.get(2);
				Resource object = model.createResource(defaultNameSpace+word);
				Resource class1=model.getResource(defaultNameSpace+"Match");
				model.add(subject,RDF.type,class1);
				model.add(object,RDF.type,class1);
				model.add(subject,pred,object);
				
			}
			return model;
		}
	public static void save(Model model) {
		String fileName = "save.rdf"; 
		FileWriter out;
		try 
		{ 
			out = new FileWriter(fileName );
			model.write( out, "RDF/XML" );
			out.close(); 
			} 
		catch(IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
