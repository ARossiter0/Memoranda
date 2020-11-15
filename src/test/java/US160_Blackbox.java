package test.java;

import main.java.memoranda.CurrentProject;
//import main.java.*;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectImpl;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.Task;
import main.java.memoranda.TaskImpl;
import main.java.memoranda.TaskList;
import main.java.memoranda.TaskListImpl;
import main.java.memoranda.date.CalendarDate;
import main.java.memoranda.date.CurrentDate;
import main.java.memoranda.ui.TaskTableModel;
import main.java.memoranda.util.Context;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.FileStorage;
import main.java.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;

public class US160_Blackbox {

    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test the getIsInReduced and setIsInReduced
     * in TaskImpl.java by creating new tasks from 
     * projects with different project names and 
     * different task names. 
     */
    @Test
    public void testGetSetIsInReducedTask() {
        // Test with normal project title and normal task title
        String PROJECT_ELEM_TXT = "test_project";
        String TASK_ELEM_TXT = "task";
        
        Element root = new Element(PROJECT_ELEM_TXT);
        Element taskElement = new Element(TASK_ELEM_TXT);
        Project project = new ProjectImpl(root);
        TaskList taskList = new TaskListImpl(new Document(root), project); 
        Task task = new TaskImpl(taskElement, taskList);
        
        task.setIsInReduced(true);                
        assertTrue(task.getIsInReduced());        
        task.setIsInReduced(false);
        assertFalse(task.getIsInReduced());     
                               
        
        // Test with one character project and task title
        PROJECT_ELEM_TXT = "a";
        TASK_ELEM_TXT = "b";
        
        root = new Element(PROJECT_ELEM_TXT);
        taskElement = new Element(TASK_ELEM_TXT);
        project = new ProjectImpl(root);
        taskList = new TaskListImpl(new Document(root), project); 
        task = new TaskImpl(taskElement, taskList);
        
        task.setIsInReduced(true);                
        assertTrue(task.getIsInReduced());        
        task.setIsInReduced(false);
        assertFalse(task.getIsInReduced());
        
        
        // Test with long character project and task titles
        PROJECT_ELEM_TXT = "fdahui13489gfhds09ujrr21q2390u1rr23r32u901ru2u02ru20rgdscu0vxu9bnvuug09bu09bgufbusgbujpsfsdfujaufhdsafh1";
        TASK_ELEM_TXT = "fodisafu90430q5tw023houqhfjdhfkdhafhdkalhjdkasjhfdklahfjdklhsfajdkafer84rf8943qrurfophoghrjkdhsajgdksfhduoaphfudahf";
        
        root = new Element(PROJECT_ELEM_TXT);
        taskElement = new Element(TASK_ELEM_TXT);
        project = new ProjectImpl(root);
        taskList = new TaskListImpl(new Document(root), project); 
        task = new TaskImpl(taskElement, taskList);
        
        task.setIsInReduced(true);                
        assertTrue(task.getIsInReduced());        
        task.setIsInReduced(false);
        assertFalse(task.getIsInReduced());        
    }
    
    /**
     * Test the getReducedTasks and filter reduced tasks methods
     * in TaskListImpl.java by creating different task lists, and 
     * comparing the size returned by getReducedTasks to the 
     * expected number of tasks in the reduced set. 
     */
    @Test
    public void testGetReducedTasks() {   
        // Tasklist normal     
        Project project = ProjectManager.createProject("Project 0", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        TaskList taskList = new TaskListImpl(project); 
                     
        
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        final int EXPECTED0 = 30;
        final int RESULT0 = taskList.getReducedTasks(taskList.getTopLevelTasks()).size();        
        
        assertEquals(EXPECTED0, RESULT0);
        
        // Zero tasklist
        project = ProjectManager.createProject("Project 1", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        taskList = new TaskListImpl(project);                     
                
        final int EXPECTED1 = 0;
        final int RESULT1 = taskList.getReducedTasks(taskList.getTopLevelTasks()).size();        
        
        assertEquals(EXPECTED1, RESULT1);
        
        // Tasklist all in reduced
        project = ProjectManager.createProject("Project 2", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        taskList = new TaskListImpl(project); 
                     
        
        for(int i = 0; i < 50; i++) {
            taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
        }
        
        final int EXPECTED2 = 50;
        final int RESULT2 = taskList.getReducedTasks(taskList.getTopLevelTasks()).size();        
        
        assertEquals(EXPECTED2, RESULT2);
        
        // Tasklist none in reduced
        project = ProjectManager.createProject("Project 2", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        taskList = new TaskListImpl(project); 
                     
        
        for(int i = 0; i < 50; i++) {
            taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
        }
        
        final int EXPECTED3 = 0;
        final int RESULT3 = taskList.getReducedTasks(taskList.getTopLevelTasks()).size();        
        
        assertEquals(EXPECTED3, RESULT3);
        
        // Tasklist one in reduced
        project = ProjectManager.createProject("Project 2", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        taskList = new TaskListImpl(project); 
                     
        
        for(int i = 0; i < 49; i++) {
            taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
        }
        
        taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Reduced Test", 0, 0, "", null, true);
        
        final int EXPECTED4 = 1;
        final int RESULT4 = taskList.getReducedTasks(taskList.getTopLevelTasks()).size();        
        
        assertEquals(EXPECTED4, RESULT4);
        
        
        // Tasklist one not in reduced
        project = ProjectManager.createProject("Project 2", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        taskList = new TaskListImpl(project); 
                     
        
        for(int i = 0; i < 49; i++) {
            taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
        }
        
        taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Non-Reduced Test", 0, 0, "", null, false);
        
        final int EXPECTED5 = 49;
        final int RESULT5 = taskList.getReducedTasks(taskList.getTopLevelTasks()).size();        
        
        assertEquals(EXPECTED5, RESULT5);                       

    }
    
        
    /**
     * Check the storeInstrTodoList and openInstrTodoList
     * methods in FileStorage.java by creating projects with
     * instructor todo lists and regular task lists and 
     * storing them, opening them back up, and checking that
     * they are of the appropriate size. 
     */    
    @Test
    public void testStoreOpenInstrTodoList() {                
        // Normal instrTodoList and TaskList
        Project project = ProjectManager.createProject("Project 0", CalendarDate.yesterday(), CalendarDate.tomorrow());     
        
        TaskList instrTodoList = new TaskListImpl(project);                  
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        TaskList taskList = new TaskListImpl(project);                  
        for(int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }                 
        
        FileStorage storage = new FileStorage();                       
                    
        
        TaskList openInstrTodoList = storage.openInstrTodoList(project);        
        final int EXPECTED0 = 0;
        final int RESULT0 = openInstrTodoList.getTopLevelTasks().size();          
        assertEquals(EXPECTED0, RESULT0);
        
        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);
                         
        TaskList openTaskList = storage.openTaskList(project);
        final int EXPECTED1 = 500;
        final int RESULT1 = openTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED1, RESULT1);
        
        openInstrTodoList = storage.openInstrTodoList(project);  
        final int EXPECTED2 = 50;
        final int RESULT2 = openInstrTodoList.getTopLevelTasks().size(); 
        assertEquals(EXPECTED2, RESULT2);          
        
        storage.removeProjectStorage(project);
        
        
        
        // Zero instrTodoList
        project = ProjectManager.createProject("Project 1", CalendarDate.yesterday(), CalendarDate.tomorrow());     
        
        instrTodoList = new TaskListImpl(project);     
        
        taskList = new TaskListImpl(project);                  
        for(int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }                 
        
        storage = new FileStorage();                       
                    
        
        openInstrTodoList = storage.openInstrTodoList(project);        
        final int EXPECTED4 = 0;
        final int RESULT4 = openInstrTodoList.getTopLevelTasks().size();          
        assertEquals(EXPECTED4, RESULT4);
        
        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);
                         
        openTaskList = storage.openTaskList(project);
        final int EXPECTED5 = 500;
        final int RESULT5 = openTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED5, RESULT5);
        
        openInstrTodoList = storage.openInstrTodoList(project);  
        final int EXPECTED6 = 0;
        final int RESULT6 = openInstrTodoList.getTopLevelTasks().size(); 
        assertEquals(EXPECTED6, RESULT6);          
        
        storage.removeProjectStorage(project);
        
        
        // Zero taskList
        project = ProjectManager.createProject("Project 1", CalendarDate.yesterday(), CalendarDate.tomorrow());     
        
        instrTodoList = new TaskListImpl(project);                  
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        taskList = new TaskListImpl(project);    
        
        storage = new FileStorage();                       
                    
        
        openInstrTodoList = storage.openInstrTodoList(project);        
        final int EXPECTED7 = 0;
        final int RESULT7 = openInstrTodoList.getTopLevelTasks().size();          
        assertEquals(EXPECTED7, RESULT7);
        
        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);
                         
        openTaskList = storage.openTaskList(project);
        final int EXPECTED8 = 0;
        final int RESULT8 = openTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED8, RESULT8);
        
        openInstrTodoList = storage.openInstrTodoList(project);  
        final int EXPECTED9 = 50;
        final int RESULT9 = openInstrTodoList.getTopLevelTasks().size(); 
        assertEquals(EXPECTED9, RESULT9);          
        
        storage.removeProjectStorage(project);
        
        
        // Tasks long ago 
        project = ProjectManager.createProject("Project 1", CalendarDate.yesterday(), CalendarDate.tomorrow());     
        
        final CalendarDate START_DATE = new CalendarDate(1, 1, 1900);
        final CalendarDate END_DATE = new CalendarDate(1, 1, 1910);
        
        
        instrTodoList = new TaskListImpl(project);                  
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(START_DATE, END_DATE, "Test: " + i, 0, 0, "", null, true);
            } else {
                instrTodoList.createTask(START_DATE, END_DATE, "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        taskList = new TaskListImpl(project);                  
        for(int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(START_DATE, END_DATE, "Test: " + i, 0, 0, "", null, true);
            } else {
                taskList.createTask(START_DATE, END_DATE, "Test: " + i, 0, 0, "", null, false);
            }
        }                 
        
        storage = new FileStorage();                       
                    
        
        openInstrTodoList = storage.openInstrTodoList(project);        
        final int EXPECTED10 = 0;
        final int RESULT10 = openInstrTodoList.getTopLevelTasks().size();          
        assertEquals(EXPECTED10, RESULT10);
        
        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);
                         
        openTaskList = storage.openTaskList(project);
        final int EXPECTED11 = 500;
        final int RESULT11 = openTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED11, RESULT11);
        
        openInstrTodoList = storage.openInstrTodoList(project);  
        final int EXPECTED12 = 50;
        final int RESULT12 = openInstrTodoList.getTopLevelTasks().size(); 
        assertEquals(EXPECTED12, RESULT12);          
        
        storage.removeProjectStorage(project);
        
        
        // Tasks in the future
        project = ProjectManager.createProject("Project 1", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        
        final CalendarDate START_DATE1 = new CalendarDate(1, 1, 3000);
        final CalendarDate END_DATE1 = new CalendarDate(1, 1, 4000);
        
        instrTodoList = new TaskListImpl(project);                  
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(START_DATE1, END_DATE1, "Test: " + i, 0, 0, "", null, true);
            } else {
                instrTodoList.createTask(START_DATE1, END_DATE1, "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        taskList = new TaskListImpl(project);                  
        for(int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(START_DATE1, END_DATE1, "Test: " + i, 0, 0, "", null, true);
            } else {
                taskList.createTask(START_DATE1, END_DATE1, "Test: " + i, 0, 0, "", null, false);
            }
        }                 
        
        storage = new FileStorage();                       
                    
        
        openInstrTodoList = storage.openInstrTodoList(project);        
        final int EXPECTED13 = 0;
        final int RESULT13 = openInstrTodoList.getTopLevelTasks().size();          
        assertEquals(EXPECTED13, RESULT13);
        
        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);
                         
        openTaskList = storage.openTaskList(project);
        final int EXPECTED14 = 500;
        final int RESULT14 = openTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED14, RESULT14);
        
        openInstrTodoList = storage.openInstrTodoList(project);  
        final int EXPECTED15 = 50;
        final int RESULT15 = openInstrTodoList.getTopLevelTasks().size(); 
        assertEquals(EXPECTED15, RESULT15);          
        
        storage.removeProjectStorage(project);                             
    }
    
    /**
     * Test the getTaskList method in CurrentProject.java
     * by creating projects with a regular task list, and an 
     * instructor todo list, and changing the current task 
     * type and ensuring that the task list that is returned
     * has the right size. 
     */
    //@Test 
    public void testGetTaskList() {
                
        // Normal instrTodoList and TaskList
        Project project = ProjectManager.createProject("Project 0", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        
        TaskList instrTodoList = new TaskListImpl(project);                  
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        TaskList taskList = new TaskListImpl(project);                  
        for(int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        CurrentStorage.get().storeTaskList(taskList, project);
        CurrentStorage.get().storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);        
                        
        CurrentProject.currentTaskType = CurrentProject.TaskType.INSTR_TODO_LIST;
        TaskList getInstrTodoList = CurrentProject.getTaskList();        
        final long EXPECTED0 = 50;
        final long RESULT0 = getInstrTodoList.getTopLevelTasks().size(); 
        
        assertEquals(EXPECTED0, RESULT0);             
                
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        TaskList getTaskList = CurrentProject.getTaskList();
        final int EXPECTED1 = 500;
        final int RESULT1 = getTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED1, RESULT1);
        
        
        // Zero instrTodoList
        project = ProjectManager.createProject("Project 0", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        
        instrTodoList = new TaskListImpl(project);                   
        taskList = new TaskListImpl(project);                  
        for(int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        CurrentStorage.get().storeTaskList(taskList, project);
        CurrentStorage.get().storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);        
                        
        CurrentProject.currentTaskType = CurrentProject.TaskType.INSTR_TODO_LIST;
        getInstrTodoList = CurrentProject.getTaskList();        
        final long EXPECTED2 = 0;
        final long RESULT2 = getInstrTodoList.getTopLevelTasks().size(); 
        
        assertEquals(EXPECTED2, RESULT2);             
                
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        getTaskList = CurrentProject.getTaskList();
        final int EXPECTED3 = 500;
        final int RESULT3 = getTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED3, RESULT3);
        
        
        
        // Zero taskList
        project = ProjectManager.createProject("Project 0", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        
        instrTodoList = new TaskListImpl(project);                  
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        CurrentStorage.get().storeTaskList(taskList, project);
        CurrentStorage.get().storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);        
                        
        CurrentProject.currentTaskType = CurrentProject.TaskType.INSTR_TODO_LIST;
        getInstrTodoList = CurrentProject.getTaskList();        
        final long EXPECTED4 = 50;
        final long RESULT4 = getInstrTodoList.getTopLevelTasks().size(); 
        
        assertEquals(EXPECTED4, RESULT4);             
                
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        getTaskList = CurrentProject.getTaskList();
        final int EXPECTED6 = 0;
        final int RESULT6 = getTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED6, RESULT6);
        
        
        
        // Tasks long ago
        project = ProjectManager.createProject("Project 0", CalendarDate.yesterday(), CalendarDate.tomorrow());  
        
        final CalendarDate START_DATE = new CalendarDate(1, 1, 1900);
        final CalendarDate END_DATE = new CalendarDate(1, 1, 1910);
        
        instrTodoList = new TaskListImpl(project);                  
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(START_DATE, END_DATE, "Test: " + i, 0, 0, "", null, true);
            } else {
                instrTodoList.createTask(START_DATE, END_DATE, "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        taskList = new TaskListImpl(project);                  
        for(int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(START_DATE, END_DATE, "Test: " + i, 0, 0, "", null, true);
            } else {
                taskList.createTask(START_DATE, END_DATE, "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        CurrentStorage.get().storeTaskList(taskList, project);
        CurrentStorage.get().storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);        
                        
        CurrentProject.currentTaskType = CurrentProject.TaskType.INSTR_TODO_LIST;
        getInstrTodoList = CurrentProject.getTaskList();        
        final long EXPECTED7 = 50;
        final long RESULT7 = getInstrTodoList.getTopLevelTasks().size(); 
        
        assertEquals(EXPECTED7, RESULT7);             
                
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        getTaskList = CurrentProject.getTaskList();
        final int EXPECTED8 = 500;
        final int RESULT8 = getTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED8, RESULT8);
        
        
        
        // Tasks in the future
        project = ProjectManager.createProject("Project 0", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        
        final CalendarDate START_DATE1 = new CalendarDate(1, 1, 3000);
        final CalendarDate END_DATE1 = new CalendarDate(1, 1, 4000);
        
        instrTodoList = new TaskListImpl(project);                  
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(START_DATE1, END_DATE1, "Test: " + i, 0, 0, "", null, true);
            } else {
                instrTodoList.createTask(START_DATE1, END_DATE1, "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        taskList = new TaskListImpl(project);                  
        for(int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(START_DATE1, END_DATE1, "Test: " + i, 0, 0, "", null, true);
            } else {
                taskList.createTask(START_DATE1, END_DATE1, "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        CurrentStorage.get().storeTaskList(taskList, project);
        CurrentStorage.get().storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);        
                        
        CurrentProject.currentTaskType = CurrentProject.TaskType.INSTR_TODO_LIST;
        getInstrTodoList = CurrentProject.getTaskList();        
        final long EXPECTED9 = 50;
        final long RESULT9 = getInstrTodoList.getTopLevelTasks().size(); 
        
        assertEquals(EXPECTED9, RESULT9);             
                
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        getTaskList = CurrentProject.getTaskList();
        final int EXPECTED10 = 500;
        final int RESULT10 = getTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED10, RESULT10);
    }
    
    /**
     * Test the getChildCount method in TaskTableModel.java
     * by creating a normal task list, and an instructor todo list, each
     * with several items. Then check that the number of items returned
     * by getChildCount is consistent with the number of items created,
     * and the context ("SHOW_REDUCED_ONLY" is true or false). 
     */
    @Test
    public void testGetChildCount() {
        // Normal instrTodoList and taskList
        Project project = ProjectManager.createProject("Project 0", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        
                        
        TaskList instrTodoList = new TaskListImpl(project);                  
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        Task instrTodoTask = instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Separate Todo", 0, 0, "", null, true);
        instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Separate Subtodo", 0, 0, "", instrTodoTask.getID(), true);
        
        TaskList taskList = new TaskListImpl(project);                  
        for(int i = 0; i < 500; i++) {       
            taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
        }
        
        Task task = taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Separate Test", 0, 0, "", null, true);
        taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Separate Subtest", 0, 0, "", task.getID(), true);
        
        
        CurrentStorage.get().storeTaskList(taskList, project);
        CurrentStorage.get().storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);        
        Context.put("SHOW_REDUCED_ONLY", false);
                
        // Check that the task table model has 501 children
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;        
        TaskTableModel taskTableModel = new TaskTableModel();        
        taskTableModel.fireUpdateCache();
        final long EXPECTED0 = 501;
        final long RESULT0 = taskTableModel.getChildCount(project);  
        assertTrue(EXPECTED0 == RESULT0);
        
        // Check that the task table model has 1 child
        taskTableModel.fireUpdateCache();
        final long EXPECTED1 = 1;
        final long RESULT1 = taskTableModel.getChildCount(task);  
        assertTrue(EXPECTED1 == RESULT1);
        
        // Check that the task table model has 51 children
        CurrentProject.currentTaskType = CurrentProject.TaskType.INSTR_TODO_LIST;        
        taskTableModel.fireUpdateCache();
        final long EXPECTED2 = 51;
        final long RESULT2 = taskTableModel.getChildCount(project);          
        assertTrue(EXPECTED2 == RESULT2);
        
        // Check that the task table model has 31 children
        Context.put("SHOW_REDUCED_ONLY", true);
        taskTableModel.fireUpdateCache();
        final long EXPECTED3 = 31;
        final long RESULT3 = taskTableModel.getChildCount(project);          
        assertTrue(EXPECTED3 == RESULT3);
        
        // Check that the task table model has 1 child
        taskTableModel.fireUpdateCache();
        final long EXPECTED4 = 1;
        final long RESULT4 = taskTableModel.getChildCount(instrTodoTask);  
        assertTrue(EXPECTED4 == RESULT4);    
        
        
        // Zero instrTodoList and taskList
        project = ProjectManager.createProject("Project 1", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        
                        
        instrTodoList = new TaskListImpl(project);          
        
        taskList = new TaskListImpl(project);                  
        
        
        CurrentStorage.get().storeTaskList(taskList, project);
        CurrentStorage.get().storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);        
        Context.put("SHOW_REDUCED_ONLY", false);
                
        // Check that the task table model has 501 children
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;        
        taskTableModel = new TaskTableModel();        
        taskTableModel.fireUpdateCache();
        final long EXPECTED5 = 0;
        final long RESULT5 = taskTableModel.getChildCount(project);  
        assertTrue(EXPECTED5 == RESULT5);
                
        // Check that the task table model has 51 children
        CurrentProject.currentTaskType = CurrentProject.TaskType.INSTR_TODO_LIST;        
        taskTableModel.fireUpdateCache();
        final long EXPECTED7 = 0;
        final long RESULT7 = taskTableModel.getChildCount(project);          
        assertTrue(EXPECTED7 == RESULT7);
        
        // Check that the task table model has 31 children
        Context.put("SHOW_REDUCED_ONLY", true);
        taskTableModel.fireUpdateCache();
        final long EXPECTED8 = 0;
        final long RESULT8 = taskTableModel.getChildCount(project);          
        assertTrue(EXPECTED8 == RESULT8);
    }
    
    /**
     * Test the getChild method in TaskTableModel.java by adding
     * several tasks and subtasks to a project, and then checking if 
     * the task at a certain index matches up with the right task in the
     * order in which it was added. 
     */
    @Test
    public void testGetChild() {
        // Normal instrTodoList and taskList
        Project project = ProjectManager.createProject("Project 3", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        
                        
        TaskList instrTodoList = new TaskListImpl(project);         
        
        Task instrTodoTask0 = instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Todo 0", 0, 0, "", null, true);
        Task instrTodoTask1 = instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Todo 1", 0, 0, "", null, true);
        Task instrTodoTask2 = instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Todo 2", 0, 0, "", null, true);
        Task subtask0 = instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Subtodo 0", 0, 0, "", instrTodoTask1.getID(), true);
        Task subtask1 = instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(), "Subtodo 1", 0, 0, "", instrTodoTask1.getID(), true);
                      
        CurrentStorage.get().storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);        
                
        
        // Check that the 3rd child of project is instrTodoTask2
        CurrentProject.currentTaskType = CurrentProject.TaskType.INSTR_TODO_LIST;       
        TaskTableModel taskTableModel = new TaskTableModel();
        taskTableModel.fireUpdateCache();
        final Task EXPECTED1 = instrTodoTask2;
        final Task RESULT1 = (Task) taskTableModel.getChild(project, 2);          
        assertEquals(EXPECTED1, RESULT1);
             
        // Check that the 2nd child of instrTodoTask1 is subtask1
        taskTableModel.fireUpdateCache();
        final Task EXPECTED2 = subtask1;
        final Task RESULT2 = (Task) taskTableModel.getChild(instrTodoTask1, 1);          
        assertEquals(EXPECTED2, RESULT2);
    }
}

