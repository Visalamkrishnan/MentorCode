package com.biz;

import java.util.List;

import com.biz.exception.BusinessServiceException;
import com.dto.StudentDTO;
import com.models.SeedMajor;
import com.models.StudentsResults;
import com.models.University;

public interface StudentsResultsService {

	List<StudentsResults> doGetAllInternsBasedOnType(StudentDTO studentDTO)
			throws BusinessServiceException;

	List<SeedMajor> doGetAllDepartment()throws BusinessServiceException;

	List<University> doGetAllUniversity()throws BusinessServiceException;

	void saveDetails(StudentsResults results)throws BusinessServiceException;

	StudentsResults getDetailsById(Long studId)throws BusinessServiceException;


}
