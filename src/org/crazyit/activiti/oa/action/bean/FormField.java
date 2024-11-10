package org.crazyit.activiti.oa.action.bean;

import java.io.Serializable;

public class FormField implements Serializable {

	// 表单域的文本
	private String filedText;
	
	// 表单域的值
	private String fieldValue;

	public FormField(String filedText, String fieldValue) {
		this.filedText = filedText;
		this.fieldValue = fieldValue;
	}

	public String getFiledText() {
		return filedText;
	}

	public void setFiledText(String filedText) {
		this.filedText = filedText;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
}
