package com.lgit.stockmgmt.rest.message;

import java.util.ArrayList;
import java.util.List;

import com.lgit.stockmgmt.domain.ProjectItem;

public class QueryProjectItemResponse {
	private List<ProjectItem> projectItems;
	
	public QueryProjectItemResponse() {
		projectItems = new ArrayList<ProjectItem>();
	}

	public List<ProjectItem> getProjectItems() {
		return projectItems;
	}

	public void setProjectItems(List<ProjectItem> projectItems) {
		this.projectItems = projectItems;
	}
	
	
}
