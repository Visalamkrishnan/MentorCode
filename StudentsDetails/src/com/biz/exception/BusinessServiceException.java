package com.biz.exception;

public class BusinessServiceException extends Exception {

	  private static final long serialVersionUID = 1L;

	  public BusinessServiceException() {
	    super();
	  }

	  public BusinessServiceException(String msg, Throwable exception) {
	    super(msg, exception);
	  }

	  public BusinessServiceException(Throwable exception) {
	    super(exception);
	  }

	  public BusinessServiceException(String message) {
	    super(message);
	  }

	}
