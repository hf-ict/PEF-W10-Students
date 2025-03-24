package data;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.ByteConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.converters.basic.ShortConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import entity.Student;

public class StudentDAOXML implements StudentDAO {

	private XStream xstream;
	private String xmlfilename = "students.xml";

	public StudentDAOXML() {
		// initialize xstream
		xstream = new XStream(new StaxDriver() {
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out, "  ");
			}
		}) {
			// only register the converters we need; other converters generate a private
			// access warning in the console on Java9+...
			@Override
			protected void setupConverters() {
				registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
				registerConverter(new IntConverter(), PRIORITY_NORMAL);
				registerConverter(new FloatConverter(), PRIORITY_NORMAL);
				registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
				registerConverter(new LongConverter(), PRIORITY_NORMAL);
				registerConverter(new ShortConverter(), PRIORITY_NORMAL);
				registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
				registerConverter(new ByteConverter(), PRIORITY_NORMAL);
				registerConverter(new StringConverter(), PRIORITY_NORMAL);
				registerConverter(new DateConverter(), PRIORITY_NORMAL);
				registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
				registerConverter(new ReflectionConverter(getMapper(), getReflectionProvider()), PRIORITY_VERY_LOW);
			}
		};
		// clear out existing permissions and set own ones
		xstream.addPermission(NoTypePermission.NONE);
		// allow some basics
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypeHierarchy(Collection.class);
		// allow own classes
		xstream.allowTypes(new Class[] { Student.class });
	}

	@Override
	public ArrayList<Student> getStudents() {
		String fromFile = "";
		try {
			fromFile = new String(Files.readAllBytes(Paths.get(xmlfilename)));//not ok for large files
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Student> retval = new ArrayList<Student>();
		if (fromFile.length() > 0) {
			retval = (ArrayList<Student>) xstream.fromXML(fromFile);
		}
		return retval;
	}

	@Override
	public void saveStudent(Student s) {
		int studentCount = 0;
		ArrayList<Student> students = this.getStudents();
		if (students.size() > 0) {
			//determine actual highest id
			Student studMaxId = Collections.max(students, Comparator.comparing(Student::getId));
			studentCount = studMaxId.getId();
		}
		s.setId(++studentCount);
		students.add(s);

		String xml = xstream.toXML(students);
		try {
			Files.write(Paths.get(xmlfilename), xml.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
