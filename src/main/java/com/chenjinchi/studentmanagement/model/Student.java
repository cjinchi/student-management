package com.chenjinchi.studentmanagement.model;

import org.springframework.core.style.ToStringCreator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
	private LocalDate birthDate;

	// public Integer getId() {
	// return id;
	// }
	//
	// public void setId(Integer id) {
	// this.id = id;
	// }

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

	@Override
	public String toString() {
		return new ToStringCreator(this).append("new", this.id == null).toString();
	}

}
