package com.chenjinchi.studentmanagement.repository;

import com.chenjinchi.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    @Override
    Iterable<Student> findAll();

    @Override
    Optional<Student> findById(Integer integer);

    @Query("SELECT student FROM Student student  WHERE lower(concat(student.studentId,student.name,student.nativePlace,student.gender,student.department) ) LIKE lower(concat('%',:query,'%') ) ")
    @Transactional(readOnly = true)
    Iterable<Student> findByQuery(@Param("query") String query);

    @Query(value="SELECT IDENTITY()",nativeQuery =true)
    Integer getMaxId();

    @Override
    void delete(Student student);
}
