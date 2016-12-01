package com.semantic.jsp;
import org.apache.jena.rdf.model.RDFNode;

public class RowObject {
    private RDFNode subject;
    private RDFNode predicate;
    private RDFNode object;
    
    
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

    }