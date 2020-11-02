package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

    JButton cancelB = new JButton();
    JButton okB = new JButton();

    Border border1;
    Border border2;
    Border border3;
    Border border4;
    Border border8;

    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel jPanel1 = new JPanel(new GridLayout(1,2));
    JPanel jPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Time panel
    JPanel jPanel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT)); //Day panel 

    String[] Days = {Local.getString("Monday"), Local.getString("Tuesday"),
        Local.getString("Wednesday"), Local.getString("Thursday"),
        Local.getString("Friday"), Local.getString("Saturday"), Local.getString("Sunday")};

    JLabel header = new JLabel();
    JLabel dayLabel = new JLabel();
    JLabel timeLabel = new JLabel();

    JComboBox daysCB = new JComboBox(Days);
    JSpinner timeSpin = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));

    public boolean CANCELLED = true;

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
        this.setSize(new Dimension(430,300));
    
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
        header.setText(Local.getString("Lecture Time and Day"));
        header.setIcon(new ImageIcon(main.java.memoranda.ui.TaskDialog.class.getResource("/ui/icons/task48.png")));
    				
        dayLabel.setMaximumSize(new Dimension(100, 16));
        dayLabel.setMinimumSize(new Dimension(60, 16));
        dayLabel.setText(Local.getString("Day of the week"));

        timeLabel.setMaximumSize(new Dimension(100, 16));
        timeLabel.setMinimumSize(new Dimension(60, 16));
        timeLabel.setText(Local.getString("Lecture Time"));

        //For days combo box
        daysCB.setFont(new java.awt.Font("Dialog", 0, 11));
        daysCB.setSelectedItem(Local.getString("Monday"));

        //For time spinner
        timeSpin.setPreferredSize(new Dimension(60, 24));
        ((JSpinner.DateEditor) timeSpin.getEditor()).getFormat().applyPattern("HH:mm");

        getContentPane().add(mPanel);

        mPanel.add(areaPanel, BorderLayout.CENTER);
        mPanel.add(buttonsPanel, BorderLayout.SOUTH);

        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);

        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        dialogTitlePanel.add(header, null);

        areaPanel.add(jPanel1, BorderLayout.CENTER);

        jPanel1.add(jPanel2, null);
        jPanel1.add(jPanel3, null);

        jPanel2.add(timeLabel, null);
        jPanel2.add(timeSpin, null);

        jPanel3.add(dayLabel, null);
        jPanel3.add(daysCB, null);
        
    }	
    void okB_actionPerformed(ActionEvent e) {
	CANCELLED = false;
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
	   
}