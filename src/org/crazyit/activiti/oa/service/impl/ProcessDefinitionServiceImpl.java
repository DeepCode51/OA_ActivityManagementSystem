package org.crazyit.activiti.oa.service.impl;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.crazyit.activiti.oa.service.ProcessDefinitionService;

public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {
	
	private RepositoryService repositoryService;
	
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public List<ProcessDefinition> list() {
		return this.repositoryService.createProcessDefinitionQuery().list();
	}

	
}
