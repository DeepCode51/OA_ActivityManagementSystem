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
<form method="post" action="process-complete.action" id="auditForm">
	<div id="main">
        
        <div class="where">
            <ul>
            </ul>
        </div>
        
      <div class="sort_switch">
          <ul id="TabsNav">
          	  <li class="selected"><a href="#">审批</a></li>
          </ul>
      </div>
      
      <div id="tagContent0" class="sort_content">
			<input type="hidden" value="${taskId}" name="taskId"/>
        	<div class="currency_area hue9">
            	<div class="the_content">
                	<table class="tableHue2" width="100%" border="1" bordercolor="#dddddd" cellspacing="0" cellpadding="0">
                      <tbody>
                      	<c:forEach items="${formFields}" var="ff">
                        <tr>
                          <td width="15%" class="title1">${ff.filedText}：</td>
                          <td class="left">${ff.fieldValue}</td>
                        </tr>
                        </c:forEach>
                        <tr>
                          <td width="15%" class="title1">评论：</td>
                          	<td class="left">
                          		
								<table>
									<c:forEach var="comment" items="${comments}">
									<tr>
										<td>${comment.userName}：2012-03-03</td>
									</tr>
									<tr>
										<td>${comment.content}</td>
									</tr>
									</c:forEach>
								</table>
							</td>
                        </tr>
                        <tr>
                          <td width="15%" class="title1">我的意见：</td>
                          	<td class="left">
								<textarea cols="33" rows="5" name="content"></textarea>
							</td>
                        </tr>
                      </tbody>
                  </table>
				  
                </div>
            </div>
            
            <div class="fun_area" style="text-align:center;"><input type="submit" value="确 定" class="input_button1"/>
            	<!-- 老板组显示该按钮  -->
	            <c:if test="${sessionScope.group.type == 'boss'}">
	            	<input type="button" value="取 消" class="input_button1" onClick="cancelAdjust()"/>
	            </c:if>
            </div>

			
      </div>
</div>
</form>

<script>
	function cancelAdjust() {
		document.getElementById("auditForm").action = "process-cancelAdjust.action";
		document.getElementById("auditForm").submit();
	}
</script>

</body>


</html>
