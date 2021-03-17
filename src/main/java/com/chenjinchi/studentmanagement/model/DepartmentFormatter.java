package com.chenjinchi.studentmanagement.model;

import com.chenjinchi.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class DepartmentFormatter implements Formatter<Department> {

	private final StudentRepository students;

	@Autowired
	public DepartmentFormatter(StudentRepository students) {
		this.students = students;
	}

	@Override
	public Department parse(String s, Locale locale) throws ParseException {
		Collection<Department> departments = this.students.findDepartment();
		for (Department department : departments) {
			if (department.getName().equals(s)) {
				return department;
			}
		}
		throw new ParseException("department not found: " + s, 0);
	}

	@Override
	public String print(Department department, Locale locale) {
		return department.getName();
	}

}
