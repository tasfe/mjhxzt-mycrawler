package com.mycrawler.dao;

// Generated 2013-5-19 21:37:51 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import com.mycrawler.pojo.TSeed;

/**
 * Home object for domain model class TSeed.
 * @see pojo.TSeed
 * @author Hibernate Tools
 */
public class TSeedHome  extends HibernateDaoBase {

	private static final Log log = LogFactory.getLog(TSeedHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();


	public void persist(TSeed transientInstance) {
		log.debug("persisting TSeed instance");
		try {
			this.getHibernateTemplate().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TSeed instance) {
		log.debug("attaching dirty TSeed instance");
		try {
			this.getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TSeed instance) {
		log.debug("attaching clean TSeed instance");
		try {
			this.getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TSeed persistentInstance) {
		log.debug("deleting TSeed instance");
		try {
			this.getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TSeed merge(TSeed detachedInstance) {
		log.debug("merging TSeed instance");
		try {
			TSeed result = (TSeed) this.getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TSeed findById(java.lang.Integer id) {
		log.debug("getting TSeed instance with id: " + id);
		try {
			TSeed instance = (TSeed) this.getHibernateTemplate().get(
					"pojo.TSeed", id);
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

	public List findByExample(TSeed instance) {
		log.debug("finding TSeed instance by example");
		try {
			List results = this.getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
