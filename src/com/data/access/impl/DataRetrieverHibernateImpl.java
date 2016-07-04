package com.data.access.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.data.access.DataAccessUtils;
import com.data.access.DataRetriever;
import com.data.access.exception.DataAccessException;
import com.data.access.exception.DuplicateRecordException;
import com.data.exception.DataSourceOperationFailedException;


/**
 * This implementation is based on hibernate and this is used for the Select DML
 * operation This uses hibernate session which is injected through spring
 * container.Transactions managed by spring container.
 * 
 * @author Selva
 * 
 */

@Repository
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
@SuppressWarnings("unchecked")
public class DataRetrieverHibernateImpl implements DataRetriever, Serializable {

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
  public <T> T retrieveById(T type, String keyValue) throws DataAccessException {
    T object = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      ClassMetadata classMetadata = getSessionFactory().getClassMetadata(type.getClass());
      String keyName = classMetadata.getIdentifierPropertyName();
      Criteria criteria = session.createCriteria(type.getClass());
      criteria.add(Restrictions.eq(keyName, keyValue));
      object = (T) criteria.uniqueResult();
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return object;
  }

  @Override
  public <T> T retrieveObjectByQuery(String queryString) throws DataAccessException {
    T object = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        object = (T) query.uniqueResult();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return object;
  }

  @Override
  public <T> T retrieveObjectByQuery(String queryString, List<QueryParameter<?>> queryParameters)
      throws DataAccessException {
    T object = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        object = (T) query.uniqueResult();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return object;
  }

  @Override
  public <E> List<E> retrieveByQuery(String queryString) throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <E> List<E> retrieveByQuery(String queryString, List<QueryParameter<?>> queryParameters)
      throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <E> List<E> retrieveByQuery(String queryString, QueryProperties queryProperties)
      throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        if (queryProperties != null) {
          if (queryProperties.getFirstResult() != null) {
            query.setFirstResult(queryProperties.getFirstResult());
          }
          if (queryProperties.getMaxResults() != null) {
            query.setMaxResults(queryProperties.getMaxResults());
          }
          if (queryProperties.getFetchSize() != null) {
            query.setFetchSize(queryProperties.getFetchSize());
          }
        }
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <E> List<E> retrieveByQuery(String queryString, List<QueryParameter<?>> queryParameters,
      QueryProperties queryProperties) throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        if (queryProperties != null) {
          if (queryProperties.getFirstResult() != null) {
            query.setFirstResult(queryProperties.getFirstResult());
          }
          if (queryProperties.getMaxResults() != null) {
            query.setMaxResults(queryProperties.getMaxResults());
          }
          if (queryProperties.getFetchSize() != null) {
            query.setFetchSize(queryProperties.getFetchSize());
          }
        }
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <E> List<E> retrieveBySQLQuery(String queryString) throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <E> List<E> retrieveBySQLQuery(String queryString, List<QueryParameter<?>> queryParameters)
      throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <E> List<E> retrieveBySQLQuery(String queryString, QueryProperties queryProperties)
      throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);
        if (queryProperties != null) {
          if (queryProperties.getFirstResult() != null) {
            query.setFirstResult(queryProperties.getFirstResult());
          }
          if (queryProperties.getMaxResults() != null) {
            query.setMaxResults(queryProperties.getMaxResults());
          }
          if (queryProperties.getFetchSize() != null) {
            query.setFetchSize(queryProperties.getFetchSize());
          }
        }
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <E> List<E> retrieveBySQLQuery(String queryString, List<QueryParameter<?>> queryParameters,
      QueryProperties queryProperties) throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        if (queryProperties != null) {
          if (queryProperties.getFirstResult() != null) {
            query.setFirstResult(queryProperties.getFirstResult());
          }
          if (queryProperties.getMaxResults() != null) {
            query.setMaxResults(queryProperties.getMaxResults());
          }
          if (queryProperties.getFetchSize() != null) {
            query.setFetchSize(queryProperties.getFetchSize());
          }
        }
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <E> List<E> retrieveByQueryWithLimits(String queryString, Integer startLimit,
      Integer endLimit) throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        query.setFirstResult(startLimit);
        query.setMaxResults(endLimit);
        objects = query.list();

      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <E> List<E> retrieveByQueryWithLimits(String queryString, Integer startLimit,
      Integer endLimit, List<QueryParameter<?>> queryParameters) throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        query.setFirstResult(startLimit);
        query.setMaxResults(endLimit);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  /**
   * 
   * This for set the Native SQL Query's column values into given DTO class
   * 
   * @author MUTHU G.K
   * @param e
   *          - DTO Class Object
   * @param query
   *          - Native SQL Query
   * @exception if
   *              query column name Setter is not there in the class means,
   * 
   *              it will throw the could not find the setter Exception <br>
   *              or miss match data type means it will throw not correct data
   *              type or convert the data type for the columns
   */
  @Override
  public <T, E> List<T> retrieveByQueryResultTransformer(E e, String query)
      throws DataAccessException {
    List<T> list = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (query != null) {
        list = session.createSQLQuery(query)
            .setResultTransformer(Transformers.aliasToBean(e.getClass())).list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return list;
  }

  @Override
  public <T, E> List<T> retrieveBySqlQueryWithLimitsAndResultTransformer(E e, String query,
      Integer startLimit, Integer endLimit) throws DataAccessException {
    List<T> list = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (query != null) {
        list = session.createSQLQuery(query).setFirstResult(startLimit).setMaxResults(endLimit)
            .setResultTransformer(Transformers.aliasToBean(e.getClass())).list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return list;
  }

  @Override
  public <E> List<E> retrieveBySqlQueryWithLimits(String queryString, Integer startLimit,
      Integer endLimit) throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query =
            session.createSQLQuery(queryString).setFirstResult(startLimit).setMaxResults(endLimit);
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <E> List<E> retrieveBySqlQueryWithResultTransformer(String query, Class<?> t)
      throws DataAccessException {
    List<E> list = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (query != null) {
        list =
            session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(t)).list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return list;
  }

  @Override
  public <E> List<E> retrieveBySqlQueryWithResultTransformer(String queryString,
      List<QueryParameter<?>> queryParameters, Class<?> t) throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        if (t != null) {
          query.setResultTransformer(Transformers.aliasToBean(t));
        }
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <T> T retrieveObjectBySqlQueryWithResultTransformer(String queryString,
      List<QueryParameter<?>> queryParameters, Class<?> t) throws DataAccessException {
    T object = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);

        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        if (t != null) {
          query.setResultTransformer(Transformers.aliasToBean(t));
        }
        object = (T) query.uniqueResult();
      }
    } catch (ConstraintViolationException cvException) {
      throw new DuplicateRecordException(
          DataAccessUtils.getInstance().getPropertyFileValue("duplicate.record.exception"),
          cvException);
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(hibernateException.getMessage());
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(exception.getMessage());
    }
    return object;
  }

  @Override
  public <E> List<E> retrieveBySqlQueryWithResultTransformer(String queryString, Class<?> t,
      Integer start, Integer size) throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);
        if (start != null) {
          query.setFirstResult(start);
        }
        if (size != null) {
          query.setMaxResults(size);
        }
        query.setResultTransformer(Transformers.aliasToBean(t));
        objects = query.list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <T> T retrieveObjectBySQLQuery(String queryString)
      throws DataSourceOperationFailedException {
    T object = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);
        object = (T) query.uniqueResult();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return object;
  }

  @Override
  public <T> T retrieveObjectBySQLQuery(String queryString, List<QueryParameter<?>> queryParameters)
      throws DataAccessException {
    T object = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);

        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }

        object = (T) query.uniqueResult();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return object;
  }

  @Override
  public <T> T retrieveObjectBySQLQuery(String queryString, List<QueryParameter<?>> queryParameters,
      Class<?> t) throws DataSourceOperationFailedException {
    T object = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createSQLQuery(queryString);

        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        if (t != null) {
          query.setResultTransformer(Transformers.aliasToBean(t));
        }
        object = (T) query.uniqueResult();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return object;
  }

  // ----------------- Root Unique Fetch -------------[START] MUTHU G.K
  @Override
  public <E> List<E> retrieveByQueryUniqueRoot(String queryString,
      List<QueryParameter<?>> queryParameters) throws DataAccessException {
    List<E> objects = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        objects = query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return objects;
  }

  @Override
  public <T> T retrieveObjectByQueryUniqueRoot(String queryString,
      List<QueryParameter<?>> queryParameters) throws DataAccessException {

    T object = null;
    try {
      Session session = getSessionFactory().getCurrentSession();
      if (queryString != null) {
        Query query = session.createQuery(queryString);
        for (QueryParameter<?> queryParameter : queryParameters) {
          if (queryParameter.getValue().getClass().equals(List.class)
              || queryParameter.getValue().getClass().equals(ArrayList.class)) {
            List<?> parameter = (List<?>) queryParameter.getValue();
            query.setParameterList(queryParameter.getName(), parameter);
          } else {
            query.setParameter(queryParameter.getName(), queryParameter.getValue());
          }
        }
        object = (T) query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
      }
    } catch (HibernateException hibernateException) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          hibernateException);
    } catch (Exception exception) {
      throw new DataSourceOperationFailedException(
          DataAccessUtils.getInstance().getPropertyFileValue("msg.datasource.retrieval.fail"),
          exception);
    }
    return object;

  }
  // ----------------- Root Unique Fetch ------------------------[END]

}
