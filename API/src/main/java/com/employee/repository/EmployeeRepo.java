package com.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import com.employee.DTO.EmployeeDTO;
import com.employee.model.Employee;

@Repository // intract with db , this interface take as a repository 
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

//	void save(Employee emp);

//	Object delete(Integer id);
		

}
