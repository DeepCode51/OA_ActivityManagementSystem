package org.crazyit.activiti.oa.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 薪资调整记录
 * @author yangenxiong
 *
 */
@Entity
@Table(name = "OA_SALARY_ADJUST")
public class SalaryAdjust {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Integer id;
	
	// 用户id
	@Column(name = "USER_ID")
	private String userId;
	
	//调整金额
	@Column(name = "ADJUST_MONEY", scale= 2)
	private BigDecimal adjustMoney;
	
	// 日期
	@Column(name = "DATE")
	private Date date;
	
	// 描述
	@Column(name = "DSCP")
	private String dscp;
	
	// 流程实例id
	@Column(name = "PROC_INST_ID")
	private String processInstanceId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getAdjustMoney() {
		return adjustMoney;
	}

	public void setAdjustMoney(BigDecimal adjustMoney) {
		this.adjustMoney = adjustMoney;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDscp() {
		return dscp;
	}

	public void setDscp(String dscp) {
		this.dscp = dscp;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
}
