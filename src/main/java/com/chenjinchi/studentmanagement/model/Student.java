package com.chenjinchi.studentmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor()
@AllArgsConstructor
@Table(name = "students")
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "student_id")
	@NotEmpty
	private String studentId;

	@Column(name = "name")
	@NotEmpty
	private String name;

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate birthDate;

	@Column(name = "gender")
	private String gender;

	@Column(name = "native_place")
	private String nativePlace;

	@Column(name = "department")
	private String department;

	Student(String studentId, String name, LocalDate birthDate, String gender, String nativePlace, String department) {
		this.studentId = studentId;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.nativePlace = nativePlace;
		this.department = department;
	}

}
