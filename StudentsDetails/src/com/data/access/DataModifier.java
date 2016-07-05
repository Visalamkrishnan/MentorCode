package com.data.access;

import java.util.List;

import com.data.access.exception.DataAccessException;
import com.data.access.impl.QueryParameter;


public interface DataModifier {

  <T> void insert(T type) throws DataAccessException;

  <E> boolean insertBulk(List<E> objects) throws DataAccessException;

  <E> boolean saveOrUpdate(List<E> objects) throws DataAccessException;

  <T> void update(T type) throws DataAccessException;

  <T> void delete(T type) throws DataAccessException;

  <E> boolean deleteBulk(List<E> objects) throws DataAccessException;

  <E> boolean updateBulk(List<E> objects) throws DataAccessException;

  Integer executeQuery(String queryString) throws DataAccessException;

  Integer executeQuery(String queryString, List<QueryParameter<?>> queryParameters)
      throws DataAccessException;

  void executeSQLQuery(String queryString) throws DataAccessException;

  void executeSQLQuery(String queryString, List<QueryParameter<?>> queryParameters)
      throws DataAccessException;

}
