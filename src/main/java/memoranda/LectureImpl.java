/**
 * DefaultTask.java
 * Created on 12.02.2003, 15:30:40 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import java.util.Collection;
import java.util.Vector;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;

import java.util.Calendar;
import java.util.Date;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

/**
 *
 */
/*$Id: TaskImpl.java,v 1.15 2005/12/01 08:12:26 alexeya Exp $*/
public class LectureImpl implements Lecture, Comparable {

    private Element _element = null;
    private Project _project = null;

    /**
     * Constructor for DefaultTask.
     */
    public LectureImpl(Element lectureElement, Project project) {
        _element = lectureElement;
        _project = project;
    }

	@Override
	public String getTopic() {
		return _element.getAttribute("topic").getValue();
	}

	@Override
	public void setTopic(String topic) {
		_element.getAttribute("topic").setValue(topic);
	}

	@Override
	public String getDate() {
		return _element.getAttribute("date").getValue();
	}

	@Override
	public void setDate(String date) {
		_element.getAttribute("date").setValue(date);
	}

	@Override
	public String getStartTime() {
		return _element.getAttribute("startTime").getValue();
	}

	@Override
	public void setStartTime(String date) {
		_element.getAttribute("startTime").setValue(date);
	}

	@Override
	public String getEndTime() {
		return _element.getAttribute("endTime").getValue();
	}

	@Override
	public void setEndTime(String date) {
		_element.getAttribute("endTime").setValue(date);
	}


	@Override
	public Element getContent() {
		return this._element;
	}


	@Override
	public String getID() {
		return _element.getAttribute("id").getValue();
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
