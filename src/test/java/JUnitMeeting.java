package test.java;


import java.sql.Timestamp;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import main.java.com.marist.mscs721.Meeting;

/**
 * 
 * @author elliott picker
 * Runs a variety of tests against the Meeting class of RoomScheduler
 *
 */
public class JUnitMeeting {
	
	/**
	 * This method tests that the string returned by toString is what we expect it to be
	 */
	@Test
	public void testToString() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:0");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:0");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		String expected = "2017-04-06 04:00:00.0-2017-04-06 05:00:00.0: meeting1";
		assertEquals(expected,meeting.toString());
	
	}

	/**
	 * This method tests the getStartTime method to ensure it returns the start time passed
	 * to the constructor
	 */
	@Test
	public void testGetStartTime() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		assertEquals(startTimestamp,meeting.getStartTime());
	}

	/**
	 * This method tests the setStartTime method by assigning a new start time and
	 * ensuring that it matches the time returned by getStartTime
	 */
	@Test
	public void testSetStartTime() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp newStartTimestamp= Timestamp.valueOf("2017-04-06 03:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"1");
		meeting.setStartTime(newStartTimestamp);
		assertEquals(newStartTimestamp,meeting.getStartTime());
	}
	
	/**
	 * This method ensures that when no subject is passed, a default 
	 * value of "No Subject" is assigned
	 */
	@Test
	public void testNoSubject() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		String blankSubject="";
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,blankSubject);
		assertEquals(meeting.getSubject(),"No Subject");
	}

	/**
	 * This method tests the getStopTime method to ensure it returns the stop time passed
	 * to the constructor
	 */
	@Test
	public void testGetStopTime() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		assertEquals(endTimestamp,meeting.getStopTime());
	}

	/**
	 * This method tests the setStopTime method by assigning a new stop time and
	 * ensuring that it matches the time returned by getStopTime
	 */
	@Test
	public void testSetStopTime() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Timestamp newEndTimestamp= Timestamp.valueOf("2017-04-06 07:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		meeting.setStopTime(newEndTimestamp);
		assertEquals(newEndTimestamp,meeting.getStopTime());
	}

	/**
	 * This method tests the getSubject method by ensuring it is the same
	 * subject as passed to the constructor
	 */
	@Test
	public void testGetSubject() {
		String subject = "subject";
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,subject);
		assertEquals(subject,meeting.getSubject());
	}

	/**
	 * This method tests the setSubject method by assigning a new subject and validating
	 * that it matches that returned by the getSubject method
	 */
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

	/**
	 * This method tests the collidesWith method by creating two meetings which should collide
	 * and ensuring that the method returns true
	 */
	@Test
	public void testCollidesWith() {
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:0");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:0");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,"meeting1");
		Timestamp startTimestamp2= Timestamp.valueOf("2017-04-06 04:30:0");
		Timestamp endTimestamp2= Timestamp.valueOf("2017-04-06 04:45:0");
		Meeting meeting2 = new Meeting(startTimestamp2,endTimestamp2,"meeting2");
		assertTrue(meeting.collidesWith(meeting2));
	}
	
	/**
	 * This method tests that the meeting built by the constructor is consistent with
	 * the variables passed to it
	 */
	@Test
	public void testMeeting(){

		String subject = "subject";
		Timestamp startTimestamp= Timestamp.valueOf("2017-04-06 04:00:00");
		Timestamp endTimestamp= Timestamp.valueOf("2017-04-06 05:00:00");
		Meeting meeting = new Meeting(startTimestamp,endTimestamp,subject);
		
		boolean subjectWorks=meeting.getSubject().equals(subject);
		boolean startTimeWorks=meeting.getStartTime().equals(startTimestamp);
		boolean endTimeWorks=meeting.getStopTime().equals(endTimestamp);
		
		assertTrue(subjectWorks && startTimeWorks && endTimeWorks);
	}

}
