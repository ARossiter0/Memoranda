package memoranda.ui;

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
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import memoranda.CurrentProject;
import memoranda.History;
import memoranda.LectureList;
import memoranda.NoteList;
import memoranda.Project;
import memoranda.ProjectListener;
import memoranda.ResourcesList;
import memoranda.Task;
import memoranda.TaskList;
import memoranda.date.CalendarDate;
import memoranda.date.CurrentDate;
import memoranda.date.DateListener;
import memoranda.ui.TaskDialog;
import memoranda.util.Context;
import memoranda.util.CurrentStorage;
import memoranda.util.Local;
import memoranda.util.Util;
import memoranda.LectureTime;
import memoranda.SpecialCalendarDate;

/*$Id: TaskPanel.java,v 1.27 2007/01/17 20:49:12 killerjoe Exp $*/
public class TaskPanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JButton historyBackB = new JButton();
    JToolBar tasksToolBar = new JToolBar();
    JButton historyForwardB = new JButton();
    JButton newTaskB = new JButton();
    JButton subTaskB = new JButton();
    JButton editTaskB = new JButton();
    JButton removeTaskB = new JButton();
    JButton completeTaskB = new JButton();

    JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();
    JCheckBoxMenuItem ppShowReducedOnlyChB = new JCheckBoxMenuItem();

    JScrollPane scrollPane = new JScrollPane();
    TaskTable taskTable = new TaskTable();
    JMenuItem ppEditTask = new JMenuItem();
    JPopupMenu taskPPMenu = new JPopupMenu();
    JMenuItem ppRemoveTask = new JMenuItem();
    JMenuItem ppNewTask = new JMenuItem();
    JMenuItem ppCompleteTask = new JMenuItem();
    // JMenuItem ppSubTasks = new JMenuItem();
    // JMenuItem ppParentTask = new JMenuItem();
    JMenuItem ppAddSubTask = new JMenuItem();
    JMenuItem ppCalcTask = new JMenuItem();
    DailyItemsPanel parentPanel = null;

    // main task panel frame
    public TaskPanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void toggleReducedOnlyChB(boolean on) {
        if (on) {
            taskPPMenu.add(ppShowReducedOnlyChB);
            
            toggleShowReducedOnly_actionPerformed(null);
        } else {
            Context.put("SHOW_REDUCED_ONLY", false);
            taskTable.tableChanged();
            taskPPMenu.remove(ppShowReducedOnlyChB);
        }
    }

    void jbInit() throws Exception {
        tasksToolBar.setFloatable(false);
        // history back button size and preferences
        historyBackB.setAction(History.historyBackAction);
        historyBackB.setFocusable(false);
        historyBackB.setBorderPainted(false);
        historyBackB.setToolTipText(Local.getString("History back"));
        historyBackB.setRequestFocusEnabled(false);
        historyBackB.setPreferredSize(new Dimension(24, 24));
        historyBackB.setMinimumSize(new Dimension(24, 24));
        historyBackB.setMaximumSize(new Dimension(24, 24));
        historyBackB.setText("");
        // history forward button size and prefrences
        historyForwardB.setAction(History.historyForwardAction);
        historyForwardB.setBorderPainted(false);
        historyForwardB.setFocusable(false);
        historyForwardB.setPreferredSize(new Dimension(24, 24));
        historyForwardB.setRequestFocusEnabled(false);
        historyForwardB.setToolTipText(Local.getString("History forward"));
        historyForwardB.setMinimumSize(new Dimension(24, 24));
        historyForwardB.setMaximumSize(new Dimension(24, 24));
        historyForwardB.setText("");
        // add new task and a call to the method newTaskB_actionPerformed(e)
        newTaskB.setIcon(new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new.png")));
        newTaskB.setEnabled(true);
        newTaskB.setMaximumSize(new Dimension(24, 24));
        newTaskB.setMinimumSize(new Dimension(24, 24));
        //Changes the layout of the tasks panel
        
        newTaskB.setToolTipText(Local.getString("Create new task"));
        newTaskB.setRequestFocusEnabled(false);
        newTaskB.setPreferredSize(new Dimension(24, 24));
        newTaskB.setFocusable(false);
        newTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Context.get("CURRENT_PANEL").equals("ASSIGN")) {
                    newAssignmentButton_actionPerformed(e);
                } else {
                    newTaskB_actionPerformed(e);
                }
            }
        });
        newTaskB.setBorderPainted(false);

        // add new sub task button and a call to method addSubTask_actionPerformed(e);
        subTaskB.setIcon(
                new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new_sub.png")));
        subTaskB.setEnabled(true);
        subTaskB.setMaximumSize(new Dimension(24, 24));
        subTaskB.setMinimumSize(new Dimension(24, 24));
        
        subTaskB.setToolTipText(Local.getString("Add subtask"));
        subTaskB.setRequestFocusEnabled(false);
        subTaskB.setPreferredSize(new Dimension(24, 24));
        subTaskB.setFocusable(false);
        subTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSubTask_actionPerformed(e);
            }
        });
        subTaskB.setBorderPainted(false);

        editTaskB.setBorderPainted(false);
        editTaskB.setFocusable(false);
        editTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editTaskB_actionPerformed(e);
            }
        });
        editTaskB.setPreferredSize(new Dimension(24, 24));
        editTaskB.setRequestFocusEnabled(false);

        //Changes edit task to edit assignment
        
        editTaskB.setToolTipText(Local.getString("Edit task"));
        editTaskB.setMinimumSize(new Dimension(24, 24));
        editTaskB.setMaximumSize(new Dimension(24, 24));
        // editTaskB.setEnabled(true);
        editTaskB.setIcon(new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_edit.png")));

        // remove task button setup and action
        removeTaskB.setBorderPainted(false);
        removeTaskB.setFocusable(false);
        removeTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeTaskB_actionPerformed(e);
            }
        });
        removeTaskB.setPreferredSize(new Dimension(24, 24));
        removeTaskB.setRequestFocusEnabled(false);
        //Changes layout
        removeTaskB.setToolTipText(Local.getString("Remove task"));
        removeTaskB.setMinimumSize(new Dimension(24, 24));
        removeTaskB.setMaximumSize(new Dimension(24, 24));
        removeTaskB
                .setIcon(new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_remove.png")));
        // mark task a complete button and action
        completeTaskB.setBorderPainted(false);
        completeTaskB.setFocusable(false);
        completeTaskB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppCompleteTask_actionPerformed(e);
            }
        });
        completeTaskB.setPreferredSize(new Dimension(24, 24));
        completeTaskB.setRequestFocusEnabled(false);
        completeTaskB.setToolTipText(Local.getString("Complete task"));
        completeTaskB.setMinimumSize(new Dimension(24, 24));
        completeTaskB.setMaximumSize(new Dimension(24, 24));
        completeTaskB.setIcon(
                new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_complete.png")));

        // added by rawsushi
//        showActiveOnly.setBorderPainted(false);
//        showActiveOnly.setFocusable(false);
//        showActiveOnly.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                toggleShowActiveOnly_actionPerformed(e);
//            }
//        });
//        showActiveOnly.setPreferredSize(new Dimension(24, 24));
//        showActiveOnly.setRequestFocusEnabled(false);
//        if (taskTable.isShowActiveOnly()) {
//            showActiveOnly.setToolTipText(Local.getString("Show All"));            
//        }
//        else {
//            showActiveOnly.setToolTipText(Local.getString("Show Active Only"));            
//        }
//        showActiveOnly.setMinimumSize(new Dimension(24, 24));
//        showActiveOnly.setMaximumSize(new Dimension(24, 24));
//        showActiveOnly.setIcon(
//            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_remove.png")));
        // added by rawsushi

        // show active tasks only method
        ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
        ppShowActiveOnlyChB.setText(Local.getString("Show Active only"));
        ppShowActiveOnlyChB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleShowActiveOnly_actionPerformed(e);
            }
        });


        //boolean isShao = (Context.get("SHOW_ACTIVE_TASKS_ONLY") != null)
                //&& (Context.get("SHOW_ACTIVE_TASKS_ONLY").equals("true"));
        boolean isShao = false;
        ppShowActiveOnlyChB.setSelected(isShao);
        toggleShowActiveOnly_actionPerformed(null);

        // For instructor todo list
        ppShowReducedOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
        ppShowReducedOnlyChB.setText(Local.getString("Show only todo items visible to students"));
        ppShowReducedOnlyChB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleShowReducedOnly_actionPerformed(e);
            }
        });
        boolean isShro = (Context.get("SHOW_REDUCED_ONLY") != null)
                && (Context.get("SHOW_REDUCED_ONLY").equals("true"));
        ppShowReducedOnlyChB.setSelected(isShro);
        toggleShowReducedOnly_actionPerformed(null);

        /*
         * showActiveOnly.setPreferredSize(new Dimension(24, 24));
         * showActiveOnly.setRequestFocusEnabled(false); if
         * (taskTable.isShowActiveOnly()) {
         * showActiveOnly.setToolTipText(Local.getString("Show All")); } else {
         * showActiveOnly.setToolTipText(Local.getString("Show Active Only")); }
         * showActiveOnly.setMinimumSize(new Dimension(24, 24));
         * showActiveOnly.setMaximumSize(new Dimension(24, 24)); showActiveOnly.setIcon(
         * new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource(
         * "/ui/icons/todo_active.png")));
         */
        // added by rawsushi

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        /*
         * taskTable.setMaximumSize(new Dimension(32767, 32767));
         * taskTable.setRowHeight(24);
         */
        ppEditTask.setFont(new java.awt.Font("Dialog", 1, 11));
        ppEditTask.setText(Local.getString("Edit task") + "...");
        ppEditTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppEditTask_actionPerformed(e);
            }
        });
        // edit task button
        ppEditTask.setEnabled(false);
        ppEditTask.setIcon(new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_edit.png")));
        taskPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        // remove task button and action to be called upon clicking
        ppRemoveTask.setFont(new java.awt.Font("Dialog", 1, 11));
        ppRemoveTask.setText(Local.getString("Remove task"));
        ppRemoveTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveTask_actionPerformed(e);
            }
        });
        ppRemoveTask
                .setIcon(new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_remove.png")));
        ppRemoveTask.setEnabled(false);
        ppNewTask.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewTask.setText(Local.getString("New task") + "...");
        ppNewTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppNewTask_actionPerformed(e);
            }
        });
        ppNewTask.setIcon(new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new.png")));
        // add sub task action performed
        ppAddSubTask.setFont(new java.awt.Font("Dialog", 1, 11));
        ppAddSubTask.setText(Local.getString("Add subtask"));
        ppAddSubTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppAddSubTask_actionPerformed(e);
            }
        });
        ppAddSubTask.setIcon(new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_new_sub.png")));

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
        //complete task button and action performed
        ppCompleteTask.setFont(new java.awt.Font("Dialog", 1, 11));
        ppCompleteTask.setText(Local.getString("Complete task"));
        ppCompleteTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppCompleteTask_actionPerformed(e);
            }
        });
        ppCompleteTask.setIcon(new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_complete.png")));
        ppCompleteTask.setEnabled(false);
        // calculate task button and action performed 
        ppCalcTask.setFont(new java.awt.Font("Dialog", 1, 11));
        ppCalcTask.setText(Local.getString("Calculate task data"));
        ppCalcTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppCalcTask_actionPerformed(e);
            }
        });
        ppCalcTask.setIcon(new ImageIcon(memoranda.ui.AppFrame.class.getResource("/ui/icons/todo_complete.png")));
        ppCalcTask.setEnabled(false);
        //add buttons to task tool bar
        scrollPane.getViewport().add(taskTable, null);
        this.add(scrollPane, BorderLayout.CENTER);
        tasksToolBar.add(historyBackB, null);
        tasksToolBar.add(historyForwardB, null);
        tasksToolBar.addSeparator(new Dimension(8, 24));

        tasksToolBar.add(newTaskB, null);

        tasksToolBar.add(subTaskB, null);

        tasksToolBar.add(removeTaskB, null);
        tasksToolBar.addSeparator(new Dimension(8, 24));
        tasksToolBar.add(editTaskB, null);
        tasksToolBar.add(completeTaskB, null);

        // tasksToolBar.add(showActiveOnly, null);

        this.add(tasksToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        taskTable.addMouseListener(ppListener);

        // show current date
        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                newTaskB.setEnabled(d.inPeriod(CurrentProject.get().getStartDate(), CurrentProject.get().getEndDate()));
            }
        });
        // add a new task to the current project
        CurrentProject.addProjectListener(new ProjectListener() {

            public void projectChange(Project p, NoteList nl, LectureList tl, TaskList instrTodoList, TaskList s1, TaskList s2, ResourcesList rl) {
                newTaskB.setEnabled(CurrentDate.get().inPeriod(p.getStartDate(), p.getEndDate()));

            }

            public void projectWasChanged() {
                // taskTable.setCurrentRootTask(null); //XXX
            }
        });
        // upon selection of a task or a sub task, emable edit and remove task buttons
        taskTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = (taskTable.getRowCount() > 0) && (taskTable.getSelectedRow() > -1);
                editTaskB.setEnabled(enbl);
                ppEditTask.setEnabled(enbl);
                removeTaskB.setEnabled(enbl);
                ppRemoveTask.setEnabled(enbl);

                ppCompleteTask.setEnabled(enbl);
                completeTaskB.setEnabled(enbl);
                ppAddSubTask.setEnabled(enbl);
                // ppSubTasks.setEnabled(enbl); // default value to be over-written later
                // depending on whether it has sub tasks
                ppCalcTask.setEnabled(enbl); // default value to be over-written later depending on whether it has sub
                                             // tasks

                /*
                 * if (taskTable.getCurrentRootTask() == null) { ppParentTask.setEnabled(false);
                 * } else { ppParentTask.setEnabled(true); }XXX
                 */

                if (enbl) {
                    String thisTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID)
                            .toString();

                    boolean hasSubTasks = CurrentProject.getTaskList().hasSubTasks(thisTaskId);
                    // ppSubTasks.setEnabled(hasSubTasks);
                    ppCalcTask.setEnabled(hasSubTasks);
                    Task t = CurrentProject.getTaskList().getTask(thisTaskId);
                    parentPanel.calendar.jnCalendar.renderer.setTask(t);
                    parentPanel.calendar.jnCalendar.updateUI();
                } else {
                    parentPanel.calendar.jnCalendar.renderer.setTask(null);
                    parentPanel.calendar.jnCalendar.updateUI();
                }
            }
        });

        // set enables for options that are not valud when there are no tasks or no
        // tasks are selected
        editTaskB.setEnabled(false);
        removeTaskB.setEnabled(false);
        completeTaskB.setEnabled(false);
        ppAddSubTask.setEnabled(false);
        // ppSubTasks.setEnabled(false);
        // ppParentTask.setEnabled(false);
        taskPPMenu.add(ppEditTask);

        taskPPMenu.addSeparator();
        taskPPMenu.add(ppNewTask);
        taskPPMenu.add(ppAddSubTask);
        taskPPMenu.add(ppRemoveTask);

        taskPPMenu.addSeparator();
        taskPPMenu.add(ppCompleteTask);
        taskPPMenu.add(ppCalcTask);

        // taskPPMenu.addSeparator();

        // taskPPMenu.add(ppSubTasks);

        // taskPPMenu.addSeparator();
        // taskPPMenu.add(ppParentTask);

        taskPPMenu.addSeparator();
        taskPPMenu.add(ppShowActiveOnlyChB);
        
        // For instructor todo list
        

        // define key actions in TaskPanel:
        // - KEY:DELETE => delete tasks (recursivly).
        // - KEY:INTERT => insert new Subtask if another is selected.
        // - KEY:INSERT => insert new Task if nothing is selected.
        // - KEY:SPACE => finish Task.
        taskTable.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (taskTable.getSelectedRows().length > 0 && e.getKeyCode() == KeyEvent.VK_DELETE)
                    ppRemoveTask_actionPerformed(null);

                else if (e.getKeyCode() == KeyEvent.VK_INSERT) {
                    if (taskTable.getSelectedRows().length > 0) {
                        ppAddSubTask_actionPerformed(null);
                    } else {
                        ppNewTask_actionPerformed(null);
                    }
                }

                else if (e.getKeyCode() == KeyEvent.VK_SPACE && taskTable.getSelectedRows().length > 0) {
                    ppCompleteTask_actionPerformed(null);
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

    }

    //defines actions to be performed when the edit task button is clicked
    void editTaskB_actionPerformed(ActionEvent e) {
        Task t = CurrentProject.getTaskList().getTask(
            taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString());
        TaskDialog dlg;

        if (Context.get("CURRENT_PANEL").equals("ASSIGN")) {
            dlg = new TaskDialog(App.getFrame(), Local.getString("Edit Assignment"));
        } else {
            dlg = new TaskDialog(App.getFrame(), Local.getString("Edit task"));
        }

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.todoField.setText(t.getText());
        dlg.descriptionField.setText(t.getDescription());
        dlg.startDate.getModel().setValue(t.getStartDate().getDate());
        dlg.endDate.getModel().setValue(t.getEndDate().getDate());
        dlg.priorityCB.setSelectedIndex(t.getPriority());
        dlg.setChkIsInReduced(t.getIsInReduced());
        dlg.effortField.setText(Util.getHoursFromMillis(t.getEffort()));

        if((t.getStartDate().getDate()).after(t.getEndDate().getDate())) { //Correct ???
            dlg.chkEndDate.setSelected(false);
        } else {
            dlg.chkEndDate.setSelected(true);
            dlg.progress.setValue(new Integer(t.getProgress()));
            dlg.chkEndDate_actionPerformed(null);
        }
     
        if (Context.get("CURRENT_PANEL").equals("ASSIGN")) {
            dlg.border3.setTitle(Local.getString("Assignment Name"));
            dlg.jLabel6.setText(Local.getString("Assigned"));
            dlg.jLabel2.setText(Local.getString("Due Date"));

            dlg.chkEndDate_Augmented();
            dlg.chkEndDate.setVisible(false);
            dlg.jPanel4.setVisible(false);
            dlg.jPanelProgress.setVisible(false);
        }

        dlg.setVisible(true);

        if (dlg.CANCELLED) {
            return;
        }

        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        CalendarDate ed = null;

        if (Context.get("CURRENT_PANEL").equals("ASSIGN")) {
            ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        } else if(dlg.chkEndDate.isSelected()) {
            ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        }

        t.setStartDate(sd);
        t.setEndDate(ed);
        t.setText(dlg.todoField.getText());
        t.setDescription(dlg.descriptionField.getText());
        t.setPriority(dlg.priorityCB.getSelectedIndex());
        t.setIsInReduced(dlg.getIsInReduced());
        t.setEffort(Util.getMillisFromHours(dlg.effortField.getText()));
        t.setProgress(((Integer) dlg.progress.getValue()).intValue());
        

        if(Context.get("CURRENT_PANEL").equals("ASSIGN")) {
            CurrentStorage.get().storeAssignList(CurrentProject.getAssignList(), CurrentProject.get());
        } else {
            CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        }
        

        taskTable.tableChanged();
        parentPanel.updateIndicators();
    }

    // defines actions to be performed when new task is added
    void newTaskB_actionPerformed(ActionEvent e) {
        TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("New task"));

        // XXX String parentTaskId = taskTable.getCurrentRootTask();

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        dlg.startDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.endDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);

        if (dlg.CANCELLED) {
            return;
        }

        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        // CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        CalendarDate ed;
        if (dlg.chkEndDate.isSelected())
            ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        else
            ed = null;



        long effort = Util.getMillisFromHours(dlg.effortField.getText());
        // XXX Task newTask = CurrentProject.getTaskList().createTask(sd, ed,
        // dlg.todoField.getText(), dlg.priorityCB.getSelectedIndex(),effort,
        // dlg.descriptionField.getText(),parentTaskId);


        Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.todoField.getText(),
                dlg.priorityCB.getSelectedIndex(), effort, dlg.descriptionField.getText(), null, dlg.getIsInReduced());
        // CurrentProject.getTaskList().adjustParentTasks(newTask);
        newTask.setProgress(((Integer) dlg.progress.getValue()).intValue());


        if (CurrentProject.currentTaskType == CurrentProject.TaskType.INSTR_TODO_LIST){
            CurrentStorage.get().storeInstrTodoList(CurrentProject.getTaskList(), CurrentProject.get());
        } else if (CurrentProject.currentTaskType == CurrentProject.TaskType.STUDENT_TODO){
            CurrentStorage.get().storeStudentTodo(CurrentProject.getTaskList(), CurrentProject.get());
        } else {
            CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        }


        taskTable.tableChanged();


        parentPanel.updateIndicators();
        // taskTable.updateUI();
    }
    /**
     * When the new assignment button is clicked, makes a new window for adding an assignment.
     * Added for US149.
     */
    void newAssignmentButton_actionPerformed(ActionEvent e) {
        TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("New Assignment"));
        
        //XXX String parentTaskId = taskTable.getCurrentRootTask();
        
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        dlg.startDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.endDate.getModel().setValue(CurrentDate.get().getDate());
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        

        //Modifications to make task window into assignment window

        dlg.border3.setTitle(Local.getString("Assignment Name"));
        dlg.jLabel6.setText(Local.getString("Assigned"));
        dlg.jLabel2.setText(Local.getString("Due Date"));

        dlg.chkEndDate_Augmented();
        dlg.chkEndDate.setVisible(false);
        dlg.jPanel4.setVisible(false);
        dlg.jPanelProgress.setVisible(false);
        
        dlg.setVisible(true);

        if (dlg.CANCELLED) {
            return;
        }

        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        //CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());

        long effort = Util.getMillisFromHours(dlg.effortField.getText());
        Task newTask = CurrentProject.getAssignList().createTask(sd, ed, dlg.todoField.getText(), dlg.priorityCB.getSelectedIndex(),effort, dlg.descriptionField.getText(),null);
        CurrentStorage.get().storeAssignList(CurrentProject.getAssignList(), CurrentProject.get());
        taskTable.tableChanged();
        parentPanel.updateIndicators();
        //taskTable.updateUI();
    }

  //New for add lecture times
    LectureTime newLectureTime_actionPerformed() {
        LectureDialog dlg = new LectureDialog(App.getFrame(), Local.getString("New Lecture Time"));

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        Calendar cdate = CurrentDate.get().getCalendar();
        cdate.set(Calendar.MINUTE,0);

        dlg.startTimeSpin.getModel().setValue(cdate.getTime());
        dlg.endTimeSpin.getModel().setValue(cdate.getTime());
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);

        //------------------------------------------

        String topic = dlg.lecTopicField.getText();
        CalendarDate date = new CalendarDate((Date) dlg.dateSpin.getModel().getValue());
        
        Calendar startTime = new GregorianCalendar(Local.getCurrentLocale());
        startTime.setTime(((Date)dlg.startTimeSpin.getModel().getValue()));
        
        Calendar endTime = new GregorianCalendar(Local.getCurrentLocale());
        endTime.setTime(((Date)dlg.endTimeSpin.getModel().getValue()));
        
        CurrentProject.getLectureList().createLecture(date, startTime, endTime, topic);
        CurrentStorage.get().storeLectureList(CurrentProject.getLectureList(), CurrentProject.get());

        //------------------------------------------

        //get the lecture time selected
        Calendar calendar = new GregorianCalendar(Local.getCurrentLocale());
        calendar.setTime(((Date) dlg.startTimeSpin.getModel().getValue()));

        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);
        String day = dlg.dayLabel.getText();

        LectureTime lecTime = new LectureTime(day, hh, mm);

        if (dlg.CANCELLED) {
            return null;
        }
        return lecTime;
    }

    //New for freedays

    SpecialCalendarDate newFreeDay_actionPerformed() {
        CourseSpecialDaysDialog dlg = new CourseSpecialDaysDialog(App.getFrame(), Local.getString("New Free Day"));

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        dlg.dateOfEvent.getModel().setValue(CurrentDate.get().getDate());

        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);

        SpecialCalendarDate freeDay = new SpecialCalendarDate(
                new CalendarDate((Date) dlg.dateOfEvent.getModel().getValue()), dlg.nameField.getText());
        /**
         * TODO Get the value for freeday and return it
         */
        if (dlg.CANCELLED)
            return null;

        return freeDay;
    }

    // New for holidays
    SpecialCalendarDate newHoliday_actionPerformed() {
        CourseSpecialDaysDialog dlg = new CourseSpecialDaysDialog(App.getFrame(), Local.getString("New Holiday"));

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        dlg.dateOfEvent.getModel().setValue(CurrentDate.get().getDate());

        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);

        SpecialCalendarDate holiday = new SpecialCalendarDate(
                new CalendarDate((Date) dlg.dateOfEvent.getModel().getValue()), dlg.nameField.getText());

        if (dlg.CANCELLED)
            return null;

        return holiday;
    }

    //New for breaks
    SpecialCalendarDate newBreak_actionPerformed() {
        CourseSpecialDaysDialog dlg = new CourseSpecialDaysDialog(App.getFrame(), Local.getString("New Break"));

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        dlg.dateOfEvent.getModel().setValue(CurrentDate.get().getDate());
        
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x, (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);

        SpecialCalendarDate courseBreak = new SpecialCalendarDate(new CalendarDate((Date) dlg.dateOfEvent.getModel().getValue()), dlg.nameField.getText());

        if (dlg.CANCELLED)
            return null;

        return courseBreak;
    }
  
    //defines actions to be performed when a new sub task is added
    void addSubTask_actionPerformed(ActionEvent e) {
        TaskDialog dlg = new TaskDialog(App.getFrame(), Local.getString("New Task"));
        String parentTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();

        // Util.debug("Adding sub task under " + parentTaskId);

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        Task parent = CurrentProject.getTaskList().getTask(parentTaskId);
        CalendarDate todayD = CurrentDate.get();
        if (todayD.after(parent.getStartDate()))
            dlg.setStartDate(todayD);
        else
            dlg.setStartDate(parent.getStartDate());
        if (parent.getEndDate() != null)
            dlg.setEndDate(parent.getEndDate());
        else
            dlg.setEndDate(CurrentProject.get().getEndDate());
        dlg.setStartDateLimit(parent.getStartDate(), parent.getEndDate());
        dlg.setEndDateLimit(parent.getStartDate(), parent.getEndDate());
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED)
            return;
        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
        // CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        CalendarDate ed;
        if (dlg.chkEndDate.isSelected())
            ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
        else
            ed = null;
        long effort = Util.getMillisFromHours(dlg.effortField.getText());
        Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.todoField.getText(),
                dlg.priorityCB.getSelectedIndex(), effort, dlg.descriptionField.getText(), parentTaskId);
        newTask.setProgress(((Integer) dlg.progress.getValue()).intValue());
        // CurrentProject.getTaskList().adjustParentTasks(newTask);

        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
        parentPanel.updateIndicators();
        // taskTable.updateUI();
    }

    // method to calculate the effort of tasks
    void calcTask_actionPerformed(ActionEvent e) {
        TaskCalcDialog dlg = new TaskCalcDialog(App.getFrame());
        dlg.pack();
        Task t = CurrentProject.getTaskList()
                .getTask(taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString());

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);
        if (dlg.CANCELLED) {
            return;
        }

        TaskList tl = CurrentProject.getTaskList();
        if (dlg.calcEffortChB.isSelected()) {
            t.setEffort(tl.calculateTotalEffortFromSubTasks(t));
        }

        if (dlg.compactDatesChB.isSelected()) {
            t.setStartDate(tl.getEarliestStartDateFromSubTasks(t));
            t.setEndDate(tl.getLatestEndDateFromSubTasks(t));
        }

        if (dlg.calcCompletionChB.isSelected()) {
            long[] res = tl.calculateCompletionFromSubTasks(t);
            int thisProgress = (int) Math.round((((double) res[0] / (double) res[1]) * 100));
            t.setProgress(thisProgress);
        }

//        CalendarDate sd = new CalendarDate((Date) dlg.startDate.getModel().getValue());
////        CalendarDate ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
//          CalendarDate ed;
//         if(dlg.chkEndDate.isSelected())
//             ed = new CalendarDate((Date) dlg.endDate.getModel().getValue());
//         else
//             ed = new CalendarDate(0,0,0);
//        long effort = Util.getMillisFromHours(dlg.effortField.getText());
//        Task newTask = CurrentProject.getTaskList().createTask(sd, ed, dlg.todoField.getText(), dlg.priorityCB.getSelectedIndex(),effort, dlg.descriptionField.getText(),parentTaskId);
//        

        CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        taskTable.tableChanged();
//        parentPanel.updateIndicators();
        // taskTable.updateUI();
    }

    // present sub tasks for a specific
    void listSubTasks_actionPerformed(ActionEvent e) {
        String parentTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();

        // XXX taskTable.setCurrentRootTask(parentTaskId);
        taskTable.tableChanged();

//        parentPanel.updateIndicators();
//        //taskTable.updateUI();
    }

    void parentTask_actionPerformed(ActionEvent e) {
//        String taskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();
//      
//        Task t = CurrentProject.getTaskList().getTask(taskId);
        /*
         * XXX Task t2 =
         * CurrentProject.getTaskList().getTask(taskTable.getCurrentRootTask());
         * 
         * String parentTaskId = t2.getParent(); if((parentTaskId == null) ||
         * (parentTaskId.equals(""))) { parentTaskId = null; }
         * taskTable.setCurrentRootTask(parentTaskId); taskTable.tableChanged();
         */

//      parentPanel.updateIndicators();
//      //taskTable.updateUI();
    }

    // actions to be performed when removing a task
    void removeTaskB_actionPerformed(ActionEvent e) {
        String msg;
        String thisTaskId = taskTable.getModel().getValueAt(taskTable.getSelectedRow(), TaskTable.TASK_ID).toString();
        if(Context.get("CURRENT_PANEL").equals("ASSIGN")) {
            if (taskTable.getSelectedRows().length > 1)
                msg = Local.getString("Remove")+" "+taskTable.getSelectedRows().length +" "+Local.getString("assignments")+"?"
                + "\n"+Local.getString("Are you sure?");
            else {            
                Task t = CurrentProject.getTaskList().getTask(thisTaskId);
                // check if there are subtasks
                if(CurrentProject.getTaskList().hasSubTasks(thisTaskId)) {
                    msg = Local.getString("Remove assignment")+"\n'" + t.getText() + Local.getString("' and all sub-assignments") +"\n"+Local.getString("Are you sure?");
                }
                else {                    
                    msg = Local.getString("Remove assignment")+"\n'" + t.getText() + "'\n"+Local.getString("Are you sure?");
                }
            }
        } else {
            if (taskTable.getSelectedRows().length > 1)
                msg = Local.getString("Remove")+" "+taskTable.getSelectedRows().length +" "+Local.getString("tasks")+"?"
                + "\n"+Local.getString("Are you sure?");
            else {            
                Task t = CurrentProject.getTaskList().getTask(thisTaskId);
                // check if there are subtasks
                if(CurrentProject.getTaskList().hasSubTasks(thisTaskId)) {
                    msg = Local.getString("Remove task")+"\n'" + t.getText() + Local.getString("' and all subtasks") +"\n"+Local.getString("Are you sure?");
                }
                else {                    
                    msg = Local.getString("Remove task")+"\n'" + t.getText() + "'\n"+Local.getString("Are you sure?");
                }
            }
        }
        String topBar = "Remove task";
        if(Context.get("CURRENT_PANEL").equals("ASSIGN")) {
            topBar = "Remove Assignment";
        }
        int n =
            JOptionPane.showConfirmDialog(
                App.getFrame(),
                msg,
                topBar,
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;
        Vector toremove = new Vector();
        for (int i = 0; i < taskTable.getSelectedRows().length; i++) {
            Task t = CurrentProject.getTaskList().getTask(
                    taskTable.getModel().getValueAt(taskTable.getSelectedRows()[i], TaskTable.TASK_ID).toString());
            if (t != null)
                toremove.add(t);
        }
        for (int i = 0; i < toremove.size(); i++) {
            CurrentProject.getTaskList().removeTask((Task) toremove.get(i));
        }
        taskTable.tableChanged();
        if(Context.get("CURRENT_PANEL").equals("ASSIGN")) {
            CurrentStorage.get().storeAssignList(CurrentProject.getAssignList(), CurrentProject.get());
        } else {
            CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        }
        parentPanel.updateIndicators();
        // taskTable.updateUI();

    }
    //action so be performed when pressing complete task button
    void ppCompleteTask_actionPerformed(ActionEvent e) {
        String msg;
        Vector tocomplete = new Vector();
        for (int i = 0; i < taskTable.getSelectedRows().length; i++) {
            Task t =
            CurrentProject.getTaskList().getTask(
                taskTable.getModel().getValueAt(taskTable.getSelectedRows()[i], TaskTable.TASK_ID).toString());
            if (t != null)
                tocomplete.add(t);
        }
        for (int i = 0; i < tocomplete.size(); i++) {
            Task t = (Task)tocomplete.get(i);
            t.setProgress(100);
        }
        taskTable.tableChanged();
        if(Context.get("CURRENT_PANEL").equals("ASSIGN")) {
            CurrentStorage.get().storeAssignList(CurrentProject.getAssignList(), CurrentProject.get());
        } else {
            CurrentStorage.get().storeTaskList(CurrentProject.getTaskList(), CurrentProject.get());
        }
        parentPanel.updateIndicators();
        //taskTable.updateUI();
    }

    // toggle "show active only"
    void toggleShowActiveOnly_actionPerformed(ActionEvent e) {
        Context.put("SHOW_ACTIVE_TASKS_ONLY", new Boolean(ppShowActiveOnlyChB.isSelected()));
        taskTable.tableChanged();
    }

    void toggleShowReducedOnly_actionPerformed(ActionEvent e) {
        Context.put("SHOW_REDUCED_ONLY", new Boolean(ppShowReducedOnlyChB.isSelected()));
        taskTable.tableChanged();
    }

    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && (taskTable.getSelectedRow() > -1)){
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
        if(Context.get("CURRENT_PANEL").equals("ASSIGN")) {
            newAssignmentButton_actionPerformed(e);
        } else {
            newTaskB_actionPerformed(e);
        }    
    }

    void ppAddSubTask_actionPerformed(ActionEvent e) {
        addSubTask_actionPerformed(e);
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

  /**
   * This serves to change the behavior of tasks panel for when Assignments are being displayed.
   */
  public void refresh() {
    if(Context.get("CURRENT_PANEL").equals("ASSIGN")) {
        subTaskB.setEnabled(false);
        taskTable.setAsAssignmentTable();
        taskTable.repaint();
        newTaskB.setToolTipText(Local.getString("Create new Assignment"));
        removeTaskB.setToolTipText(Local.getString("Remove Assignment"));
        editTaskB.setToolTipText(Local.getString("Edit Assignment"));
        subTaskB.setToolTipText(Local.getString("Add sub-Assignment"));
        completeTaskB.setToolTipText(Local.getString("Complete Assignment"));
    } else {
        subTaskB.setEnabled(true);
        taskTable.setAsTaskTable();
        taskTable.repaint();
        newTaskB.setToolTipText(Local.getString("Create new task"));
        subTaskB.setToolTipText(Local.getString("Add subtask"));
        editTaskB.setToolTipText(Local.getString("Edit task"));
        removeTaskB.setToolTipText(Local.getString("Remove task"));
        completeTaskB.setToolTipText(Local.getString("Complete task"));
    }
  }
}