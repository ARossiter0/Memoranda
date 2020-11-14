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
import main.java.memoranda.EventsManager;
import main.java.memoranda.History;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectListener;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.Lecture;
import main.java.memoranda.LectureList;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.Util;
import main.java.memoranda.LectureTime;
import main.java.memoranda.SpecialCalendarDate;
import main.java.memoranda.TaskList;

/*$Id: LecturePanel.java,v 1.27 2007/01/17 20:49:12 killerjoe Exp $*/
public class LecturePanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    JButton historyBackB = new JButton();
    JToolBar LecturesToolBar = new JToolBar();
    JButton historyForwardB = new JButton();
    JButton newLectureB = new JButton();
    JButton editLectureB = new JButton();
    JButton removeLectureB = new JButton();
    JScrollPane scrollPane = new JScrollPane();
    LectureTable LectureTable = new LectureTable();
    JMenuItem ppEditLecture = new JMenuItem();
    JPopupMenu LecturePPMenu = new JPopupMenu();
    JMenuItem ppRemoveLecture = new JMenuItem();
    JMenuItem ppNewLecture = new JMenuItem();
    JMenuItem ppCompleteLecture = new JMenuItem();
    JMenuItem ppAddSubLecture = new JMenuItem();
    JMenuItem ppCalcLecture = new JMenuItem();
    DailyItemsPanel parentPanel = null;

    // main Lecture panel frame
    public LecturePanel(DailyItemsPanel _parentPanel) {
        try {
            parentPanel = _parentPanel;
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        LecturesToolBar.setFloatable(false);
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
        // add new Lecture and a call to the method newLectureB_actionPerformed(e)
        newLectureB
                .setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png")));
        newLectureB.setEnabled(true);
        newLectureB.setMaximumSize(new Dimension(24, 24));
        newLectureB.setMinimumSize(new Dimension(24, 24));
        newLectureB.setToolTipText(Local.getString("Create new lecture"));
        newLectureB.setRequestFocusEnabled(false);
        newLectureB.setPreferredSize(new Dimension(24, 24));
        newLectureB.setFocusable(false);
        newLectureB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newLectureB_actionPerformed(e);
            }
        });
        newLectureB.setBorderPainted(false);

        // edit Lecture and call to the method editLectureB_actionPerformed(e)
        editLectureB.setBorderPainted(false);
        editLectureB.setFocusable(false);
        editLectureB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editLectureB_actionPerformed(e);
            }
        });
        editLectureB.setPreferredSize(new Dimension(24, 24));
        editLectureB.setRequestFocusEnabled(false);
        editLectureB.setToolTipText(Local.getString("Edit lecture"));
        editLectureB.setMinimumSize(new Dimension(24, 24));
        editLectureB.setMaximumSize(new Dimension(24, 24));
        editLectureB
                .setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));

        // remove Lecture button setup and action
        removeLectureB.setBorderPainted(false);
        removeLectureB.setFocusable(false);
        removeLectureB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeLectureB_actionPerformed(e);
            }
        });
        removeLectureB.setPreferredSize(new Dimension(24, 24));
        removeLectureB.setRequestFocusEnabled(false);
        removeLectureB.setToolTipText(Local.getString("Remove lecture"));
        removeLectureB.setMinimumSize(new Dimension(24, 24));
        removeLectureB.setMaximumSize(new Dimension(24, 24));
        removeLectureB.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));

        this.setLayout(borderLayout1);
        scrollPane.getViewport().setBackground(Color.white);
        /*
         * LectureTable.setMaximumSize(new Dimension(32767, 32767));
         * LectureTable.setRowHeight(24);
         */
        ppEditLecture.setFont(new java.awt.Font("Dialog", 1, 11));
        ppEditLecture.setText(Local.getString("Edit lecture") + "...");
        ppEditLecture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppEditLecture_actionPerformed(e);
            }
        });
        // edit Lecture button
        ppEditLecture.setEnabled(false);
        ppEditLecture
                .setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_edit.png")));
        LecturePPMenu.setFont(new java.awt.Font("Dialog", 1, 10));
        // remove Lecture button and action to be called upon clicking
        ppRemoveLecture.setFont(new java.awt.Font("Dialog", 1, 11));
        ppRemoveLecture.setText(Local.getString("Remove lecture"));
        ppRemoveLecture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppRemoveLecture_actionPerformed(e);
            }
        });
        ppRemoveLecture.setIcon(
                new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_remove.png")));
        ppRemoveLecture.setEnabled(false);
        ppNewLecture.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewLecture.setText(Local.getString("New lecture") + "...");
        ppNewLecture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppNewLecture_actionPerformed(e);
            }
        });
        ppNewLecture
                .setIcon(new ImageIcon(main.java.memoranda.ui.AppFrame.class.getResource("/ui/icons/event_new.png")));

        // add buttons to Lecture tool bar
        scrollPane.getViewport().add(LectureTable, null);
        this.add(scrollPane, BorderLayout.CENTER);
        LecturesToolBar.add(historyBackB, null);
        LecturesToolBar.add(historyForwardB, null);
        LecturesToolBar.addSeparator(new Dimension(8, 24));

        LecturesToolBar.add(newLectureB, null);
        LecturesToolBar.add(removeLectureB, null);
        LecturesToolBar.addSeparator(new Dimension(8, 24));
        LecturesToolBar.add(editLectureB, null);

        // LecturesToolBar.add(showActiveOnly, null);

        this.add(LecturesToolBar, BorderLayout.NORTH);

        PopupListener ppListener = new PopupListener();
        scrollPane.addMouseListener(ppListener);
        LectureTable.addMouseListener(ppListener);

        // show current date
        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                // newLectureB.setEnabled(d.inPeriod(CurrentProject.get().getStartDate(),
                // CurrentProject.get().getEndDate()));
            }
        });
        // add a new Lecture to the current project
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(Project p, NoteList nl, LectureList tl, TaskList t2, ResourcesList rl) {
                newLectureB.setEnabled(CurrentDate.get().inPeriod(p.getStartDate(), p.getEndDate()));
            }

            public void projectWasChanged() {
                // LectureTable.setCurrentRootLecture(null); //XXX
            }
        });
        // upon selection of a Lecture or a sub Lecture, emable edit and remove Lecture
        // buttons
        LectureTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                boolean enbl = LectureTable.getSelectedRow() > -1;

                editLectureB.setEnabled(enbl);
                ppEditLecture.setEnabled(enbl);
                removeLectureB.setEnabled(enbl);
                ppRemoveLecture.setEnabled(enbl);
            }
        });

        // set enables for options that are not valud when there are no Lectures or no
        // Lectures are selected
        editLectureB.setEnabled(false);
        removeLectureB.setEnabled(false);
        ppAddSubLecture.setEnabled(false);
        // ppSubLectures.setEnabled(false);
        // ppParentLecture.setEnabled(false);
        LecturePPMenu.add(ppEditLecture);

        LecturePPMenu.addSeparator();
        LecturePPMenu.add(ppNewLecture);
        LecturePPMenu.add(ppAddSubLecture);
        LecturePPMenu.add(ppRemoveLecture);

        LectureTable.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (LectureTable.getSelectedRows().length > 0 && e.getKeyCode() == KeyEvent.VK_DELETE)
                    ppRemoveLecture_actionPerformed(null);

                else if (e.getKeyCode() == KeyEvent.VK_INSERT) {
                    ppNewLecture_actionPerformed(null);
                }

            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });

    }

    // defines actions to be performed when the edit Lecture button is clicked
    void editLectureB_actionPerformed(ActionEvent e) {
        Lecture le = (Lecture) LectureTable.getModel().getValueAt(LectureTable.getSelectedRow(), LectureTable.LECTURE);
        LectureDialog dlg = new LectureDialog(App.getFrame(), Local.getString("New lecture"));

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        dlg.lecTopicField.setText(le.getTopic());

        dlg.dateSpin.getModel()
                .setValue(CalendarDate.toDate(le.getDate().getDay(), le.getDate().getMonth(), le.getDate().getYear()));

        Calendar cdate = new GregorianCalendar();
        cdate.set(Calendar.HOUR_OF_DAY, le.getStartHour());
        cdate.set(Calendar.MINUTE, 0);
        dlg.startTimeSpin.getModel().setValue(cdate.getTime());

        cdate.set(Calendar.HOUR_OF_DAY, le.getEndHour());
        dlg.endTimeSpin.getModel().setValue(cdate.getTime());
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);

        if (dlg.CANCELLED)
            return;

        // Get Topic and set
        le.setTopic(dlg.lecTopicField.getText());

        // Get date and set
        le.setDate(new CalendarDate((Date) dlg.dateSpin.getModel().getValue()).toString());

        // Get start time and set
        Calendar calendar = new GregorianCalendar(Local.getCurrentLocale());
        calendar.setTime(((Date) dlg.startTimeSpin.getModel().getValue()));
        le.setStartHour(calendar.get(Calendar.HOUR_OF_DAY));
        le.setStartMin(calendar.get(Calendar.MINUTE));

        // Get end time and set
        calendar.setTime(((Date) dlg.endTimeSpin.getModel().getValue()));
        le.setEndHour(calendar.get(Calendar.HOUR_OF_DAY));
        le.setEndMin(calendar.get(Calendar.MINUTE));

        LectureTable.getSelectionModel().clearSelection();
        CurrentStorage.get().storeLectureList(CurrentProject.getLectureList(), CurrentProject.get());
        LectureTable.refresh();
    }

    // defines actions to be performed when new Lecture is added
    void newLectureB_actionPerformed(ActionEvent e) {
        LectureDialog dlg = new LectureDialog(App.getFrame(), Local.getString("New lecture"));

        // XXX String parentLectureId = LectureTable.getCurrentRootLecture();

        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();

        Calendar cdate = CurrentDate.get().getCalendar();
        cdate.set(Calendar.MINUTE, 0);

        dlg.startTimeSpin.getModel().setValue(cdate.getTime());
        dlg.endTimeSpin.getModel().setValue(cdate.getTime());
        dlg.setLocation((frmSize.width - dlg.getSize().width) / 2 + loc.x,
                (frmSize.height - dlg.getSize().height) / 2 + loc.y);
        dlg.setVisible(true);

        if (dlg.CANCELLED)
            return;

        String topic = dlg.lecTopicField.getText();
        CalendarDate date = new CalendarDate((Date) dlg.dateSpin.getModel().getValue());

        Calendar startTime = new GregorianCalendar(Local.getCurrentLocale());
        startTime.setTime(((Date) dlg.startTimeSpin.getModel().getValue()));

        Calendar endTime = new GregorianCalendar(Local.getCurrentLocale());
        endTime.setTime(((Date) dlg.endTimeSpin.getModel().getValue()));

        CurrentProject.getLectureList().createLecture(date, startTime, endTime, topic);
        CurrentStorage.get().storeLectureList(CurrentProject.getLectureList(), CurrentProject.get());
//        LectureTable.tableChanged();
//        parentPanel.updateIndicators();
        LectureTable.refresh();
    }

    // actions to be performed when removing a Lecture
    void removeLectureB_actionPerformed(ActionEvent e) {
        String msg;
        main.java.memoranda.Lecture le;

        if (LectureTable.getSelectedRows().length > 1)
            msg = Local.getString("Remove") + " " + LectureTable.getSelectedRows().length + " "
                    + Local.getString("lectures") + "\n" + Local.getString("Are you sure?");
        else {
            le = (main.java.memoranda.Lecture) LectureTable.getModel().getValueAt(LectureTable.getSelectedRow(),
                    LectureTable.LECTURE);
            msg = Local.getString("Remove lecture") + "\n'" + le.getTopic() + "'\n" + Local.getString("Are you sure?");
        }

        int n = JOptionPane.showConfirmDialog(App.getFrame(), msg, Local.getString("Remove lecture"),
                JOptionPane.YES_NO_OPTION);
        if (n != JOptionPane.YES_OPTION)
            return;

        for (int i = 0; i < LectureTable.getSelectedRows().length; i++) {
            le = (main.java.memoranda.Lecture) LectureTable.getModel().getValueAt(LectureTable.getSelectedRows()[i],
                    EventsTable.EVENT);
            main.java.memoranda.LectureListImpl.removeLecture(le);
        }
        LectureTable.getSelectionModel().clearSelection();
        CurrentStorage.get().storeLectureList(CurrentProject.getLectureList(), CurrentProject.get());
        LectureTable.refresh();
    }

    class PopupListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if ((e.getClickCount() == 2) && (LectureTable.getSelectedRow() > -1)) {

                editLectureB_actionPerformed(null);
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
                LecturePPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

    }

    void ppEditLecture_actionPerformed(ActionEvent e) {
        editLectureB_actionPerformed(e);
    }

    void ppRemoveLecture_actionPerformed(ActionEvent e) {
        removeLectureB_actionPerformed(e);
    }

    void ppNewLecture_actionPerformed(ActionEvent e) {
        newLectureB_actionPerformed(e);
    }
}