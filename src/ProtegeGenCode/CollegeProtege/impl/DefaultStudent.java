package ProtegeGenCode.CollegeProtege.impl;

import ProtegeGenCode.CollegeProtege.*;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;

import javax.jdo.annotations.Embedded;
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
 * Source Class: DefaultStudent <br>
 * @version generated on Sat Apr 03 10:21:32 IST 2021 by prateksha
 */

 @Entity
 public class DefaultStudent extends WrappedIndividualImpl implements Student , Serializable {
	 private static final long serialVersionUID = 1L;
	 @GeneratedValue
	 private long id;
	 @Id
	 private String name;
	
@Embedded private Collection<? extends Integer> HasDebt;
@Embedded private Collection<? extends Integer> HasRollNumber;

    public DefaultStudent(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
		 name = iri.toString();
	HasDebt=getHasDebt();
	HasRollNumber=getHasRollNumber();
	System.out.println("Constructor called for Student");
	
    }


    /* ***************************************************
     * Data Property http://www.semanticweb.org/prateksha/ontologies/2021/1/college#hasDebt
     */
     
    public Collection<? extends Integer> getHasDebt() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASDEBT, Integer.class);
    }

    public boolean hasHasDebt() {
		return !getHasDebt().isEmpty();
    }

    public void addHasDebt(Integer newHasDebt) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASDEBT, newHasDebt);
    }

    public void removeHasDebt(Integer oldHasDebt) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASDEBT, oldHasDebt);
    }


    /* ***************************************************
     * Data Property http://www.semanticweb.org/prateksha/ontologies/2021/1/college#hasRollNumber
     */
     
    public Collection<? extends Integer> getHasRollNumber() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASROLLNUMBER, Integer.class);
    }

    public boolean hasHasRollNumber() {
		return !getHasRollNumber().isEmpty();
    }

    public void addHasRollNumber(Integer newHasRollNumber) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASROLLNUMBER, newHasRollNumber);
    }

    public void removeHasRollNumber(Integer oldHasRollNumber) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASROLLNUMBER, oldHasRollNumber);
    }


}
