DROP TABLE Students IF EXISTS ;

CREATE TABLE students(
    id INTEGER IDENTITY PRIMARY KEY,
    student_id VARCHAR(30) ,
    name VARCHAR(30),
    birth_date DATE,
    gender VARCHAR(30),
    native_place VARCHAR(100),
    department VARCHAR(100)
);
CREATE INDEX students_id ON students (id);
CREATE INDEX students_name ON students (name);
CREATE INDEX students_department_id ON students (department);
CREATE INDEX students_native_place_id ON students (native_place);