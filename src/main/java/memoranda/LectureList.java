/**
 * TaskList.java
 * Created on 21.02.2003, 12:25:16 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package memoranda;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;

import memoranda.date.CalendarDate;
/**
 * 
 */
/*$Id: TaskList.java,v 1.8 2005/12/01 08:12:26 alexeya Exp $*/
public interface LectureList {

	Project getProject();
    Lecture getLecture(String id);


    Lecture createLecture(CalendarDate date, Calendar startTime, Calendar endTime, String topic);

    nu.xom.Document getXMLContent();
	Vector getAllLectures();

}