package com.employee.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.employee.DTO.SalaryDTO;
import com.employee.model.EmployeeSalary;

@Service
public interface SalaryService {

	public List<EmployeeSalary> getsalaryDetails();
	
	public List<EmployeeSalary> findEmployeeSalary(SalaryDTO salaryDTO);

}
