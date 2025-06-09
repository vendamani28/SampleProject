package com.employee.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AcceptAction;
import org.apache.catalina.connector.Response;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employee.DTO.Responses;
import com.employee.constants.ApplicationConstant;
import com.employee.model.Employee;
import com.employee.service.EmployeeService;
import com.employee.serviceImpl.EmployeeServiceImpl;

import io.micrometer.core.instrument.util.StringUtils;
//import com.employee.DTO.EmployeeDTO;

//export excel
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController
@RequestMapping("/api")
public class EmployeeController<Employeedata> {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@CrossOrigin
	@GetMapping("/getAllEmployees")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@CrossOrigin
	@PostMapping(value = "/addEmployee", produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
//	@ResponseBody
	public Map<String, String> addNewEmployee(@RequestBody Employee employeeData) {
		logger.info("addNewEmployee starts");
		boolean alreadyExist = false;
		String message = "";
		Map<String, String> result = new HashMap<>();

		Employee employeeList = null;

		if (employeeData.getEmpId() != null) {
			// This block will only run if employeeList is NOT null and is an instance of
			// Employee
			employeeList = employeeService.getItem(employeeData.getEmpId());
			logger.info(employeeList != null ? employeeList.toString() : "No employee found with ID");
		}

		if (employeeList != null && employeeList instanceof Employee empList) { 
			alreadyExist = true;
			result.put(ApplicationConstant.STRING_STATUS, ApplicationConstant.STRING_ERROR);
			message = "EmployeeId already exists";
		} else {
//	    	This block runs if employeeList is null or not an instance of Employee
//			EmployeeDTO emp = new EmployeeDTO(employeeData.getId(), employeeData.getEmpId(),employeeData.getEmail(),employeeData.getUserName(),employeeData.getJobTitle());
			employeeService.addNewItem(employeeData);
			result.put(ApplicationConstant.STRING_STATUS, "OK");
			message = "Added new Employee";
		}

		result.put("message", message);
		logger.info("addNewEmployee ends");
		return result;
	}	
	
	
	
	@CrossOrigin
	@GetMapping(value = "/getAllExport", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	@ResponseBody // return object
	public ResponseEntity<byte[]> exportEmployeesToExcel() throws IOException { // http response body will be written

		List<Employee> listOfEmployees = employeeService.getAllEmployees();

		try {
			// Create an Excel workbook and sheet
			XSSFWorkbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Employees");

			// Header row
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("EmployeeId");
			header.createCell(1).setCellValue("Username");
			header.createCell(2).setCellValue("Email");
			header.createCell(3).setCellValue("Job Title");

			// Populate employee data
			int rowNum = 1;
			for (Employee emp : listOfEmployees) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(emp.getEmpId());
				row.createCell(1).setCellValue(emp.getUserName());
				row.createCell(2).setCellValue(emp.getEmail());
				row.createCell(3).setCellValue(emp.getJobTitle());
			}

			// Write to ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			workbook.close();

			byte[] excelFileAsBytes = outputStream.toByteArray();// convert bytearray

			// Return the Excel file as a response
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(
					MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
			headers.setContentDispositionFormData("attachment", "employees.xlsx");

			return ResponseEntity.ok().headers(headers).body(excelFileAsBytes);

		} catch (IOException e) {
			logger.error(e.getMessage());
			  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body(("Error creating Excel file: " + e.getMessage()).getBytes());

		}

	}
	
	@CrossOrigin
	@PostMapping(value = "/uploadEmployee", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
//	@ResponseBody
	public ResponseEntity<Map<String, String>> fileUpload(
			@RequestParam(value = "file", required = false) MultipartFile file) {
		logger.info("fileUpload starts");

		Map<String, String> response = new HashMap<>();

		if (file != null && !file.isEmpty()) {
			List<Employee> EmployeeList = employeeService.uploadFiles(file);

			if (EmployeeList != null && !EmployeeList.isEmpty()) {
				response.put(ApplicationConstant.STRING_STATUS, "success");
				response.put(ApplicationConstant.STRING_MESSAGE, "File uploaded successfully");
			} else {
				response.put(ApplicationConstant.STRING_STATUS, "Error");
				response.put(ApplicationConstant.STRING_MESSAGE, "File upload failed because the file is empty");
			}
		} else {
			response.put(ApplicationConstant.STRING_STATUS, "Error");
			response.put(ApplicationConstant.STRING_MESSAGE, "file upload failed");
		}

		logger.info("fileUpload ends");
		return ResponseEntity.ok(response);
	}	
	

	
	@CrossOrigin
	@DeleteMapping(value = "/deleteEmployees/{id}" , produces= MediaType.APPLICATION_JSON_VALUE, headers= "Accept=application/json" )
	public  ResponseEntity<Responses>  deleteAllEmployee(@PathVariable Integer id) {
		logger.info(" Starts deleteAllEmployee");
		
		 String message = "";
		
		  boolean  deleted  =  employeeService.deleteItems(id);
			logger.info("ends deleteAllEmployee");
			
		      if(Boolean.TRUE.equals(deleted)) {
		    	  Responses response = new Responses("Successfully deleted");
		    	 return ResponseEntity.accepted().body(response);
		      }
		      
		      else {
		    	 Responses response = new Responses("Could not delete with id :" + id);
		    	 return ResponseEntity.badRequest().body(response);
		      }
	}
	            

					
		

}
