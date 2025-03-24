package data;

import java.util.ArrayList;

import entity.Student;

public interface StudentDAO {

	ArrayList<Student> getStudents();
	
	void saveStudent(Student s);
}
