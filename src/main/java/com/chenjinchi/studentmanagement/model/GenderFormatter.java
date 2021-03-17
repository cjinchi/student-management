package com.chenjinchi.studentmanagement.model;

import com.chenjinchi.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class GenderFormatter implements Formatter<Gender> {
    private final StudentRepository students;

    @Autowired
    public GenderFormatter(StudentRepository students){
        this.students = students;
    }

    @Override
    public Gender parse(String s, Locale locale) throws ParseException {
        Collection<Gender> genders = this.students.findGender();
        for(Gender gender:genders){
            if(gender.getName().equals(s)){
                return gender;
            }
        }
        throw new ParseException("department not found: " + s, 0);
    }

    @Override
    public String print(Gender gender, Locale locale) {
        return gender.getName();
    }
}
