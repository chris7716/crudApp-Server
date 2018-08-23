package com.ca.server.entity;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ca.server.models.RegistratoinEM;

import com.ca.server.util.HibernateUtil;

public class RegistrationFactory {
	
	public void saveRegistration(RegistratoinEM registration){
		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			session.saveOrUpdate(registration);
			tx.commit();
		}catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	    } finally{
			session.close();
		}
		
	}
	
	public List<RegistratoinEM> getAllRegistrations() throws Exception{
		
		Session session = null;
		List<RegistratoinEM> registrationsList = null;
		
		try {
			
			SessionFactory factory = HibernateUtil.getSessionFactory();
			session = factory.openSession();
			Transaction tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(RegistratoinEM.class);
			criteria.add(Restrictions.like("isDeleted", 0));
			
			registrationsList = criteria.list();
			
		} catch (Exception e) {
			throw new Exception();
		}finally {
	    	session.flush();
			session.close();
		}
		
		return registrationsList;
		
	}

}
