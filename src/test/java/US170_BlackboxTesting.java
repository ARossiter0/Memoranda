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

public class US170_BlackboxTesting {

    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test that a task set to a certain
     * date is actually set for that date.
     */
    @Test
    public void testTaskStartDate() {
        // Add a new project
        Element root = new Element("SER316");

        Element taskElement = new Element("task");
        Project project = (Project) (new ProjectImpl(root));
        TaskList taskList = new TaskListImpl(new Document(root), project);
        Task task = new TaskImpl(taskElement, taskList);
        CalendarDate cDate1 = new CalendarDate(2, 12, 2020);
        task.setStartDate(cDate1);
        assertEquals(cDate1.getDate(), task.getStartDate().getDate());
    }

    /**
     * Test that a task set to a certain
     * date is actually set for that date.
     */
    @Test
    public void testTaskEndDate() {
        // Add a new project
        Element root = new Element("SER316");

        Element taskElement = new Element("task");
        Project project = (Project) (new ProjectImpl(root));
        TaskList taskList = new TaskListImpl(new Document(root), project);
        Task task = new TaskImpl(taskElement, taskList);
        CalendarDate cDate1 = new CalendarDate(03, 12, 2020);
        CalendarDate cDate2 = new CalendarDate(30, 12, 2020);
        task.setStartDate(cDate1);
        task.setEndDate(cDate2);
        assertEquals(cDate1.getDate(), task.getStartDate().getDate());
    }

    @Test
    public void testTaskDescription() {
        // Add a new project
        Element root = new Element("SER316");

        Element taskElement = new Element("task");
        Project project = (Project) (new ProjectImpl(root));
        TaskList taskList = new TaskListImpl(new Document(root), project);
        Task task = new TaskImpl(taskElement, taskList);
        task.setDescription("this is task 1");
        assertEquals("this is task 1", task.getDescription());
    }

    @Test
    public void testTaskEffort() {
        // Add a new project
        Element root = new Element("SER316");

        Element taskElement = new Element("task");
        Project project = (Project) (new ProjectImpl(root));
        TaskList taskList = new TaskListImpl(new Document(root), project);
        Task task = new TaskImpl(taskElement, taskList);
        task.setEffort(100);
        assertEquals(100, task.getEffort());
    }

    @Test
    public void testTaskProgress() {
        // Add a new project
        Element root = new Element("SER316");

        Element taskElement = new Element("task");
        Project project = (Project) (new ProjectImpl(root));
        TaskList taskList = new TaskListImpl(new Document(root), project);
        Task task = new TaskImpl(taskElement, taskList);
        task.setProgress(5);
        assertEquals(5, task.getProgress());
    }

    @Test
    public void testTaskPriority() {
        // Add a new project
        Element root = new Element("SER316");

        Element taskElement = new Element("task");
        Project project = (Project) (new ProjectImpl(root));
        TaskList taskList = new TaskListImpl(new Document(root), project);
        Task task = new TaskImpl(taskElement, taskList);
        task.setPriority(2);
        assertEquals(2, task.getPriority());
    }

    @Test
    public void testProjectTitle() {
        // Add a new project
        Element root = new Element("SER316");

        Element taskElement = new Element("task");
        Project project = (Project) (new ProjectImpl(root));
        project.setTitle("SER316");
        TaskList taskList = new TaskListImpl(new Document(root), project);
        Task task = new TaskImpl(taskElement, taskList);
        task.setPriority(2);
        assertEquals("SER316", project.getTitle());
    }

    @Test
    public void testProjectStartDate() {
        // Add a new project
        Element root = new Element("SER316");

        Element taskElement = new Element("task");
        Project project = (Project) (new ProjectImpl(root));
        CalendarDate cDate1 = new CalendarDate(3, 12, 2020);
        CalendarDate cDate2 = new CalendarDate(30, 12, 2020);
        project.setStartDate(cDate1);

        assertEquals(cDate1.getDate(), project.getStartDate().getDate());
    }

    @Test
    public void testProjectEndDate() {
        // Add a new project
        Element root = new Element("SER316");

        Element taskElement = new Element("task");
        Project project = (Project) (new ProjectImpl(root));
        CalendarDate cDate1 = new CalendarDate(3, 12, 2020);
        CalendarDate cDate2 = new CalendarDate(30, 12, 2020);
        project.setEndDate(cDate1);

        assertEquals(cDate1.getDate(), project.getEndDate().getDate());
    }
    

    /**
     * This confirms an issue I thought we have we that dates setup.
     * If everything works as it should, when we add an end date to a project,
     * and we add a task, if the task end date is after the project end date,
     * the task end date should be set to the ed date of the project.
     */
    @Test
    public void testGetReducedTasks() {
        // Tasklist normal
        CalendarDate projectStartDate = new CalendarDate(3, 12, 2020);
        CalendarDate projectEndDate = new CalendarDate(01, 12, 2020);
        CalendarDate taskStartDate = new CalendarDate(3, 12, 2020);
        CalendarDate taskEndDate = new CalendarDate(27, 12, 2020);
        Project project = ProjectManager.createProject("Project 0", projectStartDate, projectEndDate);
        TaskList taskList = new TaskListImpl(project);
        Task task1 = taskList.createTask(taskStartDate, taskEndDate, "test", 0, 0, "", null);
        task1.setEndDate(taskEndDate);


        assertNotEquals(projectEndDate.getDate(), task1.getEndDate().getDate());

    }

    @Test
    public void countTasks() {
        // Tasklist normal
        CalendarDate projectStartDate = new CalendarDate(3, 12, 2020);
        CalendarDate projectEndDate = new CalendarDate(01, 12, 2020);
        CalendarDate taskStartDate = new CalendarDate(3, 12, 2020);
        CalendarDate taskEndDate = new CalendarDate(27, 12, 2020);
        Project project = ProjectManager.createProject("Project 0", projectStartDate, projectEndDate);
        TaskList taskList = new TaskListImpl(project);
        Task task1 = taskList.createTask(taskStartDate, taskEndDate, "test1", 0, 0, "", null);
        Task task2 = taskList.createTask(taskStartDate, taskEndDate, "test2", 0, 0, "", null);
        Task task3 = taskList.createTask(taskStartDate, taskEndDate, "test3", 0, 0, "", null);

        int result = taskList.getTopLevelTasks().size();

        assertEquals(3, result);

    }

    @Test
    public void countTasksAfterRemove() {
        // Tasklist normal
        CalendarDate projectStartDate = new CalendarDate(3, 12, 2020);
        CalendarDate projectEndDate = new CalendarDate(01, 12, 2020);
        CalendarDate taskStartDate = new CalendarDate(3, 12, 2020);
        CalendarDate taskEndDate = new CalendarDate(27, 12, 2020);
        Project project = ProjectManager.createProject("Project 0", projectStartDate, projectEndDate);
        TaskList taskList = new TaskListImpl(project);
        Task task1 = taskList.createTask(taskStartDate, taskEndDate, "test1", 0, 0, "", null);
        Task task2 = taskList.createTask(taskStartDate, taskEndDate, "test2", 0, 0, "", null);
        Task task3 = taskList.createTask(taskStartDate, taskEndDate, "test3", 0, 0, "", null);
        taskList.removeTask(task3);
        int result = taskList.getTopLevelTasks().size();

        assertEquals(2, result);

    }

}

