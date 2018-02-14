package comp3350.iPuP.persistence;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import comp3350.iPuP.application.Main;
import comp3350.iPuP.objects.ParkingSpot;
import comp3350.iPuP.objects.ReservationTime;

public class DataAccessStub
{
	private List<ParkingSpot> parkingSpots;

	public DataAccessStub()
	{
        parkingSpots = new ArrayList<ParkingSpot>();
	}

	public List<ParkingSpot> open()
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
		name = "Rodney N-chris";
		phone = "204-855-2342";
		email = "poor&Homeless@gmail.com";
		rate = 2;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 14, 45);
		address = "2 chancellor drive";
		name = "Scott Gordon";
		phone = "204-122-1234";
		email = "scottfils@hotmail.ca";
		rate = 4.50;
		tempSpot = new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 11, 20);
		address = "30 chancellor drive";
		name = "Roberto Nesta Marley";
		phone = "204-577-3422";
		email = "rastaLikebob@gmail.com";
		rate = 0.10;
		tempSpot = new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 11, 0);
		address = "60 main street";
		name = "Avocado Stevenson";
		phone = "601-122-1211";
		email = "avocadoisgood@gmail.com";
		rate = 5.25;
		tempSpot = new ParkingSpot(time , address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 45);
		address = "566 Pasedina avenue";
		name = "Brian Cambell";
		phone = "204-419-8819";
		email = "Brian1989@gmail.com";
		rate = 4;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 20, 0);
		address = "Jenifer Aniston";
		name = "1 kings drive";
		phone = "604-253-1111";
		email = "JeniferAniston@hotmail.ca";
		rate = 7;
		tempSpot = new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 13,30);
		address = "20 silverston avenue";
		name = "Christopher Turk";
		phone = "204-236-2322";
		email = "chrisTurk27@gmail.com";
		rate = 5;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 15, 0);
		address = "20 kings drive";
		name = "Tom Brady";
		phone = "877-377-4234";
		email = "theGoat@gmail.com";
		rate = 10;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 14, 30);
		address = "1 pembina hwy";
		name = "George H. Bush";
		phone = "204-927-9277";
		email = "myFamilyLikesWar@gmail.com";
		rate = 10;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 0);
		address = "100 st. mary's rd";
		name = "Watson k. Smith";
		phone = "204-245-3433";
		email = "watsonK@gmail.com";
		rate = 7;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);


		time = new ReservationTime(2018, 6, 11, 10, 30, 12, 30);
		address = "1691 pemina hwy";
		name = "Victory Iyakoregha";
		phone = "204-888-9292";
		email = "Ivic565@gmail.com";
		rate = 5;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 14, 45);
		address = "1338 chancellor drive";
		name = "Micheal Douglas";
		phone = "204-123-1234";
		email = "theblondegirl22@hotmail.ca";
		rate = 4.50;
		tempSpot = new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 11, 20);
		address = "1122 chancellor drive";
		name = "Kelly Cook";
		phone = "204-566-7122";
		email = "cookk@gmail.com";
		rate = 4;
		tempSpot = new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 11, 0);
		address = "91 Dalhousie drive";
		name = "Madison Fishburne";
		phone = "204-345-4353";
		email = "madifish101@gmail.com";
		rate = 5.25;
		tempSpot = new ParkingSpot(time , address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 45);
		address = "565 Pasedina avenue";
		name = "Ronald Regan";
		phone = "204-419-1419";
		email = "theDevil666@gmail.com";
		rate = 100;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 20, 0);
		address = "Marilyn Monroe";
		name = "1334 Pembina Hwy";
		phone = "604-253-3424";
		email = "iammonroe@hotmail.ca";
		rate = 7;
		tempSpot = new ParkingSpot(time,address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 13,30);
		address = "200 pasedina avenue";
		name = "Nelson Mandela";
		phone = "204-234-2555";
		email = "Nelson27@gmail.com";
		rate = 5;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 15, 0);
		address = "Brady road landfill";
		name = "Donald Trump";
		phone = "877-311-4974";
		email = "lolattheUSA@gmail.com";
		rate = 100;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 14, 30);
		address = "1 pembina hwy";
		name = "George W. Bush";
		phone = "204-927-9277";
		email = "myFamilyLikesWar@gmail.com";
		rate = 10;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 20, 30, 22, 0);
		address = "29 st. mary's rd";
		name = "Mary Watson";
		phone = "204-242-2255";
		email = "sherlock101@gmail.com";
		rate = 4.50;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 10, 30, 16, 0);
		address = "1000 st. Mary's rd";
		name = "Philipe Coutinho";
		phone = "204-124-2222";
		email = "iAmAsnake10@hotmail.ca";
		rate = 0.10;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		time = new ReservationTime(2018, 6, 11, 17, 30, 19, 0);
		address = "1000 st. Mary's rd";
		name = "Anne Coutinho";
		phone = "204-124-2222";
		email = "iAmAlsoAsnake10@hotmail.ca";
		rate = 0.20;
		tempSpot = new ParkingSpot(time, address, name, phone, email,rate);
		parkingSpots.add(tempSpot);

		System.out.println("Initialized the array of ParkingSpot object!");
		return parkingSpots;
	}


	public void close()
    {
        System.out.println("Closed parkingSpots arraylist!");
    }



    public String insertParkingSpot(ParkingSpot currentParkingSpot)
    {
        // not checking for duplicates yet
        parkingSpots.add(currentParkingSpot);
        return null;
    }


	public List<ParkingSpot> getParkingSpots()
	{
		return parkingSpots;
	}


	public String setSpotToBooked(String id)
	{
		String bookMessage = "Not Booked";
	    for (int i = 0; i < parkingSpots.size(); i++)
	    {
            if (parkingSpots.get(i).getId().equals(id))
            {
            	if (parkingSpots.get(i).isBooked())
            	{
            		bookMessage = "Already Booked";
				}
				else
				{
					parkingSpots.get(i).setBooked(true);
					bookMessage = "Booked";
				}
                break;
            }
        }
        return bookMessage;
	}

	public void clearSpotList()
	{
		parkingSpots.clear();
	}


}
