package Pipeline;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

import com.google.common.collect.Iterables;

import ProtegeGenCode.CollegeProtege.CollegeProtege;
import ProtegeGenCode.CollegeProtege.*;
import ProtegeGenCode.CollegeProtege.impl.*;

import ProtegeGenCode.Root2.Root2;
import ProtegeGenCode.Root2.*;
import ProtegeGenCode.Root2.impl.*;



public class Main {
	public static void main(String[] argv) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		try {
			String db_path = "jars/db/people.odb";
			String owl_file_path = "src/OWL_files/people.owl";
			String prefix = "ProtegeGenCode.Root2.impl.Default";

			
//			String db_path = "jars/db/college.odb";
//			String owl_file_path = "src/OWL_files/college.owl";
//			String prefix = "ProtegeGenCode.CollegeProtege.impl.Default";
						
			File file = new File(owl_file_path);  
			OWLOntologyManager om = OWLManager.createOWLOntologyManager();
			OWLOntology o = om.loadOntologyFromOntologyDocument(file);
			Collection<OWLObjectProperty> owl_obj_props = o.getObjectPropertiesInSignature();
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(db_path);
			EntityManager em = emf.createEntityManager();
			
			persistData(o, db_path, em);
			
			if(owl_file_path == "src/OWL_files/college.owl") {
				inverseProperty(o, owl_obj_props, prefix, em);
				em.close();
				emf.close();
			}
			
			else {
				EntityManagerFactory emf1 = Persistence.createEntityManagerFactory(db_path);
				EntityManager em1 = emf1.createEntityManager();
				
				ArrayList<Object> emfs = new ArrayList<Object>();

				emfs = transitiveProperty(o, owl_obj_props, prefix, em, emf, db_path);
				symmetricProperty(o, owl_obj_props, prefix, (EntityManager)(emfs.get(1)), (EntityManagerFactory)(emfs.get(0)), db_path);
//				symmetricProperty(o, owl_obj_props, prefix, em, emf, db_path);
			}
			
		} catch(Exception e) {
			System.out.println("Exception from main: "+e.getMessage());
		}
	}


	public static void persistData(OWLOntology o, String db_path, EntityManager em) {
		try {
			Root2 root = new Root2(o);
			Class root_class = Root2.class;
			
//			CollegeProtege root = new CollegeProtege(o);
//			Class root_class = CollegeProtege.class;
		
			em.getTransaction().begin();
	        Method[] methods = root_class.getMethods();
	        
	        for (int i = 0; i < methods.length; i++) {
	            String methodName = methods[i].getName();
	            if (methodName.length()>=6 && "getAll".equals(methodName.substring(0,6))) {
	                Object returnVal = methods[i].invoke(root);
	                Collection<Object> returnCol = (Collection<Object>) returnVal;
	                if (returnVal instanceof Collection<?>) {
	                	for(Object obj: returnCol) { em.persist(obj); }
	                }
	            }
	        }
	        em.getTransaction().commit();
	        System.out.println("----------------------Persisting complete----------------------");

		}
		catch(Exception e) {
			System.out.println("Exception from presist data: "+e.getMessage());
		}
	}
	
	public static void inverseProperty(OWLOntology o, Collection<OWLObjectProperty> owl_obj_props, String prefix, EntityManager em) {
		try {
			for(OWLObjectProperty op: owl_obj_props) {
				Set<OWLInverseObjectPropertiesAxiom> set_inv = o.getInverseObjectPropertyAxioms(op);
				Set<OWLObjectPropertyDomainAxiom> set_inv_dom = o.getObjectPropertyDomainAxioms(op);
				Set<OWLObjectPropertyRangeAxiom> set_inv_range = o.getObjectPropertyRangeAxioms(op);
				
				if(!set_inv.isEmpty() && !set_inv_dom.isEmpty()) {
					String domain_iri = Iterables.getOnlyElement(set_inv_dom).getDomain().toString();
					String range_iri = Iterables.getOnlyElement(set_inv_range).getRange().toString();
					String source_property_iri = Iterables.getOnlyElement(set_inv_dom).getProperty().toString();
					String target_property_iri = (source_property_iri.equals(Iterables.getOnlyElement(set_inv).getFirstProperty().toString()) ? 
					Iterables.getOnlyElement(set_inv).getSecondProperty().toString() :
					Iterables.getOnlyElement(set_inv).getFirstProperty().toString()	);
					
					String domain = domain_iri.substring(domain_iri.indexOf("#")+"#".length(), domain_iri.length()-1);
					String range = range_iri.substring(range_iri.indexOf("#")+"#".length(), range_iri.length()-1);
					String source_property = source_property_iri.substring(source_property_iri.indexOf("#")+"#".length(),source_property_iri.length()-1);
					String target_property = target_property_iri.substring(target_property_iri.indexOf("#")+"#".length(),target_property_iri.length()-1);
					
					domain = domain.substring(0, 1).toUpperCase() + domain.substring(1);
					range = range.substring(0, 1).toUpperCase() + range.substring(1);
					source_property = source_property.substring(0, 1).toUpperCase() + source_property.substring(1);
					target_property = target_property.substring(0, 1).toUpperCase() + target_property.substring(1);
					
					System.out.println("----------------------InverseProperty----------------------");
					System.out.println("Domain Class: "+domain);
					System.out.println("Range Class: "+range);
					System.out.println("Source Property: "+source_property);
					System.out.println("Target Property: "+target_property);
					
					em.getTransaction().begin();
					Class domain_class = Class.forName(prefix+domain);
					Class range_class = Class.forName(prefix+range);
			
					
					List<Object> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", domain_class).getResultList();
					
					for(Object p: retrieve) {
					    Object p1 = em.find(domain_class, domain_class.getDeclaredMethod("getName").invoke(p));
	
						Collection<? extends Object> t = (Collection<? extends Object>) domain_class.getDeclaredMethod("get"+source_property).invoke(p);
				    	for(Object cour: t) {
				    		Object c1 = em.find(range_class, range_class.getDeclaredMethod("getName").invoke(cour));
				    		Class[] cArg = new Class[1];
							cArg[0] = Object.class;
							range_class.getDeclaredMethod("set"+target_property, cArg).invoke(c1, p1);	
				    	}
					}
					em.getTransaction().commit();
					System.out.println("--------------------------------------------");
			}
		}
		System.out.println("----------------------Inverse updates complete-------------------------------------");
		}
		catch(Exception e) {
			System.out.println("Exception from inverse: "+e.getMessage());
		}
	}
	
	public static ArrayList<Object> transitiveProperty(OWLOntology o, Collection<OWLObjectProperty> owl_obj_props, String prefix, EntityManager em, EntityManagerFactory emf, String db_path) {
		ArrayList<Object> res = new ArrayList<Object>();
		try {
			
			for(OWLObjectProperty op: owl_obj_props) {
				Set<OWLTransitiveObjectPropertyAxiom> set_trans = o.getTransitiveObjectPropertyAxioms(op);
				Set<OWLObjectPropertyDomainAxiom> set_trans_dom = o.getObjectPropertyDomainAxioms(op);

					
				if(!set_trans.isEmpty() && !set_trans_dom.isEmpty()) {					
					String domain_iri = Iterables.getOnlyElement(set_trans_dom).getDomain().toString();
					String property_iri = Iterables.getOnlyElement(set_trans_dom).getProperty().toString();
					
					String domain = domain_iri.substring(domain_iri.indexOf("#")+"#".length(), domain_iri.length()-1);
					String property = property_iri.substring(property_iri.indexOf("#")+"#".length(),property_iri.length()-1);
					property = property.substring(0, 1).toUpperCase() + property.substring(1);
					domain = domain.substring(0, 1).toUpperCase() + domain.substring(1);
					
					System.out.println("----------------------TransitiveProperty----------------------");
					System.out.println("Domain Class: "+domain);
					System.out.println("Property: "+property);
										
					Class domain_class = Class.forName(prefix+domain);
					
					List<Object> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", domain_class).getResultList();	
					Map<Object, HashSet<Object>> relationshipMap = new HashMap<>();
					for(Object retObject : retrieve) {
						HashSet<Object> ancestors = (HashSet<Object>) domain_class.getDeclaredMethod("get"+property).invoke(retObject);
						relationshipMap.put(retObject, ancestors);
					}
					
					for (Map.Entry<Object,HashSet<Object>> relation : relationshipMap.entrySet()) {
						Object object1 = relation.getKey();
						HashSet<Object> objects = relation.getValue();
						for(Object obj : objects) {
							if(relationshipMap.containsKey(obj)) {
							for(Object obj2 : relationshipMap.get(obj)) {
								if(obj2 != null) {
									objects.add(obj2);
								}
							}
							}
						}
					}

					em.getTransaction().begin();
			    	int deletedCount = em.createQuery("DELETE FROM Default"+domain).executeUpdate();
			    	em.getTransaction().commit();
			    	
			    	em.close();
			    	emf.close();
			    	
			    	EntityManagerFactory emf_new = Persistence.createEntityManagerFactory(db_path);
					EntityManager em_new = emf_new.createEntityManager();
					em_new.getTransaction().begin();
					for(Map.Entry<Object,HashSet<Object>> relation : relationshipMap.entrySet()) {
						for(Object obj : relation.getValue()) {
							Class[] cArg = new Class[1];
							cArg[0] = Object.class;
							domain_class.getDeclaredMethod("set"+property, cArg).invoke(relation.getKey(), obj);
						}
						em_new.persist(relation.getKey());
					}
					
					em_new.getTransaction().commit();	

					res.add(emf_new);
					res.add(em_new);
					System.out.println("--------------------------------------------");
				}
			}
			System.out.println("----------------------Transitive updates complete-------------------------------------");
			return res;
		}
		catch(Exception e) {
			System.out.println("Exception from transitive: "+e.getMessage());
		}
		return res;
	}
	
	public static void symmetricProperty(OWLOntology o, Collection<OWLObjectProperty> owl_obj_props, String prefix, EntityManager em, EntityManagerFactory emf, String db_path) {
		try {
			for(OWLObjectProperty op: owl_obj_props) {
				Set<OWLSymmetricObjectPropertyAxiom> set_symm = o.getSymmetricObjectPropertyAxioms(op);
				Set<OWLObjectPropertyDomainAxiom> set_symm_dom = o.getObjectPropertyDomainAxioms(op);
					
				if(!set_symm.isEmpty() && !set_symm_dom.isEmpty()) {
					
					String domain_iri = Iterables.getOnlyElement(set_symm_dom).getDomain().toString();
					String property_iri = Iterables.getOnlyElement(set_symm_dom).getProperty().toString();
					
					String domain = domain_iri.substring(domain_iri.indexOf("#")+"#".length(), domain_iri.length()-1);
					String property = property_iri.substring(property_iri.indexOf("#")+"#".length(),property_iri.length()-1);
					property = property.substring(0, 1).toUpperCase() + property.substring(1);
					domain = domain.substring(0, 1).toUpperCase() + domain.substring(1);
					
					System.out.println("----------------------SymmetricProperty----------------------");
					System.out.println("Domain Class: "+domain);
					System.out.println("Property: "+property);
											
					Class domain_class = Class.forName(prefix+domain);
					List<Object> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", domain_class).getResultList();	
					Map<Object, HashSet<Object>> relationshipMap = new HashMap<>();
					for(Object retObject : retrieve) {
						HashSet<Object> friends = (HashSet<Object>) domain_class.getDeclaredMethod("get"+property).invoke(retObject);
							relationshipMap.put(retObject, friends);	
					}

					for (Map.Entry<Object,HashSet<Object>> relation : relationshipMap.entrySet()) {
						Object object1 = relation.getKey();
						HashSet<Object> objects = relation.getValue();
						for(Object obj : objects) {
							if (relationshipMap.containsKey(obj))
							{
								if(!relationshipMap.get(obj).contains(object1)) {
									relationshipMap.get(obj).add(object1);
								}
							}
							else {
								HashSet<Object> friendssss = new HashSet<Object>();
								relationshipMap.put(obj, friendssss);
								relationshipMap.get(obj).add(object1);
							}
						}
					}
					
					em.getTransaction().begin();
			    	int deletedCount = em.createQuery("DELETE FROM Default"+domain).executeUpdate();
			    	em.getTransaction().commit();
			    	em.close();
			    	emf.close();
			    	
			    	EntityManagerFactory emf_new = Persistence.createEntityManagerFactory(db_path);
					EntityManager em_new = emf_new.createEntityManager();
					
					em_new.getTransaction().begin();
					for(Map.Entry<Object,HashSet<Object>> relation : relationshipMap.entrySet()) {
						for(Object obj : relation.getValue()) {
							Class[] cArg = new Class[1];
							cArg[0] = Object.class;
							domain_class.getDeclaredMethod("set"+property, cArg).invoke(relation.getKey(), obj);
						}
						em_new.persist(relation.getKey());
					}
					
					em_new.getTransaction().commit();	
					em_new.close();
					emf_new.close();
					System.out.println("--------------------------------------------");
				}
			}
			System.out.println("----------------------Symmetric updates complete-------------------------------------");
		}
		catch(Exception e) {
			System.out.println("Exception from symmetric: "+e.getMessage());
		}
	}
}



