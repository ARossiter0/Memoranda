/**
 * TaskListImpl.java
 * Created on 21.02.2003, 12:29:54 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;
//import nu.xom.converters.*;
//import org.apache.xerces.dom.*;
//import nux.xom.xquery.XQueryUtil;

/**
 * 
 */
/*$Id: TaskListImpl.java,v 1.14 2006/07/03 11:59:19 alexeya Exp $*/
public class LectureListImpl implements LectureList {

    private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
	
    
    /**
     * Constructor for TaskListImpl.
     */
    public LectureListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
    }
    
    public LectureListImpl(Project prj) {            
            _root = new Element("lecturelist");
            _doc = new Document(_root);
            _project = prj;
    }
    
	public Project getProject() {
		return _project;
	}


    public Lecture createLecture(CalendarDate date, CalendarDate startTime, CalendarDate endTime, String topic) {
        Element el = new Element("lecture");
        el.addAttribute(new Attribute("date", date.toString()));
        el.addAttribute(new Attribute("startTime", startTime.toString()));
        el.addAttribute(new Attribute("endTime", endTime.toString()));
        el.addAttribute(new Attribute("topic", topic));
		String id = Util.generateId();
        el.addAttribute(new Attribute("id", id));
        
        _root.appendChild(el);
        return new LectureImpl(el, _project);
    }

	@Override
	public Lecture getLecture(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLecture(Task task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Document getXMLContent() {
		// TODO Auto-generated method stub
		return null;
	}

 
}