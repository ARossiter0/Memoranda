/**
 * TaskList.java
 * Created on 21.02.2003, 12:25:16 Alex
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
/*$Id: TaskList.java,v 1.8 2005/12/01 08:12:26 alexeya Exp $*/
public interface LectureList {

	Project getProject();
    Lecture getLecture(String id);

    Lecture createLecture(CalendarDate date, CalendarDate startTime, CalendarDate endTime, String topic);
    
    void removeLecture(Task task);

    nu.xom.Document getXMLContent();

}