package com.chenjinchi.studentmanagement.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "students")
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@NotEmpty
	private String id;

	@Column(name = "name")
	@NotEmpty
	private String name;

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate birthDate;

	@Column(name = "gender")
	@NotNull
	private String gender;

	@Column(name = "phone")
	@NotNull
	private String phone;

	@Column(name = "department")
	@NotNull
	private String department;

	public Student() {
	}

	public Student(String id, String name, LocalDate birthDate, String gender, String phone, String department) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phone = phone;
		this.department = department;
	}

	public String getId() {
		return id;
	}

	public void setId(String studentId) {
		this.id = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String studentName) {
		this.name = studentName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
