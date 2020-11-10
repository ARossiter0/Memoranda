/**
 * TaskListImpl.java
 * Created on 21.02.2003, 12:29:54 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

import java.util.Calendar;
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
import nu.xom.ParentNode;

/**
 * 
 */
/*$Id: TaskListImpl.java,v 1.14 2006/07/03 11:59:19 alexeya Exp $*/
public class LectureListImpl implements LectureList {

    private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
    
    private Hashtable elements = new Hashtable();
	
    
    /**
     * Constructor for TaskListImpl.
     */
    public LectureListImpl(Document doc, Project prj) {
        _doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
        buildElements(_root);
    }
    
    public LectureListImpl(Project prj) {            
            _root = new Element("lecturelist");
            _doc = new Document(_root);
            _project = prj;
    }
    
	/*
	 * Build the hashtable recursively
	 */
	private void buildElements(Element parent) {
		Elements els = parent.getChildElements("lecture");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			elements.put(el.getAttribute("id").getValue(), el);
			buildElements(el);
        }
	}
    
	public Project getProject() {
		return _project;
	}


    public Lecture createLecture(CalendarDate date, Calendar startTime, Calendar endTime, String topic) {
        Element el = new Element("lecture");
        el.addAttribute(new Attribute("date", date.toString()));
        el.addAttribute(new Attribute("startHour", String.valueOf(startTime.get(Calendar.HOUR_OF_DAY))));
        el.addAttribute(new Attribute("startMin", String.valueOf(startTime.get(Calendar.MINUTE))));
        el.addAttribute(new Attribute("endHour", String.valueOf(endTime.get(Calendar.HOUR_OF_DAY))));
        el.addAttribute(new Attribute("endMin", String.valueOf(endTime.get(Calendar.MINUTE))));
        el.addAttribute(new Attribute("topic", topic));
		String id = Util.generateId();
        el.addAttribute(new Attribute("id", id));
        
        _root.appendChild(el);
        return new LectureImpl(el, _project);
    }

	@Override
	public Vector getAllLectures() {
		Elements lecs = _root.getChildElements("lecture");
        Vector v = new Vector();
        for (int i = 0; i < lecs.size(); i++) {
        	v.add(new LectureImpl(lecs.get(i), _project));
        }
        return v;
	}

	public static void removeLecture(Lecture lecture) {
		ParentNode parent = lecture.getContent().getParent();
		parent.removeChild(lecture.getContent());
	}

	@Override
	public Document getXMLContent() {
		return _doc;
	}

	@Override
	public Lecture getLecture(String id) {
		return new LectureImpl(getLectureElement(id), this._project); 
	}

	private Element getLectureElement(String id) {
		Element el = (Element)elements.get(id);
		if (el == null) {
			Util.debug("Task " + id + " cannot be found in project " + _project.getTitle());
		}
		return el;
	}

 
}