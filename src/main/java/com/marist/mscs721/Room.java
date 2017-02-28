package main.java.com.marist.mscs721;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a room and contains the list of meetings scheduled to this room
 * @author      Elliott Picker
 * @version     1.1                       
 *  */

public class Room {	
	
	private String name;
	private int capacity;
	private ArrayList<Meeting> meetings; 
	
	
	/**
	 * Creates Room object with specified name and capacity
	 * @param newName the name to be assigned to this room
	 * @param newCapacity the capacity assigned to this room
	 */
	public Room(String newName, int newCapacity) {
		setName(newName);
		setCapacity(newCapacity);
		setMeetings(new ArrayList<Meeting>());
	}
	
	/**
	 * Adds a new meeting to this rooms schedule
	 * @param  newMeeting the meeting to be added
	 * @return a string indicating whether or not the meeting was scheduled
	 */
	public String addMeeting(Meeting newMeeting) {
		if(!collides(newMeeting))
		{
		this.getMeetings().add(newMeeting);
		return "Sucessfully Scheduled Meeting!"; 
		}
		else
		{
		return "Unable to schedule due to a conflict!";
		}
	}

	/**
	 * Returns the name of this Room 
	 * @return the name of this Room as a String
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name of this Room
	 * @param  name the new name to be assigned to this room
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Returns the capacity of this Room 
	 * @return the capacity of this Room
	 */
	public int getCapacity() {
		return capacity;
	}


	/**
	 * Sets the capacity of this Room
	 * @param capacity the new capacity to be assigned to this room
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	/**
	 * Returns all meetings scheduled for this room
	 * @return a list of Meetings scheduled for this room
	 */
	public List<Meeting> getMeetings() {
		return meetings;
	}


	/**
	 * Assings a new set of meetings scheduled for this room
	 * @param meetings a list of meetings to replace the current schedule
	 */
	public void setMeetings(List<Meeting> meetings) {
		this.meetings = new ArrayList<>(meetings);
	}
	
	/**
	 * Checks if an incoming meeting would collide with the current schedule of this room
	 * @param request a meeting to be checked against the current schedule
	 * @return true if the meeting would collide with a meeting in the current schedule otherwise false
	 */
	public boolean collides(Meeting request)
	{
		for (Meeting m : meetings) {
			
			if (request.collidesWith(m))
				return true;
		}
		return false;
	}
}
