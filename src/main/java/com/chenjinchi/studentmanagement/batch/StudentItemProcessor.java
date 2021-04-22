package com.chenjinchi.studentmanagement.batch;

import com.chenjinchi.studentmanagement.model.Student;
import org.springframework.batch.item.ItemProcessor;

public class StudentItemProcessor implements ItemProcessor<Student, Student> {

	@Override
	public Student process(Student student) throws Exception {
		return new Student(student.getId(), student.getName(), RandomDataGenerator.getDate(),
				RandomDataGenerator.getGender(), student.getPhone(), student.getDepartment());
	}

}
