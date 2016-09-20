/**
 *  DB의 tb_project에 매칭되는 클래스 
 *  
 *  @author jaeyong1.park
 *  
 */
package com.lgit.stockmgmt.domain;

public class ProjectItem {
	private String projectCode;
	private String projectName;
	private String projectOwnerId;
	private String projectShipperId;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectOwnerId() {
		return projectOwnerId;
	}

	public void setProjectOwnerId(String projectOwnerId) {
		this.projectOwnerId = projectOwnerId;
	}

	public String getProjectShipperId() {
		return projectShipperId;
	}

	public void setProjectShipperId(String projectShipperId) {
		this.projectShipperId = projectShipperId;
	}

	public ProjectItem(String projectCode, String projectName, String projectOwnerId, String projectShipperId) {
		super();
		this.projectCode = projectCode;
		this.projectName = projectName;
		this.projectOwnerId = projectOwnerId;
		this.projectShipperId = projectShipperId;
	}

	@Override
	public String toString() {
		return "ProjectItem [projectCode=" + projectCode + ", projectName=" + projectName + ", projectOwnerId="
				+ projectOwnerId + ", projectShipperId=" + projectShipperId + "]";
	}

}
