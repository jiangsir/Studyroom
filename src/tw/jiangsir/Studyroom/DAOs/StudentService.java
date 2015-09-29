package tw.jiangsir.Studyroom.DAOs;

import java.util.Hashtable;
import java.util.logging.Logger;
import tw.jiangsir.Studyroom.Objects.Student;

public class StudentService {
	Logger logger = Logger.getLogger(this.getClass().getName());
	protected static Hashtable<String, Student> TodayStudents = new Hashtable<String, Student>();

	public static Hashtable<String, Student> getTodayStudents() {
		return TodayStudents;
	}

	public static void setTodayStudents(
			Hashtable<String, Student> todayStudents) {
		TodayStudents = todayStudents;
	}

}
