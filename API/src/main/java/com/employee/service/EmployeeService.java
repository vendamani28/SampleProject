package com.employee.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.employee.DTO.EmployeeDTO;
import com.employee.model.Employee;
import com.employee.model.EmployeeSalary;

public interface EmployeeService {

	public Employee getItem(Integer empid);

	public void addNewItem(Employee employeeData);

	public List<Employee> getAllEmployees();
	
	public List<Employee> uploadFiles(MultipartFile files);

    public boolean deleteItems(Integer id);
    
    

}
