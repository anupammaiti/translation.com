package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.provider;

import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.TaskHandler;

public interface TaskHandlerProvider {
    
    TaskHandler getTaskHandler(String taskName);
    
}