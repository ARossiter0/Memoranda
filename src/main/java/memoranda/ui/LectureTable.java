/**
 * EventsTable.java
 * Created on 09.03.2003, 9:52:02 Alex
 * Package: net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda.ui;

import java.awt.Component;
import java.awt.Font;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.Event;
import main.java.memoranda.EventsManager;
import main.java.memoranda.Lecture;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectImpl;
import main.java.memoranda.ProjectListener;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.TaskList;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.Local;
import nu.xom.Element;
/**
 *
 */
/*$Id: EventsTable.java,v 1.6 2004/10/11 08:48:20 alexeya Exp $*/
public class LectureTable extends JTable {

    public static final int LECTURE = 100;
    public static final int LECTURE_ID = 101;

    Vector lectures = new Vector();
    /**
     * Constructor for EventsTable.
     */
    public LectureTable() {
        super();
        setModel(new LectureTableModel());
        initTable();
        this.setShowGrid(false);
        CurrentProject.addProjectListener(new ProjectListener() {
			@Override
			public void projectWasChanged() {
				initTable();
			}

			@Override
			public void projectChange(Project prj, NoteList nl, TaskList tl, ResourcesList rl) {
			}
        });
    }

    public void initTable() {
        lectures = CurrentProject.getLectureList().getAllLectures();
        getColumnModel().getColumn(1).setPreferredWidth(60);
        getColumnModel().getColumn(1).setMaxWidth(60);
        getColumnModel().getColumn(2).setPreferredWidth(60);
        getColumnModel().getColumn(2).setMaxWidth(60);
        getColumnModel().getColumn(3).setPreferredWidth(60);
        getColumnModel().getColumn(3).setMaxWidth(60);
	clearSelection();
        updateUI();
    }

    public void refresh() {
        initTable();
    }

     public TableCellRenderer getCellRenderer(int row, int column) {
        return new javax.swing.table.DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
                Component comp;
                comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Lecture le = (Lecture)getModel().getValueAt(row, LECTURE);
                comp.setForeground(java.awt.Color.gray);            
                return comp;
            }
        };

    }
    
    class LectureTableModel extends AbstractTableModel {

        String[] columnNames = {
            Local.getString("Topic"),
                Local.getString("Date"),
                Local.getString("Start Time"),
                Local.getString("End Time"),             
        };

        LectureTableModel() {
            super();
        }

        public int getColumnCount() {
            return 4;
        }

        public int getRowCount() {
			int i;
			try {
				i = lectures.size();
			}
			catch(NullPointerException e) {
				i = 1;
			}
			return i;
        }

        public Object getValueAt(int row, int col) {
           Lecture le = (Lecture)lectures.get(row);
           if (col == 0)
                //return ev.getHour()+":"+ev.getMinute();
                return le.getTopic();
           else if (col == 1)
                return le.getDate();
           else if (col == 2)
        	   return le.getStartTime();
           else if (col == 3)
        	   return le.getEndTime();
           else if (col == LECTURE_ID)
                return le.getID();
           else return le;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }
    }
}
