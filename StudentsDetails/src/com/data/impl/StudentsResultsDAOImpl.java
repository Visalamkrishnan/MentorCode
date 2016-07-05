package com.data.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.data.StudentsResultsDAO;
import com.data.access.DataModifier;
import com.data.access.DataRetriever;
import com.data.access.exception.DataAccessException;
import com.data.access.impl.QueryParameter;
import com.data.exception.DataServiceException;
import com.data.util.DataUtils;
import com.dto.StudentDTO;
import com.models.SeedMajor;
import com.models.StudentsResults;
import com.models.University;


@Transactional
@Repository
public class StudentsResultsDAOImpl implements StudentsResultsDAO, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	  @Autowired
	  private DataRetriever dataRetriever;
	  
	  @Autowired
	  private DataModifier dataModifier;
	  

	
public DataRetriever getDataRetriever() {
		return dataRetriever;
	}



	public void setDataRetriever(DataRetriever dataRetriever) {
		this.dataRetriever = dataRetriever;
	}



@Override
	public List<StudentsResults> getStudentsResultsBasedOnType(StudentDTO studentDTO) throws DataServiceException {
		List<StudentsResults> studentsResults = null;

		StringBuffer query = null;
		List<QueryParameter<?>> queryParameters = new ArrayList<QueryParameter<?>>();
		try {
			if (studentDTO != null) {
				query = new StringBuffer(
						"FROM StudentsResults cu WHERE cu.isActive=true");
				
				if(studentDTO.getMajorId()!=null ){
					query.append(" AND cu.major.id= :major");
					queryParameters.add(new QueryParameter<Long>("major", studentDTO.getMajorId()));
				}
				
				if(studentDTO.getUniversityId()!=null){
					query.append(" AND cu.university.id= :univ");
					queryParameters.add(new QueryParameter<Long>("univ", studentDTO.getUniversityId()));
				}
				
				if(studentDTO.getTechPercentage()!=null){
					query.append(" AND cu.techPercentage>= :tech");
					queryParameters.add(new QueryParameter<Integer>("tech", studentDTO.getTechPercentage()));
				}
				if(studentDTO.getAptPercentage()!=null){
					query.append(" AND cu.aptPercentage>= :apt");
					queryParameters.add(new QueryParameter<Integer>("apt", studentDTO.getAptPercentage()));
				}
				if(studentDTO.getCurrentArrears()!=null){
					query.append(" AND cu.currentArrears= :arr");
					queryParameters.add(new QueryParameter<Boolean>("arr", studentDTO.getCurrentArrears()));
				}
				
			studentsResults = dataRetriever.retrieveByQuery(query.toString(),queryParameters);

			}

		} catch (DataAccessException e) {
			throw new DataServiceException(DataUtils.getInstance().getPropertyFileValue("msg.data.retrieval.fail"), e);
		} 
		return studentsResults;
	}



@Override
public List<SeedMajor> getAllMajor() throws DataServiceException {
	  List<SeedMajor> majors = null;
	    try {
	      StringBuffer queryString = new StringBuffer("select m.id as majId,m.name as name from seed_major m where name<>'Other' order by name");
	      majors = getDataRetriever().retrieveBySqlQueryWithResultTransformer(queryString.toString(),SeedMajor.class);
	      queryString = new StringBuffer(
	              "select m.id as majId,m.name as name from seed_major m where name='Other' order by name");
	      List<SeedMajor> majorsTmp = getDataRetriever()
	              .retrieveBySqlQueryWithResultTransformer(queryString.toString(),SeedMajor.class);
	      if (majorsTmp != null && majorsTmp.size() > 0) {
	    	  majors.addAll(majorsTmp);
	      }
	    } catch (DataAccessException dataAccessException) {
	      throw new DataServiceException(
	          DataUtils.getInstance().getPropertyFileValue("msg.data.retrieval.fail"),
	          dataAccessException);
	    } 
	    return majors;
}



@Override
public List<University> getAllUniversity() throws DataServiceException {
	  List<University> universities = null;
	    try {
	      StringBuffer queryString = new StringBuffer("select u.id as univId,u.name as name from seed_universities u where name<>'Dummy' order by name");
	      universities = getDataRetriever().retrieveBySqlQueryWithResultTransformer(queryString.toString(),University.class);
	    } catch (DataAccessException dataAccessException) {
	      throw new DataServiceException(
	          DataUtils.getInstance().getPropertyFileValue("msg.data.retrieval.fail"),
	          dataAccessException);
	    } 
	    return universities;
}



@Override
public void saveStudentDetails(StudentsResults results) throws DataServiceException {
	try {
		dataModifier.update(results);
	} catch (DataAccessException e) {
		throw new DataServiceException("Save failed", e);
	}
	
}



@Override
public StudentsResults getStudentById(Long studId) throws DataServiceException {
	StudentsResults result = null;
	    try {
	      StringBuffer queryString = new StringBuffer("from StudentsResults sr where sr.id=:studId");
	      List<QueryParameter<?>> queryParameters = new ArrayList<QueryParameter<?>>();
	      queryParameters.add(new QueryParameter<Long>("studId", studId));
	      result = getDataRetriever().retrieveObjectByQuery(queryString.toString(),queryParameters);
	    } catch (DataAccessException dataAccessException) {
	      throw new DataServiceException(
	          DataUtils.getInstance().getPropertyFileValue("msg.data.retrieval.fail"),
	          dataAccessException);
	    } 
	    return result;
}


}
