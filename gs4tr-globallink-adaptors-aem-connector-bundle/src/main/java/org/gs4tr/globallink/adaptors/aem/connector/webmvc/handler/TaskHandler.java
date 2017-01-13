package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler;

import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.AbstractTaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.TaskCommand;

public interface TaskHandler {
    
    String doTask(TaskCommand taskCommand);
    
    String getResponseContentDisposition();
    
    String getResponseContentType();
    
    Class<? extends AbstractTaskCommand> getTaskCommandClass();
    
}