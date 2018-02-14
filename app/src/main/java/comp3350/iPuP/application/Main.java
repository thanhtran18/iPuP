package comp3350.iPuP.application;

public class Main
{
	public static void main(String[] args)
	{
		startUp();
		shutDown();
		System.out.println("All done");
	}

	public static void startUp()
	{
		Services.createDataAccess();
	}

	public static void shutDown()
	{
		Services.closeDataAccess();
	}
}
