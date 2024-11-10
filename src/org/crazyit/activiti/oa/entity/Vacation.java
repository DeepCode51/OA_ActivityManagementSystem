package org.crazyit.activiti.oa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 休假对象
 * @author yangenxiong
 *
 */
@Entity
@Table(name = "OA_VACATION")
public class Vacation {	
	// 带薪假
	public final static int TYPE_PAID = 0;	
	// 病假
	public final static int TYPE_SICK = 1;	
	// 事假
	public final static int TYPE_MATTER = 2;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private int id;

	// 休假的工作日
	@Column(name = "WORK_DAYS")
	private int days;
	
	// 开始日期
	@Column(name = "BEGIN_DATE")
	private Date beginDate;
	
	// 结束日期
	@Column(name = "END_DATE")
	private Date endDate;
	
	// 休假类型
	@Column(name = "VAC_TYPE")
	private int vacationType;
	
	//原因
	@Column(name = "REASON")
	private String reason;
	
	// 对应的流程实例id
	@Column(name = "PROC_INST_ID")
	private String processInstanceId;
	
	// 用户id
	@Column(name = "USER_ID")
	private String userId;

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
