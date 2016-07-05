package com.data.access.exception;

public class DataSourceConnectivityFailedException extends DataAccessException {

  private static final long serialVersionUID = 1L;

  public DataSourceConnectivityFailedException() {
    super();
  }

  public DataSourceConnectivityFailedException(String message) {
    super(message);
  }

  public DataSourceConnectivityFailedException(Throwable exception) {
    super(exception);
  }

  public DataSourceConnectivityFailedException(String message, Throwable exception) {
    super(message, exception);
  }

}
