package ProtegeGenCode.CollegeProtege.impl;

import ProtegeGenCode.CollegeProtege.*;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.jdo.annotations.Embedded;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.datatype.XMLGregorianCalendar;

import org.protege.owl.codegeneration.WrappedIndividual;
import org.protege.owl.codegeneration.impl.WrappedIndividualImpl;

import org.protege.owl.codegeneration.inference.CodeGenerationInference;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Generated by Protege (http://protege.stanford.edu).<br>
 * Source Class: DefaultCourse <br>
 * @version generated on Sat Apr 03 10:21:32 IST 2021 by prateksha
 */

 @Entity
 public class DefaultCourse extends WrappedIndividualImpl implements Course , Serializable {
	 private static final long serialVersionUID = 1L;
	 @GeneratedValue
	 private long id;
	 @Id
	 private String name;
	 @Embedded private Collection<? extends Professor> TaughtBy;
	

    public DefaultCourse(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
		 name = iri.toString();
		 TaughtBy  = new HashSet<DefaultProfessor>();	
    }

    public String getName() {
    	return name;
    }

    
    public void setTaughtBy(Object p) {
    	DefaultProfessor p_new = (DefaultProfessor)p;
    	HashSet<DefaultProfessor> p1  = new HashSet<DefaultProfessor>();
    	p1.add((DefaultProfessor) p_new);
    	this.TaughtBy = p1;
    }
    
    @Override 
    public String toString() {
    	return name;
    }

}
