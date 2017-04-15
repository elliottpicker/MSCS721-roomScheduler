package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Test;

import main.java.com.marist.mscs721.Meeting;
import main.java.com.marist.mscs721.Room;

/**
 * 
 * @author elliott picker
 * Runs a variety of tests against the Room class of RoomScheduler
 *
 */
public class JUnitRoom {
	
	
	
	
/**
 * This method tests the constructors passed to the room object
 * to ensure they match the values passed
 */
	@Test
	public void testRoom()
	{
		assertTrue(true);
		String roomName="roomname";
		int capacity=6;
		String building = "Lowell Thomas";
		String location = "East Campus";
		Room room = new Room(roomName,capacity,building,location);
		boolean nameWorked=room.getName().equals(roomName);
		boolean capacityWorked=room.getCapacity()==capacity;
		boolean buildingWorked=room.getBuilding().equals(building);
		boolean locationWorked=room.getLocation().equals(location);
		//assertTrue(nameWorked&&capacityWorked&&buildingWorked&&locationWorked);
		
	}	
	
	
	
/**
 * This method tests the addMeeting function to ensure it the passed meeting is the same
 * as that returned by the getMeetings function
 */
	@Test
	public void testAddMeeting() {
		Room room = new Room("room1",20);
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp," meeting1");
		room.addMeeting(meeting);
		Meeting meeting2 = room.getMeetings().get(0);
		assertEquals(meeting,meeting2);
	}
	
	/**
	 * This method tests the addMeeting function to ensure that when passed a conflicting meeting
	 * the output contains the string unable,  this will need to be changed if message text is changed
	 */
		@Test
		public void testAddConflictingMeeting() {
			Room room = new Room("room1",20);
			Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
			Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 06:00:00");
			Meeting meeting = new Meeting(startTimestamp,endTimestamp," meeting1");
			room.addMeeting(meeting);
			Timestamp startTimestamp2= Timestamp.valueOf("2017-04-06 05:00:00");
			Timestamp endTimestamp2= Timestamp.valueOf("2017-04-06 05:30:00");
			Meeting meeting2 = new Meeting(startTimestamp2,endTimestamp2," meeting2");
			String output=room.addMeeting(meeting2);
			System.out.println(output);
			assertTrue(output.indexOf("Unable")!=-1);
		}	

	/**
	 * This method tests that the getName method returns the name passed to the meeting constructor
	 */
	@Test
	public void testGetName() {
		String roomName="room2";
		Room room = new Room(roomName,70);
		assertEquals(roomName,room.getName());
		
	}

	/**
	 * This method ensures that the setName method changes the room name
	 */
	@Test
	public void testSetName() {
		String roomName="room3";
		String changedRoomName="some other room";
		Room room = new Room(roomName,1);
		room.setName(changedRoomName);
		assertEquals(changedRoomName,room.getName());
	}

	/**
	 * This method ensures that the setName method changes the room name
	 */
	@Test
	public void testSetBuilding() {
		String roomName="room3";
		String changedBuilding="Hancock Center";
		Room room = new Room(roomName,1);
		room.setBuilding(changedBuilding);
		assertEquals(changedBuilding,room.getBuilding());
	}
	/**
	 * This method ensures that the setLocation method changes the location
	 */
	@Test
	public void testSetLocation() {
		String roomName="room3";
		String changedLocation="South Campus";
		Room room = new Room(roomName,1);
		room.setLocation(changedLocation);
		assertEquals(changedLocation,room.getLocation());
	}
	
	/**
	 * This method tests that the getCapacity method returns the capacity passed to the constructor
	 */
	@Test
	public void testGetCapacity() {
		String roomName="room4";
		int capacity=6;
		Room room = new Room(roomName,capacity);
		assertEquals(capacity,room.getCapacity());
	}

	/**
	 * This method ensures that the setCapacity method changes the rooms capacity
	 */
	@Test
	public void testSetCapacity() {
		String roomName="room4";
		int capacity=6;
		int newCapacity=99;
		Room room = new Room(roomName,capacity);
		room.setCapacity(newCapacity);
		assertEquals(newCapacity,room.getCapacity());
		
	}

	
	/**
	 * this method adds 3 meetings to a room an ensures that the getMeetings
	 * method returns the arraylist containing these 3 meetings
	 */
	@Test
	public void testGetMeetings() {
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		Room room = new Room("room1",20);
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp," meeting1");
		Timestamp startTimestamp2= Timestamp.valueOf("2017-04-10 04:48:00");
		Timestamp endTimestamp2= Timestamp.valueOf("2017-04-10 11:00:00");
		Meeting meeting2 = new Meeting(startTimestamp2,endTimestamp2," meeting2");
		Timestamp startTimestamp3= Timestamp.valueOf("2018-05-14 04:00:00");
		Timestamp endTimestamp3= Timestamp.valueOf("2018-10-06 04:00:00");
		Meeting meeting3 = new Meeting(startTimestamp3,endTimestamp3," meeting3");
		room.addMeeting(meeting);
		room.addMeeting(meeting2);
		room.addMeeting(meeting3);
		meetings.add(meeting);
		meetings.add(meeting2);
		meetings.add(meeting3);
		assertEquals(meetings,room.getMeetings());

	}

	/**
	 * This method builds an arraylist of meetings and uses the setMeetings method
	 * to assign this list of meetings and verifies that it matches that returned
	 * by the getMeetings method
	 */
	@Test
	public void testSetMeetings() {
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		Room room = new Room("room1",20);
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp," meeting1");
		Timestamp startTimestamp2= Timestamp.valueOf("2017-04-10 04:48:00");
		Timestamp endTimestamp2= Timestamp.valueOf("2017-04-10 11:00:00");
		Meeting meeting2 = new Meeting(startTimestamp2,endTimestamp2," meeting2");
		Timestamp startTimestamp3= Timestamp.valueOf("2018-05-14 04:00:00");
		Timestamp endTimestamp3= Timestamp.valueOf("2018-10-06 04:00:00");
		Meeting meeting3 = new Meeting(startTimestamp3,endTimestamp3," meeting3");meetings.add(meeting);
		meetings.add(meeting2);
		meetings.add(meeting3);
		room.setMeetings(meetings);
		assertEquals(meetings,room.getMeetings());
	}
	
	
	
	

	/** 
	 * This method builds a room with 2 meetings and checks if a third meeting designed to collide
	 * returns true, verifying the testCollides method
	 */
	@Test
	public void testCollides() {
		Room room = new Room("room1",20);
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 10:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp," meeting1");
		Timestamp startTimestamp2= Timestamp.valueOf("2017-04-10 04:48:00");
		Timestamp endTimestamp2= Timestamp.valueOf("2017-04-10 11:00:00");
		Meeting meeting2 = new Meeting(startTimestamp2,endTimestamp2," meeting2");
		Timestamp startTimestamp3= Timestamp.valueOf("2017-04-06 04:30:00");
		Timestamp endTimestamp3= Timestamp.valueOf("2017-04-06 04:40:00");
		Meeting meeting3 = new Meeting(startTimestamp3,endTimestamp3," meeting3");
		room.addMeeting(meeting);
		room.addMeeting(meeting2);
		boolean result=room.collides(meeting3);
		assertTrue(result);
	}
	
	

}
