package com.cricmantic.functions;
import java.util.ArrayList;

import org.apache.jena.rdf.model.RDFNode;

public class RowObject {
    private RDFNode subject;
    private RDFNode predicate;
    private RDFNode object;
    
    private ArrayList<String> list1;
    private ArrayList<Integer> list2;
    
    
    
    public void setSubject(RDFNode subject) {
        this.subject = subject;
    }
    public void setPredicate(RDFNode predicate) {
        this.predicate = predicate;
    }
    public void setObject(RDFNode object) {
        this.object = object;
    }
	public RDFNode getSubject() {
	    return subject;
	}
	public RDFNode getPredicate() {
	    return predicate;
	}
	public RDFNode getObject() {
	    return object;
	}
	public ArrayList<String> getList1() {
		return list1;
	}
	public void setList1(ArrayList<String> list1) {
		this.list1 = list1;
	}
	public ArrayList<Integer> getList2() {
		return list2;
	}
	public void setList2(ArrayList<Integer> list2) {
		this.list2 = list2;
	}

    }