package com.chenjinchi.studentmanagement.repository;

import com.chenjinchi.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface StudentRepository extends Repository<Student, Integer> {

	@Query("SELECT student FROM Student student WHERE student.id =:studentId")
	@Transactional(readOnly = true)
	Student findById(@Param("studentId") String studentId);

	@Query("SELECT student FROM Student student WHERE lower(student.name) LIKE lower(concat('%',:name,'%') ) ")
	@Transactional(readOnly = true)
	Collection<Student> findByName(@Param("name") String name);

	void save(Student student);

	@Modifying
	@Transactional
	@Query("DELETE FROM Student student where student.id = :id")
	void deleteStudent(@Param("id") String id);

}
