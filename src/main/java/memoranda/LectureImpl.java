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
import main.java.memoranda.util.Local;

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
	public CalendarDate getDate() {
        if (_element.getAttribute("date") != null) {
            return new CalendarDate(_element.getAttribute("date").getValue());
        } else {
            return new CalendarDate((Date) CurrentDate.get().getDate());
        }
    }

	@Override
	public void setDate(String date) {
		_element.getAttribute("date").setValue(date);
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
	    Lecture l = (Lecture) o;
	    
	    if (this.getDate().getYear() < l.getDate().getYear()) {
	        return -1;
	    } else if (this.getDate().getYear() == l.getDate().getYear()) {
	        
	        if (this.getDate().getMonth() < l.getDate().getMonth()) {
	            return -1;
	        } else if (this.getDate().getMonth() == l.getDate().getMonth()) {
	            
	            if (this.getDate().getDay() < l.getDate().getDay()) {
	                return -1;
	            } else if (this.getDate().getDay() == l.getDate().getDay()) {
	                
	                if (this.getStartHour() < l.getStartHour()) {
	                    return -1;
	                } else if (this.getStartHour() == l.getStartHour()) {
	                    
	                    if (this.getEndHour() < l.getEndHour()) {
	                        return -1;
	                    } else if (this.getEndHour() == l.getEndHour()) {
	                        
	                        return this.getTopic().compareToIgnoreCase(l.getTopic());
	                    } else {
	                        return 1;
	                    }
	                } else {
	                    return 1;
	                }
	            } else { 
	                return 1;
	            }
	        } else {
	            return 1;
	        }
	        
	    } else { 
	        return 1;
	    }
	}

	
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) {
	        return true;
	    }
	    if (o == null) {
	        return false;
	    }
	    if (this.getClass() != o.getClass()) {
	        return false;
	    }
	    if (this.compareTo(o) == 0) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	

	@Override
	public String getStartTimeString() {
		return Local.getTimeString(getStartHour(), getStartMin());
	}

	@Override
	public int getStartHour() {
		return new Integer(_element.getAttribute("startHour").getValue()).intValue();
	}

	@Override
	public int getStartMin() {
		return new Integer(_element.getAttribute("startMin").getValue()).intValue();
	}
	
	@Override
	public String getEndTimeString() {
		return Local.getTimeString(getEndHour(), getEndMin());
	}

	@Override
	public int getEndHour() {
		return new Integer(_element.getAttribute("endHour").getValue()).intValue();
	}

	@Override
	public int getEndMin() {
		return new Integer(_element.getAttribute("endMin").getValue()).intValue();
	}

	@Override
	public void setStartHour(int hh) {
		_element.getAttribute("startHour").setValue(String.valueOf(hh));
		
	}

	@Override
	public void setStartMin(int mm) {
		_element.getAttribute("startMin").setValue(String.valueOf(mm));
	}

	@Override
	public void setEndHour(int hh) {
		_element.getAttribute("endHour").setValue(String.valueOf(hh));
	}

	@Override
	public void setEndMin(int mm) {
		_element.getAttribute("endMin").setValue(String.valueOf(mm));
	}
	
}
