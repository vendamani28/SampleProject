package com.employee.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.employee.DTO.SalaryDTO;
import com.employee.model.EmployeeSalary;
import com.employee.service.SalaryService;


@RestController
@RequestMapping(value = "api")
public class SalaryControllor {
	
     private static org.slf4j.Logger logger = LoggerFactory.getLogger(SalaryControllor.class);	
     
     @Autowired
     public SalaryService salaryService;
     
	
	@CrossOrigin
	@GetMapping(value = "/getEmployeeSalary")
	@ResponseBody
	public List<EmployeeSalary> getEmployeeSalary() {
		logger.info("starts getEmployeeSalary ==============");
		    List<EmployeeSalary> employeeSalaries = salaryService.getsalaryDetails();
		 logger.info("ends getEmployeeSalary ===========");
		 return employeeSalaries;
	}
	
	
	@CrossOrigin
	@PostMapping(value = "/SearchSalaryDeatils")
	@ResponseBody
	 public List<EmployeeSalary> findEmployeeSalary(@RequestBody SalaryDTO salaryDTO) {
		logger.info("starts findEmployeeSalary===========");
		return  salaryService.findEmployeeSalary(salaryDTO);
		                              
	}
	
	

}
