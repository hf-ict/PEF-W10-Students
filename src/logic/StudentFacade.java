package logic;

import java.util.ArrayList;

import data.Factory;
import data.Storage;
import data.StudentDAO;
import entity.Student;

public class StudentFacade {

	private StudentDAO dao = Factory.getDAO(Storage.XML);

	public ArrayList<Student> getAllStudents()  {
		return this.dao.getStudents();
	}
	
	public void addStudent(Student s) {
		this.dao.saveStudent(s);
	}
	
}
