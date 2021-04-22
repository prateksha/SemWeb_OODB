package ProtegeGenCode.CollegeProtege.impl;

import ProtegeGenCode.CollegeProtege.*;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;

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
 * Source Class: DefaultCollege <br>
 * @version generated on Sat Apr 03 10:21:32 IST 2021 by prateksha
 */

 public class DefaultCollege extends WrappedIndividualImpl implements College {

    public DefaultCollege(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://www.semanticweb.org/prateksha/ontologies/2021/1/college#admits
     */
     
    public Collection<? extends Student> getAdmits() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_ADMITS,
                                               DefaultStudent.class);
    }

    public boolean hasAdmits() {
	   return !getAdmits().isEmpty();
    }

    public void addAdmits(Student newAdmits) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_ADMITS,
                                       newAdmits);
    }

    public void removeAdmits(Student oldAdmits) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_ADMITS,
                                          oldAdmits);
    }


    /* ***************************************************
     * Object Property http://www.semanticweb.org/prateksha/ontologies/2021/1/college#employs
     */
     
    public Collection<? extends Professor> getEmploys() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_EMPLOYS,
                                               DefaultProfessor.class);
    }

    public boolean hasEmploys() {
	   return !getEmploys().isEmpty();
    }

    public void addEmploys(Professor newEmploys) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_EMPLOYS,
                                       newEmploys);
    }

    public void removeEmploys(Professor oldEmploys) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_EMPLOYS,
                                          oldEmploys);
    }


}
