package com.data;

import java.util.List;

import com.data.exception.DataServiceException;
import com.dto.StudentDTO;
import com.models.SeedMajor;
import com.models.StudentsResults;
import com.models.University;

public interface StudentsResultsDAO {

	List<StudentsResults> getStudentsResultsBasedOnType(StudentDTO studentDTO)
			throws DataServiceException;

	List<SeedMajor> getAllMajor()throws DataServiceException;

	List<University> getAllUniversity()throws DataServiceException;

	void saveStudentDetails(StudentsResults results)throws DataServiceException;

	StudentsResults getStudentById(Long studId)throws DataServiceException;

}
