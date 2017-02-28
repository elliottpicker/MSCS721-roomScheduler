package test.java;
import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


import main.java.com.marist.mscs721.Meeting;

public class JUnitMeeting {

	@Test
	public void testToString() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:0");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:0");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		String expected = "2017-04-06 04:00:00.0-2017-04-06 05:00:00.0: meeting1";
		assertEquals(expected,meeting.toString());
	
	}

	@Test
	public void testGetStartTime() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		assertEquals(startTimestamp,meeting.getStartTime());
	}

	@Test
	public void testSetStartTime() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp newStartTimestamp= Timestamp.valueOf("2017-04-06 03:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		meeting.setStartTime(newStartTimestamp);
		assertEquals(newStartTimestamp,meeting.getStartTime());
	}

	@Test
	public void testGetStopTime() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		assertEquals(endTimestamp,meeting.getStopTime());
	}

	@Test
	public void testSetStopTime() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Timestamp newEndTimestamp= Timestamp.valueOf("2017-04-06 07:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		meeting.setStopTime(newEndTimestamp);
		assertEquals(newEndTimestamp,meeting.getStopTime());
	}

	@Test
	public void testGetSubject() {
		String subject = "subject";
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,subject);
		assertEquals(subject,meeting.getSubject());
	}

	@Test
	public void testSetSubject() {
		String subject = "subject";
		String newSubject = "another subject";
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,subject);
		meeting.setSubject(newSubject);
		assertEquals(newSubject,meeting.getSubject());
	}

	@Test
	public void testCollidesWith() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:0");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:0");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		Timestamp startTimestamp2= Timestamp.valueOf("2017-04-06 04:00:0");
		Timestamp endTimestamp2= Timestamp.valueOf("2017-04-06 05:00:0");
		Meeting meeting2 = new Meeting(startTimestamp2,endTimestamp2,"meeting1");
	}

}
