package com.employee.DTO;

public record EmployeeDTO(
		Integer id,
		Integer empId,
		String userName,
		String email,
		String jobTitle)
{
	
//	@Override
//	public String toString() {
//	    return """
//	           EmployeeDTO = [
//	               id = %d,
//	               empId = %d,
//	               userName = %s,
//	               email = %s,
//	               jobTitle = %s
//	           ]
//	           """.formatted(id, empId, userName, email, jobTitle);
//	}



}


//JPA needs setters to update entity fields, but records don’t allow field modifications.
// No No-Arg Constructor – JPA requires a default constructor (zero-argument constructor), but records do not have it.
// Better Performance – More memory-efficient than normal Java classes.
// Less Boilerplate Code – No need to write getters, toString(), equals(), etc.
// Java automatically creates the constructor, getters, toString(), equals(), and hashCode().
//This is perfect for API responses and does not interact with the database.

