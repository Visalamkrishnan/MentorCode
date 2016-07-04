package com.data.access.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.data.access.DataAccessUtils;
import com.data.access.DataModifier;
import com.data.access.exception.DataAccessException;
import com.data.access.exception.DuplicateRecordException;
import com.data.access.exception.ReferentialIntegrityException;
import com.data.exception.DataSourceOperationFailedException;



/**
 * This implementation is based on hibernate and this is used for the following
 * DML operation Insert , Update , Delete This uses hibernate session which is
 * injected through spring container and also the transaction managed by spring
 * container.
 * 
 * @author Selva
 * 
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = false,
    rollbackFor = DataAccessException.class)
public class DataModifierHibernateImpl implements DataModifier, Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public <T> void insert(T type) throws DataAccessException {
    try {
      Session session = getSessionFactory().getCurrentSession();
      session.save(type);
      session.flush();
    } catch (ConstraintViolationException cvException) {
      throw new DuplicateRecordException(
          DataAccessUtils.getInstance().getPropertyFileValue("duplicate.record.exception"),
          cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
  }

  @Override
  public <E> boolean insertBulk(List<E> objects) throws DataAccessException {
    boolean result = false;
    try {
      Session session = getSessionFactory().getCurrentSession();
      for (int i = 0; i < objects.size(); i++) {
        Object type = objects.get(i);
        session.saveOrUpdate(type);
        if (i % 20 == 0) {
          session.flush();
          session.clear();
        }
      }
      session.flush();
    } catch (ConstraintViolationException cvException) {
      throw new DuplicateRecordException(
          DataAccessUtils.getInstance().getPropertyFileValue("duplicate.record.exception"),
          cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
    return result;
  }

  @Override
  public <E> boolean saveOrUpdate(List<E> objects) throws DataAccessException {
    boolean result = false;
    try {
      Session session = getSessionFactory().getCurrentSession();
      for (int i = 0; i < objects.size(); i++) {
        session.saveOrUpdate(objects.get(i));
        if (i % 20 == 0) {
          session.flush();
          session.clear();
        }
      }
      session.flush();
    } catch (ConstraintViolationException cvException) {
      throw new DuplicateRecordException(
          DataAccessUtils.getInstance().getPropertyFileValue("duplicate.record.exception"),
          cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
    return result;
  }

  @Override
  public <T> void update(T type) throws DataAccessException {
    try {
      Session session = getSessionFactory().getCurrentSession();
      session.clear();
      session.saveOrUpdate(type);
      session.flush();
    } catch (ConstraintViolationException cvException) {
      throw new DuplicateRecordException(
          DataAccessUtils.getInstance().getPropertyFileValue("duplicate.record.exception"),
          cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
  }

  @Override
  public <T> void delete(T type) throws DataAccessException {
    try {
      Session session = getSessionFactory().getCurrentSession();
      session.delete(type);
      session.flush();
    } catch (ConstraintViolationException cvException) {
      throw new ReferentialIntegrityException(
          DataAccessUtils.getInstance().getPropertyFileValue("foreign.key.exception"), cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
  }

  @Override
  public <E> boolean deleteBulk(List<E> objects) throws DataAccessException {
    boolean result = false;
    try {
      Session session = getSessionFactory().getCurrentSession();
      for (int i = 0; i < objects.size(); i++) {
        Object type = objects.get(i);
        session.delete(type);
        if (i % 20 == 0) {
          session.flush();
          session.clear();
        }
      }
      session.flush();
    } catch (ConstraintViolationException cvException) {
      throw new ReferentialIntegrityException(
          DataAccessUtils.getInstance().getPropertyFileValue("foreign.key.exception"), cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
    return result;
  }

  @Override
  public <E> boolean updateBulk(List<E> objects) throws DataAccessException {
    boolean result = false;
    try {
      Session session = getSessionFactory().getCurrentSession();
      for (int i = 0; i < objects.size(); i++) {
        Object type = objects.get(i);
        session.saveOrUpdate(type);
        if (i % 20 == 0) {
          session.flush();
          session.clear();
        }
      }
      session.flush();
    } catch (ConstraintViolationException cvException) {
      throw new DuplicateRecordException(
          DataAccessUtils.getInstance().getPropertyFileValue("duplicate.record.exception"),
          cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
    return result;
  }

  @Override
  public <T> void merge(T type) throws DataAccessException {
    try {
      Session session = getSessionFactory().getCurrentSession();
      session.clear();
      session.merge(type);
      session.flush();
    } catch (ConstraintViolationException cvException) {
      throw new DuplicateRecordException(
          DataAccessUtils.getInstance().getPropertyFileValue("duplicate.record.exception"),
          cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
  }

  @Override
  public <E> boolean mergeBulk(List<E> objects) throws DataAccessException {
    boolean result = false;
    try {
      Session session = getSessionFactory().getCurrentSession();
      for (int i = 0; i < objects.size(); i++) {
        Object type = objects.get(i);
        session.merge(type);
        if (i % 20 == 0) {
          session.flush();
          session.clear();
        }
      }
      session.flush();
    } catch (ConstraintViolationException cvException) {
      throw new DuplicateRecordException(
          DataAccessUtils.getInstance().getPropertyFileValue("duplicate.record.exception"),
          cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
    return result;
  }

  @Override
  public Integer executeQuery(String queryString) throws DataAccessException {
    Integer noOfRowsUpdated = 0;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        noOfRowsUpdated = query.executeUpdate();
        session.flush();
      }
      return noOfRowsUpdated;
    } catch (ConstraintViolationException cvException) {
      throw new ReferentialIntegrityException(
          DataAccessUtils.getInstance().getPropertyFileValue("foreign.key.exception"), cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
  }

  @Override
  public Integer executeQuery(String queryString, List<QueryParameter<?>> queryParameters)
      throws DataAccessException {
    Integer noOfRowsUpdated = 0;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue() != null
              && (queryParameter.getValue().getClass().equals(List.class)
                  || queryParameter.getValue().getClass().equals(ArrayList.class))) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        noOfRowsUpdated = query.executeUpdate();
      }
      return noOfRowsUpdated;
    } catch (ConstraintViolationException cvException) {
      throw new ReferentialIntegrityException(
          DataAccessUtils.getInstance().getPropertyFileValue("foreign.key.exception"), cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
  }

  @Override
  public void executeSQLQuery(String queryString) throws DataAccessException {
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);
        query.executeUpdate();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
  }

  @Override
  public void executeSQLQuery(String queryString, List<QueryParameter<?>> queryParameters)
      throws DataAccessException {
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue() != null
              && (queryParameter.getValue().getClass().equals(List.class)
                  || queryParameter.getValue().getClass().equals(ArrayList.class))) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        query.executeUpdate();

      }
    } catch (ConstraintViolationException cvException) {
      throw new ReferentialIntegrityException(
          DataAccessUtils.getInstance().getPropertyFileValue("foreign.key.exception"), cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
  }

  @Override
  public <T> T refreshObject(T type) throws DataSourceOperationFailedException {
    try {
      Session session = getSessionFactory().getCurrentSession();
      session.refresh(type);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
    return type;
  }

}
