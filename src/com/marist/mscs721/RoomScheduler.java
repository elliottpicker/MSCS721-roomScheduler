package com.marist.mscs721;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

// Used for conversion to and from json
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Used to launch the main menu to process room schedules.
 * @author      Elliott Picker
 * @version     1.1                       
 *  */

public class RoomScheduler {

	final protected static Scanner keyboard = new Scanner(System.in);
	final protected static String filename="roomscheduler.json";
	
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
		if(doesRoomExist(roomList,roomName))
		{ 
			System.out.println(roomName + " Schedule");
			System.out.println("---------------------");
		
			for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) {
				System.out.println(m.toString());
			}
		}
		else
			System.out.println(roomName+" not found!");

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
		keyboard.next();
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
		int capacity=-1;
		while(capacity==-1)
		{	
			System.out.println("Room capacity?");
			try{
				capacity = keyboard.nextInt();
				}
			catch(Exception e)
			{
				System.out.println("Invalid Capacity, enter a valid integer");
				capacity=-1;
				keyboard.next();
			}

		}
			
		Room newRoom = new Room(name, capacity);
		roomList.add(newRoom);

		return "Room '" + newRoom.getName() + "' added successfully!";
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
			return "Room removed successfully!";
		}
		else
		{
			return "Room could not be found, no rooms were deleted";
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
		System.out.println("Room Name - Capacity");
		System.out.println(dashesPrint);

		for (Room room : roomList) {
			System.out.println(room.getName() + " - " + room.getCapacity());
		}

		System.out.println(dashesPrint);

		return roomList.size() + " Room(s)";
	}

	/**
	 * Reads in roomList from external json file  
	 * <p>
	 * Opens roomscheduler.json and converts to arraylist of rooms
	 *
	 * @param  roomListOld the list of rooms to return if roomscheudler.json cannot be opened
	 * @return      either the roomList read in from file or the roomList passed in if file could not be opened
	 */
	protected static ArrayList<Room> importRoomData( ArrayList<Room> roomListOld) {
		try{
		 FileReader fr = new FileReader(filename);
		 BufferedReader br = new BufferedReader(fr);
		 Type listType = new TypeToken<ArrayList<Room>>() {}.getType();
		 ArrayList<Room> roomList = new Gson().fromJson(br,listType); 
		 fr.close();
		 br.close();
		 System.out.println("imported "+filename);
		 return roomList;
		}
		catch(Exception e)
		{
			System.out.println("An unknown error has occurred, unable to import json data");
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
		try
		{
			
			PrintWriter out = new PrintWriter(filename);
			String json = new Gson().toJson(roomList);
			out.println(json);
			out.close();
			return "Json data saved to "+filename+". ";
		}
		catch (Exception e)
		{
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
		String name = getRoomName();	
		if(!doesRoomExist(roomList,name))
		{
			return "Unable to schedule meeting, room not found";
		}
		System.out.println("Start Date? (yyyy-mm-dd):");
		String startDate = keyboard.next();
		System.out.println("Start Time?");
		String startTime = keyboard.next();
		startTime = startTime + ":00.0";
		Timestamp startTimestamp=null;
		while(startTimestamp==null)
		{
			try{
				startTimestamp= Timestamp.valueOf(startDate + " " + startTime);
			}
			catch(Exception e)
			{
				//either startDate or startTime is not suitable for Timestamp
				System.out.println("Unable to read start date or time");
				System.out.println("Start Date? (yyyy-mm-dd):");
				startDate = keyboard.next();
				System.out.println("Start Time?");
				startTime = keyboard.next();
				//use StringBuilder
				startTime = new StringBuilder(startTime).append(":00.0").toString();
				 
			}
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
			catch(Exception e)
			{
				//either endDate or endTime is not suitable for Timestamp
				System.out.println("Unable to read start date or time.");
				System.out.println("End Date? (yyyy-mm-dd):");
				endDate = keyboard.next();
				System.out.println("End Time?");
				endTime = keyboard.next();
				//Use String builder 
				endTime = new StringBuilder(endTime).append(":00.0").toString();
			}
		}
		if(startTimestamp.after(endTimestamp))
		{
			return "Error detected, start time is after before time!";
		}
		else
		{
			System.out.println("Subject?");
			keyboard.nextLine();
			String subject = keyboard.nextLine();
			Room curRoom = getRoomFromName(roomList, name);
			Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);
			return curRoom.addMeeting(meeting);
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
		return keyboard.next();
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
}
