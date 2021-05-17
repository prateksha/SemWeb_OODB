package ProtegeGenCode.Root2;

import ProtegeGenCode.Root2.impl.*;


import java.util.Collection;

import org.protege.owl.codegeneration.CodeGenerationFactory;
import org.protege.owl.codegeneration.WrappedIndividual;
import org.protege.owl.codegeneration.impl.FactoryHelper;
import org.protege.owl.codegeneration.impl.ProtegeJavaMapping;
import org.protege.owl.codegeneration.inference.CodeGenerationInference;
import org.protege.owl.codegeneration.inference.SimpleInference;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * A class that serves as the entry point to the generated code providing access
 * to existing individuals in the ontology and the ability to create new individuals in the ontology.<p>
 * 
 * Generated by Protege (http://protege.stanford.edu).<br>
 * Source Class: Root2<br>
 * @version generated on Mon May 17 15:08:03 IST 2021 by gurleen
 */
public class Root2 implements CodeGenerationFactory {
    private OWLOntology ontology;
    private ProtegeJavaMapping javaMapping = new ProtegeJavaMapping();
    private FactoryHelper delegate;
    private CodeGenerationInference inference;

    public Root2(OWLOntology ontology) {
	    this(ontology, new SimpleInference(ontology));
    }
    
    public Root2(OWLOntology ontology, CodeGenerationInference inference) {
        this.ontology = ontology;
        this.inference = inference;
        javaMapping.initialize(ontology, inference);
        delegate = new FactoryHelper(ontology, inference);
    }

    public OWLOntology getOwlOntology() {
        return ontology;
    }
    
    public void saveOwlOntology() throws OWLOntologyStorageException {
        ontology.getOWLOntologyManager().saveOntology(ontology);
    }
    
    public void flushOwlReasoner() {
        delegate.flushOwlReasoner();
    }
    
    public boolean canAs(WrappedIndividual resource, Class<? extends WrappedIndividual> javaInterface) {
    	return javaMapping.canAs(resource, javaInterface);
    }
    
    public  <X extends WrappedIndividual> X as(WrappedIndividual resource, Class<? extends X> javaInterface) {
    	return javaMapping.as(resource, javaInterface);
    }
    
    public Class<?> getJavaInterfaceFromOwlClass(OWLClass cls) {
        return javaMapping.getJavaInterfaceFromOwlClass(cls);
    }
    
    public OWLClass getOwlClassFromJavaInterface(Class<?> javaInterface) {
	    return javaMapping.getOwlClassFromJavaInterface(javaInterface);
    }
    
    public CodeGenerationInference getInference() {
        return inference;
    }

    /* ***************************************************
     * Class http://www.semanticweb.org/gurleen/ontologies/people#people
     */

    {
        javaMapping.add("http://www.semanticweb.org/gurleen/ontologies/people#people", People.class, DefaultPeople.class);
    }

    /**
     * Creates an instance of type People.  Modifies the underlying ontology.
     */
    public People createPeople(String name) {
		return delegate.createWrappedIndividual(name, Vocabulary.CLASS_PEOPLE, DefaultPeople.class);
    }

    /**
     * Gets an instance of type People with the given name.  Does not modify the underlying ontology.
     * @param name the name of the OWL named individual to be retrieved.
     */
    public People getPeople(String name) {
		return delegate.getWrappedIndividual(name, Vocabulary.CLASS_PEOPLE, DefaultPeople.class);
    }

    /**
     * Gets all instances of People from the ontology.
     */
    public Collection<? extends People> getAllPeopleInstances() {
		return delegate.getWrappedIndividuals(Vocabulary.CLASS_PEOPLE, DefaultPeople.class);
    }


}
