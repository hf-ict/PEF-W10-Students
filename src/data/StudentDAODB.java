package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Student;

/**
 * Speichert und lädt Studenten von einer DB.
 * Dafür muss die DB inkl. Tabelle bereits existieren.
 * Dafür existiert ein Skript im Projekt. (student-table.txt)
 * Unschön an dieser Klasse: die Connection wird pro Aktion
 * geöffnet bzw. geschlossen.
 */
public class StudentDAODB implements StudentDAO {

	// attributes used for data connection
	private String conStr = "jdbc:mysql://localhost/studentdb?user=root";

	public StudentDAODB() {

		// init data driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Student> getStudents() {
		ArrayList<Student> retval = new ArrayList<Student>();
		
		try (Connection con = DriverManager.getConnection(this.conStr)) {
			
			PreparedStatement ps = con.prepareStatement("select * from students");
			ResultSet rs =  ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String n = rs.getString("lastname");
				String v = rs.getString("firstname");
				Student s = new Student(id, n, v);
				retval.add(s);
			
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retval;
	}

	@Override
	public void saveStudent(Student s) {
		try (Connection con = DriverManager.getConnection(this.conStr)) {
			String sql = 
					"insert into students" +
				    "(lastname, firstname)" +
				    "values (?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, s.getLastname());
			ps.setString(2, s.getFirstname());
			ps.execute();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
