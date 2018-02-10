package comp3350.iPuP.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import comp3350.iPuP.application.Main;
//import comp3350.iPuP.objects.Student;
//import comp3350.iPuP.objects.Course;
//import comp3350.iPuP.objects.SC;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.ReservationTime;

public class DataAccessStub
{
//	private String dbName;
//	private String dbType = "stub";
//
//	private ArrayList<Student> students;
//	private ArrayList<Course> courses;
//	private ArrayList<SC> scs;

	private List<ParkingSpot> parkingSpots;

	public DataAccessStub()
	{
		//nothing yet
	}

//	public DataAccessStub(String dbName)
//	{
//		this.dbName = dbName;
//	}

//	public DataAccessStub()
//	{
//		this(Main.dbName);
//	}

	public List<ParkingSpot>  open()
	{
		parkingSpots = new ArrayList<ParkingSpot>();
		ParkingSpot tempSpot;
        ReservationTime time;
		String address;
		String name;
		String phone;
		String email;
		double rate;

		time = new ReservationTime(2018, 6, 11, 10, 30, 12, 30);
		address = "20 place ave";
		name="this dude";
		phone="the number";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 14, 45);
		address = "21 place ave";
		name="this dude 1";
		phone="the number 1";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time,address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 11, 20);
		address = "22 place ave";
		name="this dude 2";
		phone="the number 2";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time,address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 11, 0);
		address = "23 place ave";
		name="this dude 3";
		phone="the number 3";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time , address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 45);
		address = "24 place ave";
		name="this dude 4";
		phone="the number 4";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 20, 0);
		address = "25 place ave";
		name="this dude 5";
		phone="the number 5";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time,address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 13,30);
		address = "26 place ave";
		name="this dude 6";
		phone="the number 6";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 15, 0);
		address = "27 place ave";
		name="this dude 7";
		phone="the number 7";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 14, 30);
		address = "28 place ave";
		name="this dude 8";
		phone="the number 8";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 0);
		address = "29 place ave";
		name="this dude 9";
		phone="the number 9";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 0);
		address = "30 place ave";
		name="this dude 10";
		phone="the number 10";
		email="theguy@host.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate, false);
		parkingSpots.add(tempSpot);

		System.out.println("Initialized the array of ParkingSpot object!");
		return parkingSpots;
	}

//	public void open(String dbName)
//	{
//		Student student;
//		Course course;
//		SC mySC;
//
//		students = new ArrayList<Student>();
//		student = new Student("100", "Gary Chalmers", "Management");
//		students.add(student);
//		student = new Student("200", "Selma Bouvier", "University Centre");
//		students.add(student);
//		student = new Student("300", "Arnie Pye", "Frank Kennedy");
//		students.add(student);
//		student = new Student("400", "Mary Bailey", "Off Campus");
//		students.add(student);
//
//		courses = new ArrayList<Course>();
//		course = new Course("COMP3010", "Distributed Computing");
//		courses.add(course);
//		course = new Course("COMP3020", "Human-Computer Interaction");
//		courses.add(course);
//		course = new Course("COMP3350", "Software Development");
//		courses.add(course);
//		course = new Course("COMP3380", "Databases");
//		courses.add(course);
//
//		scs = new ArrayList<SC>();
//		mySC = new SC("100", "COMP3010", "Gary Chalmers", "Distributed Computing", "C+");
//		scs.add(mySC);
//		mySC = new SC("200", "COMP3010", "Selma Bouvier", "Distributed Computing", "A+");
//		scs.add(mySC);
//		mySC = new SC("100", "COMP3350", "Gary Chalmers", "Software Development", "A");
//		scs.add(mySC);
//		mySC = new SC("300", "COMP3350", "Arnie Pye", "Software Development", "B");
//		scs.add(mySC);
//		mySC = new SC("100", "COMP3380", "Gary Chalmers", "Databases", "A");
//		scs.add(mySC);
//		mySC = new SC("200", "COMP3380", "Selma Bouvier", "Databases", "B");
//		scs.add(mySC);
//
//		System.out.println("Opened " +dbType +" database " +dbName);
//	}
//
	public void close()
	{
		System.out.println("closed array of ParkingSpot object!");
	}
//
//	public String getStudentSequential(List<Student> studentResult)
//	{
//        studentResult.addAll(students);
//		return null;
//	}
//
//	public ArrayList<Student> getStudentRandom(Student currentStudent)
//	{
//		ArrayList<Student> newStudents;
//		int index;
//
//		newStudents = new ArrayList<Student>();
//		index = students.indexOf(currentStudent);
//		if (index >= 0)
//		{
//			newStudents.add(students.get(index));
//		}
//		return newStudents;
//	}

	public String insertParkingSpot(ParkingSpot currentParkingSpot)
	{
		// not checking for duplicates yet
		parkingSpots.add(currentParkingSpot);
		return null;
	}
//	public String insertStudent(Student currentStudent)
//	{
//		// don't bother checking for duplicates
//		students.add(currentStudent);
//		return null;
//	}
//
//	public String updateStudent(Student currentStudent)
//	{
//		int index;
//
//		index = students.indexOf(currentStudent);
//		if (index >= 0)
//		{
//			students.set(index, currentStudent);
//		}
//		return null;
//	}
//
//	public String deleteStudent(Student currentStudent)
//	{
//		int index;
//
//		index = students.indexOf(currentStudent);
//		if (index >= 0)
//		{
//			students.remove(index);
//		}
//		return null;
//	}
//
//	public String getCourseSequential(List<Course> courseResult)
//	{
//        courseResult.addAll(courses);
//		return null;
//	}
//
//	public ArrayList<Course> getCourseRandom(Course currentCourse)
//	{
//		ArrayList<Course> newCourses;
//		int index;
//
//		newCourses = new ArrayList<Course>();
//		index = courses.indexOf(currentCourse);
//		if (index >= 0)
//		{
//			newCourses.add(courses.get(index));
//		}
//		return newCourses;
//	}
//
//	public String insertCourse(Course currentCourse)
//	{
//		// don't bother checking for duplicates
//		courses.add(currentCourse);
//		return null;
//	}
//
//	public String updateCourse(Course currentCourse)
//	{
//		int index;
//
//		index = courses.indexOf(currentCourse);
//		if (index >= 0)
//		{
//			courses.set(index, currentCourse);
//		}
//		return null;
//	}
//
//	public String deleteCourse(Course currentCourse)
//	{
//		int index;
//
//		index = courses.indexOf(currentCourse);
//		if (index >= 0)
//		{
//			courses.remove(index);
//		}
//		return null;
//	}
//
//	public ArrayList<SC> getSC(SC currentSC)
//	{
//		ArrayList<SC> newSCs;
//		SC sc;
//		int counter;
//
//		// get the SC objects with the same studentID as currentSC
//		newSCs = new ArrayList<SC>();
//		for (counter=0; counter<scs.size(); counter++)
//		{
//			sc = scs.get(counter);
//			if (sc.getStudentID().equals(currentSC.getStudentID()))
//			{
//				newSCs.add(scs.get(counter));
//			}
//		}
//		return newSCs;
//	}
//
//	public ArrayList<SC> getCS(SC currentSC)
//	{
//		ArrayList<SC> newSCs;
//		SC cs;
//		int counter;
//
//		// get the SC objects with the same courseID as currentSC
//		newSCs = new ArrayList<SC>();
//		for (counter=0; counter<scs.size(); counter++)
//		{
//			cs = scs.get(counter);
//			if (cs.getCourseID().equals(currentSC.getCourseID()))
//			{
//				newSCs.add(scs.get(counter));
//			}
//		}
//		return newSCs;
//	}
}