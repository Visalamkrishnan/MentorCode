package com.dto;

public class StudentDTO {
	
	private Long id;
	private Long majorId;
	private Long universityId;
	private String enrolledOn;
	private Integer aptPercentage;
	private Integer techPercentage;
	private Boolean currentArrears;
	private String prevArrears;
	private String reviewerResult;
	private String name;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMajorId() {
		return majorId;
	}
	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}
	public Long getUniversityId() {
		return universityId;
	}
	public void setUniversityId(Long universityId) {
		this.universityId = universityId;
	}
	public String getEnrolledOn() {
		return enrolledOn;
	}
	public void setEnrolledOn(String enrolledOn) {
		this.enrolledOn = enrolledOn;
	}
	public Integer getAptPercentage() {
		return aptPercentage;
	}
	public void setAptPercentage(Integer aptPercentage) {
		this.aptPercentage = aptPercentage;
	}
	public Integer getTechPercentage() {
		return techPercentage;
	}
	public void setTechPercentage(Integer techPercentage) {
		this.techPercentage = techPercentage;
	}
	public Boolean getCurrentArrears() {
		return currentArrears;
	}
	public void setCurrentArrears(Boolean currentArrears) {
		this.currentArrears = currentArrears;
	}
	public String getPrevArrears() {
		return prevArrears;
	}
	public void setPrevArrears(String prevArrears) {
		this.prevArrears = prevArrears;
	}
	public String getReviewerResult() {
		return reviewerResult;
	}
	public void setReviewerResult(String reviewerResult) {
		this.reviewerResult = reviewerResult;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

	
	

}
