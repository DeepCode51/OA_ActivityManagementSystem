package org.crazyit.activiti.oa.dao.impl;

import java.util.List;

import org.crazyit.activiti.oa.dao.ApplicationDao;
import org.crazyit.activiti.oa.entity.ExpenseAccount;
import org.crazyit.activiti.oa.entity.SalaryAdjust;
import org.crazyit.activiti.oa.entity.Vacation;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ApplicationDaoImpl implements ApplicationDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void saveVacation(Vacation vac) {
		Session session = sessionFactory.openSession();
		session.save(vac);
	}

	public List<Vacation> listVacation(String userId) {
		Session session = sessionFactory.openSession();
		String hql = "from Vacation as vac where vac.userId=:userId";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		return query.list();
	}

	public void saveSalaryAdjust(SalaryAdjust salary) {
		Session session = sessionFactory.openSession();
		session.save(salary);
	}

	public List<SalaryAdjust> listSalaryAdjust(String userId) {
		String hql = "from SalaryAdjust sa where sa.userId = :userId";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		return query.list();
	}

	public void saveExpenseAccount(ExpenseAccount account) {
		Session session = sessionFactory.openSession();
		session.save(account);
	}

	public List<ExpenseAccount> listExpenseAccount(String userId) {
		String hql = "from ExpenseAccount ea where ea.userId = :userId";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		return query.list();
	}

}
