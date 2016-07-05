package com.mbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.biz.StudentsResultsService;
import com.biz.exception.BusinessServiceException;
import com.dto.StudentDTO;
import com.mbeans.utils.FacesContextUtil;
import com.mbeans.utils.MessageRender;
import com.models.SeedMajor;
import com.models.StudentsResults;
import com.models.University;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Font;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@SuppressWarnings("deprecation")
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

	String pageFlag;

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
			if (studentId != null) {
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
		Map<String, String> reqMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if (reqMap.containsKey("add")) {
			if (results.getReviewerResult() != null && !results.getReviewerResult().isEmpty()) {
				comments = results.getReviewerResult();
			}
			pf = "add";
		}
		return pf;
	}

	// search student
	public void searchForStudent() {
		try {
			if (universityId != null) {
				studentDTO.setUniversityId(universityId);
			} 
			if (majorId != null) {
				studentDTO.setMajorId(majorId);
			} else {
				studentDTO.setMajorId(null);
			}
			if(studentDTO.getUniversityId()!=null)
			{
			students = getStudentsResultsService().doGetAllInternsBasedOnType(studentDTO);
			}

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

	// download details in excel
	public void downloadAsExcel() {
		try {
			HttpServletResponse res = (HttpServletResponse) FacesContextUtil.getExternalContext().getResponse();
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-Disposition", "attachment; filename=ReportSheet.xls");

			WritableWorkbook w = Workbook.createWorkbook(res.getOutputStream());
			WritableSheet s = w.createSheet("Report", 0);
			WritableFont cellFont = new WritableFont(WritableFont.COURIER, 12);
			cellFont.setColour(Colour.BLACK);
			cellFont.setBoldStyle(Font.BOLD);
			s.setColumnView(0, 30);
			s.setColumnView(1, 45);
			s.setColumnView(2, 35);
			s.setColumnView(3, 25);
			s.setColumnView(4, 25);
			s.setColumnView(5, 25);
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.WHITE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat.setAlignment(Alignment.CENTRE);
			s.addCell((WritableCell) new Label(0, 0, "NAME", (CellFormat) cellFormat));
			s.addCell((WritableCell) new Label(1, 0, "EMAIL", (CellFormat) cellFormat));
			s.addCell((WritableCell) new Label(2, 0, "UNIVERSITY", (CellFormat) cellFormat));
			s.addCell((WritableCell) new Label(3, 0, "DEPARTMENT", (CellFormat) cellFormat));
			s.addCell((WritableCell) new Label(4, 0, "APTITUDE PERCENTAGE", (CellFormat) cellFormat));
			s.addCell((WritableCell) new Label(5, 0, "TECHNICAL PERCENTAGE", (CellFormat) cellFormat));
			int ite = 1;
			for (StudentsResults student : students) {

				if (student.getName() != null) {
					s.addCell((WritableCell) new Label(0, ite, student.getName()));
				}
				if (student.getEmail() != null) {
					s.addCell((WritableCell) new Label(1, ite, student.getEmail()));
				}
				if (student.getUniversity().getName() != null) {
					s.addCell((WritableCell) new Label(2, ite, student.getUniversity().getName()));

				}
				if (student.getMajor().getName() != null) {
					s.addCell((WritableCell) new Label(3, ite, student.getMajor().getName()));
				}
				if (student.getAptPercentage().toString() != null) {
					s.addCell((WritableCell) new Label(4, ite, student.getAptPercentage().toString()));
				}
				if (student.getTechPercentage().toString() != null) {
					s.addCell((WritableCell) new Label(5, ite, student.getTechPercentage().toString()));
				}

				ite++;
			}
			w.write();
			w.close();
			res.getOutputStream().flush();
			res.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
