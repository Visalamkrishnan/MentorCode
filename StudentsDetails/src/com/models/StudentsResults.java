package com.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "students_results")

public class StudentsResults implements Serializable{
	 private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHONE")
	private String mobile;

	@ManyToOne
	@JoinColumn(name = "MAJOR_ID")
	private SeedMajor major;
	
	@ManyToOne
	@JoinColumn(name = "UNIVERSITY_ID")
	private University university;

	@Column(name = "IS_ACTIVE")
	private Boolean isActive;

	@Column(name = "ENROLLED_ON")
	private Date enrolledOn;

	@Column(name = "APTITUDE_PERCENTAGE")
	private Integer aptPercentage;

	@Column(name = "IS_PASSED_IN_APTITUDE")
	private Boolean isPassedinApt;

	@Column(name = "TECHNICAL_PERCENTAGE")
	private Integer techPercentage;

	@Column(name = "IS_PASSED_IN_TECHNICAL")
	private Boolean isPassedInTech;

	@Column(name = "OVERALL_PERCENTAGE")
	private Integer overallPercentage;

	@Column(name = "HAVE_ARREARS")
	private Boolean currentArrears;

	@Column(name = "HAD_ARREARS")
	private Boolean prevArrears;

	@Column(name = "REVIEWER_RESULT")
	private String reviewerResult;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public SeedMajor getMajor() {
		return major;
	}

	public void setMajor(SeedMajor major) {
		this.major = major;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getEnrolledOn() {
		return enrolledOn;
	}

	public void setEnrolledOn(Date enrolledOn) {
		this.enrolledOn = enrolledOn;
	}

	public Integer getAptPercentage() {
		return aptPercentage;
	}

	public void setAptPercentage(Integer aptPercentage) {
		this.aptPercentage = aptPercentage;
	}

	public Boolean getIsPassedinApt() {
		return isPassedinApt;
	}

	public void setIsPassedinApt(Boolean isPassedinApt) {
		this.isPassedinApt = isPassedinApt;
	}

	public Integer getTechPercentage() {
		return techPercentage;
	}

	public void setTechPercentage(Integer techPercentage) {
		this.techPercentage = techPercentage;
	}

	public Boolean getIsPassedInTech() {
		return isPassedInTech;
	}

	public void setIsPassedInTech(Boolean isPassedInTech) {
		this.isPassedInTech = isPassedInTech;
	}

	public Integer getOverallPercentage() {
		return overallPercentage;
	}

	public void setOverallPercentage(Integer overallPercentage) {
		this.overallPercentage = overallPercentage;
	}

	public Boolean getCurrentArrears() {
		return currentArrears;
	}

	public void setCurrentArrears(Boolean currentArrears) {
		this.currentArrears = currentArrears;
	}

	public Boolean getPrevArrears() {
		return prevArrears;
	}

	public void setPrevArrears(Boolean prevArrears) {
		this.prevArrears = prevArrears;
	}

	public String getReviewerResult() {
		return reviewerResult;
	}

	public void setReviewerResult(String reviewerResult) {
		this.reviewerResult = reviewerResult;
	}
	
}
