package com.employee.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import com.employee.DTO.EmployeeDTO;
import com.employee.model.Employee;
import com.employee.model.EmployeeSalary;
import com.employee.repository.EmployeeRepo;
import com.employee.service.EmployeeService;
import org.hibernate.Session;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	public static org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepo employeeRepo;
	

	public List<Employee> getAllEmployees() {
		return employeeRepo.findAll().stream().toList();
	}

	@Override
	public Employee getItem(Integer empid) {
		return employeeRepo.findById(empid).orElse(null);
	}

	@Override
	public void addNewItem(Employee emp) {
		employeeRepo.save(emp);

	}

	public List<Employee> uploadFiles(MultipartFile files) {
		logger.info("starts uploadFiles");

		List<Employee> employeeList = new ArrayList<>();

		try {
			// create new workbook
			XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);

			int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();

			for (int i = firstRow + 1; i <= lastRow; i++) { //
				Row row = sheet.getRow(i);

				if (row != null) {
					Employee employee = new Employee();

					for (int j = 0; j <= 4; j++) {
						Cell cell = row.getCell(j);

						if (cell != null) {

							switch (j) {
							case 0 -> employee.setId((int) cell.getNumericCellValue());
							case 1-> employee.setEmpId((int) cell.getNumericCellValue());
							case 2 -> employee.setUserName(cell.getStringCellValue());
							case 3 -> employee.setEmail(cell.getStringCellValue());
							case 4 -> employee.setJobTitle(cell.getStringCellValue());
							default -> throw new IllegalStateException("Unexpected column index: " + j);
							}

						}
					}
					if (!employeeRepo.existsById(employee.getId())) {
						 employeeRepo.save(employee);
						employeeList.add(employee);
					}
				}
			}
			workbook.close();

		} catch (IOException e) {
			logger.error("Error processing in file: ", e);
		}

		logger.info("ends uploadFiles");
		return employeeList;

	}
	
	
	
	
	
	public boolean deleteItems(Integer id){
		logger.info("Starts deleteItems");
	    boolean	isDeleted = false;
	    
		   if (employeeRepo.existsById(id)) {
			   employeeRepo.deleteById(id);
			   isDeleted = true;
		   }
		   else {
			   
			   logger.warn("id not found :" + id);
		   }
		  
		logger.info("ends deleteItems");
		return isDeleted;
	}
	
	
	 

}














//if(row.getCell(0) != null) {
//	  employee.setEmpId((int)row.getCell(0).getNumericCellValue());
//  }
//
//if (row.getCell(1) != null) {
//      employee.setUserName(row.getCell(1).getStringCellValue());
//  }
//  if (row.getCell(2) != null) {
//      employee.setEmail(row.getCell(2).getStringCellValue());
//  }
//  if (row.getCell(3) != null) {
//      employee.setJobTitle(row.getCell(3).getStringCellValue());
//  }

//employee.setEmpId((int)row.getCell(0).getNumericCellValue());
//employee.setUserName(row.getCell(1).getStringCellValue());
//employee.setEmail(row.getCell(2).getStringCellValue());
//employee.setJobTitle(row.getCell(3).getStringCellValue());
