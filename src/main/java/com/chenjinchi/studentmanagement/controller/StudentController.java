package com.chenjinchi.studentmanagement.controller;

import com.chenjinchi.studentmanagement.model.Student;
import com.chenjinchi.studentmanagement.repository.StudentRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class StudentController {

	private final StudentRepository repository;

	public StudentController(StudentRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/students")
	ResponseEntity<CollectionModel<EntityModel<Student>>> find(@RequestParam(name = "q", required = false) String q) {
		Iterable<Student> studentsFound;
		if (q == null) {
			studentsFound = repository.findAll();
		}
		else {
			studentsFound = repository.findByQuery(q);
		}
		List<EntityModel<Student>> students = StreamSupport.stream(studentsFound.spliterator(), false)
				.map(student -> EntityModel.of(student,
						linkTo(methodOn(StudentController.class).findOne(student.getId())).withSelfRel(),
						linkTo(methodOn(StudentController.class).find(null)).withRel("students").expand()))
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				CollectionModel.of(students, linkTo(methodOn(StudentController.class).find(q)).withSelfRel().expand()));
	}

	@PostMapping("/students")
	ResponseEntity<?> newStudent(@RequestBody Student student) {
		Student savedStudent = repository.save(student);
		EntityModel<Student> studentResource = EntityModel.of(savedStudent,
				linkTo(methodOn(StudentController.class).findOne(savedStudent.getId())).withSelfRel());
		try {
			return ResponseEntity.created(new URI(studentResource.getRequiredLink(IanaLinkRelations.SELF).getHref()))
					.body(studentResource);
		}
		catch (URISyntaxException e) {
			return ResponseEntity.badRequest().body("Unable to create " + student);
		}
	}

	@GetMapping("/students/{id}")
	ResponseEntity<EntityModel<Student>> findOne(@PathVariable Integer id) {
		return repository.findById(id)
				.map(student -> EntityModel.of(student,
						linkTo(methodOn(StudentController.class).findOne(student.getId())).withSelfRel(),
						linkTo(methodOn(StudentController.class).find(null)).withRel("students").expand()))
				.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/students/{id}")
	ResponseEntity<?> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
		boolean isStudentPresent = repository.findById(id).isPresent();
		// resource is not existing && resource cannot be created
		if (!isStudentPresent && id != repository.getMaxId() + 1) {
			return ResponseEntity.status(409).build();
		}

		student.setId(id);
		repository.save(student);

		URI uri = null;
		try {
			uri = new URI(linkTo(methodOn(StudentController.class).findOne(id)).withSelfRel().getHref());
		}
		catch (URISyntaxException e) {
			return ResponseEntity.badRequest().body("Unable to update " + student);
		}

		if (isStudentPresent) {
			return ResponseEntity.ok().location(uri).build();
		}
		else {
			return ResponseEntity.created(uri).build();
		}
	}

	@DeleteMapping("/students/{id}")
	ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
		Optional<Student> optionalStudent = repository.findById(id);
		if (!optionalStudent.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		else {
			repository.delete(optionalStudent.get());
			return ResponseEntity.ok().build();
		}
	}

}
