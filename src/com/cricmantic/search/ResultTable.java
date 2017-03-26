package com.cricmantic.search;

import java.util.ArrayList;

import org.apache.jena.query.QuerySolution;

 public class ResultTable {


	public String field1;
	public String field2;
	public String field3;
	public String field4;
	public String field5;
	public String field6;

	public String getField6() {
		return field6;
	}
	public void setField6(String field6) {
		this.field6 = field6;
	}
	public String getField4() {
		return field4;
	}
	public void setField4(String field4) {
		this.field4 = field4;
	}
	public String getField5() {
		return field5;
	}
	public void setField5(String field5) {
		this.field5 = field5;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public ResultTable setAllFields(int n,QuerySolution soln, ArrayList<String> queryList) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		String ns="http://www.semanticweb.org/Hamza/ontologies/2016/7/untitled-ontology-1#";
		for (int i = 0; i < n; i++) {
			ResultTable.class.getField("field" + Integer.toString((i+1))).set(this, soln.get(queryList.get(i)).toString().replaceAll(ns, "").replaceAll("..http(.*)", ""));
			
			
		}
		return this;
	}
	
}
