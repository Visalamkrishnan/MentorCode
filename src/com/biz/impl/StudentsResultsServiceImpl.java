package com.biz.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.StudentsResultsService;
import com.biz.exception.BusinessServiceException;
import com.data.StudentsResultsDAO;
import com.data.exception.DataServiceException;
import com.dto.StudentDTO;
import com.models.SeedMajor;
import com.models.StudentsResults;
import com.models.University;

@Service
public class StudentsResultsServiceImpl implements StudentsResultsService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private StudentsResultsDAO studentsResultsDAO;

	public StudentsResultsDAO getStudentsResultsDAO() {
		return studentsResultsDAO;
	}

	public void setStudentsResultsDAO(StudentsResultsDAO studentsResultsDAO) {
		this.studentsResultsDAO = studentsResultsDAO;
	}

	@Override
	public List<StudentsResults> doGetAllInternsBasedOnType(StudentDTO studentDTO) throws BusinessServiceException {
		List<StudentsResults> studentsResults;
		try {
			studentsResults = studentsResultsDAO.getStudentsResultsBasedOnType(studentDTO);
		} catch (DataServiceException dataServiceException) {
			throw new BusinessServiceException(dataServiceException.getMessage(), dataServiceException);
		}
		return studentsResults;
	}

	@Override
	public List<SeedMajor> doGetAllDepartment() throws BusinessServiceException {
		List<SeedMajor> majors = null;
		try {
			majors = studentsResultsDAO.getAllMajor();
			return majors;
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage(), e);
		} finally {
			majors = null;
		}
	}

	@Override
	public List<University> doGetAllUniversity() throws BusinessServiceException {
		List<University> universities = null;
		try {
			universities = studentsResultsDAO.getAllUniversity();
			return universities;
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage(), e);
		} finally {
			universities = null;
		}
	}

	@Override
	public void saveDetails(StudentsResults results) throws BusinessServiceException {
		try {
			studentsResultsDAO.saveStudentDetails(results);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage(), e);
		}

	}

	@Override
	public StudentsResults getDetailsById(Long studId) throws BusinessServiceException {
		StudentsResults result = null;
		try {
			result = studentsResultsDAO.getStudentById(studId);
			return result;
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage(), e);
		} finally {
			result = null;
		}

	}

}
