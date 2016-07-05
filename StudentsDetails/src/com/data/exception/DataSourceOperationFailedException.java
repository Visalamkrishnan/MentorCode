package com.data.exception;

import com.data.access.exception.DataAccessException;

public class DataSourceOperationFailedException extends DataAccessException {

  private static final long serialVersionUID = 1L;

  public DataSourceOperationFailedException() {
    super();
  }

  public DataSourceOperationFailedException(String message) {
    super(message);
  }

  public DataSourceOperationFailedException(Throwable exception) {
    super(exception);
  }

  public DataSourceOperationFailedException(String message, Throwable exception) {
    super(message, exception);
  }

}
