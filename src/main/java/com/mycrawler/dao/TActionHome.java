package com.mycrawler.dao;

// Generated 2013-5-19 21:37:51 by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.mycrawler.pojo.TAction;

/**
 * Home object for domain model class TAction.
 * @see pojo.TAction
 * @author Hibernate Tools
 */
public class TActionHome  extends HibernateDaoBase {

	private static final Log log = LogFactory.getLog(TActionHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();


	public void persist(TAction transientInstance) {
		log.debug("persisting TAction instance");
		try {
			this.getHibernateTemplate().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TAction instance) {
		log.debug("attaching dirty TAction instance");
		try {
			this.getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TAction instance) {
		log.debug("attaching clean TAction instance");
		try {
			this.getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TAction persistentInstance) {
		log.debug("deleting TAction instance");
		try {
			this.getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TAction merge(TAction detachedInstance) {
		log.debug("merging TAction instance");
		try {
			TAction result = (TAction) this.getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TAction findById(java.lang.Integer id) {
		log.debug("getting TAction instance with id: " + id);
		try {
			TAction instance = (TAction) this.getHibernateTemplate()
					.get("pojo.TAction", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*public List findByExample(TAction instance) {
		log.debug("finding TAction instance by example");
		try {
			List results = this.getHibernateTemplate()
					.createCriteria("pojo.TAction")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}*/
}
