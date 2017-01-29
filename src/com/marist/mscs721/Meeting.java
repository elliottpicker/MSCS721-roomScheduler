package com.marist.mscs721;

import java.sql.Timestamp;

public class Meeting {
	
	private Timestamp startTime = null;
	private Timestamp stopTime = null;
	private String subject = null;

	
	public Meeting(Timestamp newStartTime, Timestamp newEndTime, String newSubject) {
		setStartTime(newStartTime);
		setStopTime(newEndTime);
		if (newSubject.isEmpty()) {
			setSubject("N/A");
		}
		else {
			setSubject(newSubject);
		}
	}

	public String toString() {
		return this.getStartTime().toString() + " - " + this.getStopTime() + ": " + getSubject();
	}
	
	/**
	 * Returns start time of meeting as a Timestamp object
	 * @return     Timestamp object representing start time of this meeting
	 */
	public Timestamp getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time of this meeting
	 * @param  startTime the time to be stored as the start time
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	/**
	 * Returns stop time of meeting as a Timestamp object
	 * @return     Timestamp object representing stop time of this meeting
	 */
	public Timestamp getStopTime() {
		return stopTime;
	}

	/**
	 * Sets the stop time of this meeting
	 * @param  stopTime the time to be stored as the stop time
	 */
	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}

	/**
	 * Returns subject of meeting
	 * @return     subject the subject of this meeting
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject of this meeting
	 * @param  subject the subject to be stored for this meeting
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * Determines whether another meetings timestamp overlaps this meetings timestamp
	 * @param  m another meeting which should be compared against this
	 * @return true if the meetings overlap, otherwise false
	 */
	public boolean collidesWith(Meeting m)
	{
		if (this.startTime.before(m.getStartTime()))
		{
			return this.stopTime.after(m.getStartTime());
		}
		else
		{
		return this.startTime.before(m.getStopTime());	
		}
		
	}

}
