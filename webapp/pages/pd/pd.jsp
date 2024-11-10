<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/main.css" type="text/css" />


</head>

<body>

	<div id="main">
        
      <div class="sort_switch">
          <ul id="TabsNav">
          	  <li class="selected"><a href="#">流程定义 </a></li>
          </ul>
      </div>
      
      <div class="sort_content">
          <table class="tableHue1" width="100%" border="1" bordercolor="#a4d5e3" cellspacing="0" cellpadding="0">
              <thead>
                <tr>
                  <td width="10%"><strong>名称</strong></td>
                  <td width="15%"><strong>Key</strong></td>
                  <td width="15%"><strong>流程ID </strong></td>
                  <td width="15%"><strong>流程文件</strong></td>
                  <td width="15%"><strong>流程图</strong></td>
                  <td width="20%"><strong>状态</strong></td>
                </tr>
              </thead>
              <tbody id="">
              	<c:forEach items="${defs}" var="def">
                <tr>
                  <td>${def.name}</td>
                  <td>${def.key}</td>
                  <td>${def.id}</td>
                  <td>${def.resourceName}</td>
                  <td>${def.diagramResourceName}</td>
                  <td>
	                  <c:if test="${def.suspended == true}">
	                  	<img src="${ctx}/images/check_error.gif" title="中止"/>
	                  </c:if>
	                  <c:if test="${def.suspended == false}">
	                  	<img src="${ctx}/images/icon_next2.gif" title="激活"/>
	                  </c:if>
                  </td>
                </tr>
                </c:forEach>
              </tbody>
          </table>
      </div>

      
	</div>
    
</body>

</html>
