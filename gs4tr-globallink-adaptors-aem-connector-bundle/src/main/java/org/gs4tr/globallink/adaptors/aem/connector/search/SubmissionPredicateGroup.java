package org.gs4tr.globallink.adaptors.aem.connector.search;

import java.util.HashMap;
import java.util.Map;

import org.apache.jackrabbit.JcrConstants;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemSubmission;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemSubmissionInfo;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;

import com.day.cq.search.PredicateGroup;

public class SubmissionPredicateGroup {
    
    private static final String PATH = "path";
    
    private static final String TYPE = "type";
    
    private static final String PRPERTY_1 = "1_property";
    private static final String PRPERTY_VALUE_1 = "1_property.value";
    
    private static final String PRPERTY_2 = "2_property";
    private static final String PRPERTY_VALUE_2 = "2_property.value";
    
    private Map<String, String> _parameters;
    
    public SubmissionPredicateGroup() {
	
	_parameters = new HashMap<String, String>();
	
	_parameters.put(PATH, StringConstraints.SUBMISSIONS_PATH);
	_parameters.put(TYPE, JcrConstants.NT_UNSTRUCTURED);
    }
    
    public PredicateGroup create(String connectionPath, String submissionStatus) {
	
	_parameters.put(PRPERTY_1, AemSubmission.CONNECTION_PATH);
	_parameters.put(PRPERTY_VALUE_1, connectionPath);

	_parameters.put(PRPERTY_2, AemSubmissionInfo.AEM_TRANSLATION_STATUS);
	_parameters.put(PRPERTY_VALUE_2, submissionStatus);
	
	return PredicateGroup.create(getParameters());
    }

    private Map<String, String> getParameters() {
        return _parameters;
    }
    
}