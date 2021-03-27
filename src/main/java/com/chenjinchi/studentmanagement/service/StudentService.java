package com.chenjinchi.studentmanagement.service;

import com.chenjinchi.studentmanagement.model.Department;
import com.chenjinchi.studentmanagement.model.Gender;
import com.chenjinchi.studentmanagement.model.NativePlace;
import com.chenjinchi.studentmanagement.model.Student;
import com.chenjinchi.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    @Cacheable(value = "gender")
    public List<Gender> findGender() {
        return this.repository.findGender();
    }

    @Cacheable(value = "department")
    public List<Department> findDepartment() {
        return this.repository.findDepartment();
    }

    @Cacheable(value = "native_place")
    public List<NativePlace> findNativePlace() {
        return this.repository.findNativePlace();
    }

    public Student findById(String studentId) {
        return this.repository.findById(studentId);
    }

    @Cacheable(value = "all_student")
    public Collection<Student> findAllStudents() {
        return this.repository.findAllStudents();
    }

    public Collection<Student> findByKeyword(String keyword) {
        return this.repository.findByKeyword(keyword);
    }

    @CacheEvict(value = "all_student", allEntries = true)
    public void save(Student student) {
        this.repository.save(student);
    }

    @CacheEvict(value = "all_student", allEntries = true)
    public void deleteStudent(String id) {
        this.repository.deleteStudent(id);
    }
}
