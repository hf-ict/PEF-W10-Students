package data;

import java.util.ArrayList;

import entity.Student;

public class StudentDAOMemory implements StudentDAO {

	private int studentCount = 0;
	private ArrayList<Student> students = new ArrayList<Student>();
	
	public StudentDAOMemory(){
		//dummy students
		Student dummy1 = new Student(++studentCount, "Mus", "ter");
		Student dummy2 = new Student(++studentCount, "Mu", "ster");
		Student dummy3 = new Student(++studentCount, "M", "uster");
		students.add(dummy1);
		students.add(dummy2);
		students.add(dummy3);
	}
	
	@Override
	public ArrayList<Student> getStudents() {
		return (ArrayList<Student>) students.clone(); //return copy
	}

	@Override
	public void saveStudent(Student student) {
		student.setId(++studentCount);
		students.add(student);
	}

}
