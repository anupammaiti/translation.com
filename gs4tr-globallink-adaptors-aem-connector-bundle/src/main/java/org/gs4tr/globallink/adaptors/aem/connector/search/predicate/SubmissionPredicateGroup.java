package org.gs4tr.globallink.adaptors.aem.connector.search.predicate;

import org.apache.jackrabbit.JcrConstants;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.PropertyConstraints;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;

public class SubmissionPredicateGroup extends AbstractPredicateGroup {
        
    public SubmissionPredicateGroup() {
	
	super.getParameters().put(
		PropertyConstraints.PATH,
		StringConstraints.SUBMISSIONS_PATH);
	
	super.getParameters().put(
		PropertyConstraints.TYPE,
		JcrConstants.NT_UNSTRUCTURED);
    }
        
}