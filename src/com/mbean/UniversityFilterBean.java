package com.mbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.biz.StudentsResultsService;
import com.biz.exception.BusinessServiceException;
import com.dto.StudentDTO;
import com.mbeans.utils.MessageRender;
import com.models.SeedMajor;
import com.models.StudentsResults;
import com.models.University;

@ManagedBean
@ViewScoped
public class UniversityFilterBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{studentsResultsServiceImpl}")
	private StudentsResultsService studentsResultsService;

	List<SeedMajor> majors;

	private Long majorId;

	private List<StudentsResults> students;

	List<University> universities;

	private Long universityId;

	private StudentDTO studentDTO = new StudentDTO();

	private StudentsResults results;

	private String comments;

	private Boolean isSelected;
	
	private String studentId;
	
	String pageFlag ;

	// getter setter starts

	public StudentsResultsService getStudentsResultsService() {
		return studentsResultsService;
	}

	public void setStudentsResultsService(StudentsResultsService studentsResultsService) {
		this.studentsResultsService = studentsResultsService;
	}

	public List<SeedMajor> getMajors() {
		return majors;
	}

	public void setMajors(List<SeedMajor> majors) {
		this.majors = majors;
	}

	public Long getMajorId() {
		return majorId;
	}

	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}

	public List<StudentsResults> getStudents() {
		return students;
	}

	public void setStudents(List<StudentsResults> students) {
		this.students = students;
	}

	public List<University> getUniversities() {
		return universities;
	}

	public void setUniversities(List<University> universities) {
		this.universities = universities;
	}

	public StudentDTO getStudentDTO() {
		return studentDTO;
	}

	public void setStudentDTO(StudentDTO studentDTO) {
		this.studentDTO = studentDTO;
	}

	public Long getUniversityId() {
		return universityId;
	}

	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public StudentsResults getResults() {
		return results;
	}

	public void setResults(StudentsResults results) {
		this.results = results;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}

	@PostConstruct
	public void init() {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			 studentId = params.get("studentId");
			 if(studentId!=null)
			 {
				 Long studId = Long.valueOf(studentId);
					results = getStudentsResultsService().getDetailsById(studId);
			 }
			pageFlag = getPageFlagFromReq();
			if (majors == null) {
				majors = getStudentsResultsService().doGetAllDepartment();
			}
			if (universities == null) {
				universities = getStudentsResultsService().doGetAllUniversity();
			}
		} catch (BusinessServiceException ex) {
			MessageRender.addErrorMessage(ex.getMessage());
		}
	}

	private String getPageFlagFromReq() {
	    String pf = null;
	    Map<String, String> reqMap =
	    		FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
	    if (reqMap.containsKey("add")) {
	    	comments=results.getReviewerResult();
	      pf = "add";
	    }
	    return pf;
	}

	// search student
	public void searchForStudent() {
		try {
			if (universityId != null) {
				studentDTO.setUniversityId(universityId);
			} else {
				studentDTO.setUniversityId(null);
			}
			if (majorId != null) {
				studentDTO.setMajorId(majorId);
			} else {
				studentDTO.setMajorId(null);
			}

			students = getStudentsResultsService().doGetAllInternsBasedOnType(studentDTO);

		} catch (BusinessServiceException ex) {
			MessageRender.addErrorMessage(ex.getMessage());
		}

	}

	// save
	public void saveDetails() {
		try {
			if (comments != null && !comments.isEmpty()) {
				results.setReviewerResult(comments);
				getStudentsResultsService().saveDetails(results);
				MessageRender.addInfoMessage("Review notes Saved Successfully");
			}
		} catch (BusinessServiceException ex) {
			MessageRender.addErrorMessage(ex.getMessage());
		}
	}
}
