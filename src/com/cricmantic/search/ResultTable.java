package com.cricmantic.search;

import java.util.ArrayList;

import org.apache.jena.query.QuerySolution;

public class ResultTable {
	private String[] field;

	public String[] getField() {
		return field;
	}

	public void setField(String[] field) {
		this.field = field;
	}

	public void setAllFields2(int n, QuerySolution soln, ArrayList<String> queryList)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		field=new String[n];
		String ns = "http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
		for (int i = 0; i < n; i++) {
			this.field[i] = soln.get(queryList.get(i)).toString().replaceAll(ns, "").replaceAll("..http(.*)", "");
		}

	}

	public void setField1(String sentence) {
		// TODO Auto-generated method stub
		field=new String[1];
		this.field[0] = sentence;
	}

}
