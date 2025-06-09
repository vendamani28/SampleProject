package com.employee.DTO;

public record SalaryDTO(
		Integer employeeId, 
		Long basicSalary, 
		String salaryRange, 
		Integer employeeCount) 
{

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}


}

