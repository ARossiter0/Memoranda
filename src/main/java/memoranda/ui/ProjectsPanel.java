package main.java.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.NoteList;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectListener;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.TaskList;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.date.DateListener;
import main.java.memoranda.util.*;

/*$Id: ProjectsPanel.java,v 1.14 2005/01/04 09:59:22 pbielen Exp $*/
public class ProjectsPanel extends JPanel implements ExpandablePanel {

    BorderLayout borderLayout1 = new BorderLayout();
    JToolBar topBar = new JToolBar();
    JPanel toolbarPanel = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel buttonsPanel = new JPanel();
    JButton toggleButton = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    Vector expListeners = new Vector();
    boolean expanded = false;
    ImageIcon expIcon =
            new ImageIcon(
                    main.java.memoranda.ui.AppFrame.class.getResource(
                            "/ui/icons/exp_panel.png"));
    ImageIcon collIcon =
            new ImageIcon(
                    main.java.memoranda.ui.AppFrame.class.getResource(
                            "/ui/icons/coll_panel.png"));
    JLabel curProjectTitle = new JLabel();
    Component component1;
    JPopupMenu projectsPPMenu = new JPopupMenu();
    JMenuItem ppNewProject = new JMenuItem();
    JMenuItem ppProperties = new JMenuItem();
    JMenuItem ppDeleteProject = new JMenuItem();
    JMenuItem ppOpenProject = new JMenuItem();
    JCheckBoxMenuItem ppShowActiveOnlyChB = new JCheckBoxMenuItem();
    JButton ppOpenB = new JButton();
    ProjectsTablePanel prjTablePanel = new ProjectsTablePanel();

    public Action newProjectAction =
            new AbstractAction(
                    Local.getString("New project") + "...",
                    new ImageIcon(
                            main.java.memoranda.ui.AppFrame.class.getResource(
                                    "/ui/icons/newproject.png"))) {

                public void actionPerformed(ActionEvent e) {
                    ppNewProject_actionPerformed(e);
                }
            };


    public ProjectsPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
    }

    void jbInit() throws Exception {
        component1 = Box.createHorizontalStrut(20);
        this.setLayout(borderLayout1);

        // Set the style for the top bar
        topBar.setBackground(new Color(215, 225, 250));
        topBar.setAlignmentX((float) 0.0);
        topBar.setFloatable(false);

        // Set the style for the toolbar panel
        toolbarPanel.setLayout(borderLayout2);

        // Set the style for the toggle button
        toggleButton.setMaximumSize(new Dimension(20, 20));
        toggleButton.setMinimumSize(new Dimension(20, 20));
        toggleButton.setOpaque(false);
        toggleButton.setPreferredSize(new Dimension(20, 20));
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setVerticalAlignment(SwingConstants.TOP);
        toggleButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        toggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleButton_actionPerformed(null);
            }
        });

        toggleButton.setIcon(expIcon);
        toggleButton.setMargin(new Insets(0, 0, 0, 0));

        // Set the style for the buttons panel
        buttonsPanel.setMinimumSize(new Dimension(70, 22));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setPreferredSize(new Dimension(80, 22));
        buttonsPanel.setRequestFocusEnabled(false);
        buttonsPanel.setLayout(flowLayout1);

        // Set the style for the toolbar panel
        toolbarPanel.setBackground(SystemColor.textHighlight);
        toolbarPanel.setMinimumSize(new Dimension(91, 22));
        toolbarPanel.setOpaque(false);
        toolbarPanel.setPreferredSize(new Dimension(91, 22));

        // Set the style for flow layout 1
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);

        // Set the style for the current project title
        curProjectTitle.setFont(new java.awt.Font("Dialog", 1, 11));
        curProjectTitle.setForeground(new Color(64, 70, 128));
        curProjectTitle.setMaximumSize(new Dimension(32767, 22));
        curProjectTitle.setPreferredSize(new Dimension(32767, 22));
        curProjectTitle.setText(CurrentProject.get().getTitle());
        curProjectTitle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                toggleButton_actionPerformed(null);
            }
        });

        /*
         * buttonsPanel.add(newProjectB, null); buttonsPanel.add(editProjectB,
         * null);
         */

        // Set the style for the 'New Project' menu item. Add the newProjectAction
        ppNewProject.setFont(new java.awt.Font("Dialog", 1, 11));
        ppNewProject.setAction(newProjectAction);

        // Set the style for the 'Project properties' menu item
        ppProperties.setFont(new java.awt.Font("Dialog", 1, 11));
        ppProperties.setText(Local.getString("Project properties"));
        ppProperties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppProperties_actionPerformed(e);
            }
        });
        ppProperties.setIcon(
                new ImageIcon(
                        main.java.memoranda.ui.AppFrame.class.getResource(
                                "/ui/icons/editproject.png")));
        ppProperties.setEnabled(false);

        // Set the style for the 'Delete project' menu item
        ppDeleteProject.setFont(new java.awt.Font("Dialog", 1, 11));
        ppDeleteProject.setText(Local.getString("Delete project"));
        ppDeleteProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppDeleteProject_actionPerformed(e);
            }
        });
        ppDeleteProject.setIcon(
                new ImageIcon(
                        main.java.memoranda.ui.AppFrame.class.getResource(
                                "/ui/icons/removeproject.png")));
        ppDeleteProject.setEnabled(false);


        // Set the style for the 'Open project' menu item
        ppOpenProject.setFont(new java.awt.Font("Dialog", 1, 11));
        ppOpenProject.setText(" " + Local.getString("Open project"));
        ppOpenProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppOpenProject_actionPerformed(e);
            }
        });
        ppOpenProject.setEnabled(false);

        // Set the style for the 'Show active projects only' checkbox menu item
        ppShowActiveOnlyChB.setFont(new java.awt.Font("Dialog", 1, 11));
        ppShowActiveOnlyChB.setText(
                Local.getString("Show active projects only"));
        ppShowActiveOnlyChB
                .addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ppShowActiveOnlyChB_actionPerformed(e);
                    }
                });
        boolean isShao =
                (Context.get("SHOW_ACTIVE_PROJECTS_ONLY") != null)
                        && (Context.get("SHOW_ACTIVE_PROJECTS_ONLY").equals("true"));
        ppShowActiveOnlyChB.setSelected(isShao);
        ppShowActiveOnlyChB_actionPerformed(null);

        // Set the style for the projects popup menu
        projectsPPMenu.setFont(new java.awt.Font("Dialog", 1, 10));

        // Set the style for the button that opens the popup menu in the buttons panel
        ppOpenB.setMaximumSize(new Dimension(34, 20));
        ppOpenB.setMinimumSize(new Dimension(24, 10));
        ppOpenB.setOpaque(false);
        ppOpenB.setPreferredSize(new Dimension(24, 20));
        ppOpenB.setBorderPainted(false);
        ppOpenB.setFocusPainted(false);
        ppOpenB.setMargin(new Insets(0, 0, 0, 0));
        ppOpenB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ppOpenB_actionPerformed(e);
            }
        });
        ppOpenB.setIcon(
                new ImageIcon(
                        main.java.memoranda.ui.AppFrame.class.getResource(
                                "/ui/icons/ppopen.png")));

        // Add the 'open popup menu' button and component1 to the buttons panel
        buttonsPanel.add(ppOpenB, null);
        buttonsPanel.add(component1, null);

        // Add the top bar, project table panel to this projects panel
        this.add(topBar, BorderLayout.NORTH);
        this.add(prjTablePanel, BorderLayout.CENTER);

        // Add the toolbar panel to the top bar
        topBar.add(toolbarPanel, null);

        // Add the buttons panel to the toolbar panel
        toolbarPanel.add(buttonsPanel, BorderLayout.EAST);

        // Add the toggle button to the buttons panel
        buttonsPanel.add(toggleButton, null);

        // Add the current project title to the toolbar panel
        toolbarPanel.add(curProjectTitle, BorderLayout.CENTER);

        // Add menu items to projects popup menu.
        projectsPPMenu.add(ppOpenProject);
        projectsPPMenu.addSeparator();
        projectsPPMenu.add(ppNewProject);
        projectsPPMenu.add(ppDeleteProject);
        projectsPPMenu.addSeparator();
        projectsPPMenu.add(ppProperties);
        projectsPPMenu.addSeparator();
        projectsPPMenu.add(ppShowActiveOnlyChB);

        // Add a project listener to the current project
        CurrentProject.addProjectListener(new ProjectListener() {
            public void projectChange(
                    Project p,
                    NoteList nl,
                    TaskList tl,
                    ResourcesList rl) {
            }

            public void projectWasChanged() {
                curProjectTitle.setText(CurrentProject.get().getTitle());
                prjTablePanel.updateUI();
            }
        });

        // Add a date listener to the current date
        CurrentDate.addDateListener(new DateListener() {
            public void dateChange(CalendarDate d) {
                prjTablePanel.updateUI();
            }
        });

        // Add a mouse listener to the projects table panel
        prjTablePanel.projectsTable.addMouseListener(new PopupListener());
        prjTablePanel
                .projectsTable
                .getSelectionModel()
                .addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        boolean enabled =
                                !prjTablePanel
                                        .projectsTable
                                        .getModel()
                                        .getValueAt(
                                                prjTablePanel.projectsTable.getSelectedRow(),
                                                ProjectsTablePanel.PROJECT_ID)
                                        .toString()
                                        .equals(CurrentProject.get().getID());
                        ppDeleteProject.setEnabled(enabled);
                        ppOpenProject.setEnabled(enabled);
                        ppProperties.setEnabled(true);
                    }
                });

        // Set tool-tip text on projects table panel
        prjTablePanel.projectsTable.setToolTipText(
                Local.getString("Double-click to set a current project"));

        // delete projects using the DEL key
        prjTablePanel.projectsTable.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (prjTablePanel.projectsTable.getSelectedRows().length > 0
                        && e.getKeyCode() == KeyEvent.VK_DELETE)
                    ppDeleteProject_actionPerformed(null);
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
    }

    /**
     * Class: PopupListener
     *
     * Description: Listens for mouse events that maybe
     * open the projects popup menu, or maybe
     * opens a different project.
     */
    class PopupListener extends MouseAdapter {

        /**
         * If the left-mouse button is clicked, open the
         * clicked project.
         * @param e a mouse click event
         */
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2)
                ppOpenProject_actionPerformed(null);
        }

        /**
         * If the mouse is pressed, maybe show a popup
         * @param e a mouse press event
         */
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        /**
         * If the mouse is released, maybe show a popup
         * @param e a mouse released event
         */
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        /**
         * If the mouse event is a popup trigger,
         * show the popup menu where the mouse currently is,
         * emanating from the component that the mouse event
         * originated from.
         * @param e a mouse event
         */
        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                projectsPPMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    /**
     * Expand/Collapse the project panel.
     * @param e the action event that triggers the expansion/collapse
     */
    void toggleButton_actionPerformed(ActionEvent e) {
        for (int i = 0; i < expListeners.size(); i++)
            ((ActionListener) expListeners.get(i)).actionPerformed(
                    new ActionEvent(this, 0, "Panel expanded (collapsed)"));
        if (expanded) {
            expanded = false;
            toggleButton.setIcon(expIcon);
        } else {
            expanded = true;
            toggleButton.setIcon(collIcon);
        }
    }

    /**
     * Add an action listener to expListeners
     * @param al the action listener to be added
     */
    public void AddExpandListener(ActionListener al) {
        expListeners.add(al);
    }

    /**
     * Open the project popup panel.
     * @param e the action event that opens the project panel.
     */
    void ppOpenB_actionPerformed(ActionEvent e) {
        projectsPPMenu.show(
                buttonsPanel,
                (int) (ppOpenB.getLocation().getX() + 24)
                        - projectsPPMenu.getWidth(),
                (int) ppOpenB.getLocation().getY() + 24);
    }

    /**
     * Changes the open project by setting the
     * current project to the selected project in the
     * project table panel, updating the project
     * table panel ui, and disabling the menu items
     * for deleting/opening the project for the
     * currently selected project.
     *
     * @param e the action event that triggers opening a project
     */
    void ppOpenProject_actionPerformed(ActionEvent e) {
        CurrentProject.set(prjTablePanel.getSelectedProject());
        prjTablePanel.updateUI();
        ppDeleteProject.setEnabled(false);
        ppOpenProject.setEnabled(false);
    }

    /**
     * Open up the dialog for creating a new project
     * @param e the action event that creates the dialog for a new project
     */
    void ppNewProject_actionPerformed(ActionEvent e) {
        ProjectDialog.newProject();
        prjTablePanel.updateUI();
    }

    /**
     * Open up the dialog for deleting a project
     * @param e the action event for deleting a project
     */
    void ppDeleteProject_actionPerformed(ActionEvent e) {
        String msg;
        Project prj;
        Vector toremove = new Vector();

        // Select the project to remove and set the message

        if (prjTablePanel.projectsTable.getSelectedRows().length > 1)
            // For deleting multiple projects at a time
            msg =
                    Local.getString("Delete")
                            + " "
                            + prjTablePanel.projectsTable.getSelectedRows().length
                            + " "
                            + Local.getString("projects") /**/
                            + "\n"
                            + Local.getString("Are you sure?");
        else {
            // For deleting one project
            prj = prjTablePanel.getSelectedProject();
            msg =
                    Local.getString("Delete project")
                            + " '"
                            + prj.getTitle()
                            + "'.\n"
                            + Local.getString("Are you sure?");
        }

        // Show the delete project confirm dialog
        int n =
                JOptionPane.showConfirmDialog(
                        App.getFrame(),
                        msg,
                        Local.getString("Delete project"),
                        JOptionPane.YES_NO_OPTION);

        // If not delete, do nothing and return.
        if (n != JOptionPane.YES_OPTION)
            return;

        // If delete, update projects table, remove project
        for (int i = 0;
             i < prjTablePanel.projectsTable.getSelectedRows().length;
             i++) {
            prj =
                    (main.java.memoranda.Project) prjTablePanel
                            .projectsTable
                            .getModel()
                            .getValueAt(
                                    prjTablePanel.projectsTable.getSelectedRows()[i],
                                    ProjectsTablePanel.PROJECT);
            toremove.add(prj.getID());
        }
        for (int i = 0; i < toremove.size(); i++) {
            ProjectManager.removeProject((String) toremove.get(i));
        }
        CurrentStorage.get().storeProjectManager();
        prjTablePanel.projectsTable.clearSelection();
        prjTablePanel.updateUI();
        setMenuEnabled(false);
    }

    /**
     * Open the project properties dialog, and maybe
     * edit the project properties
     * @param e the action event for opening the project properties dialog
     */
    void ppProperties_actionPerformed(ActionEvent e) {
        // Get the selected project
        Project prj = prjTablePanel.getSelectedProject();

        // Open the project properties dialog
        ProjectDialog dlg =
                new ProjectDialog(null, Local.getString("Project properties"));
        Dimension dlgSize = dlg.getSize();
        Dimension frmSize = App.getFrame().getSize();
        Point loc = App.getFrame().getLocation();
        dlg.setLocation(
                (frmSize.width - dlgSize.width) / 2 + loc.x,
                (frmSize.height - dlgSize.height) / 2 + loc.y);

        // Show the project title
        dlg.prTitleField.setText(prj.getTitle());

        // Show the project start date
        dlg.startDate.getModel().setValue(
                prj.getStartDate().getCalendar().getTime());

        // If the project has an end date, show it
        if (prj.getEndDate() != null) {
            dlg.edButton.setEnabled(true);
            dlg.endDateChB.setForeground(Color.BLACK);

            dlg.endDateChB.setSelected(true);
            dlg.endDate.setEnabled(true);
            dlg.endDate.getModel().setValue(
                    prj.getEndDate().getCalendar().getTime());
        }
		/*if (prj.getStatus() == Project.FROZEN)
			dlg.freezeChB.setSelected(true);*/
        dlg.setVisible(true);

        // If select cancel, do nothing and return
        if (dlg.CANCELLED)
            return;

        // Else, set title, start date, and end date for
        // project based on fields in project properties dialog.
        // Also update the project table panel's UI.
        prj.setTitle(dlg.prTitleField.getText());
        prj.setStartDate(
                new CalendarDate((Date) dlg.startDate.getModel().getValue()));

        if (dlg.endDateChB.isSelected())
            prj.setEndDate(
                    new CalendarDate((Date) dlg.endDate.getModel().getValue()));
        else
            prj.setEndDate(null);
        prjTablePanel.updateUI();
        /*
         * if (dlg.freezeChB.isSelected()) prj.freeze(); else
         */
    }

    /**
     * Check whether to only show active projects in the
     * project table panel
     * @param e the action event for toggling between all projects and only active projects
     */
    void ppShowActiveOnlyChB_actionPerformed(ActionEvent e) {
        prjTablePanel.setShowActiveOnly(ppShowActiveOnlyChB.isSelected());
        Context.put(
                "SHOW_ACTIVE_PROJECTS_ONLY",
                new Boolean(ppShowActiveOnlyChB.isSelected()));
    }

    /**
     * Enable/disable elements of the popup menu.
     * @param enabled are the elements being enabled?
     */
    void setMenuEnabled(boolean enabled) {
        ppDeleteProject.setEnabled(enabled);
        ppOpenProject.setEnabled(enabled);
        ppProperties.setEnabled(enabled);
    }

}