package org.gs4tr.globallink.adaptors.aem.connector.model.adapter.factory;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.model.adapter.impl.AemServiceLocatorAdapter;
import org.gs4tr.projectdirector.ws.client.ServiceLocator;

@Component(metatype = false)
@Service(value = AdapterFactory.class)
public class ServiceLocatorAdapterFactory implements AdapterFactory {
    
    @Property(name = "adapters")
    public static final String[] ADAPTER_CLASSES = {
	ServiceLocator.class.getName()
    };
    
    @Property(name = "adaptables")
    public static final String[] ADAPTABLE_CLASSES = {
	AemConnection.class.getName(),
	Resource.class.getName()
    };
    
    
    public <T> T getAdapter(Object object, Class<T> type) {
	
	if (object != null) {
	    
	    if (object instanceof AemConnection) {
		
		return getAdapter((AemConnection) object, type);
	    }
	    
	    if (object instanceof Resource) {
		
		return getAdapter((Resource) object, type);
	    }
	}

	return null;
    }
    
    @SuppressWarnings("unchecked")
    private <T> T getAdapter(AemConnection aemConnection, Class<T> type) {
	
	if (ServiceLocator.class == type) {
	    
	    return (T) new AemServiceLocatorAdapter().adaptFromConnection(aemConnection);
	}
	
	return null;
    }
    
    private <T> T getAdapter(Resource resource, Class<T> type) {
	
	if (ServiceLocator.class == type) {
	    
	    AemConnection aemConnection = resource.adaptTo(AemConnection.class);
	    if (aemConnection != null) {
		
		return getAdapter(aemConnection, type);
	    }
	}
	
	return null;
    }
    
}