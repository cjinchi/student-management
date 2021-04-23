package com.chenjinchi.studentmanagement.batch;

import com.chenjinchi.studentmanagement.model.Student;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;

public class StudentExcelRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(RowSet rowSet) throws Exception {

        String[] cols = rowSet.getCurrentRow();
        Student student = new Student();

        student.setId(cols[0]);
        student.setName(cols[1]);
        student.setDepartment(cols[2]);
        student.setPhone(cols[3]);

        return student;
    }

}
