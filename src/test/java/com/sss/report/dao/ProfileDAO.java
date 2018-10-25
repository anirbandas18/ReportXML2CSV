package com.sss.report.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.sss.report.core.HibernateUtil;
import com.sss.report.entity.ProfileEntity;

public class ProfileDAO {
	
	public Boolean create(ProfileEntity profile) {
		SessionFactory sessionfactory = HibernateUtil.getSessionFactory();
		Session session = sessionfactory.getCurrentSession();
		Transaction tx = null;
		Boolean flag = false;
		try {
			tx = session.beginTransaction();
			String before = profile.getName();
			session.save(profile);
			ProfileEntity after = session.get(ProfileEntity.class, before);
			flag = before.equalsIgnoreCase(after.getName());
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		return flag;
	}

}
