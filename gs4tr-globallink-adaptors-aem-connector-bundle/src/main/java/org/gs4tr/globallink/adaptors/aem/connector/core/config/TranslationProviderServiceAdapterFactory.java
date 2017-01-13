package org.gs4tr.globallink.adaptors.aem.connector.core.config;

import java.net.URL;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.gs4tr.globallink.adaptors.aem.connector.i18n.Messages;
import org.gs4tr.projectdirector.ws.client.DocumentServiceImpl;
import org.gs4tr.projectdirector.ws.client.ProjectServiceImpl;
import org.gs4tr.projectdirector.ws.client.ServiceLocator;
import org.gs4tr.projectdirector.ws.client.SubmissionServiceImpl;
import org.gs4tr.projectdirector.ws.client.TargetServiceImpl;

@Component(metatype = false)
@Service(value = AdapterFactory.class)
public class TranslationProviderServiceAdapterFactory implements AdapterFactory {

    @Property(name = "adapters")
    public static final String[] ADAPTER_CLASSES = {
	ServiceLocator.class.getName(),
	DocumentServiceImpl.class.getName(),
	ProjectServiceImpl.class.getName(),
	SubmissionServiceImpl.class.getName(),
	TargetServiceImpl.class.getName()
    };
    
    @Property(name = "adaptables")
    public static final String[] ADAPTABLE_CLASSES = {
	TranslationProviderConfig.class.getName()
    };
    
    
    @SuppressWarnings("unchecked")
    public <T> T getAdapter(Object object, Class<T> type) {
	
	if (object instanceof TranslationProviderConfig) {
	
	    TranslationProviderConfig config = (TranslationProviderConfig) object;
	    
	    // Adapt to service locator
	    if (type == ServiceLocator.class) {
		ServiceLocator serviceLocator = getServiceLocator(config);
		if (serviceLocator != null) {
		    return (T) serviceLocator;
		}
	    }
	    
	    // Adapt to document service
	    if (type == DocumentServiceImpl.class) {
		ServiceLocator serviceLocator = getServiceLocator(config);
		if (serviceLocator != null) {
		    return (T) serviceLocator.getDocumentService();
		}
	    }
	    
	    // Adapt to project service
	    if (type == ProjectServiceImpl.class) {
		ServiceLocator serviceLocator = getServiceLocator(config);
		if (serviceLocator != null) {
		    return (T) serviceLocator.getProjectService();
		}
	    }
	    
	    // Adapt to submission service
	    if (type == SubmissionServiceImpl.class) {
		ServiceLocator serviceLocator = getServiceLocator(config);
		if (serviceLocator != null) {
		    return (T) serviceLocator.getSubmissionService();
		}
	    }
	    
	    // Adapt to target service
	    if (type == TargetServiceImpl.class) {
		ServiceLocator serviceLocator = getServiceLocator(config);
		if (serviceLocator != null) {
		    return (T) serviceLocator.getTargetService();
		}
	    }
	}
	
	return null;
    }
    
    private ServiceLocator getServiceLocator(TranslationProviderConfig config) {
	
	URL webServicesURL = config.getWebServicesURL();
	if (webServicesURL != null) {
	    
	    String userName = config.getUserName();
	    if (userName != null && !userName.isEmpty()) {
		
		String userPassword = config.getUserPassword();
		if (userPassword != null && !userPassword.isEmpty()) {
		    
		    return new ServiceLocator(webServicesURL,
			    30000, userName, userPassword,
			    Boolean.TRUE);
		}
		
		throw new RuntimeException(Messages
			.getString("TranslationProviderServiceAdapterFactory.2"));
	    }
	    
	    throw new RuntimeException(Messages
		    .getString("TranslationProviderServiceAdapterFactory.1"));
	}
	
	throw new RuntimeException(Messages
		.getString("TranslationProviderServiceAdapterFactory.0"));
    }
    
}