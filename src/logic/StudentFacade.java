package logic;

import java.util.ArrayList;

import data.*;
import entity.Student;

public class StudentFacade {

	private StudentDAO dao;

	public StudentFacade() {
		this.setDAO(Storage.MEMORY);
	}
	public ArrayList<Student> getAllStudents()  {
		return this.dao.getStudents();
	}

	public void addStudent(Student student) {
		this.dao.saveStudent(student);
	}

	private void setDAO(Storage storage) {
		switch (storage) {
			case XML:
				dao = new StudentDAOXML();
				break;

			case DB:
				dao = new StudentDAODB();
				break;

			default:
				dao = new StudentDAOMemory();
				break;
		}
	}


}
