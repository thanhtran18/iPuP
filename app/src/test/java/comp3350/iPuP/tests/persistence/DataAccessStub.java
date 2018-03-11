//package comp3350.iPuP.tests.persistence;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//
//import comp3350.iPuP.objects.Booking;
//import comp3350.iPuP.objects.DateFormatter;
//import comp3350.iPuP.objects.ParkingSpot;
//import comp3350.iPuP.objects.TimeSlot;
//import comp3350.iPuP.persistence.DataAccess;
//
//public class DataAccessStub implements DataAccess
//{
//    private String dbName;
//    private String dbType = "stub";
//    private long dayslotCounter = 0;
//    private long timeslotCounter = 0;
//
//    private DateFormatter df = new DateFormatter();
//    private ArrayList<ParkingSpot> parkingSpots;
//
//    public DataAccessStub(String dbName)
//    {
//        this.dbName = dbName;
//    }
//
//    public void open(String dbPath) throws Exception
//    {
//        parkingSpots = new ArrayList<ParkingSpot>();
//        ParkingSpot tempSpot;
//        TimeSlot time;
//        String address;
//        String name;
//        String phone;
//        String email;
//        double rate;
//        Calendar calStart = Calendar.getInstance();
//        Calendar calEnd = Calendar.getInstance();
//
//        try {
//            address = "88 Plaza Drive";
//            name = "Rodney N-chris";
//            phone = "204-855-2342";
//            email = "poor&Homeless@gmail.com";
//            rate = 2;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 12:30:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "2 Chancellor Drive";
//            name = "Scott Gordon";
//            phone = "204-122-1234";
//            email = "scottfils@hotmail.ca";
//            rate = 4.50;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "30 Chancellor Drive";
//            name = "Roberto Nesta Marley";
//            phone = "204-577-3422";
//            email = "rastaLikebob@gmail.com";
//            rate = 0.10;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 11:30:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "60 Main Street";
//            name = "Avocado Stevenson";
//            phone = "601-122-1211";
//            email = "avocadoisgood@gmail.com";
//            rate = 5.25;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "566 Pasedina avenue";
//            name = "Brian Cambell";
//            phone = "204-419-8819";
//            email = "Brian1989@gmail.com";
//            rate = 4;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "1 Kings Drive";
//            name = "Jenifer Aniston";
//            phone = "604-253-1111";
//            email = "JeniferAniston@hotmail.ca";
//            rate = 7;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 20:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "20 Silverston Avenue";
//            name = "Christopher Turk";
//            phone = "204-236-2322";
//            email = "chrisTurk27@gmail.com";
//            rate = 5;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 13:30:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "20 Kings Drive";
//            name = "Tom Brady";
//            phone = "877-377-4234";
//            email = "theGoat@gmail.com";
//            rate = 10;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 15:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "1 Pembina Hwy";
//            name = "George H. Bush";
//            phone = "204-927-9277";
//            email = "myFamilyLikesWar@gmail.com";
//            rate = 10;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "100 St. Mary's Rd";
//            name = "Watson k. Smith";
//            phone = "204-245-3433";
//            email = "watsonK@gmail.com";
//            rate = 7;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 16:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "1691 Pembina Hwy";
//            name = "Victory Iyakoregha";
//            phone = "204-888-9292";
//            email = "Ivic565@gmail.com";
//            rate = 5;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 12:30:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "1338 Chancellor Drive";
//            name = "Micheal Douglas";
//            phone = "204-123-1234";
//            email = "theblondegirl22@hotmail.ca";
//            rate = 4.50;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "1122 Chancellor Drive";
//            name = "Kelly Cook";
//            phone = "204-566-7122";
//            email = "cookk@gmail.com";
//            rate = 4;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 11:30:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "91 Dalhousie Drive";
//            name = "Madison Fishburne";
//            phone = "204-345-4353";
//            email = "madifish101@gmail.com";
//            rate = 5.25;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 11:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "565 Pasedina Avenue";
//            name = "Ronald Regan";
//            phone = "204-419-1419";
//            email = "theDevil666@gmail.com";
//            rate = 100;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 16:30:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "1334 Pembina Hwy";
//            name = "Marilyn Monroe";
//            phone = "604-253-3424";
//            email = "iammonroe@hotmail.ca";
//            rate = 7;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 20:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "Brady Road Landfill";
//            name = "Donald Trump";
//            phone = "877-311-4974";
//            email = "lolattheUSA@gmail.com";
//            rate = 100;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 15:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "1 Pembina Hwy";
//            name = "George W. Bush";
//            phone = "204-927-9277";
//            email = "myFamilyLikesWar@gmail.com";
//            rate = 10;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 14:30:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            //TODO change this depending on how to add more spots
//            address = "29 St. Mary's Rd";
//            name = "Mary Watson";
//            phone = "204-242-2255";
//            email = "sherlock101@gmail.com";
//            rate = 4.50;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 20:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 02:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "1000 St. Mary's Rd";
//            name = "Philipe Coutinho";
//            phone = "204-124-2222";
//            email = "iAmAsnake10@hotmail.ca";
//            rate = 0.10;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 10:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 16:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//            address = "1000 St. Mary's Rd";
//            name = "Anne Coutinho";
//            phone = "204-124-2222";
//            email = "iAmAlsoAsnake10@hotmail.ca";
//            rate = 0.20;
//            calStart.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 17:30:00"));
//            calEnd.setTime(df.getSqlDateTimeFormat().parse("2018-06-11 19:00:00"));
//            time = new TimeSlot(calStart.getTime(), calEnd.getTime(), timeslotCounter++);
//            tempSpot = new ParkingSpot(address, name, phone, email, rate, time);
//            tempSpot.addDaySlot(new DaySlot(calStart.getTime(), calEnd.getTime(), dayslotCounter++));
//            parkingSpots.add(tempSpot);
//
//        } catch (ParseException e)
//        {
//            throw new Exception("Failed to open " + dbType +" database " + dbName + "!",e);
//        }
//
//        System.out.println("Opened " +dbType +" database " +dbName);
//    }
//
//
//    public void close()
//    {
//        System.out.println("Closed " +dbType +" database " +dbName);
//    }
//
//
//    public String insertDaySlot(String psID, DaySlot daySlot)
//    {
//        //TODO: implement insertDaySlot method
//        return null;
//    }
//
//    public String insertDaySlots(String psID, ArrayList<DaySlot> daySlots)
//    {
//        //TODO: implement insertDaySlots method
//        return null;
//    }
//
//    public String insertTimeSlot(String psID, Long dsID, Date start, Date end)
//    {
//        //TODO: implement insertTimeSlot method
//        return null;
//    }
//
//    public String insertTimeSlot(String psID, Long dsID, TimeSlot timeSlot)
//    {
//        return insertTimeSlot(psID, dsID, timeSlot.getStart(), timeSlot.getEnd());
//    }
//
//    public String insertTimeSlots(String psID, Long dsID, ArrayList<TimeSlot> timeSlots)
//    {
//        //TODO: implement insertTimeSlots method
//        return null;
//    }
//
//    public String insertParkingSpot(String user, ParkingSpot currentParkingSpot)
//    {
//        // not checking for duplicates yet
//        parkingSpots.add(currentParkingSpot);
//        return null;
//    }
//
//    public boolean insertUser(String username)
//    {
//        //TODO: implement insertUser method
//        return false;
//    }
//
//    public ArrayList<ParkingSpot> getParkingSpotsByDate(Date date)
//    {
//        ArrayList<ParkingSpot> parkingSpots = new ArrayList<ParkingSpot>();
//
//        //TODO: finish this method
//
//        return parkingSpots;
//    }
//
//    public ArrayList<ParkingSpot> getHostedSpotsOfGivenUser(String username)
//    {
//        ArrayList<ParkingSpot> parkingSpots = new ArrayList<ParkingSpot>();
//
//        //TODO: finish this method
//
//        return parkingSpots;
//    }
//
//    public ArrayList<Booking> getBookedSpotsOfGivenUser(String username)
//    {
//        ArrayList<Booking> bookings = new ArrayList<Booking>();
//
//        //TODO: finish this method!
//
//        return bookings;
//    }
//
//    public boolean setSpotToCancelled(String username, Long timeSlotId)
//    {
//        //TODO: finish this method
//        return false;
//    }
//
//    public ArrayList<ParkingSpot> getParkingSpots()
//    {
//        return parkingSpots;
//    }
//
////	public String setSpotToBooked(String spotID, String slotID)
////	{
////		String bookMessage = "Not Booked";
////	    for (int i = 0; i < parkingSpots.size(); i++)
////	    {
////            if (parkingSpots.get(i).isSpot(spotID, slotID))
////            {
////            	if (parkingSpots.get(i).isBooked())
////            	{
////            		bookMessage = "Already Booked";
////				}
////				else
////				{
////					parkingSpots.get(i).setBooked(true);
////					bookMessage = "Booked";
////				}
////                break;
////            }
////        }
////        return bookMessage;
////	}
//
//    public void clearSpotList()
//    {
//        parkingSpots.clear();
//    }
//
//
//}
