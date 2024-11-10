package org.crazyit.activiti.oa.service;

import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;

/**
 * 流程定义服务
 * @author yangenxiong
 *
 */
public interface ProcessDefinitionService {

	/**
	 * 查询全部的流程定义
	 * @return
	 */
	List<ProcessDefinition> list();
}
