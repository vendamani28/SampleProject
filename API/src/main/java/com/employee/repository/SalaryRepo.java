package com.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.employee.model.EmployeeSalary;

@Repository
public interface SalaryRepo extends JpaRepository<EmployeeSalary, Integer> {

	
	 @Query(nativeQuery=true , value="select * from employee_salary")
	 List<EmployeeSalary> getAllRecords();

}
