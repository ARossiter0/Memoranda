/**
 * CurrentProject.java
 * Created on 13.02.2003, 13:16:52 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *
 */
package main.java.memoranda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import main.java.memoranda.ui.AppFrame;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Storage;

/**
 *
 */
/*$Id: CurrentProject.java,v 1.6 2005/12/01 08:12:26 alexeya Exp $*/
public class CurrentProject {

    private static Project _project = null;
    private static TaskList _tasklist = null;
    private static TaskList _studenttodo = null;
    private static NoteList _notelist = null;
    private static ResourcesList _resources = null;
    private static Vector projectListeners = new Vector();

        
    public enum TaskType {TASK, STUDENT_TODO}
    public static TaskType task = TaskType.TASK;
    
    static {
        // Check if there is some project that has been opened last.
        // If not, create a default.
        String prjId = (String)Context.get("LAST_OPENED_PROJECT_ID");
        if (prjId == null) {
            prjId = "__default";
            Context.put("LAST_OPENED_PROJECT_ID", prjId);
        }
        
        
        // Get the project. If there is no project associated with the
        // project id, get the first project that exists in storage (prjId=0)
        
        //ProjectManager.init();
        _project = ProjectManager.getProject(prjId);
        
        if (_project == null) {
            // alexeya: Fixed bug with NullPointer when LAST_OPENED_PROJECT_ID
            // references to missing project
            _project = ProjectManager.getProject("__default");
            if (_project == null) 
                _project = (Project)ProjectManager.getActiveProjects().get(0);                      
            Context.put("LAST_OPENED_PROJECT_ID", _project.getID());
            
        }       
        
        // Get the tasks, notes, and resources from the project
        _tasklist = CurrentStorage.get().openTaskList(_project);
        _studenttodo = CurrentStorage.get().openStudentTodo(_project);
        _notelist = CurrentStorage.get().openNoteList(_project);
        _resources = CurrentStorage.get().openResourcesList(_project);
        
        // When exiting the application, save the current project
        AppFrame.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();                                               
            }
        });
    }
        

    public static Project get() {
        return _project;
    }

    /**
     * Get the tasks associated with this project
     * @return the list of tasks associated with this project
     */
    public static TaskList getTaskList() {
        if (task == TaskType.STUDENT_TODO) {
            return _studenttodo;
        } else {
            return _tasklist;
        }
    }

    
    /**
     * Get the notes associated with this project
     * @return the list of notes associated with this project
     */
    public static NoteList getNoteList() {
            return _notelist;
    }
    
    /**
     * Get the list of resources associated with this project
     * @return the list of resources associated with this project
     */
    public static ResourcesList getResourcesList() {
            return _resources;
    }

    /**
     * Set the current project by retrieving the appropriate 
     * tasks, notes, and resources, notifying listeners, and
     * setting last opened project id to be the id of the project. 
     * TODO: Edit this to be able to insert the task list into the 
     * storage process
     * @param project the project to be made current
     */
    public static void set(Project project) {
        if (project.getID().equals(_project.getID())) return;
        TaskList newtasklist = CurrentStorage.get().openTaskList(project);
        TaskList newstudentodo = CurrentStorage.get().openStudentTodo(project);
        NoteList newnotelist = CurrentStorage.get().openNoteList(project);
        ResourcesList newresources = CurrentStorage.get().openResourcesList(project);
        notifyListenersBefore(project, newnotelist, newtasklist,newstudentodo , newresources);
        _project = project;
        _tasklist = newtasklist;
        _studenttodo = newstudentodo;
        _notelist = newnotelist;
        _resources = newresources;
        notifyListenersAfter();
        Context.put("LAST_OPENED_PROJECT_ID", project.getID());
    }

    /**
     * Add a listener to current project
     * @param pl the listener
     */
    public static void addProjectListener(ProjectListener pl) {
        projectListeners.add(pl);
    }

    /**
     * Get the set of listeners to the current project
     * @return the set of listeners to the current project
     */
    public static Collection getChangeListeners() {
        return projectListeners;
    }

    /**
     * Notify listeners before the current project is changed
     * @param project the new project
     * @param nl the new note list
     * @param tl the new task list
     * @param rl the new resource list
     */
    private static void notifyListenersBefore(Project project, NoteList nl, TaskList tl, TaskList s1, ResourcesList rl) {
        for (int i = 0; i < projectListeners.size(); i++) {
            ((ProjectListener)projectListeners.get(i)).projectChange(project, nl, tl, s1, rl);
            /*DEBUGSystem.out.println(projectListeners.get(i));*/
        }
    }
    
    /**
     * Notify listeners after the current project is changed
     */
    private static void notifyListenersAfter() {
        for (int i = 0; i < projectListeners.size(); i++) {
            ((ProjectListener)projectListeners.get(i)).projectWasChanged();            
        }
    }

    /**
     * Save all of the info associated with the current project
     */
    public static void save() {
        Storage storage = CurrentStorage.get();

        storage.storeNoteList(_notelist, _project);
        storage.storeTaskList(_tasklist, _project); 
        storage.storeStudentTodo(_studenttodo, _project); 
        storage.storeResourcesList(_resources, _project);
        storage.storeProjectManager();
    }
    
    /**
     * Reset the current project to null
     */
    public static void free() {
        _project = null;
        _tasklist = null;
        _studenttodo = null;
        _notelist = null;
        _resources = null;
    }
}