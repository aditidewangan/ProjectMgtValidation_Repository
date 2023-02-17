package com.ProjectManagement.ProjectManagement.Controllers;

import java.net.URI;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ProjectManagement.ProjectManagement.Model.Project;
import com.ProjectManagement.ProjectManagement.Model.Projects;
import com.ProjectManagement.ProjectManagement.dao.ProjectsDAO;

@RestController
//@Controller
@RequestMapping("/Project") //Defines  a context
public class ProjectController extends ResponseEntityExceptionHandler{

	@Autowired
	private ProjectsDAO  projectsdao; //Good practice is to make Autowiring as private
	Logger  logger=Logger.getLogger("customlogger");
	@GetMapping (value="/listProjects", produces="application/json")
	//All end-point implementations must be non private by convention
	//And for none of them return types should be void.
	public Projects getProjects()
	{
		logger.info("Returned list of projects");
		return projectsdao.getAllProjects();
	}
	
	@GetMapping("/addNewProject")
	public ModelAndView addNewProject()
	{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("addProject"); //View
		mv.addObject("Today", new java.util.Date().toString()); //Model
		return mv;
	}
	
	@PostMapping(value="/addProject", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE}, produces="application/json")
	public ModelAndView addProject(@Valid Project p)
	{
		//@Valid causes validation to be done at the time addProject() method is called.
//Validations : projectname -min length to be 5 characters. Less than 5 it should not accept and prints invalid project name
//start-date - It should be later than 01-Jan-1994		
//end-date - should be lesser than 31-Dec-2030
		
		projectsdao.addProject(p);
		logger.info("Added project");
		//To keep track of Request-Response pair, ServletUriComponentsBuilder is used
		//Those requests that are sending data are chained to the response.
		//URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{ProjectName}").
				//buildAndExpand(p.getProjectName()).toUri();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addedProject");
		mv.addObject("ProjectId",p.getProjectId());
		mv.addObject("ProjectName",p.getProjectName());
		return mv;
	}
	
	@GetMapping("/deleteProj")
	public ModelAndView deleteProject() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("deleteProject");
		
		return mv;
	}
	
	
	@PostMapping(value="/deleteProject")
	public ModelAndView deleteProject(@RequestParam("projectname") String projectname)
	{
		projectsdao.deleteProject(projectname);
		logger.info("Project deleted...");
		//To keep track of Request-Response pair, ServletUriComponentsBuilder is used
		//URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{ProjectName}").
				//buildAndExpand(projectname).toUri();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("deletedProject");
		mv.addObject("ProjectName",projectname);
		return mv;
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid
	(MethodArgumentNotValidException m, HttpHeaders headers, HttpStatus status, WebRequest webreq)
	{
		System.out.println("Exception : " + m.getMessage());
		System.out.println("Total failed validations : " + m.getErrorCount());
		return super.handleMethodArgumentNotValid(m, headers, status, webreq);
	}
}
