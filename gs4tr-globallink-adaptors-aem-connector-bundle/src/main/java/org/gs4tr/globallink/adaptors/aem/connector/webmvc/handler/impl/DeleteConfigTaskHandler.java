package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.HandlerConstraints;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.AbstractTaskHandler;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.TaskHandler;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.AbstractTaskCommand;
import org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.TaskCommand;

@Component(immediate = true)
@Service(value = TaskHandler.class)
@Properties({
    @Property( name = HandlerConstraints.TASK_NAME,
	    value = HandlerConstraints.DELETE_CONFIG_TASK_NAME) })
public class DeleteConfigTaskHandler extends AbstractTaskHandler {

    public String doTask(TaskCommand taskCommand) {
	return null;
    }

    public Class<? extends AbstractTaskCommand> getTaskCommandClass() {
	return null;
    }

}