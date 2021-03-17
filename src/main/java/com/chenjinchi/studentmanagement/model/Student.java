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

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// private Integer id;

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

	@ManyToOne
	@JoinColumn(name = "gender_id")
	private Gender gender;

	@ManyToOne
	@JoinColumn(name = "native_place_id")
	private NativePlace nativePlace;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public NativePlace getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(NativePlace nativePlace) {
		this.nativePlace = nativePlace;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
