package com.employee.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Employee_Salaries")
public class EmployeeSalary {
	@Id
	@Column(name = "employee_Id")
	private Integer employeeId;
	
	@ManyToOne
	@JoinColumn(name = "employee_Id", referencedColumnName = "emp_id", insertable = false, updatable = false)
	private Employee employee;
	
	@Column(name = "basic_salary")
	private Long basicSalary;
	
	@Column(name = "salary_range")
	private String salaryRange;
	
	@Column(name = "employee_count")
	private Integer employeeCount;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Long getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(Long basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getSalaryRange() {
		return salaryRange;
	}

	public void setSalaryRange(String salaryRange) {
		this.salaryRange = salaryRange;
	}

	public Integer getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(Integer employeeCount) {
		this.employeeCount = employeeCount;
	}

	@Override
	public String toString() {
		return "EmployeeSalary [employeeId=" + employeeId + ", employee=" + employee + ", basicSalary=" + basicSalary
				+ ", salaryRange=" + salaryRange + ", employeeCount=" + employeeCount + "]";
	}

			
	
}
	

