package ProtegeGenCode.Root1.impl;

import ProtegeGenCode.Root1.*;

import java.util.HashSet;

import java.io.Serializable;
import javax.jdo.annotations.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



import java.net.URI;
import java.util.Collection;
import javax.xml.datatype.XMLGregorianCalendar;

import org.protege.owl.codegeneration.WrappedIndividual;
import org.protege.owl.codegeneration.impl.WrappedIndividualImpl;

import org.protege.owl.codegeneration.inference.CodeGenerationInference;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Generated by Protege (http://protege.stanford.edu).<br>
 * Source Class: DefaultValuePartition <br>
 * @version generated on Wed Apr 28 11:02:02 IST 2021 by prateksha
 */
@Entity
 public class DefaultValuePartition extends WrappedIndividualImpl implements ValuePartition , Serializable {
	 private static final long serialVersionUID = 1L;
	 @GeneratedValue
	 private long id;
	 @Id private String name;
	

    public DefaultValuePartition(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
		 name = iri.toString();
	
    }





}
