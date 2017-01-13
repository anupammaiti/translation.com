package org.gs4tr.globallink.adaptors.aem.connector.core.util.search;

import java.util.HashMap;
import java.util.Map;

import org.apache.jackrabbit.JcrConstants;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.PropertyConstraints;

import com.day.cq.search.PredicateGroup;

public class TranslationObjectPredicateGroup {
    
    private static final String PATH_VALUE = "/content/projects";
    
    private static final String PROPERTY_1 = "sling:resourceType";
    
    private static final String PROPERTY_1_NAME = "1_property";
    
    private static final String PROPERTY_1_VALUE = "cq/gui/components/projects/admin/card/translation_object";
    
    private static final String PROPERTY_1_VALUE_NAME = "1_property.value";
    
    private static final String PROPERTY_2 = "../../translationObjectID";
    
    private static final String PROPERTY_2_NAME = "2_property";
    
    private static final String PROPERTY_2_VALUE_NAME = "2_property.value";
        
    private static final String PROPERTY_3_NAME = "3_property";
    
    private static final String PROPERTY_3_VALUE_NAME = "3_property.value";
        
    private Map<String, String> parameters = new HashMap<String, String>();
    
    public PredicateGroup create(String jobIdentifier, String objIdentifier) {
	
	parameters.put(PropertyConstraints.PATH, PATH_VALUE);
	
	parameters.put(PropertyConstraints.TYPE, JcrConstants.NT_UNSTRUCTURED);
	
	parameters.put(PROPERTY_1_NAME, PROPERTY_1);
	
	parameters.put(PROPERTY_1_VALUE_NAME, PROPERTY_1_VALUE);
	
	parameters.put(PROPERTY_2_NAME, PROPERTY_2);
	
	parameters.put(PROPERTY_2_VALUE_NAME, jobIdentifier);
	
//	parameters.put(PROPERTY_3_NAME, TranslationContraints.TRANSLATION_OBJECT_ID);
	parameters.put(PROPERTY_3_NAME, "sourcePath");
	
	parameters.put(PROPERTY_3_VALUE_NAME, objIdentifier);
	
	return PredicateGroup.create(parameters);
    }
    
}