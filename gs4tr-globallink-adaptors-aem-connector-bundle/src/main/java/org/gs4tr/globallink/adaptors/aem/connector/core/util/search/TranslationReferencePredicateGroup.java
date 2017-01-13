package org.gs4tr.globallink.adaptors.aem.connector.core.util.search;

import java.util.HashMap;
import java.util.Map;

import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.PropertyConstraints;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;

public class TranslationReferencePredicateGroup {
    
    private static final String PATH_VALUE = "/apps/globallink-adaptor/connector/submissions";
    
    private static final String PROPERTY = "property";
    
    private static final String PROPERTY_VALUE = "property.value";
    
    private Map<String, String> parameters = new HashMap<String, String>();
    
    public PredicateGroup create(String property, String propertyValue) {
	
	parameters.put(PropertyConstraints.PATH, PATH_VALUE);
	
	parameters.put(PropertyConstraints.TYPE, JcrConstants.NT_UNSTRUCTURED);
	
	parameters.put(PROPERTY, property);
	
	parameters.put(PROPERTY_VALUE, propertyValue);
	
	return PredicateGroup.create(parameters);
    }
    
}