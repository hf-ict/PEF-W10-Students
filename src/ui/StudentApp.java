package ui;

import java.util.ArrayList;

import entity.Student;
import logic.StudentFacade;

public class StudentApp {

	public static void main(String[] args) {
		StudentFacade facade = new StudentFacade();
		
		Student student1 = new Student("Muster", "Hans");
		facade.addStudent(student1);
		Student student2 = new Student("Mustermann", "Max");
		facade.addStudent(student2);
		
		ArrayList<Student> students = facade.getAllStudents();
		for(Student student : students) {
			System.out.println(student.getId() + ": " + student.getLastname() + " " + student.getFirstname());
		}
	}
}
