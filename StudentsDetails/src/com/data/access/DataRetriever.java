package com.data.access;

import java.util.List;

import com.data.access.exception.DataAccessException;
import com.data.access.impl.QueryParameter;
import com.data.access.impl.QueryProperties;
import com.data.exception.DataSourceOperationFailedException;

public interface DataRetriever {

	<T> T retrieveById(T type, String keyValue) throws DataAccessException;

	<E> List<E> retrieveByQuery(String queryString) throws DataAccessException;

	<E> List<E> retrieveByQuery(String queryString, List<QueryParameter<?>> queryParameters) throws DataAccessException;

	<E> List<E> retrieveByQuery(String queryString, QueryProperties queryProperties) throws DataAccessException;

	<E> List<E> retrieveByQuery(String queryString, List<QueryParameter<?>> queryParameters,
			QueryProperties queryProperties) throws DataAccessException;

	<E> List<E> retrieveBySQLQuery(String queryString) throws DataAccessException;

	<E> List<E> retrieveBySQLQuery(String queryString, List<QueryParameter<?>> queryParameters)
			throws DataAccessException;

	<T> T retrieveObjectBySQLQuery(String string, List<QueryParameter<?>> queryParameters, Class<?> t)
			throws DataSourceOperationFailedException;

	<E> List<E> retrieveBySQLQuery(String queryString, QueryProperties queryProperties) throws DataAccessException;

	<E> List<E> retrieveBySQLQuery(String queryString, List<QueryParameter<?>> queryParameters,
			QueryProperties queryProperties) throws DataAccessException;

	<T> T retrieveObjectByQuery(String queryString) throws DataAccessException;

	<T> T retrieveObjectByQuery(String queryString, List<QueryParameter<?>> queryParameters) throws DataAccessException;

	<E> List<E> retrieveBySqlQueryWithResultTransformer(String query, Class<?> t) throws DataAccessException;

	<E> List<E> retrieveBySqlQueryWithResultTransformer(String queryString, List<QueryParameter<?>> queryParameters,
			Class<?> t) throws DataAccessException;

	<T> T retrieveObjectBySqlQueryWithResultTransformer(String queryString, List<QueryParameter<?>> queryParameters,
			Class<?> t) throws DataAccessException;

	<E> List<E> retrieveBySqlQueryWithResultTransformer(String queryString, Class<?> t, Integer start, Integer size)
			throws DataAccessException;

	<T> T retrieveObjectBySQLQuery(String queryString) throws DataSourceOperationFailedException;

	<T> T retrieveObjectBySQLQuery(String queryString, List<QueryParameter<?>> queryParameters)
			throws DataAccessException;

}
