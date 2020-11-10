package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
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
import javax.swing.JRadioButton;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.util.Local;

import javax.swing.JCheckBox;

/*$Id: TaskDialog.java,v 1.25 2005/12/01 08:12:26 alexeya Exp $*/
public class CourseSpecialDaysDialog extends JDialog {
    
    JPanel mPanel = new JPanel(new BorderLayout());
    JPanel areaPanel = new JPanel(new BorderLayout());
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    JButton cancelB = new JButton();
    JButton okB = new JButton();

    Border border1;
    Border border2;
    Border border4;
    Border border8;

    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel jPanel1 = new JPanel(new BorderLayout(3,1));
    JPanel jPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Time panel
    JPanel jPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Day panel 
    JPanel jPanel4 = new JPanel(new BorderLayout(2,1)); //Repetition super panel
    JPanel jPanel5 = new JPanel(new FlowLayout(FlowLayout.LEFT)); //subpanel to put all repeat options in

    JLabel header = new JLabel();
    JLabel dateLabel = new JLabel();
    JLabel nameLabel = new JLabel();
    JLabel repeatLabel = new JLabel();

    //name
    JTextField nameField = new JTextField();
    //calender
    JSpinner dateOfEvent = new JSpinner(new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_WEEK));
    
    public boolean CANCELLED = true;

    public CourseSpecialDaysDialog(Frame frame, String title) {
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
        header.setText(Local.getString("Event Name and Date"));
        header.setIcon(new ImageIcon(main.java.memoranda.ui.TaskDialog.class.getResource("/ui/icons/task48.png")));

        dateLabel.setMaximumSize(new Dimension(100, 16));
        dateLabel.setMinimumSize(new Dimension(60, 16));
        dateLabel.setText(Local.getString("Date"));

        nameLabel.setMaximumSize(new Dimension(100, 16));
        nameLabel.setMinimumSize(new Dimension(60, 16));
        nameLabel.setText(Local.getString("Event name"));

        //Settings for text field
        nameField.setBorder(border8);
        nameField.setPreferredSize(new Dimension(375, 24));

        //settings for date field
        dateOfEvent.setPreferredSize(new Dimension(80, 24));
		SimpleDateFormat sdf = new SimpleDateFormat();
        sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
        dateOfEvent.setEditor(new JSpinner.DateEditor(dateOfEvent, sdf.toPattern()));

        getContentPane().add(mPanel);

        mPanel.add(areaPanel, BorderLayout.CENTER);
        //mPanel.add(buttonsPanel, BorderLayout.SOUTH);

        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);

        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        dialogTitlePanel.add(header, null);

        //Repetion panel formation
        JCheckBoxMenuItem repeatCheckBox = new JCheckBoxMenuItem("Repeating Event");
        repeatLabel.setText(Local.getString("Majic text ..."));

        areaPanel.add(jPanel1, BorderLayout.CENTER);

        jPanel1.add(jPanel2, null);
        jPanel1.add(jPanel3, null);
        jPanel1.add(jPanel4, null); //New sub panel for repetition

        jPanel2.add(nameLabel, null);
        jPanel2.add(nameField, null);

        jPanel3.add(dateLabel, null);
        jPanel3.add(dateOfEvent, null);

        jPanel4.add(repeatCheckBox, null);
      
        jPanel4.add(jPanel5, null);

        jPanel5.setVisible(false);

        repeatCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent av) {
                if(repeatCheckBox.getState()) {
                    jPanel5.setVisible(true);
                } else {
                    jPanel5.setVisible(false);
                }
            }
        });

    }	

    void okB_actionPerformed(ActionEvent e) {
	    CANCELLED = false;
        this.dispose();
    }

    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    public void windowClosing( WindowEvent e ) {
        CANCELLED = true;
        this.dispose();
    }
    
}