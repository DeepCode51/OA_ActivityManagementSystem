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
          	  <li class="" id="vacation"><a href="${ctx}/process-listProcessInstance.action?processType=vacation">请假申请</a></li>
          	  <li class="" id="salary"><a href="${ctx}/process-listProcessInstance.action?processType=salary">薪资调整申请</a></li>
          	  <li class="" id="expense"><a href="${ctx}/process-listProcessInstance.action?processType=expense">报销申请</a></li>
          </ul>
      </div>
      
      <div class="sort_content">
          <table class="tableHue1" width="100%" border="1" bordercolor="#a4d5e3" cellspacing="0" cellpadding="0">
              <thead>
                <tr>
                  <td width="30"><strong>标题</strong></td>
                  <td width="20%"><strong>创建时间 </strong></td>
                  <td width="10%"><strong>操作 </strong></td>
                </tr>
              </thead>
              <tbody id="tbody">
              <c:forEach var="pro" items="${processes}">
                <tr>
                  <td>${pro.title}</td>
                  <td>${pro.requestDate}</td>
                  <td><a href="${ctx}/pages/diagram.jsp?processInstanceId=${pro.id}">查看</a></td>
                </tr>
              </c:forEach>
              </tbody>
          </table>
      </div>

      
</div>
    
</body>
<script>
	var processType = "${processType}";
	$("#" + processType).attr("class", "selected");
</script>
</html>
