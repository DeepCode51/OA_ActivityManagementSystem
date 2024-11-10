package org.crazyit.activiti.oa.action.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.crazyit.activiti.oa.entity.Vacation;
import org.crazyit.activiti.oa.util.DateUtil;

/**
 * 请假表单
 * @author yangenxiong
 *
 */
public class VacationForm extends BaseForm {

	// 休假开始日期
	private String startDate;
	
	// 休假结束日期
	private String endDate;
	
	// 天数
	private int days;
	
	// 类型
	private int vacationType;
	
	// 原因
	private String reason;
		
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getVacationType() {
		return vacationType;
	}

	public void setVacationType(int vacationType) {
		this.vacationType = vacationType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void createFormFields(List<FormField> fields) {
		fields.add(super.getFormField("startDate", "请假开始日期", startDate));
		fields.add(super.getFormField("endDate", "请假结束日期", endDate));
		fields.add(super.getFormField("days", "休假天数", String.valueOf(days)));
		fields.add(super.getFormField("vacationType", "请假类型", getVacationType(this.vacationType)));
		fields.add(super.getFormField("reason", "原因", reason));
	}

	private String getVacationType(int vacationType) {
		if (Vacation.TYPE_MATTER == vacationType) {
			return "事假";
		} else if (Vacation.TYPE_PAID == vacationType) {
			return "年假";
		} else if (Vacation.TYPE_SICK == vacationType) {
			return "病假";
		}
		return "";
	}
	
}
