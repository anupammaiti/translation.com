package org.gs4tr.globallink.adaptors.aem.connector.model.adapter.factory;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemSubmission;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemSubmissionTarget;
import org.gs4tr.globallink.adaptors.aem.connector.model.adapter.impl.AemConnectionAdapter;
import org.gs4tr.globallink.adaptors.aem.connector.model.adapter.impl.AemSubmissionAdapter;
import org.gs4tr.globallink.adaptors.aem.connector.model.adapter.impl.AemSubmissionTargetAdapter;

@Component(metatype = false)
@Service(value = AdapterFactory.class)
public class ModelAdapterFactory implements AdapterFactory {
    
    @Property(name = "adapters")
    public static final String[] ADAPTER_CLASSES = {	
	AemConnection.class.getName(),	
	AemSubmission.class.getName(),
	AemSubmissionTarget.class.getName()
    };
    
    @Property(name = "adaptables")
    public static final String[] ADAPTABLE_CLASSES = {
	Resource.class.getName()
    };
    
    
    public <T> T getAdapter(Object object, Class<T> type) {
	
	if (object instanceof Resource) {
	    
	    return getAdapter((Resource) object, type);
	}
	
	return null;
    }
    
    @SuppressWarnings("unchecked")
    private <T> T getAdapter(Resource resource, Class<T> type) {
		
	if (AemConnection.class == type) {
	    return (T) new AemConnectionAdapter().adaptFromResource(resource);
	}
		
	if (AemSubmission.class == type) {
	    return (T) new AemSubmissionAdapter().adaptFromResource(resource);
	}
	
	if (AemSubmissionTarget.class == type) {
	    return (T) new AemSubmissionTargetAdapter().adaptFromResource(resource);
	}
	
	return null;
    }

}