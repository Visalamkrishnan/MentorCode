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

}
