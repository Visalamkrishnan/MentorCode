package com.shared.util;

import com.shared.vo.ServiceResponse;

/**
 * This class used to manage the service response object for all controller.
 * 
 * @author Visalam
 * 
 */
public class ServiceResponseUtil {
	  private static ServiceResponse serviceResponse;

	  /**
	   * This method used to get dataResponse as output.
	   * 
	   * @param code
	   *          code
	   * @param description
	   *          description
	   * @param type
	   *          type
	   * @return serviceResponse
	   */
	  public static ServiceResponse dataResponse(String code, String description, Object type) {
	    serviceResponse = new ServiceResponse();
	    serviceResponse.setStatusCode(code);
	    serviceResponse.setDescription(description);
	    serviceResponse.setData(type);
	    return serviceResponse;

	  }
}
