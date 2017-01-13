package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter.factory;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.adapter.AdapterFactory;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.TaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter.impl.DeleteConfigTaskCommandAdapter;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter.impl.PullUserProjectsTaskCommandAdapter;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter.impl.SaveConnectionConfigTaskCommandAdapter;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl.DeleteConfigTaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl.PullUserProjectsTaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.impl.SaveConnectionConfigTaskCommand;

@Component(metatype = false)
@Service(value = AdapterFactory.class)
public class TaskCommandAdapterFactory implements AdapterFactory {
    
    @Property(name = "adapters")
    public static final String[] ADAPTER_CLASSES = {
	DeleteConfigTaskCommand.class.getName(),
	PullUserProjectsTaskCommand.class.getName(),
	SaveConnectionConfigTaskCommand.class.getName()
    };
    
    @Property(name = "adaptables")
    public static final String[] ADAPTABLE_CLASSES = {
	SlingHttpServletRequest.class.getName()
    };
    
    @SuppressWarnings("unchecked")
    public <T> T getAdapter(Object object, Class<T> type) {
	
	TaskCommand taskCommand = null;
	
	if (object != null && object instanceof SlingHttpServletRequest) {
	    
	    SlingHttpServletRequest request = (SlingHttpServletRequest) object;
	    
	    if (DeleteConfigTaskCommand.class == type) {
		
		taskCommand = new DeleteConfigTaskCommandAdapter().adaptFromServletRequest(request);
		
	    } else if (PullUserProjectsTaskCommand.class == type) {
		
		taskCommand = new PullUserProjectsTaskCommandAdapter().adaptFromServletRequest(request);
	    
	    } else if (SaveConnectionConfigTaskCommand.class == type) {
		
		taskCommand = new SaveConnectionConfigTaskCommandAdapter().adaptFromServletRequest(request);
	    }
	    
	    if (taskCommand != null) {
		
		taskCommand.setResourceResolver(request.getResourceResolver());
	    }
	}
	
	return (T) taskCommand;
    }

}