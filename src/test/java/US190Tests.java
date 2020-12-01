import static org.junit.Assert.assertEquals;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.Task;
import main.java.memoranda.TaskList;
import main.java.memoranda.TaskListImpl;

import main.java.memoranda.date.CalendarDate;

import main.java.memoranda.ui.TaskTable;
import main.java.memoranda.ui.TaskTableSorter;

import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Storage;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Name: US190Tests.java
 * <p></p>
 * Test Description: Test the methods changed significantly in US190.
 * <p></p>
 * @Author Tristan Johnson
 * @Date 11/30/2020
 */
public class US190Tests {

    @Before
    public void setup() {

    }

    /**
     * Test getStatus method in TaskImpl.java.
     */
    @Test
    public void testGetStatus() {
        // Create task
        CalendarDate projectStartDate = new CalendarDate(10, 8, 2020);
        CalendarDate projectEndDate = new CalendarDate(10, 2, 2021);
        Project project = ProjectManager.createProject("Project 1",
                projectStartDate, projectEndDate);
        TaskList taskList = new TaskListImpl(project);
        CalendarDate taskStartDate = new CalendarDate(10, 11, 2020);
        CalendarDate taskEndDate = new CalendarDate(10, 12, 2020);
        Task task = taskList.createTask(taskStartDate, taskEndDate, "Task 1",
                1, 3, "The first task", null);

        // Create dates
        final CalendarDate date1 = new CalendarDate(9, 12, 2020);
        final CalendarDate date6 = new CalendarDate(9, 11, 2020);
        final CalendarDate date7 = new CalendarDate(11, 12, 2020);

        // Check task status
        assertEquals(Task.ACTIVE, task.getStatus(taskStartDate));
        assertEquals(Task.FAILED, task.getStatus(taskEndDate));
        assertEquals(Task.ACTIVE, task.getStatus(date1));
        assertEquals(Task.SCHEDULED, task.getStatus(date6));
        assertEquals(Task.FAILED, task.getStatus(date7));
    }

    /**
     * Test getChild method in TaskTableSorter.java.
     */
    @Test
    public void testGetChild() {
        // Create tasks
        CalendarDate projectStartDate = new CalendarDate(10, 8, 2020);
        CalendarDate projectEndDate = new CalendarDate(10, 2, 2021);
        Project project = ProjectManager.createProject("Project 1",
                projectStartDate, projectEndDate);
        TaskList taskList = new TaskListImpl(project);

        CalendarDate taskStartDate1 = new CalendarDate(10, 10, 2020);
        CalendarDate taskEndDate1 = new CalendarDate(10, 1, 2021);
        CalendarDate taskStartDate2 = new CalendarDate(10, 8, 2020);
        CalendarDate taskEndDate2 = new CalendarDate(10, 9, 2020);
        CalendarDate taskStartDate3 = new CalendarDate(10, 1, 2021);
        CalendarDate taskEndDate3 = new CalendarDate(10, 2, 2021);


        final Task task1 = taskList.createTask(taskStartDate1, taskEndDate1,
                "US190"
                + "-Test Task 1", 1, 3, "The first task", null);
        final Task task2 = taskList.createTask(taskStartDate2, taskEndDate2,
                "US190"
                + "-Test Task 2", 1, 3, "The first task", null);
        final Task task3 = taskList.createTask(taskStartDate1, taskEndDate1,
                "US190"
                + "-Test Task 3", 1, 3, "The first task", null);
        final Task task4 = taskList.createTask(taskStartDate1, taskEndDate1,
                "US190"
                + "-Test Task 4", 1, 3, "The first task", null);
        final Task task5 = taskList.createTask(taskStartDate3, taskEndDate3,
                "US190"
                + "-Test Task 5", 1, 3, "The first task", null);

        Storage storage = CurrentStorage.get();
        storage.storeTaskList(taskList, project);

        main.java.memoranda.CurrentProject.set(project);
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;

        TaskTable taskTable = new TaskTable();
        TaskTableSorter taskTableSorter = new TaskTableSorter(taskTable);

        main.java.memoranda.util.Context.put("SHOW_ACTIVE_TASKS_ONLY", true);

        final Task firstChild = (Task) taskTableSorter.getChild(project, 0);
        final Task secondChild = (Task) taskTableSorter.getChild(project, 1);
        final Task thirdChild = (Task) taskTableSorter.getChild(project, 2);

        assertEquals(task1, firstChild);
        assertEquals(task3, secondChild);
        assertEquals(task5, thirdChild);

        storage.removeProjectStorage(project);
        main.java.memoranda.util.Context.put("SHOW_ACTIVE_TASKS_ONLY", false);
    }


}
