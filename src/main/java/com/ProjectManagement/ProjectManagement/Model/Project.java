package com.ProjectManagement.ProjectManagement.Model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class Project {
	//projectId should be in the range of 100 to 10000
	@Range(min=100, max=10000, message="projectID must be between 100 and 10000")
	@NotNull(message="projecId is mandatory")
	int projectId;
	@Size(min=5, message="projectName must be equal to 5 or more characters.")
	String  projectName;

	//@NotNull will work with dates, strings and numbers. For @NotNull, validator is not required.
	@NotEmpty(message="startDate is mandatory")
	String	startDate;
	//@NotEmpty works only with Strings and collections
	@NotEmpty(message="endDate is mandatory")
	String	endDate;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "Project [projectName=" + projectName + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}
