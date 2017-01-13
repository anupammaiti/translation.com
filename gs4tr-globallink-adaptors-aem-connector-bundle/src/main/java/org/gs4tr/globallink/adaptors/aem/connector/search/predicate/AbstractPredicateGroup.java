package org.gs4tr.globallink.adaptors.aem.connector.search.predicate;

import java.util.HashMap;
import java.util.Map;

import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;

import com.day.cq.search.PredicateGroup;

public abstract class AbstractPredicateGroup {
    
    private static final String PROPERTY = "property";
    
    private static final String PROPERTY_VALUE = "property.value";
    
    private Map<String, String> parameters = new HashMap<String, String>();

    public PredicateGroup create(String name, String value) {
	
	parameters.put(PROPERTY, name);	
	parameters.put(PROPERTY_VALUE, value);
	
	return PredicateGroup.create(parameters);
    }
    
    public PredicateGroup create(String[] names, String[] values) {
	
	for (int index = 0; index < names.length; index++) {
	    
	    String prefix = String.valueOf(index + 1);	    
	    prefix = prefix.concat(StringConstraints.UNDERSCORE);
	    
	    parameters.put(prefix.concat(PROPERTY), names[index]);	    
	    parameters.put(prefix.concat(PROPERTY_VALUE), values[index]);
	}
	
	return PredicateGroup.create(parameters);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
    
}