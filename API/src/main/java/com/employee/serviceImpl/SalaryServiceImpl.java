package com.employee.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.mapping.Collection;
import org.hibernate.query.NativeQuery;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.DTO.SalaryDTO;
import com.employee.model.EmployeeSalary;
import com.employee.repository.SalaryRepo;
import com.employee.service.SalaryService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class SalaryServiceImpl implements SalaryService {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(SalaryServiceImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	 @Autowired
	 private SalaryRepo salaryRepo;
	
	@Override
	@Transactional
	public List<EmployeeSalary> getsalaryDetails() {
		logger.info("getsalaryDetails starts");
		List<EmployeeSalary> employeeSalaries = new ArrayList<>();
		
		try {
			   
//			String sql = "Select * from employee_salary es join employee_data ed on es.employeeId = ed.emp_id";
//			String sql = "SELECT es.* FROM employee_salary es JOIN employee_data ed ON es.employeeId = ed.emp_id";
			String sql = """
				    SELECT es.employee_Id, es.basic_salary, es.salary_range, 
				    es.employee_count, ed.emp_id, ed.user_name, ed.email, ed.job_title
				    FROM employee_salary es
				    JOIN employee_data ed ON es.employee_Id = ed.emp_id
				    """;
			Query query = entityManager.createNativeQuery(sql, EmployeeSalary.class);
			employeeSalaries = query.getResultList();
			
//			String jpql = "SELECT es FROM EmployeeSalary es JOIN FETCH es.employee";
//			Query query = session.createQuery(jpql, EmployeeSalary.class);
//		        employeeSalaries = query.getResultList();

		}
		catch (Exception e) {
			logger.error("display error message =====>" + e.getMessage());
		}
		
		logger.info("getsalaryDetails ends");
		return employeeSalaries;
	}
	
	
	
	
//	@Override
//	@Transactional
//	public List<EmployeeSalary> findEmployeeSalary(SalaryDTO salaryDTO) {
//		logger.info("starts findEmployeeSalary==============> ");
//		
//		List<EmployeeSalary> employeeSalaries = salaryRepo.getAllRecords();
//		 
//		   // If all fields are empty (null or empty string), return all records
//			if ((salaryDTO.employeeId() == null || salaryDTO.employeeId().toString().strip().isEmpty())
//					&& (salaryDTO.basicSalary() == null || salaryDTO.basicSalary().toString().strip().isEmpty())
//					&& (salaryDTO.salaryRange() == null || salaryDTO.salaryRange().toString().isEmpty())
//					&& (salaryDTO.employeeCount() == null || salaryDTO.employeeCount().toString().strip().isEmpty())) {
//				logger.info("All search fields are empty. Returning all records.");
//				return employeeSalaries;
//			}
		
		   
//		   // Apply filters based on provided search criteria
//		    		 return employeeSalaries.stream()
//		    				    .filter(emp -> salaryDTO.employeeId() == null || 
//		    				                   salaryDTO.employeeId().toString().isEmpty() || 
//		    				                   Objects.equals(emp.getEmployeeId(), salaryDTO.employeeId())) // Use Objects.equals()
//		    				    
//		    				    .filter(emp -> salaryDTO.basicSalary() == null || 
//		    				                   salaryDTO.basicSalary().toString().isEmpty() || 
//		    				                   Objects.equals(emp.getBasicSalary(), salaryDTO.basicSalary()))
//		    				    
//		    				    .filter(emp -> salaryDTO.salaryRange() == null || 
//		    				                   salaryDTO.salaryRange().isEmpty() || 
//		    				                   emp.getSalaryRange().equals(salaryDTO.salaryRange()))
//		    				    
//		    				    .filter(emp -> salaryDTO.employeeCount() == null || 
//		    				                   emp.getEmployeeCount().equals(salaryDTO.employeeCount()))
//		    				    .toList();


		                
//		logger.info("ends findEmployeeSalary =============>");
//		return employeeSalaries;
//	}
	
	
	
	
	@Override
	@Transactional
	public List<EmployeeSalary> findEmployeeSalary(SalaryDTO salaryDTO) {
	    logger.info("Start findEmployeeSalary");

	    List<EmployeeSalary> employeeSalaries = salaryRepo.getAllRecords(); // Fetch all

	    // If all fields are empty, null, whitspace then  condition is true so return all records
	    if ((salaryDTO.employeeId() == null) &&
	        (salaryDTO.basicSalary() == null) &&
	        (salaryDTO.salaryRange() == null || salaryDTO.salaryRange().toString().isEmpty()) &&
	        (salaryDTO.employeeCount() == null)) {
	        return employeeSalaries;
	    }

	    List<EmployeeSalary> filteredList = new ArrayList<>();

	    
//	    condition false apply filters
	    for (EmployeeSalary emp : employeeSalaries) {
	        boolean matches = true;

	        if (salaryDTO.employeeId() != null && !salaryDTO.employeeId().toString().isEmpty()) {
	            if (!emp.getEmployeeId().equals(salaryDTO.employeeId())) {
	                matches = false;
	            }
	        }

	        if (salaryDTO.basicSalary() != null) {
	            if (!Objects.equals(emp.getBasicSalary(), salaryDTO.basicSalary())) {
	                matches = false;
	            }
	        }

	        if (salaryDTO.salaryRange() != null && !salaryDTO.salaryRange().isBlank()) {
	            if (!emp.getSalaryRange().equals(salaryDTO.salaryRange())) {
	                matches = false;
	            }
	        }

	        if (salaryDTO.employeeCount() != null) {
	            if (!emp.getEmployeeCount().equals(salaryDTO.employeeCount())) {
	                matches = false;
	            }
	        }

	        if (matches) {
	            filteredList.add(emp);
	        }
	    }

	    logger.info("End findEmployeeSalary, Found: " + filteredList.size() + " records");
	    return filteredList;
	}


}
