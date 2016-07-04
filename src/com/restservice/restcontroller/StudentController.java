package com.restservice.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biz.StudentsResultsService;
import com.biz.exception.BusinessServiceException;
import com.data.util.ServiceConstants;
import com.models.SeedMajor;
import com.shared.util.ServiceResponseUtil;
import com.shared.vo.ServiceResponse;

@RestController
@RequestMapping("/student")
public class StudentController {
	
@Autowired
public StudentsResultsService studentService;

/**
 This service is to get all departments. 
@author Visalam k

*/
@RequestMapping(method = RequestMethod.GET)
public ServiceResponse getAllDepartment() {
    List<SeedMajor> departments = null;
    ServiceResponse serviceResponse;
    try {
    	departments = studentService.doGetAllDepartment();
      if (departments == null || departments.size() == 0) {
        serviceResponse =
            ServiceResponseUtil.dataResponse(ServiceConstants.NO_RECORDS_FOUND_ERROR_CODE,
                ServiceConstants.NO_RECORDS_FOUND_ERROR_MESSAGE, null);
      } else {
        serviceResponse =
            ServiceResponseUtil.dataResponse(ServiceConstants.DATA_RETRIEVAL_SUCCESS_CODE,
                "Majors Retrieved Successfully", departments);
      }
      return serviceResponse;
    } catch (BusinessServiceException be) {
      serviceResponse =
          ServiceResponseUtil.dataResponse(ServiceConstants.DATA_RETRIEVAL_FAILED_CODE,
              be.getMessage(), null);
      return serviceResponse;
    } catch (Throwable t) {
      serviceResponse =
          ServiceResponseUtil.dataResponse(ServiceConstants.SYSTEM_ERROR_CODE,
              ServiceConstants.SYSTEM_ERROR_MESSAGE, null);
      return serviceResponse;
    } finally {
      serviceResponse = null;
    }
  }


}
