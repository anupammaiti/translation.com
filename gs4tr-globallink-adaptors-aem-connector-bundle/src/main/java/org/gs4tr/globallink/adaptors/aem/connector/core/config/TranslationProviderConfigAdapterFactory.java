package org.gs4tr.globallink.adaptors.aem.connector.core.config;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;

import com.adobe.granite.crypto.CryptoException;
import com.adobe.granite.crypto.CryptoSupport;

@Component(metatype = false)
@Service(value = AdapterFactory.class)
public class TranslationProviderConfigAdapterFactory implements AdapterFactory {
    
    @Property(name = "adapters")
    public static final String[] ADAPTER_CLASSES = { TranslationProviderConfig.class.getName() };
    
    @Property(name = "adaptables")
    public static final String[] ADAPTABLE_CLASSES = { Resource.class.getName() };
    
    @Reference
    private CryptoSupport cryptoSupportService;
    
    
    @SuppressWarnings("unchecked")
    public <T> T getAdapter(Object object, Class<T> type) {
	
	if (object instanceof Resource) {
	    
	    Resource resource = (Resource) object;
	    
	    if (type == TranslationProviderConfig.class) {
		
		TranslationProviderConfigAdapter adapter = new TranslationProviderConfigAdapter();
		
		TranslationProviderConfig config = adapter.adaptFromResource(resource);
		
		if (config != null) {
		    
		    try {
			
			String pdUserPassword = cryptoSupportService.unprotect(config.getUserPassword());
			config.setUserPassword(pdUserPassword);
			
			return (T) config;
		    
		    } catch (CryptoException exception) {
			exception.printStackTrace();
		    }
		    
		}
	    }
	}
	
	return null;
    }

}