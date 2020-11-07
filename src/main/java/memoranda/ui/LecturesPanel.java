package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;
import java.util.GregorianCalendar;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.History;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectListener;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.Task;
import main.java.memoranda.TaskList;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;
import main.java.memoranda.LectureTime;
import main.java.memoranda.SpecialCalendarDate;

/*$Id: TaskPanel.java,v 1.27 2007/01/17 20:49:12 killerjoe Exp $*/
public class LecturesPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JButton historyBackB = new JButton();
    JToolBar tasksToolBar = new JToolBar();
    JButton historyForwardB = new JButton();
    JButton newLectureB = new JButton();
    JButton editLectureB = new JButton();
    JButton removeLectureB = new JButton();
    
	JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();
		
    JScrollPane scrollPane = new JScrollPane();
    TaskTable taskTable = new TaskTable();
	JMenuItem ppEditTask = new JMenuItem();
	JPopupMenu taskPPMenu = new JPopupMenu();
	JMenuItem ppRemoveTask = new JMenuItem();
	JMenuItem ppNewTask = new JMenuItem();
	JMenuItem ppCompleteTask = new JMenuItem();
	//JMenuItem ppSubTasks = new JMenuItem();
	//JMenuItem ppParentTask = new JMenuItem();
	JMenuItem ppAddSubTask = new JMenuItem();
	JMenuItem ppCalcTask = new JMenuItem();
	DailyItemsPanel parentPanel = null;
	
	//main task panel frame
    public LecturesPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void jbInit() throws Exception {
        tasksToolBar.setFloatable(false);
        //history back button size and preferences
        historyBackB.setAction(History.historyBackAction);
        historyBackB.setFocusable(false);
        historyBackB.setBorderPainted(false);
        historyBackB.setToolTipText(Local.getString("History back"));
        historyBackB.setRequestFocusEnabled(false);
        historyBackB.setPreferredSize(new Dimension(24, 24));
        historyBackB.setMinimumSize(new Dimension(24, 24));
        historyBackB.setMaximumSize(new Dimension(24, 24));
        historyBackB.setText("");
        //history forward button size and prefrences
        historyForwardB.setAction(History.historyForwardAction);
        historyForwardB.setBorderPainted(false);
        historyForwardB.setFocusable(false);
        historyForwardB.setPreferredSize(new Dimension(24, 24));
        historyForwardB.setRequestFocusEnabled(false);
        historyForwardB.setToolTipText(Local.getString("History forward"));
        historyForwardB.setMinimumSize(new Dimension(24, 24));
        historyForwardB.setMaximumSize(new Dimension(24, 24));
        historyForwardB.setText("");
        //add new task and a call to the method newTaskB_actionPerformed(e)
        newLectureB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new.png")));
        newLectureB.setEnabled(true);
        newLectureB.setMaximumSize(new Dimension(24, 24));
        newLectureB.setMinimumSize(new Dimension(24, 24));
        newLectureB.setToolTipText(Local.getString("Create new lecture"));
        newLectureB.setRequestFocusEnabled(false);
        newLectureB.setPreferredSize(new Dimension(24, 24));
        newLectureB.setFocusable(false);
        newLectureB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newTaskB_actionPerformed(e);
            }
        });
        newLectureB.setBorderPainted(false);
        
        editLectureB.setBorderPainted(false);
        editLectureB.setFocusable(false);
        editLectureB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editTaskB_actionPerformed(e);
            }
        });
        editLectureB.setPreferredSize(new Dimension(24, 24));
        editLectureB.setRequestFocusEnabled(false);
        editLectureB.setToolTipText(Local.getString("Edit lecture"));
        editLectureB.setMinimumSize(new Dimension(24, 24));
        editLectureB.setMaximumSize(new Dimension(24, 24));
        //        editTaskB.setEnabled(true);
        editLectureB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_edit.png")));
        
        //remove task button setup and action
        removeLectureB.setBorderPainted(false);
        removeLectureB.setFocusable(false);
        removeLectureB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeTaskB_actionPerformed(e);
            }
        });
        removeLectureB.setPreferredSize(new Dimension(24, 24));
        removeLectureB.setRequestFocusEnabled(false);
        removeLectureB.setToolTipText(Local.getString("Remove lecture"));
        removeLectureB.setMinimumSize(new Dimension(24, 24));
        removeLectureB.setMaximumSize(new Dimension(24, 24));
        removeLectureB.setIcon(
            new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_remove.png")));

		// added by rawsushi
//		showActiveOnly.setBorderPainted(false);
//		showActiveOnly.setFocusable(false);
//		showActiveOnly.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				toggleShowActiveOnly_actionPerformed(e);
//			}
//		});
//		showActiveOnly.setPreferredSize(new Dimension(24, 24));
//		showActiveOnly.setRequestFocusEnabled(false);
//		if (taskTable.isShowActiveOnly()) {
//			showActiveOnly.setToolTipText(Local.getString("Show All"));			
//		}
//		else {
//			showActiveOnly.setToolTipText(Local.getString("Show Active Only"));			
//		}
//		showActiveOnly.setMinimumSize(new Dimension(24, 24));
//		showActiveOnly.setMaximumSize(new Dimension(24, 24));
//		showActiveOnly.setIcon(
//			new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_remove.png")));
		// added by rawsushi
		
        // show active tasks only method
        ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
		ppShowActiveOnlyChB.setText(
			Local.getString("Show Active only"));
		ppShowActiveOnlyChB
			.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleShowActiveOnly_actionPerformed(e);
			}
		});		
		boolean isShao =
			(Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
				&& (Context.get("SHOW_ACTIVE_TASKS_ONLY").equals("true"));
		ppShowActiveOnlyChB.setSelected(isShao);
		toggleShowActiveOnly_actionPerformed(null);

		/*showActiveOnly.setPreferredSize(new Dimension(24, 24));
		showActiveOnly.setRequestFocusEnabled(false);
		if (taskTable.isShowActiveOnly()) {
			showActiveOnly.setToolTipText(Local.getString("Show All"));			
		}
		else {
			showActiveOnly.setToolTipText(Local.getString("Show Active Only"));			
		}
		showActiveOnly.setMinimumSize(new Dimension(24, 24));
		showActiveOnly.setMaximumSize(new Dimension(24, 24));
		showActiveOnly.setIcon(
			new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_active.png")));*/
		// added by rawsushi


        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        /*taskTable.setMaximumSize(new Dimension(32767, 32767));
        taskTable.setRowHeight(24);*/
        ppEditTask.setFont(new java.awt.Font("Dialog", 1, 11));
    ppEditTask.setText(Local.getString("Edit lecture")+"...");
    ppEditTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppEditTask_actionPerformed(e);
            }
        });
    //edit task button 
    ppEditTask.setEnabled(false);
    ppEditTask.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_edit.png")));
    taskPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
    // remove task button and action to be called upon clicking
    ppRemoveTask.setFont(new java.awt.Font("Dialog", 1, 11));
    ppRemoveTask.setText(Local.getString("Remove lecture"));
    ppRemoveTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveTask_actionPerformed(e);
            }
        });
    ppRemoveTask.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_remove.png")));
    ppRemoveTask.setEnabled(false);
    ppNewTask.setFont(new java.awt.Font("Dialog", 1, 11));
    ppNewTask.setText(Local.getString("New lecture")+"...");
    ppNewTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppNewTask_actionPerformed(e);
            }
        });
    ppNewTask.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new.png")));
    /*
    ppSubTasks.setFont(new java.awt.Font("Dialog", 1, 11));
    ppSubTasks.setText(Local.getString("List sub tasks"));
    ppSubTasks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppListSubTasks_actionPerformed(e);
            }
        });
    ppSubTasks.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new.png")));

    ppParentTask.setFont(new java.awt.Font("Dialog", 1, 11));
    ppParentTask.setText(Local.getString("Parent Task"));
    ppParentTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppParentTask_actionPerformed(e);
            }
        });
    ppParentTask.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new.png")));
    */

	// calculate task button and action performed 
	ppCalcTask.setFont(new java.awt.Font("Dialog", 1, 11));
	ppCalcTask.setText(Local.getString("Calculate lecture data"));
	ppCalcTask.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ppCalcTask_actionPerformed(e);
			}
		});
	ppCalcTask.setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_complete.png")));
	ppCalcTask.setEnabled(false);
	//add buttons to task tool bar
    scrollPane.getViewport().add(taskTable, null);
        this.add(scrollPane, BorderLayout.CENTER);
        tasksToolBar.add(historyBackB, null);
        tasksToolBar.add(historyForwardB, null);
        tasksToolBar.addSeparator(new Dimension(8, 24));

        tasksToolBar.add(newLectureB, null);
        tasksToolBar.add(removeLectureB, null);
        tasksToolBar.addSeparator(new Dimension(8, 24));
        tasksToolBar.add(editLectureB, null);

		//tasksToolBar.add(showActiveOnly, null);
        

        this.add(tasksToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        taskTable.addMouseListener(ppListener);


        //show current date 
        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                newLectureB.setEnabled(d.inPeriod(CurrentProject.get().getStartDate(), CurrentProject.get().getEndDate()));
            }
        });
        //add a new task to the current project
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, TaskList tl, ResourcesList rl) {
                newLectureB.setEnabled(
                    CurrentDate.get().inPeriod(p.getStartDate(), p.getEndDate()));
            }
            public void projectWasChanged() {
            	//taskTable.setCurrentRootTask(null); //XXX
            }
        });
        //upon selection of a task or a sub task, emable edit and remove task buttons
        taskTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = (taskTable.getRowCount() > 0)&&(taskTable.getSelectedRow() > -1);
                editLectureB.setEnabled(enbl);ppEditTask.setEnabled(enbl);
                removeLectureB.setEnabled(enbl);ppRemoveTask.setEnabled(enbl);
				
				ppCompleteTask.setEnabled(enbl);
				ppAddSubTask.setEnabled(enbl);
				//ppSubTasks.setEnabled(enbl); // default value to be over-written later depending on whether it has sub tasks
				ppCalcTask.setEnabled(enbl); // default value to be over-written later depending on whether it has sub tasks
				
				/*if (taskTable.getCurrentRootTask() == null) {
					ppParentTask.setEnabled(false);
				}
				else {
					ppParentTask.setEnabled(true);
				}XXX*/
				
                if (enbl) {   
    				String thisTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();
    				
    				boolean hasSubTasks = CurrentProject.getTaskList().hasSubTasks(thisTaskId);
    				//ppSubTasks.setEnabled(hasSubTasks);
    				ppCalcTask.setEnabled(hasSubTasks);
    				Task t = CurrentProject.getTaskList().getTask(thisTaskId);
                    parentPanel.calendar.jnCalendar.renderer.setTask(t);
                    parentPanel.calendar.jnCalendar.updateUI();
                }    
                else {
                    parentPanel.calendar.jnCalendar.renderer.setTask(null);
                    parentPanel.calendar.jnCalendar.updateUI();
                }
            }
        });
        
        //set enables for options that are not valud when there are no tasks or no tasks are selected
        editLectureB.setEnabled(false);
        removeLectureB.setEnabled(false);
		ppAddSubTask.setEnabled(false);
		//ppSubTasks.setEnabled(false);
		//ppParentTask.setEnabled(false);
    taskPPMenu.add(ppEditTask);
    
    taskPPMenu.addSeparator();
    taskPPMenu.add(ppNewTask);
    taskPPMenu.add(ppAddSubTask);
    taskPPMenu.add(ppRemoveTask);
    
    taskPPMenu.addSeparator();
	taskPPMenu.add(ppCompleteTask);
	taskPPMenu.add(ppCalcTask);
	
    //taskPPMenu.addSeparator();
    
    //taskPPMenu.add(ppSubTasks);
    
    //taskPPMenu.addSeparator();
    //taskPPMenu.add(ppParentTask);
    
    taskPPMenu.addSeparator();
	taskPPMenu.add(ppShowActiveOnlyChB);

	
		// define key actions in TaskPanel:
		// - KEY:DELETE => delete tasks (recursivly).
		// - KEY:INTERT => insert new Subtask if another is selected.
		// - KEY:INSERT => insert new Task if nothing is selected.
		// - KEY:SPACE => finish Task.
		taskTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				if(taskTable.getSelectedRows().length>0 
					&& e.getKeyCode()==KeyEvent.VK_DELETE)
					ppRemoveTask_actionPerformed(null);
				
				else if(e.getKeyCode()==KeyEvent.VK_INSERT) {
					ppNewTask_actionPerformed(null);						
				}
				
			}
			public void	keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){} 
		});	

    }
    //defines actions to be performed when the edit task button is clicked
    void editTaskB_actionPerformed(ActionEvent e) {
        Task t =
            CurrentProject.getTaskList().getTask(
                taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString());
        TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("Edit lecture"));
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.todoField.setText(t.getText());
        dlg.descriptionField.setText(t.getDescription());
        dlg.startDate.getModel().setValue(t.getStartDate().getDate());
        dlg.endDate.getModel().setValue(t.getEndDate().getDate());
        dlg.priorityCB.setSelectedIndex(t.getPriority());                
        dlg.effortField.setText(Util.getHoursFromMillis(t.getEffort()));
	if((t.getStartDate().getDate()).after(t.getEndDate().getDate()))
		dlg.chkEndDate.setSelected(false);
	else
		dlg.chkEndDate.setSelected(true);
		dlg.progress.setValue(new Integer(t.getProgress()));
 	dlg.chkEndDate_actionPerformed(null);	
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
//        CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
         CalendarDate ed;
 		if(dlg.chkEndDate.isSelected())
 			ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
 		else
 			ed = null;
        t.setStartDate(sd);
        t.setEndDate(ed);
        t.setText(dlg.todoField.getText());
        t.setDescription(dlg.descriptionField.getText());
        t.setPriority(dlg.priorityCB.getSelectedIndex());
        t.setEffort(Util.getMillisFromHours(dlg.effortField.getText()));
        t.setProgress(((Integer)dlg.progress.getValue()).intValue());
        
//		CurrentProject.getTaskList().adjustParentTasks(t);

        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
        parentPanel.updateIndicators();
        //taskTable.updateUI();
    }
    //defines actions to be performed when new task is added
    void newTaskB_actionPerformed(ActionEvent e) {
        TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("New task"));
        
        //XXX String parentTaskId = taskTable.getCurrentRootTask();
        
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        dlg.startDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.endDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);

        if (dlg.CANCELLED)
            return;

        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        //CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        CalendarDate ed;
 		if(dlg.chkEndDate.isSelected())
 			ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
 		else
 			ed = null;
        long effort = Util.getMillisFromHours(dlg.effortField.getText());
		//XXX Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.todoField.getText(), dlg.priorityCB.getSelectedIndex(),effort, dlg.descriptionField.getText(),parentTaskId);
		Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.todoField.getText(), dlg.priorityCB.getSelectedIndex(),effort, dlg.descriptionField.getText(),null);
        //CurrentProject.getTaskList().adjustParentTasks(newTask);
		newTask.setProgress(((Integer)dlg.progress.getValue()).intValue());
        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
        parentPanel.updateIndicators();
        //taskTable.updateUI();
    }

    


    //New for add lecture times
    LectureTime newLectureTime_actionPerformed() {
        LectureDialog dlg = new LectureDialog(App.getFrame(), Local.getString("New Lecture Time"));
        
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        
        //get the lecture time selected
        Calendar calendar = new GregorianCalendar(Local.getCurrentLocale());
		calendar.setTime(((Date)dlg.timeSpin.getModel().getValue()));
		int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);

        //gets the day selected
        String day = (String)dlg.daysCB.getSelectedItem();

        LectureTime lecTime = new LectureTime(day, hh, mm);

        if (dlg.CANCELLED)
            return null;

        return lecTime;
    }
    //New for freedays
    SpecialCalendarDate newFreeDay_actionPerformed() {
        CourseSpecialDaysDialog dlg = new CourseSpecialDaysDialog(App.getFrame(), Local.getString("New Free Day"));

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        dlg.dateOfEvent.getModel().setValue(CurrentDate.get().getDate());
        
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);

        SpecialCalendarDate freeDay = new SpecialCalendarDate(new CalendarDate((Date) dlg.dateOfEvent.getModel().getValue()), dlg.nameField.getText());
        /**
         * TODO
         * Get the value for freeday and return it
         */
        if (dlg.CANCELLED)
            return null;

        return freeDay;
    }
    //New for holidays
    SpecialCalendarDate newHoliday_actionPerformed() {
        CourseSpecialDaysDialog dlg = new CourseSpecialDaysDialog(App.getFrame(), Local.getString("New Holiday"));

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        dlg.dateOfEvent.getModel().setValue(CurrentDate.get().getDate());
        
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);

        SpecialCalendarDate holiday = new SpecialCalendarDate(new CalendarDate((Date) dlg.dateOfEvent.getModel().getValue()), dlg.nameField.getText());

        if (dlg.CANCELLED)
            return null;

        return holiday;
    }
    
    //method to calculate the effort of tasks
    void calcTask_actionPerformed(ActionEvent e) {
        TaskCalcDialog dlg = new TaskCalcDialog(App.getFrame());
        dlg.pack();
        Task t = CurrentProject.getTaskList().getTask(taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString());
        
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED) {
            return;            
        }
        
        TaskList tl = CurrentProject.getTaskList();
        if(dlg.calcEffortChB.isSelected()) {
            t.setEffort(tl.calculateTotalEffortFromSubTasks(t));
        }
        
        if(dlg.compactDatesChB.isSelected()) {
            t.setStartDate(tl.getEarliestStartDateFromSubTasks(t));
            t.setEndDate(tl.getLatestEndDateFromSubTasks(t));
        }
        
        if(dlg.calcCompletionChB.isSelected()) {
            long[] res = tl.calculateCompletionFromSubTasks(t);
            int thisProgress = (int) Math.round((((double)res[0] / (double)res[1]) * 100));
            t.setProgress(thisProgress);
        }
        
//        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
////        CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
//          CalendarDate ed;
// 		if(dlg.chkEndDate.isSelected())
// 			ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
// 		else
// 			ed = new CalendarDate(0,0,0);
//        long effort = Util.getMillisFromHours(dlg.effortField.getText());
//		Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.todoField.getText(), dlg.priorityCB.getSelectedIndex(),effort, dlg.descriptionField.getText(),parentTaskId);
//		
		
        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
//        parentPanel.updateIndicators();
        //taskTable.updateUI();
    }

    //present sub tasks for a specific 
    void listSubTasks_actionPerformed(ActionEvent e) {
        String parentTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();
        
        //XXX taskTable.setCurrentRootTask(parentTaskId); 
		taskTable.tableChanged();

//        parentPanel.updateIndicators();
//        //taskTable.updateUI();
    }

    void parentTask_actionPerformed(ActionEvent e) {
//    	String taskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();
//      
//    	Task t = CurrentProject.getTaskList().getTask(taskId);
    	/*XXX Task t2 = CurrentProject.getTaskList().getTask(taskTable.getCurrentRootTask());
    	
    	String parentTaskId = t2.getParent();
    	if((parentTaskId == null) || (parentTaskId.equals(""))) {
    		parentTaskId = null;
    	}
    	taskTable.setCurrentRootTask(parentTaskId); 
    	taskTable.tableChanged();*/

//      parentPanel.updateIndicators();
//      //taskTable.updateUI();
  }
    
    //actions to be performed when removing a task
    void removeTaskB_actionPerformed(ActionEvent e) {
        String msg;
        String thisTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();
        
        if (taskTable.getSelectedRows().length > 1)
            msg = Local.getString("Remove")+" "+taskTable.getSelectedRows().length +" "+Local.getString("tasks")+"?"
             + "\n"+Local.getString("Are you sure?");
        else {        	
        	Task t = CurrentProject.getTaskList().getTask(thisTaskId);
        	// check if there are subtasks
			if(CurrentProject.getTaskList().hasSubTasks(thisTaskId)) {
				msg = Local.getString("Remove lecture")+"\n'" + t.getText() + "\n"+Local.getString("Are you sure?");
			}
			else {		            
				msg = Local.getString("Remove lecture")+"\n'" + t.getText() + "'\n"+Local.getString("Are you sure?");
			}
        }
        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                Local.getString("Remove lecture"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;
        Vector toremove = new Vector();
        for (int i = 0; i < taskTable.getSelectedRows().length; i++) {
            Task t =
            CurrentProject.getTaskList().getTask(
                taskTable.getModel().getValueAt(taskTable.getSelectedRows()[i], TaskTable.TASK_ID).toString());
            if (t != null)
                toremove.add(t);
        }
        for (int i = 0; i < toremove.size(); i++) {
            CurrentProject.getTaskList().removeTask((Task)toremove.get(i));
        }
        taskTable.tableChanged();
        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        parentPanel.updateIndicators();
        //taskTable.updateUI();

    }

	// toggle "show active only"
	void toggleShowActiveOnly_actionPerformed(ActionEvent e) {
		Context.put(
			"SHOW_ACTIVE_TASKS_ONLY",
			new Boolean(ppShowActiveOnlyChB.isSelected()));
		taskTable.tableChanged();
	}

    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
		if ((e.getClickCount() == 2) && (taskTable.getSelectedRow() > -1)){
			// ignore "tree" column
			//if(taskTable.getSelectedColumn() == 1) return;
			
			editTaskB_actionPerformed(null);
		}
        }

                public void mousePressed(MouseEvent e) {
                    maybeShowPopup(e);
                }

                public void mouseReleased(MouseEvent e) {
                    maybeShowPopup(e);
                }

                private void maybeShowPopup(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        taskPPMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }

    }

  void ppEditTask_actionPerformed(ActionEvent e) {
    editTaskB_actionPerformed(e);
  }
  void ppRemoveTask_actionPerformed(ActionEvent e) {
    removeTaskB_actionPerformed(e);
  }
  void ppNewTask_actionPerformed(ActionEvent e) {
    newTaskB_actionPerformed(e);
  }

  void ppListSubTasks_actionPerformed(ActionEvent e) {
  	listSubTasks_actionPerformed(e);
  }

  void ppParentTask_actionPerformed(ActionEvent e) {
  	parentTask_actionPerformed(e);
  }

  void ppCalcTask_actionPerformed(ActionEvent e) {
      calcTask_actionPerformed(e);
  }

}