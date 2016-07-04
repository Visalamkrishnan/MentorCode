package com.shared.vo;
/**
 * This class using all controllers as service response.
 * 
 * @author Visalam
 */
public class ServiceResponse {

	  private String statusCode;
	  private String description;
	  private Object data;

	  public ServiceResponse() {
	  }

	  public String getDescription() {
	    return description;
	  }

	  public void setDescription(String description) {
	    this.description = description;
	  }

	  public Object getData() {
	    return data;
	  }

	  public void setData(Object data) {
	    this.data = data;
	  }

	  public String getStatusCode() {
	    return statusCode;
	  }

	  public void setStatusCode(String statusCode) {
	    this.statusCode = statusCode;
	  }

}
