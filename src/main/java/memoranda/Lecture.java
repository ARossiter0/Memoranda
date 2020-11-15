/**
 * Task.java
 * Created on 11.02.2003, 16:39:13 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import java.util.Collection;

import main.java.memoranda.date.CalendarDate;

/**
 * 
 */
/*$Id: Task.java,v 1.9 2005/06/16 04:21:32 alexeya Exp $*/
public interface Lecture {

    String getTopic();
    void setTopic(String topic);
    
    CalendarDate getDate();
    void setDate(String date);
    
    String getStartTimeString();
    int getStartHour();
    void setStartHour(int hh);
    int getStartMin();
    void setStartMin(int mm);
    
    String getEndTimeString();
    int getEndHour();
    void setEndHour(int hh);
    int getEndMin();
    void setEndMin(int mm);
    
    
    String getID();
    
    nu.xom.Element getContent();
}
