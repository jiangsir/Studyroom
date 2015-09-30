package tw.jiangsir.Studyroom.DAOs;

import java.util.Hashtable;
import java.util.logging.Logger;
import tw.jiangsir.Studyroom.Objects.Student;

public class StudentService {
	Logger logger = Logger.getLogger(this.getClass().getName());
	protected static Hashtable<String, Student> CacheStudents = new Hashtable<String, Student>();

	public static Hashtable<String, Student> getCacheStudents() {
		return CacheStudents;
	}

	public static void setCacheStudents(
			Hashtable<String, Student> cacheStudents) {
		CacheStudents = cacheStudents;
	}

	public static void putCacheStudent(Student student) {
		CacheStudents.put(student.getStudentid(), student);
	}

	public static void clearCacheStudents() {
		CacheStudents.clear();
	}

	public static void clearCacheStudent(String studentid) {
		CacheStudents.remove(studentid);
	}

}
