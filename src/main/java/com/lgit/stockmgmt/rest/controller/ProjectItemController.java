package com.lgit.stockmgmt.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgit.stockmgmt.domain.ProjectItem;
import com.lgit.stockmgmt.rest.message.QueryProjectItemResponse;
import com.lgit.stockmgmt.service.ItemService;

@RestController
public class ProjectItemController {
	/*
	 * Controller - Service 연결
	 */
	@Autowired
	private ItemService itemService;

	
	
	//@RequestParam(value = "status", required = false) String portStatus)
	
	@RequestMapping(value = "/rest/project-item", method = RequestMethod.GET)
	public ResponseEntity<?> queryProjectItems() {
		System.out.println("queryProjectItem");
		
		List<ProjectItem> projectItems = itemService.getProjectItems();

		QueryProjectItemResponse response = new QueryProjectItemResponse();
		response.setProjectItems(projectItems);

		return new ResponseEntity<QueryProjectItemResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rest/project-item", method = RequestMethod.POST)
	public void createProjectItem(@RequestBody ProjectItem projectItem) {
		System.out.println("createProjectItem");
		
		
		itemService.setProjectItem(projectItem);
		
	}

	/*
	@RequestMapping(value = RestUriMapping.UNDERLAY_PORT, method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> queryPhysicalPorts(@PathVariable("nodeId") String nodeId,
			@RequestHeader(value = "X-Auth-Token") String tokenId,
			@RequestParam(value = "status", required = false) String portStatus) {

		String userName = tokenUtil.getUserNamebyTokenId(tokenId);
		String RequestURI;
		if (portStatus == null) {
			RequestURI = RestUriMapping.PORT.replace("{nodeId}", nodeId);
		} else {
			RequestURI = RestUriMapping.PORT.replace("{nodeId}", nodeId) + "?status=" + portStatus;
		}

		logger.info("RequestMethod:GET" + "," + " RequestURI:" + RequestURI + ", " + "RequestClient:" + userName);

		QueryPhysicalPortResponse response = new QueryPhysicalPortResponse();

		
		response.getHeader().setSuccessful(true);
		response.getHeader().setResultCode(0);
		

		List<PhysicalPort> physicalPorts = portService.getPhysicalPortsByNodeId(nodeId);
		response.setPhysicalPorts(physicalPorts);

		logger.info("Successful response for Request [GET] " + RequestURI);
		return new ResponseEntity<QueryPhysicalPortResponse>(response, HttpStatus.OK);
	}
	*/
}
