package com.cricmantic.parsing;

import java.awt.AWTException;
import java.io.IOException;

import com.cricmantic.functions.InsertRDF;

public class Cricmantic {
	// URI of ontology to select individuals
	// private static String
	// newuri="http://www.semanticweb.org/tayyab/ontologies/2016/7/untitled-ontology-2#";
	// static String defaultNameSpace =
	// "http://semwebprogramming.org/2009/ont/chp2:#";

	public static void main(String[] args) throws IOException, InterruptedException {
		String sumdata = null;
		String in1data = null;
		String in2data = null;
		String fdata = null;
		// http://www.cricbuzz.com/cricket-full-commentary/16344/pak-vs-sl-10th-match-asia-cup-2016
		String url = null;
		// http://www.cricbuzz.com/cricket-full-commentary/16338/ind-vs-pak-4th-match-asia-cup-2016
		url = "http://www.cricbuzz.com/live-cricket-full-commentary/17867/wi-vs-pak-1st-t20i-pakistan-tour-of-west-indies-2017";
		parse par = new parse();

		scrap s = new scrap();

		try {
			fdata = s.facts(url);
			in2data = s.second(url);
			sumdata = s.summary(url);
			in1data = s.first(url);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		par.parseFact(fdata);
		par.parseSum(sumdata);
		par.parseCommentary(in1data, 1);
		par.parseCommentary(in2data, 2);
		DynamicRules rules = new DynamicRules();
		rules.inferRules();
		InsertRDF rdf=new InsertRDF();
		rdf.insertParsedRDF();
		
	}

}
