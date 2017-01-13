package org.gs4tr.globallink.adaptors.aem.connector.core.util.search;

import java.util.HashMap;
import java.util.Map;

import org.apache.jackrabbit.JcrConstants;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.TranslationContraints;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.PropertyConstraints;

import com.day.cq.search.PredicateGroup;

public class TranslationJobPredicateGroup {
    
    private static final String PATH_VALUE = "/content/projects";
        
    private static final String PROPERTY_NAME = "property";
    
    private static final String PROPERTY_VALUE_NAME = "property.value";
        
    private Map<String, String> parameters = new HashMap<String, String>();
        
    public PredicateGroup create(String jobIdentifier) {
	
	parameters.put(PropertyConstraints.PATH, PATH_VALUE);
	
	parameters.put(PropertyConstraints.TYPE, JcrConstants.NT_UNSTRUCTURED);
	
	parameters.put(PROPERTY_NAME, TranslationContraints.TRANSLATION_OBJECT_ID);
	
	parameters.put(PROPERTY_VALUE_NAME, jobIdentifier);
	
	return PredicateGroup.create(parameters);
    }
        
}