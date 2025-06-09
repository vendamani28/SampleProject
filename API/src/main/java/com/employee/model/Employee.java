package com.employee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "employee_data")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "employee_data_id_seq_gen")
    @SequenceGenerator(name = "employee_data_id_seq_gen", sequenceName = "employee_data_id_seq", allocationSize = 1)
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "emp_id")
	private Integer empId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "email")
	private String email;

	@Column(name = "job_title")
	private String jobTitle;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", empId=" + empId + ", userName=" + userName + ", email=" + email + ", jobTitle="
				+ jobTitle + "]";
	}
	
	

	

}
