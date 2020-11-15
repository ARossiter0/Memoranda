package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.Local;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: WorkPanel.java,v 1.9 2004/04/05 10:05:44 alexeya Exp $*/
public class WorkPanel extends JPanel {
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar toolBar = new JToolBar();
	JPanel panel = new JPanel();
	CardLayout cardLayout1 = new CardLayout();

	public JButton notesB = new JButton();
	public DailyItemsPanel dailyItemsPanel = new DailyItemsPanel(this);
	public ResourcesPanel filesPanel = new ResourcesPanel();
	public JButton coursesB = new JButton();
	public JButton assignmentsB = new JButton();
	public JButton studentsB = new JButton();
	public JButton instructorTasksB = new JButton();
	public JButton tagraderTasksB = new JButton();
	public JButton lecturesB = new JButton();
	public JButton filesB = new JButton();
	JButton currentB = null;
	Border border1;

	public WorkPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	void jbInit() throws Exception {
		border1 =
			BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(
					BevelBorder.LOWERED,
					Color.white,
					Color.white,
					new Color(124, 124, 124),
					new Color(178, 178, 178)),
				BorderFactory.createEmptyBorder(0, 2, 0, 0));

		this.setLayout(borderLayout1);
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.setBackground(Color.white);

		toolBar.setBorderPainted(false);
		toolBar.setFloatable(false);
		panel.setLayout(cardLayout1);

		// Courses button
		coursesB.setBackground(Color.white);
		coursesB.setMaximumSize(new Dimension(60, 80));
		coursesB.setMinimumSize(new Dimension(30, 30));

		coursesB.setFont(new java.awt.Font("Dialog", 1, 10));
		coursesB.setPreferredSize(new Dimension(50, 50));
		coursesB.setBorderPainted(false);
		coursesB.setContentAreaFilled(false);
		coursesB.setFocusPainted(false);
		coursesB.setHorizontalTextPosition(SwingConstants.CENTER);
		coursesB.setText(Local.getString("Courses"));
		coursesB.setVerticalAlignment(SwingConstants.TOP);
		coursesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		coursesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agendaB_actionPerformed(e);
			}
		});
		coursesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/agenda.png")));
		coursesB.setOpaque(false);
		coursesB.setMargin(new Insets(0, 0, 0, 0));
		coursesB.setSelected(true);

		// Lectures button
		lecturesB.setBackground(Color.white);
		lecturesB.setMaximumSize(new Dimension(60, 80));
		lecturesB.setMinimumSize(new Dimension(30, 30));

		lecturesB.setFont(new java.awt.Font("Dialog", 1, 10));
		lecturesB.setPreferredSize(new Dimension(50, 50));
		lecturesB.setBorderPainted(false);
		lecturesB.setContentAreaFilled(false);
		lecturesB.setFocusPainted(false);
		lecturesB.setHorizontalTextPosition(SwingConstants.CENTER);
		lecturesB.setText(Local.getString("Lectures"));
		lecturesB.setVerticalAlignment(SwingConstants.TOP);
		lecturesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		lecturesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lecturesB_actionPerformed(e);
			}
		});
		lecturesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/events.png")));
		lecturesB.setOpaque(false);
		lecturesB.setMargin(new Insets(0, 0, 0, 0));
		
		
		// Assignments button
		assignmentsB.setFont(new java.awt.Font("Dialog", 1, 10));
		assignmentsB.setMargin(new Insets(0, 0, 0, 0));
		assignmentsB.setVerticalAlignment(SwingConstants.TOP);
		assignmentsB.setText(Local.getString("Assignments")); /***/
		assignmentsB.setHorizontalTextPosition(SwingConstants.CENTER);
		assignmentsB.setFocusPainted(false);
		assignmentsB.setBorderPainted(false);
		assignmentsB.setContentAreaFilled(false);
		assignmentsB.setPreferredSize(new Dimension(50, 50));
		assignmentsB.setMinimumSize(new Dimension(30, 30));
		assignmentsB.setOpaque(false);
		assignmentsB.setMaximumSize(new Dimension(60, 80));
		assignmentsB.setBackground(Color.white);
		assignmentsB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/tasks.png")));
		assignmentsB.setVerticalTextPosition(SwingConstants.BOTTOM);
		assignmentsB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				assignB_actionPerformed(e);
			}
		});
		
		
		
		// Students button
		studentsB.setSelected(true);
		studentsB.setFont(new java.awt.Font("Dialog", 1, 10));
		studentsB.setMargin(new Insets(0, 0, 0, 0));	
		studentsB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/tasks.png")));
		studentsB.setVerticalTextPosition(SwingConstants.BOTTOM);
		studentsB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentsB_actionPerformed(e);
			}
		});
		studentsB.setVerticalAlignment(SwingConstants.TOP);
		studentsB.setText(Local.getString("Students")); /***/
		studentsB.setHorizontalTextPosition(SwingConstants.CENTER);
		studentsB.setFocusPainted(false);
		studentsB.setBorderPainted(false);
		studentsB.setContentAreaFilled(false);
		studentsB.setPreferredSize(new Dimension(50, 50));
		studentsB.setMinimumSize(new Dimension(30, 30));
		studentsB.setOpaque(false);
		studentsB.setMaximumSize(new Dimension(60, 80));
		studentsB.setBackground(Color.white);
		
		// Instructor Tasks button
		instructorTasksB.setSelected(true);
		instructorTasksB.setFont(new java.awt.Font("Dialog", 1, 10));
		instructorTasksB.setMargin(new Insets(0, 0, 0, 0));
		instructorTasksB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/tasks.png")));
		instructorTasksB.setVerticalTextPosition(SwingConstants.BOTTOM);
		instructorTasksB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructorTasksB_actionPerformed(e);
			}
		});
		instructorTasksB.setVerticalAlignment(SwingConstants.TOP);
		instructorTasksB.setText(Local.getString("Instructor Tasks")); /***/
		instructorTasksB.setHorizontalTextPosition(SwingConstants.CENTER);
		instructorTasksB.setFocusPainted(false);
		instructorTasksB.setBorderPainted(false);
		instructorTasksB.setContentAreaFilled(false);
		instructorTasksB.setPreferredSize(new Dimension(50, 50));
		instructorTasksB.setMinimumSize(new Dimension(30, 30));
		instructorTasksB.setOpaque(false);
		instructorTasksB.setMaximumSize(new Dimension(60, 80));
		instructorTasksB.setBackground(Color.white);
		
		// TA / Grader Tasks Tasks button
		tagraderTasksB.setSelected(true);
		tagraderTasksB.setFont(new java.awt.Font("Dialog", 1, 10));
		tagraderTasksB.setMargin(new Insets(0, 0, 0, 0));
		tagraderTasksB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/tasks.png")));
		tagraderTasksB.setVerticalTextPosition(SwingConstants.BOTTOM);
		tagraderTasksB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tagraderTasksB_actionPerformed(e);
			}
		});
		tagraderTasksB.setVerticalAlignment(SwingConstants.TOP);
		tagraderTasksB.setText(Local.getString("TA/Grader Tasks")); /***/
		tagraderTasksB.setHorizontalTextPosition(SwingConstants.CENTER);
		tagraderTasksB.setFocusPainted(false);
		tagraderTasksB.setBorderPainted(false);
		tagraderTasksB.setContentAreaFilled(false);
		tagraderTasksB.setPreferredSize(new Dimension(50, 50));
		tagraderTasksB.setMinimumSize(new Dimension(30, 30));
		tagraderTasksB.setOpaque(false);
		tagraderTasksB.setMaximumSize(new Dimension(60, 80));
		tagraderTasksB.setBackground(Color.white);

		// Notes button
		notesB.setFont(new java.awt.Font("Dialog", 1, 10));
		notesB.setBackground(Color.white);
		notesB.setBorder(null);
		notesB.setMaximumSize(new Dimension(60, 80));
		notesB.setMinimumSize(new Dimension(30, 30));
		notesB.setOpaque(false);
		notesB.setPreferredSize(new Dimension(60, 50));
		notesB.setBorderPainted(false);
		notesB.setContentAreaFilled(false);
		notesB.setFocusPainted(false);
		notesB.setHorizontalTextPosition(SwingConstants.CENTER);
		notesB.setText(Local.getString("Notes"));
		notesB.setVerticalAlignment(SwingConstants.TOP);
		notesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		notesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notesB_actionPerformed(e);
			}
		});
		notesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/notes.png")));
		notesB.setMargin(new Insets(0, 0, 0, 0));
		notesB.setSelected(true);
		this.setPreferredSize(new Dimension(1073, 300));

		// Resources Button
		filesB.setSelected(true);
		filesB.setMargin(new Insets(0, 0, 0, 0));
		filesB.setIcon(
			new ImageIcon(
				main.java.memoranda.ui.AppFrame.class.getResource(
					"/ui/icons/files.png")));
		filesB.setVerticalTextPosition(SwingConstants.BOTTOM);
		filesB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filesB_actionPerformed(e);
			}
		});
		filesB.setFont(new java.awt.Font("Dialog", 1, 10));
		filesB.setVerticalAlignment(SwingConstants.TOP);
		filesB.setText(Local.getString("Resources"));
		filesB.setHorizontalTextPosition(SwingConstants.CENTER);
		filesB.setFocusPainted(false);
		filesB.setBorderPainted(false);
		filesB.setContentAreaFilled(false);
		filesB.setPreferredSize(new Dimension(50, 50));
		filesB.setMinimumSize(new Dimension(30, 30));
		filesB.setOpaque(false);
		filesB.setMaximumSize(new Dimension(60, 80));
		filesB.setBackground(Color.white);
		
		
		
		this.add(toolBar, BorderLayout.WEST);
		this.add(panel, BorderLayout.CENTER);
		panel.add(dailyItemsPanel, "DAILYITEMS");
		panel.add(filesPanel, "FILES");
		
		// Adding buttons to toolbar
		toolBar.add(coursesB, null);
		toolBar.add(lecturesB, null);
		toolBar.add(assignmentsB, null);
		toolBar.add(instructorTasksB, null);
		toolBar.add(tagraderTasksB, null);
		toolBar.add(studentsB, null);
		toolBar.add(notesB, null);
		toolBar.add(filesB, null);
		currentB = coursesB;
		// Default blue color
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);

		toolBar.setBorder(null);
		panel.setBorder(null);
		dailyItemsPanel.setBorder(null);
		filesPanel.setBorder(null);

	}

	public void selectPanel(String pan) {
		if (pan != null) {
			if (pan.equals("NOTES"))
				notesB_actionPerformed(null);
			else if (pan.equals("TASKS"))
				tasksB_actionPerformed(null);
			else if (pan.equals("EVENTS"))
				eventsB_actionPerformed(null);
			else if (pan.equals("FILES"))
				filesB_actionPerformed(null);
			else if (pan.contentEquals("STUDENTS"))
				studentsB_actionPerformed(null);
			else if (pan.contentEquals("ASSIGN"))
				assignB_actionPerformed(null);
		}
	}

	public void agendaB_actionPerformed(ActionEvent e) {
		Context.put("CURRENT_PANEL", "AGENDA");	
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("AGENDA");
		setCurrentButton(coursesB);
		
	}

	public void notesB_actionPerformed(ActionEvent e) {
		Context.put("CURRENT_PANEL", "NOTES");
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("NOTES");
		setCurrentButton(notesB);
		
	}
	
	public void tasksB_actionPerformed(ActionEvent e) {
		Context.put("CURRENT_PANEL", "TASKS");
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TASKS");
		setCurrentButton(assignmentsB);
		
	}
	
	public void assignB_actionPerformed(ActionEvent e) {
		Context.put("CURRENT_PANEL", "ASSIGN");
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("ASSIGN");
		setCurrentButton(assignmentsB);
	}
	
	public void lecturesB_actionPerformed(ActionEvent e) {
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("LECTURES");
		setCurrentButton(lecturesB);
		Context.put("CURRENT_PANEL", "LECTURES");
	}
		
	public void studentsB_actionPerformed(ActionEvent e) {
		Context.put("CURRENT_PANEL", "STUDENTS");
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("STUDENTS");
		setCurrentButton(studentsB);
		
	}

	public void instructorTasksB_actionPerformed(ActionEvent e) {
		Context.put("CURRENT_PANEL", "INSTRUCT");
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("INSTRUCT");
		setCurrentButton(instructorTasksB);
		
	}
	
	public void tagraderTasksB_actionPerformed(ActionEvent e) {
		Context.put("CURRENT_PANEL", "TA");
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("TA");
		setCurrentButton(tagraderTasksB);
		
	}

	public void eventsB_actionPerformed(ActionEvent e) {
		Context.put("CURRENT_PANEL", "EVENTS");
		cardLayout1.show(panel, "DAILYITEMS");
		dailyItemsPanel.selectPanel("EVENTS");
		setCurrentButton(lecturesB);
		
		
	}

	public void filesB_actionPerformed(ActionEvent e) {
		Context.put("CURRENT_PANEL", "FILES");
		cardLayout1.show(panel, "FILES");
		setCurrentButton(filesB);
		
	}

	void setCurrentButton(JButton cb) {
		currentB.setBackground(Color.white);
		currentB.setOpaque(false);
		currentB = cb;
		// Default color blue
		currentB.setBackground(new Color(215, 225, 250));
		currentB.setOpaque(true);
	}
}