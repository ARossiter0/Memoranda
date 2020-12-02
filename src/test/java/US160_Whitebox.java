

import memoranda.CurrentProject;
//import *;
import memoranda.Project;
import memoranda.ProjectImpl;
import memoranda.ProjectManager;
import memoranda.Task;
import memoranda.TaskImpl;
import memoranda.TaskList;
import memoranda.TaskListImpl;
import memoranda.date.CalendarDate;
import memoranda.date.CurrentDate;
import memoranda.ui.TaskTableModel;
import memoranda.util.Context;
import memoranda.util.CurrentStorage;
import memoranda.util.FileStorage;
import memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;

public class US160_Whitebox {

    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test the getIsInReduced and setIsInReduced
     * in TaskImpl.java by creating a new task, 
     * and then invoking those two methods. 
     */
    @Test
    public void testGetSetIsInReducedTask() {
        final String PROJECT_ELEM_TXT = "test_project";
        final String TASK_ELEM_TXT = "task";
        
        Element root = new Element(PROJECT_ELEM_TXT);
        Element taskElement = new Element(TASK_ELEM_TXT);
        Project project = new ProjectImpl(root);
        TaskList taskList = new TaskListImpl(new Document(root), project); 
        Task task = new TaskImpl(taskElement, taskList);
        
        task.setIsInReduced(true);
                
        assertTrue(task.getIsInReduced());
    }
    
    /**
     * Test the getReducedTasks and filter reduced tasks methods
     * in TaskListImpl.java by populating a task list with 50 
     * tasks, and only 30 in the reduced set, then checking that
     * the getReducedTasks method returns a collection of size 30. 
     */
    @Test
    public void testGetReducedTasks() {        
        Project project = ProjectManager.createProject("Project 1", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        TaskList taskList = new TaskListImpl(project); 
                     
        
        for(int i = 0; i < 50; i++) {
            if (i < 30) {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, true);
            } else {
                taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Test: " + i, 0, 0, "", null, false);
            }
        }
        
        final int EXPECTED = 30;
        final int RESULT = taskList.getReducedTasks(taskList.getTopLevelTasks()).size();        
        
        assertEquals(EXPECTED, RESULT);
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
        Project project = ProjectManager.createProject("Project 1", CalendarDate.yesterday(), CalendarDate.tomorrow());     
        
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
        final int EXPECTED1 = 0;
        final int RESULT1 = openInstrTodoList.getTopLevelTasks().size();          
        assertEquals(EXPECTED1, RESULT1);
        
        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);
                         
        TaskList openTaskList = storage.openTaskList(project);
        final int EXPECTED2 = 500;
        final int RESULT2 = openTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED2, RESULT2);
        
        openInstrTodoList = storage.openInstrTodoList(project);  
        final int EXPECTED3 = 50;
        final int RESULT3 = openInstrTodoList.getTopLevelTasks().size(); 
        assertEquals(EXPECTED3, RESULT3);
        
        
        storage.removeProjectStorage(project);
    }
    
    /**
     * Test the getTaskList method in CurrentProject.java
     * by creating a regular task list, and an instructor 
     * todo list, and changing the current task type
     * and ensuring that the task list that is returned
     * has the right size. 
     */
    //@Test 
    public void testGetTaskList() {
                
        Project project = ProjectManager.createProject("Project 2", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        
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
        final long EXPECTED1 = 50;
        final long RESULT1 = getInstrTodoList.getTopLevelTasks().size(); 
        
        assertEquals(EXPECTED1, RESULT1);             
                
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        TaskList getTaskList = CurrentProject.getTaskList();
        final int EXPECTED2 = 500;
        final int RESULT2 = getTaskList.getTopLevelTasks().size();          
        assertEquals(EXPECTED2, RESULT2);
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
        //CurrentProject.free();
        Project project = ProjectManager.createProject("Project 3", CalendarDate.yesterday(), CalendarDate.tomorrow());   
        
                        
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
        final long EXPECTED1 = 501;
        final long RESULT1 = taskTableModel.getChildCount(project);  
        assertTrue(EXPECTED1 == RESULT1);
        
        // Check that the task table model has 1 child
        taskTableModel.fireUpdateCache();
        final long EXPECTED2 = 1;
        final long RESULT2 = taskTableModel.getChildCount(task);  
        assertTrue(EXPECTED2 == RESULT2);
        
        // Check that the task table model has 51 children
        CurrentProject.currentTaskType = CurrentProject.TaskType.INSTR_TODO_LIST;        
        taskTableModel.fireUpdateCache();
        final long EXPECTED3 = 51;
        final long RESULT3 = taskTableModel.getChildCount(project);          
        assertTrue(EXPECTED3 == RESULT3);
        
        // Check that the task table model has 31 children
        Context.put("SHOW_REDUCED_ONLY", true);
        taskTableModel.fireUpdateCache();
        final long EXPECTED4 = 31;
        final long RESULT4 = taskTableModel.getChildCount(project);          
        assertTrue(EXPECTED4 == RESULT4);
        
        // Check that the task table model has 1 child
        taskTableModel.fireUpdateCache();
        final long EXPECTED5 = 1;
        final long RESULT5 = taskTableModel.getChildCount(instrTodoTask);  
        assertTrue(EXPECTED5 == RESULT5);        
    }
    
    /**
     * Test the getChild method in TaskTableModel.java by adding
     * several tasks and subtasks to a project, and then checking if 
     * the task at a certain index matches up with the right task in the
     * order in which it was added. 
     */
    @Test
    public void testGetChild() {
        //CurrentProject.free();
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

