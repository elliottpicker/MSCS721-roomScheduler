package main.java.com.marist.mscs721;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


import org.apache.log4j.*;

// Used for conversion to and from json
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Used to launch the main menu to process room schedules.
 * @author      Elliott Picker
 * @version     1.1                       
 *  */

public class RoomScheduler {

	
	protected static final Scanner keyboard = new Scanner(System.in);
	protected static final String FILENAME="roomscheduler.json";
	
	//private static final Logger logger = Logger.getLogger( RoomScheduler.class.getName() );
	static Logger logger = Logger.getLogger(RoomScheduler.class);

	
	private RoomScheduler() {
	    throw new IllegalAccessError("Error RoomScheduler");
	  }
	
	
	
	/**
	 * Main method for execution
	 * <p>
	 * Displays main menu in loop until boolean end is set
	 * @param args no arguments accepted at this time
	 */
	public static void main(String[] args) {
		
		Boolean end = false;
		ArrayList<Room> rooms = new ArrayList<>();
		while (!end) {
			switch (mainMenu()) {

			case 1:
				System.out.println(addRoom(rooms));
				break;
			case 2:
				System.out.println(removeRoom(rooms));
				break;
			case 3:
				System.out.println(scheduleRoom(rooms));
				break;
			case 4:
				listSchedule(rooms);
				break;
			case 5:
				System.out.println(listRooms(rooms));
				break;
			case 6:
				rooms=importRoomData(rooms);
				break;
			case 7:
				System.out.println(exportRoomData(rooms));
				break;
			case 8:
				System.out.println("Thank you for using RoomScheduler, Goodbye");
				end=true;
				break;
			default:
				System.out.println("Unrecognized command, Please enter a menu option 1-8");
				break;
			}

		}

	}

	/**
	 * Prints out the meetings associated with a given room 
	 * <p>
	 * Calls getRoomName to prompt for a room name. 
	 * Locates the room and prints it's schedule 
	 * @param  roomList an ArrayList containing the room to be displayed

	 */
	protected static void listSchedule(ArrayList<Room> roomList) {
		String roomName = getRoomName();
		Room room=getRoomFromName(roomList, roomName);
		if(room!=null)
		{ 
			System.out.println(roomName + " Schedule");
			System.out.println("---------------------");
		
			for (Meeting m : room.getMeetings() ) {
				System.out.println(m.toString());
			}
			logger.info("listschedule requested for: "+roomName);
		}
		else
		{
			System.out.println(roomName+" not found!");
			logger.warn("Invalid request to listschedule for unknown room: "+roomName);
		}
		
		

	}

	/**
	 * Displays main menu and waits for user input 
	 *
	 * @return      the specified option as an int entered by user
	 */
	protected static int mainMenu() {
		System.out.println("Main Menu:");
		System.out.println("  1 - Add a room");
		System.out.println("  2 - Remove a room");
		System.out.println("  3 - Schedule a room");
		System.out.println("  4 - List Schedule");
		System.out.println("  5 - List Rooms");
		System.out.println("  6 - Import Room Data from JSON file");
		System.out.println("  7 - Export Room Data to JSON file");
		System.out.println("  8 - Quit RoomScheduler");
		System.out.println("Enter your selection: ");
		int selection;
		if(keyboard.hasNextInt())
		{
		selection=keyboard.nextInt();
		
		}
		else
		{
		selection=-1;
		String input=keyboard.nextLine();
		logger.warn("invalid menu selection made: "+input);
		}
		return selection;
	}

	/**
	 * Prompts user for details of a new room and adds it to roomList array 
	 *
	 * @param  roomList  an array of rooms to add the newly created room to
	 * @return      Message indicating the room was added successfully
	 */
	protected static String addRoom(ArrayList<Room> roomList) {
		System.out.println("Add a room:");
		String name = getRoomName();
		if(name.equalsIgnoreCase("#query"))
		{
			logger.warn("attempt to create reserved room name "+name);
			return "Sorry, this is a reserved keyword that cannot be used";
		}

		int capacity=-1;
		String building="";
		String location="";

		System.out.println("Building?");
		try{
			building = keyboard.nextLine();
			}
		catch(NoSuchElementException e)
		{
			
			logger.warn("invalid building enterred for room: "+name+" "+e);
			keyboard.next();
		}
		
		System.out.println("Location?");
		try{
			location = keyboard.nextLine();
			}
		catch(NoSuchElementException e)
		{

			logger.warn("invalid location enterred for room: "+name+" "+e);
			keyboard.next();
		}
		
		
		while(capacity<0)
		{	
			System.out.println("Room capacity?");
			try{
				capacity = keyboard.nextInt();
				}
			catch(NoSuchElementException e)
			{
				capacity=-1;
				logger.warn("invalid room capacity enterred for room: "+name+" "+e);
				keyboard.next();
			}
			if(capacity<1)
			{
				System.out.println("Invalid Capacity, enter a valid whole number!");
			}
			if(doesRoomExist(roomList,name))
			{
				logger.warn("attempt to add duplicate room name: "+name);
				return "room "+name+" already exists!";
			}

		}
			
		Room newRoom = new Room(name, capacity,building,location);
		roomList.add(newRoom);
		logger.info(newRoom.getName()+" room created");
		return "Room '" + newRoom.getName() + " added successfully!";
	}

	/**
	 * Removes a room from a list of rooms  
	 * <p>
	 * Calls getRoomName to prompt user for room name and remove 
	 * it from given list
	 *
	 * @param  roomList the list of rooms from which user enterred room is to be removed from
	 * @return      String indicating whether or not room was removed
	 */
	protected static String removeRoom(ArrayList<Room> roomList) {
		System.out.println("Remove a room:");
		String roomName=getRoomName();
 
		if (doesRoomExist(roomList,roomName))
		{
			int index=findRoomIndex(roomList,roomName);
			roomList.remove(index);
			logger.info(roomName+" room removed");
			
			return "Room removed successfully!";
		}
		else
		{
			logger.warn("Invalid request to remove unkown room: "+roomName);
			return "Room "+roomName+" could not be found, no rooms were deleted";
		}

		
	}
	
	/**
	 * Displays a list of rooms and their capacity
	 *
	 * @param  roomList the list of rooms to be displayed
	 * @return      String indicating number of rooms
	 */
	protected static String listRooms(ArrayList<Room> roomList) {
		String dashesPrint="---------------------"; // to save on String building
		System.out.println("Room Name - Capacity - Building - Location");
		System.out.println(dashesPrint);

		for (Room room : roomList) {
			System.out.println(room);
		}

		System.out.println(dashesPrint);
		logger.info(roomList.size() + " Room(s) listed");
		return roomList.size() + " Room(s)";
	}
	/**
	 * Reads in roomList from external json file  
	 * <p>
	 * Opens roomscheduler.json and converts to arraylist of rooms
	 *
	 * @param  roomListOld the list of rooms to return if roomscheudler.json cannot be opened
	 * @return      Thhe list of rooms passed with the addition of the room list read from the json file
	 */
	protected static ArrayList<Room> importRoomData( ArrayList<Room> roomListOld) {
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME)))
		{
		 Type listType = new TypeToken<ArrayList<Room>>() {}.getType();
		 ArrayList<Room> roomList = new Gson().fromJson(br,listType); 
		 System.out.println("imported "+roomList.size()+" rooms from "+FILENAME);
		 logger.info("imported "+roomList.size()+" rooms from "+FILENAME);
		 roomList.addAll(roomListOld);
		 System.out.println(roomList.size()+" rooms total"); 
		 logger.info(roomList.size()+" rooms total");
		 return roomList;
		}
		catch(Exception e)
		{
			System.out.println("An unknown error has occurred, unable to import json data");
			logger.warn("Unable to import json from"+FILENAME+" "+e);
			return roomListOld;
		}
		
	}
	
	/**
	 * Exports roomList array to json file
	 *
	 * @param  roomList  an array of rooms to be written to file
	 * @return      Message indicating whether the room was exported successfully or not
	 */
	protected static String exportRoomData(ArrayList<Room> roomList) {
		try(PrintWriter out = new PrintWriter(FILENAME))
		{
			
			
			String json = new Gson().toJson(roomList);
			out.println(json);
			String message="Json data saved to "+FILENAME+". \n"+roomList.size()+" rooms written";
			logger.info(message);
			return message ;
		}
		catch (IOException e)
		{
		
			logger.warn("Unable to export json "+e);
			return "An unknown error has occurred, unable to export json data";	
		}
	}
	
	/**
	 * Prompts user for details of a meetings and adds it for a given room
	 *
	 * @param  roomList  an array of rooms containing the room for which a meeting is to be scheduled
	 * @return      Message indicating whether the meeting was scheduled successfully or not
	 */
	protected static String scheduleRoom(ArrayList<Room> roomList) {
		System.out.println("Schedule a room:");
		System.out.println("(type #query to find available room)");
		String name = getRoomName();	
		if(!name.equalsIgnoreCase("#query") && !doesRoomExist(roomList,name))
		{
			logger.warn("Invalid schedule room request for "+name);
			return "Unable to schedule meeting, room not found!";
		}
		System.out.println("Start Date? (yyyy-mm-dd):");
		String startDate = keyboard.next();
		System.out.println("Start Time?");
		String startTime = keyboard.next();
		startTime = startTime + ":00.0";
		Timestamp startTimestamp=null;
		Timestamp today = new Timestamp(System.currentTimeMillis());
		Timestamp inTenYears =  Timestamp.valueOf(today.toLocalDateTime().plusYears(10));
		while(startTimestamp==null)
		{
			try{
				startTimestamp= Timestamp.valueOf(startDate + " " + startTime);
			}
			catch(IllegalArgumentException e)
			{
				//either startDate or startTime is not suitable for Timestamp
				logger.info("invalid start date or time enterred for room "+name+" "+startDate+" "+startTime+" "+e);
				System.out.println("Unable to read start date or time");
				System.out.println("Start Date? (yyyy-mm-dd):");
				startDate = keyboard.next();
				System.out.println("Start Time?");
				startTime = keyboard.next();
				//use StringBuilder
				startTime = new StringBuilder(startTime).append(":00.0").toString();
				
				 
			}
			
		}
		if(startTimestamp.after(inTenYears))
		{
			return "error start date is more than 10 years in the future";
		}
		if(startTimestamp.before(today))
		{
			return "error start date is before the current time";
		}

		Timestamp endTimestamp=null;
		System.out.println("End Date? (yyyy-mm-dd):");
		String endDate = keyboard.next();
		System.out.println("End Time?");
		String endTime = keyboard.next();
		endTime = endTime + ":00.0";
		while(endTimestamp==null)	
		{
			try{
			endTimestamp = Timestamp.valueOf(endDate + " " + endTime);
			}
			catch(IllegalArgumentException e)
			{
				//either endDate or endTime is not suitable for Timestamp
				logger.info("invalid end date or time enterred for room "+name+" "+endDate+" "+endTime+" "+e);
				System.out.println("Unable to read start date or time.");
				System.out.println("End Date? (yyyy-mm-dd):");
				endDate = keyboard.next();
				System.out.println("End Time?");
				endTime = keyboard.next();
				//Use String builder 
				endTime = new StringBuilder(endTime).append(":00.0").toString();
				
			}
		}
		if(endTimestamp.after(inTenYears))
		{
			return "error end date is more than 10 years in the future";
		}
		if(endTimestamp.before(today))
		{
			return "error end date is before today";
		}
		
		if(startTimestamp.after(endTimestamp))
		{
			String message="Error detected, end time is before start time for room "+name+"";
			logger.warn(message);
			return message ;
		}
		if(Timestamp.valueOf(startTimestamp.toLocalDateTime().plusMinutes(5)).after(endTimestamp))
		{
			String message="Meeting not scheduled, meeting must be at least 5 minutes in duration";
			logger.warn(message);
			return message ;
	
		}
		else
		{
			
			System.out.println("Subject?");
			String subject = keyboard.next();
			if(name.equalsIgnoreCase("#query"))
			{
				name=findAvailableRoom(roomList,startTimestamp,endTimestamp);
			}
			Room curRoom = getRoomFromName(roomList, name);
			if(curRoom!=null)
			{
				Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);
				String message= curRoom.addMeeting(meeting);
				logger.info(message);
				return message;
			}
			else
			{	
				logger.info("No rooms were scheduled");
				return "No rooms were scheduled";
			}
		}

		
	}

	/**
	 * Returns a room object of given name
	 *
	 * @param  roomList  an array of rooms to search for given room name
	 * @param  name  A string representing the name of the room to be searched for
	 * @return     Room object of given name if found otherwise null
	 */
	protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
		if(doesRoomExist(roomList,name))
		return roomList.get(findRoomIndex(roomList, name));
		else
		return null;
	}

	/**
	 * Returns index of given room in a roomlist or else returns roomlist size
	 * <p>
	 * It is neccessary to either check if the room exists either through doesRoomExist
	 * method or else check if returns index equal to roomList size
	 *
	 * @param  roomList  an array of rooms to search for given room name
	 * @param  roomName  A string representing the name of the room to be searched for
	 * @return     index in roomList of roomName or else the size of the roomList
	 */
	protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
		int roomIndex = 0;

		for (Room room : roomList) {
			if (room.getName().compareTo(roomName) == 0) {
				break;
			}
			roomIndex++;
		}

		return roomIndex;
	}

	/**
	 * Prompts user for a room name
	 *
	 * @return     String captured from keyboard input to be used as a room name
	 */
	protected static String getRoomName() {
		System.out.println("Room Name?");
		String name=keyboard.next()+keyboard.nextLine();
		return name;
	}

	/**
	 * Checks if the given room name exists within the passed room list
	 * @param  roomList  an array of rooms to search for given room name
	 * @param  roomName  A string representing the name of the room to be searched for
	 * @return     true if roomname is the name of a room in roomlist, otherwise false
	 */
	protected static boolean doesRoomExist(ArrayList<Room> roomList, String roomName){
		return findRoomIndex(roomList,roomName)!=roomList.size();
	
		
	}
	/**
	 * Determines which rooms are available for a particular time and promps the user to select one
	 * @param roomList a list of rooms to be checked
	 * @param startDateTime the start of the meeting
	 * @param endDateTime the end of the meeting
	 * @return the name of the room the user has selected as a String, or null if the user decided not to schedule
	 */
	protected static String findAvailableRoom(ArrayList<Room> roomList,Timestamp startDateTime,Timestamp endDateTime)
	{
		String roomName="";
		Meeting request=new Meeting(startDateTime,endDateTime,"");
		ArrayList<Room> acceptableRoomList = new ArrayList<Room>();
		for (Room room : roomList) {
			if (!room.collides(request))
					acceptableRoomList.add(room);
		}
		if (acceptableRoomList.size()==0)	
		{
		System.out.println("Sorry no rooms were available for "+startDateTime+" - "+endDateTime);
		logger.info("No rooms were available for "+startDateTime+" - "+endDateTime);
		return null;
		}
		System.out.println("The following rooms are available for that time");
		System.out.println("Please select which room you would like to schedule");
		for(int i=0;i<acceptableRoomList.size();i++)
		{
			System.out.println((i+1)+" - "+acceptableRoomList.get(i));
		}
		
		
		System.out.println((acceptableRoomList.size()+1)+" - quit");
		int selection=acceptableRoomList.size()+1;
		try{
		selection = keyboard.nextInt()-1;	// because selection is 1 based and acceptableRoomList is 0 based
		}
		catch (Exception e)
		{logger.info("Invalid selection made within find available room "+selection+e);}
		if(selection>-1 && selection <acceptableRoomList.size())
			roomName=acceptableRoomList.get(selection).getName();
		else if(selection!=acceptableRoomList.size()) // if theyre equal the user correctly indicated no rooms
			System.out.println(selection+" "+acceptableRoomList.size()+" invalid room selection made");
	return roomName;	
	}
}
	
