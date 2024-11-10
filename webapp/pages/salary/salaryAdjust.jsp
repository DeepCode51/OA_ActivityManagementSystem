<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站后台管理</title>

<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/main.css" type="text/css" />


</head>

<body>
<form method="post" action="process-startSalaryAdjust.action">
	<div id="main">
        
        <div class="where">
            <ul>
            </ul>
        </div>
        
      <div class="sort_switch">
          <ul id="TabsNav">
          	  <li class="selected"><a href="#">薪资调整申请</a></li>
          </ul>
      </div>
      
      <div id="tagContent0" class="sort_content">
        	  <span style="color:red; height: 30px;">${errorMsg}</span>
        	<div class="currency_area hue9">
            	<div class="the_content">
					<input name="salaryForm.userId" type="hidden" value="${sessionScope.user.id}"/>
					<input name="salaryForm.userName" type="hidden" value="${sessionScope.user.lastName}"/>
                	<table class="tableHue2" width="100%" border="1" bordercolor="#dddddd" cellspacing="0" cellpadding="0">
                      <tbody>
                        <tr>
							<td width="15%" class="title1">输入员工姓名：</td>
							<td class="left"><input name="salaryForm.employeeName" type="text" class="input_text2" size="30" value="${salaryForm.employeeName}"/></td>
                        </tr>
                        <tr>
							<td width="15%" class="title1">调薪金额 ：</td>
							<td class="left"><input name="salaryForm.money" type="text" class="input_text2" size="30" value="${salaryForm.money}"/></td>
                        </tr>
                        <tr>
                          <td width="15%" class="title1">描述：</td>
                          	<td class="left">
								<textarea cols="33" rows="5" name="salaryForm.dscp">${salaryForm.dscp}</textarea>
							</td>
                        </tr>
                      </tbody>
                  </table>
				  
                </div>
            </div>
            
            <div class="fun_area" style="text-align:center;"><input type="submit" value="确 定" class="input_button1"/></div>

      </div>
</div>
</form>

</body>

</html>
