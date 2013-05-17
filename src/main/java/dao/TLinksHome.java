package dao;

// Generated 2013-5-13 0:41:16 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;

import pojo.TLinks;

/**
 * Home object for domain model class TLinks.
 * 
 * @see pojo.TLinks
 * @author Hibernate Tools
 */
public class TLinksHome extends HibernateDaoBase {

	private static final Log log = LogFactory.getLog(TLinksHome.class);

	public List list(TLinks transientInstance) {
		log.debug("List TLinks instance");
		try {
			return this.getHibernateTemplate().findByExample(transientInstance);
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	public void persist(TLinks transientInstance) {
		log.debug("persisting TLinks instance");
		try {
			this.getHibernateTemplate().save(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TLinks instance) {
		log.debug("attaching dirty TLinks instance");
		try {
			this.getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TLinks instance) {
		log.debug("attaching clean TLinks instance");
		try {
			this.getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TLinks persistentInstance) {
		log.debug("deleting TLinks instance");
		try {
			this.getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TLinks merge(TLinks detachedInstance) {
		log.debug("merging TLinks instance");
		try {
			TLinks result = (TLinks) this.getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TLinks findById(java.lang.Integer id) {
		log.debug("getting TLinks instance with id: " + id);
		try {
			TLinks instance = (TLinks) this.getHibernateTemplate().get(
					TLinks.class, id);
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

	// public List findByExample(TLinks instance) {
	// log.debug("finding TLinks instance by example");
	// try {
	// List results = this.getHibernateTemplate()
	// .c("pojo.TLinks")
	// .add(Example.create(instance)).list();
	// log.debug("find by example successful, result size: "
	// + results.size());
	// return results;
	// } catch (RuntimeException re) {
	// log.error("find by example failed", re);
	// throw re;
	// }
	// }
}
