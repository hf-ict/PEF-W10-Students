package entity;

public class Student {

	private int id;
	private String lastname;
	private String firstname;
	
	public Student() {
		this(0,"","");
	}
	
	public Student(String lastname, String firstname) {
		this(0, lastname, firstname);
		
	}
	public Student(int id, String lastname, String firstname) {
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
}
