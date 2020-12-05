package memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import memoranda.util.Local;

/*$Id: ResourceTypeDialog.java,v 1.11 2004/07/01 14:44:10 pbielen Exp $*/
public class ResourceTypeDialog extends JDialog {
    JButton cancelB = new JButton();
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JLabel header = new JLabel();
    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton okB = new JButton();
    ResourceTypePanel areaPanel = new ResourceTypePanel();
    // JPanel mPanel = new JPanel(new BorderLayout());

    Border border2;
    TitledBorder titledBorder2;
    public String ext = "";
    boolean CANCELLED = true;

    /**
     * Construct and initialize the resource type dialog 
     * @param frame the containing frame
     * @param title the title of this dialog
     */
    public ResourceTypeDialog(JFrame frame, String title) {
        super(frame, title, true);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    /**
     * Initialize the resource type dialog
     * @throws Exception
     */
    void jbInit() throws Exception {
        this.setResizable(false);
        // Set the style and add the title and header
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString("Resource type"));
        header.setIcon(
                new ImageIcon(memoranda.ui.ResourceTypeDialog.class.getResource("/ui/icons/resource48.png")));
        dialogTitlePanel.add(header);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);

        // mPanel.add(areaPanel, BorderLayout.CENTER);

        // Add the area (resources type) panel
        this.getContentPane().add(areaPanel, BorderLayout.CENTER);

        // Style the cancel button
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));

        // Style the ok button
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

        // Add the ok and cancel buttons to the button panel
        buttonsPanel.add(okB, null);
        buttonsPanel.add(cancelB, null);

        // Add the buttons panel to this
        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Cancel setting a resource type, and dispose of this dialog
     * 
     * @param e action event from the cancel button
     */
    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }

    /**
     * Confirm setting a resource type, set CANCELLED to false, and dispose of this
     * dialog
     * 
     * @param e action event from the ok button
     */
    void okB_actionPerformed(ActionEvent e) {
        CANCELLED = false;
        this.dispose();
    }

    /**
     * Get the list of all resource types
     * 
     * @return the list of all types
     */
    public JList getTypesList() {
        return areaPanel.typesList;
    }

}