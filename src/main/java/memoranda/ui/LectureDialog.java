package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
//import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Local;

import javax.swing.JCheckBox;

/*$Id: TaskDialog.java,v 1.25 2005/12/01 08:12:26 alexeya Exp $*/
public class LectureDialog extends JDialog {
    
    JPanel mPanel = new JPanel(new BorderLayout());
    JPanel areaPanel = new JPanel(new BorderLayout());
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    JLabel topicLabel = new JLabel();
    JTextField lecTopicField = new JTextField();

    JButton cancelB = new JButton();
    JButton okB = new JButton();

    Border border1;
    Border border2;
    Border border3;
    Border border4;
    Border border8;
    
    GridBagConstraints gbc;

    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel jPanel1 = new JPanel(new GridBagLayout());
    JPanel topicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Time panel
    JPanel startTimePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); //Day panel 
    JPanel endTimePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    String[] Days = {Local.getString("Monday"), Local.getString("Tuesday"),
        Local.getString("Wednesday"), Local.getString("Thursday"),
        Local.getString("Friday"), Local.getString("Saturday"), Local.getString("Sunday")};

    JLabel header = new JLabel();
    JLabel startTimeLabel = new JLabel();
    JLabel endTimeLabel = new JLabel();
    JLabel dateLabel = new JLabel();

    JSpinner dateSpin = new JSpinner(new SpinnerDateModel());
    JSpinner startTimeSpin = new JSpinner (new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
    JSpinner endTimeSpin = new JSpinner (new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));

    public boolean CANCELLED = true;
    
    String topic;
    CalendarDate date;
    CalendarDate startTime;
    CalendarDate endTime;

    public LectureDialog(Frame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();            
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }
    
    void jbInit() throws Exception {
        this.setResizable(false);
    
        //borders
        border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(142, 142, 142));
        border4 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
        border8 = BorderFactory.createEtchedBorder(Color.white, new Color(178, 178, 178));

        //cancel button
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));

        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        
        //ok button
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));

        okB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okB_actionPerformed(e);
            }
        });
        
        this.getRootPane().setDefaultButton(okB);

        mPanel.setBorder(border1);
        areaPanel.setBorder(border2);

        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(border4);

        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Lecture Date and Time"));
        header.setIcon(new ImageIcon(main.java.memoranda.ui.TaskDialog.class.getResource("/ui/icons/task48.png")));
        
        topicLabel.setMaximumSize(new Dimension(100, 16));
        topicLabel.setMinimumSize(new Dimension(60, 16));
        topicLabel.setText(Local.getString("Lecture Topic"));
    				
        startTimeLabel.setMaximumSize(new Dimension(100, 16));
        startTimeLabel.setMinimumSize(new Dimension(60, 16));
        startTimeLabel.setText(Local.getString("Day of the Week"));

        dateLabel.setMaximumSize(new Dimension(100, 16));
        dateLabel.setMinimumSize(new Dimension(60, 16));
        dateLabel.setText(Local.getString("Lecture Day"));
        
        startTimeLabel.setMaximumSize(new Dimension(100, 16));
        startTimeLabel.setMinimumSize(new Dimension(60, 16));
        startTimeLabel.setText(Local.getString("Start Time"));
        
        endTimeLabel.setMaximumSize(new Dimension(100, 16));
        endTimeLabel.setMinimumSize(new Dimension(60, 16));
        endTimeLabel.setText(Local.getString("End Time"));
        


        dateSpin.setPreferredSize(new Dimension(80, 20));
        dateSpin.setLocale(Local.getCurrentLocale());
        //For time spinner
        SimpleDateFormat sdf1 = new SimpleDateFormat();
		sdf1 = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
		dateSpin.setEditor(new JSpinner.DateEditor(dateSpin, sdf1.toPattern()));
        
        
        startTimeSpin.setPreferredSize(new Dimension(80, 20));
        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");
		startTimeSpin.setEditor(new JSpinner.DateEditor(startTimeSpin, sdf2.toPattern()));
        
        endTimeSpin.setPreferredSize(new Dimension(80, 20));
        endTimeSpin.setEditor(new JSpinner.DateEditor(endTimeSpin, sdf2.toPattern()));

        getContentPane().add(mPanel);

        mPanel.add(areaPanel, BorderLayout.CENTER);
        mPanel.add(buttonsPanel, BorderLayout.SOUTH);

        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);

        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        dialogTitlePanel.add(header, null);

        areaPanel.add(jPanel1, BorderLayout.CENTER);

        gbc = new GridBagConstraints();
        gbc.gridwidth = 5;
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        jPanel1.add(topicLabel, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridwidth = 15;
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(lecTopicField, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(dateLabel, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridwidth = 5;
        gbc.gridx = 3; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(dateSpin, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridwidth = 5;
        gbc.gridx = 9; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(startTimeLabel, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridwidth = 5;
        gbc.gridx = 15; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(startTimeSpin, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridwidth = 5;
        gbc.gridx = 9; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(endTimeLabel, gbc);
        
        gbc = new GridBagConstraints();
        gbc.gridwidth = 5;
        gbc.gridx = 15; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 5, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(endTimeSpin, gbc);
        
//        jPanel1.add(timePanel, null);
//        jPanel1.add(dayPanel, null);
        
//        topicPanel.add(topicLabel, null);
//        topicPanel.add(lecTopicField, null);

//        datePanel.add(dateLabel, null);
//        datePanel.add(dateSpin, null);
//
//        startTimePanel.add(startTimeLabel, null);
        
    }	
    void okB_actionPerformed(ActionEvent e) {
	CANCELLED = false;
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
	   
}