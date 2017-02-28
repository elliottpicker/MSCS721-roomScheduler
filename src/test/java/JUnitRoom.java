package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Test;

import main.java.com.marist.mscs721.Meeting;
import main.java.com.marist.mscs721.Room;

public class JUnitRoom {
	
	

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

	@Test
	public void testGetName() {
		String roomName="room2";
		Room room = new Room(roomName,70);
		assertEquals(roomName,room.getName());
		
	}

	@Test
	public void testSetName() {
		String roomName="room3";
		String changedRoomName="some other room";
		Room room = new Room(roomName,1);
		room.setName(changedRoomName);
		assertEquals(changedRoomName,room.getName());
	}

	@Test
	public void testGetCapacity() {
		String roomName="room4";
		int capacity=6;
		Room room = new Room(roomName,capacity);
		assertEquals(capacity,room.getCapacity());
	}

	@Test
	public void testSetCapacity() {
		String roomName="room4";
		int capacity=6;
		int newCapacity=99;
		Room room = new Room(roomName,capacity);
		room.setCapacity(newCapacity);
		assertEquals(newCapacity,room.getCapacity());
		
	}

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
