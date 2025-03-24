package data;

public class Factory {

	public static StudentDAO getDAO(Storage storage) {
		StudentDAO dao;
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
		
		return dao;
	}
}
