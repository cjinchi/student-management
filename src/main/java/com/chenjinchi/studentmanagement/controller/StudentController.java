package com.chenjinchi.studentmanagement.controller;

import com.chenjinchi.studentmanagement.model.Department;
import com.chenjinchi.studentmanagement.model.Gender;
import com.chenjinchi.studentmanagement.model.NativePlace;
import com.chenjinchi.studentmanagement.model.Student;
import com.chenjinchi.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class StudentController {

	private final StudentRepository students;

	public StudentController(StudentRepository studentService) {
		this.students = studentService;
	}

	@ModelAttribute("genders")
	public Collection<Gender> populateGenders() {
		return this.students.findGender();
	}

	@ModelAttribute("departments")
	public Collection<Department> populateDepartments() {
		return this.students.findDepartment();
	}

	@ModelAttribute("native_places")
	public Collection<NativePlace> populateNativePlaces() {
		return this.students.findNativePlace();
	}

	@GetMapping("/")
	public String listAllStudents(Map<String, Object> model) {
		Collection<Student> results = this.students.findAllStudents();
		model.put("selections", results);
		model.put("title","全部有"+results.size()+"名学生");
		return "students/studentsList";
	}

	@GetMapping("/students/search")
	public String initSearchForm(Map<String, Object> model) {
		model.put("student", new Student());
		return "students/findStudents";
	}

	@GetMapping("/students")
	public String processSearchForm(Student student, BindingResult result, Map<String, Object> model) {
		String studentName = student.getName();
		Collection<Student> results = null;
		if (studentName == null) {
			studentName = "";
			results = this.students.findAllStudents();
		}else{
			results = this.students.findByKeyword(studentName);
		}

		if (results.isEmpty()) {
			result.rejectValue("name", "notFound", "not found");
			return "students/findStudents";
		}
		else {
			model.put("selections", results);
			model.put("title","关键字“"+studentName+"”共有"+results.size()+"个搜索结果");
			return "students/studentsList";
		}

	}

	@GetMapping("/students/new")
	public String initCreationForm(Map<String, Object> model) {
		Student student = new Student();
		model.put("student", student);
		return "students/createOrUpdateForm";
	}

	@PostMapping("/students/new")
	public String processCreationForm(@Valid Student student, BindingResult result) {
		if (result.hasErrors()) {
			return "students/createOrUpdateForm";
		}
		else {
			this.students.save(student);
			return "redirect:/students/" + student.getId();
		}
	}

	@GetMapping("/students/{studentId}/edit")
	public String initUpdateStudentForm(@PathVariable("studentId") String studentId, Model model) {
		Student student = this.students.findById(studentId);
		model.addAttribute(student);
		return "students/createOrUpdateForm";
	}


	@RequestMapping(value="/students/{studentId}/delete", method=GET)
	@ResponseBody
	public String processDeleteRequest(@PathVariable("studentId") String studentId, Model model){
		Student student = this.students.findById(studentId);
		if (student==null){
			return "notfound";
		}else{
			this.students.deleteStudent(studentId);
			return "done";
		}
	}

	@GetMapping("/students/{studentId}")
	public ModelAndView showStudent(@PathVariable("studentId") String studentId) {
		ModelAndView mav = new ModelAndView("students/studentDetails");
		Student student = this.students.findById(studentId);
		mav.addObject(student);
		return mav;
	}

	@PostMapping("/students/{id}/edit")
	public String processUpdateStudentForm(@Valid Student student, BindingResult result,
			@PathVariable("id") String id) {
		System.out.println("here1");
		if (result.hasErrors()) {
			System.out.println("5");
			return "students/createOrUpdateForm";
		}
		else {
			System.out.println("here2");
			if (!id.equals(student.getId())) {
				this.students.deleteStudent(id);
			}
			System.out.println("here3");
			this.students.save(student);
			System.out.println("here4");
			return "redirect:/students/" + student.getId();
		}
	}

}
