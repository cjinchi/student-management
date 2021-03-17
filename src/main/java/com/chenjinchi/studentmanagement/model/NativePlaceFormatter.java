package com.chenjinchi.studentmanagement.model;

import com.chenjinchi.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class NativePlaceFormatter implements Formatter<NativePlace> {

	private final StudentRepository students;

	@Autowired
	public NativePlaceFormatter(StudentRepository students) {
		this.students = students;
	}

	@Override
	public NativePlace parse(String s, Locale locale) throws ParseException {
		Collection<NativePlace> nativePlaces = this.students.findNativePlace();
		for (NativePlace nativePlace : nativePlaces) {
			if (nativePlace.getName().equals(s)) {
				return nativePlace;
			}
		}
		throw new ParseException("native place not found: " + s, 0);
	}

	@Override
	public String print(NativePlace nativePlace, Locale locale) {
		return nativePlace.getName();
	}

}
