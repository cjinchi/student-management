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

	@Column(name = "native_place")
	@NotNull
	private String nativePlace;

	@Column(name = "department")
	@NotNull
	private String department;

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

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
