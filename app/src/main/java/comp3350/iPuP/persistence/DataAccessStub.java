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
		address = "88 plaza dive";
		name="Rodney N-chris";
		phone="204-855-2342";
		email="poor&Homeless@gmail.com";
		rate=2;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 14, 45);
		address = "2 chancellor drive";
		name="Scott Gordon";
		phone="204-122-1234";
		email="scottfils@hotmail.ca";
		rate=4.50;
		tempSpot= new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 11, 20);
		address = "30 chancellor drive";
		name="Roberto Nesta Marley";
		phone="204-577-3422";
		email="rastaLikebob@gmail.com";
		rate=0.10;
		tempSpot= new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 11, 0);
		address = "60 main street";
		name="Avocado Stevenson";
		phone="601-122-1211";
		email="avocadoisgood@gmail.com";
		rate=5.25;
		tempSpot= new ParkingSpot(time , address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 45);
		address = "566 Pasedina avenue";
		name="Brian Cambell";
		phone="204-419-8819";
		email="Brian1989@gmail.com";
		rate=4;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 20, 0);
		address = "Jenifer Aniston";
		name="1 kings drive";
		phone="604-253-1111";
		email="JeniferAniston@hotmail.ca";
		rate=7;
		tempSpot= new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 13,30);
		address = "20 silverston avenue";
		name="Christopher Turk";
		phone="204-236-2322";
		email="chrisTurk27@gmail.com";
		rate=5;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 15, 0);
		address = "20 kings drive";
		name="Tom Brady";
		phone="877-377-4234";
		email="theGoat@gmail.com";
		rate=10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 14, 30);
		address = "1 pembina hwy";
		name="George H. Bush";
		phone="204-927-9277";
		email="myFamilyLikesWar@gmail.com";
		rate=10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 0);
		address = "100 st. mary's rd";
		name="Watson k. Smith";
		phone="204-245-3433";
		email="watsonK@gmail.com";
		rate=7;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);


		time = new ReservationTime(2018, 6, 11, 10, 30, 12, 30);
		address = "1691 pemina hwy";
		name="Victory Iyakoregha";
		phone="204-888-9292";
		email="Ivic565@gmail.com";
		rate=5;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 14, 45);
		address = "1338 chancellor drive";
		name="Micheal Douglas";
		phone="204-123-1234";
		email="theblondegirl22@hotmail.ca";
		rate=4.50;
		tempSpot= new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 11, 20);
		address = "1122 chancellor drive";
		name="Kelly Cook";
		phone="204-566-7122";
		email="cookk@gmail.com";
		rate=4;
		tempSpot= new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 11, 0);
		address = "91 Dalhousie drive";
		name="Madison Fishburne";
		phone="204-345-4353";
		email="madifish101@gmail.com";
		rate=5.25;
		tempSpot= new ParkingSpot(time , address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 45);
		address = "565 Pasedina avenue";
		name="Ronald Regan";
		phone="204-419-1419";
		email="theDevil666@gmail.com";
		rate=100;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 20, 0);
		address = "Marilyn Monroe";
		name="1334 Pembina Hwy";
		phone="604-253-3424";
		email="iammonroe@hotmail.ca";
		rate=7;
		tempSpot= new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 13,30);
		address = "200 pasedina avenue";
		name="Nelson Mandela";
		phone="204-234-2555";
		email="Nelson27@gmail.com";
		rate=5;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 15, 0);
		address = "Brady road landfill";
		name="Donald Trump";
		phone="877-311-4974";
		email="lolattheUSA@gmail.com";
		rate=100;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 14, 30);
		address = "1 pembina hwy";
		name="George W. Bush";
		phone="204-927-9277";
		email="myFamilyLikesWar@gmail.com";
		rate=10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 20, 30, 22, 0);
		address = "29 st. mary's rd";
		name="Mary Watson";
		phone="204-242-2255";
		email="sherlock101@gmail.com";
		rate=4.50;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 0);
		address = "1000 st. Mary's rd";
		name="Philipe Coutinho";
		phone="204-124-2222";
		email="iAmAsnake10@hotmail.ca";
		rate=0.10;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 17, 30, 19, 0);
		address = "1000 st. Mary's rd";
		name="Anne Coutinho";
		phone="204-124-2222";
		email="iAmAlsoAsnake10@hotmail.ca";
		rate=0.20;
		tempSpot= new ParkingSpot(time, address, name, phone, email,rate);
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