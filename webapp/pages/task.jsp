<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/main.css" type="text/css" />

<script type="text/javascript" src="${ctx}/js/jquery-1.4.2.min.js"></script>

</head>

<body>

	<div id="main">
        
      <div class="sort_switch">
          <ul id="TabsNav">
          	  <li class="" id="candidate"><a href="${ctx}/process-listTask.action?taskType=candidate">待办的任务</a></li>
          	  <li class="" id="assignee"><a href="${ctx}/process-listTask.action?taskType=assignee">受理的任务</a></li>
          </ul>
      </div>
      
      <div class="sort_content">
          <table class="tableHue1" width="100%" border="1" bordercolor="#a4d5e3" cellspacing="0" cellpadding="0">
              <thead>
                <tr>
                  <td width="40%"><strong>标题</strong></td>
                  <td width="15%"><strong>申请时间</strong></td>
                  <td width="10%"><strong>操作</strong></td>
                </tr>
              </thead>
              <tbody id="">
              	<c:forEach items="${tasks}" var="task">
                <tr>
                  <td>${task.title}</td>
                  <td>${task.requestDate}</td>
                  <td> 
                  <c:if test="${taskType == 'assignee'}">
                  	<a href="${ctx}/process-perform.action?taskId=${task.taskId}">办理</a>
                  </c:if>
                  <c:if test="${taskType == 'candidate'}">
                  	<a href="${ctx}/process-claim.action?taskId=${task.taskId}">领取</a>
                  </c:if>
                  	<a href="${ctx}/pages/diagram.jsp?processInstanceId=${task.processInstanceId}">查看</a>
                  </td>
                </tr>
                </c:forEach>
              </tbody>
          </table>
      </div>

      
	</div>
    
</body>
<script>
	var taskType = "${taskType}";
	$("#" + taskType).attr("class", "selected");
</script>
</html>
