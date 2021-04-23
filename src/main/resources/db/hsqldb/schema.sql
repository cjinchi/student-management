DROP TABLE Students IF EXISTS ;

CREATE TABLE students(
    id VARCHAR(30) PRIMARY KEY,
    name VARCHAR(30),
    birth_date DATE,
    gender VARCHAR(30) NOT NULL,
    phone VARCHAR(100) NOT NULL ,
    department VARCHAR(100) NOT NULL
);
CREATE INDEX students_id ON students (id);
CREATE INDEX students_name ON students (name);
CREATE INDEX students_department ON students (department);
CREATE INDEX students_phone ON students (phone);


