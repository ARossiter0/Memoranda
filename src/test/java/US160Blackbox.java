package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import main.java.memoranda.CurrentProject;
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
import main.java.memoranda.util.Storage;
import main.java.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;

import org.junit.Before;

import org.junit.Test;

public class US160Blackbox {

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
        String projectElemTxt = "test_project";
        String taskElemTxt = "task";

        Element root = new Element(projectElemTxt);
        Element taskElement = new Element(taskElemTxt);
        Project project = new ProjectImpl(root);
        TaskList taskList = new TaskListImpl(new Document(root), project);
        Task task = new TaskImpl(taskElement, taskList);

        task.setIsInReduced(true);
        assertTrue(task.getIsInReduced());
        task.setIsInReduced(false);
        assertFalse(task.getIsInReduced());


        // Test with one character project and task title
        projectElemTxt = "a";
        taskElemTxt = "b";

        root = new Element(projectElemTxt);
        taskElement = new Element(taskElemTxt);
        project = new ProjectImpl(root);
        taskList = new TaskListImpl(new Document(root), project);
        task = new TaskImpl(taskElement, taskList);

        task.setIsInReduced(true);
        assertTrue(task.getIsInReduced());
        task.setIsInReduced(false);
        assertFalse(task.getIsInReduced());


        // Test with long character project and task titles
        projectElemTxt =
                "fdahui13489gfhds09ujrr21q2390u1rr23r32u901ru2u02ru20"
                        + "rgdscu0vxu9bnvuug09bu09bgufbusgbujpsfsdfujaufhdsafh";
        taskElemTxt =
                "fodisafu90430q5tw023houqhfjdhfkdhafhdkalhjdkasjhfdklahfjdklhsf"
                        + "ajdkafer84rf8943qrurfophoghrjkdhsajgdksfhduoa"
                        + "phfudahf";

        root = new Element(projectElemTxt);
        taskElement = new Element(taskElemTxt);
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
     * Expected number of tasks in the reduced set.
     */
    @Test
    public void testGetReducedTasks() {
        // Tasklist normal     
        Project project = ProjectManager.createProject("Project 0",
                CalendarDate.yesterday(), CalendarDate.tomorrow());
        TaskList taskList = new TaskListImpl(project);


        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                taskList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        true);
            } else {
                taskList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        false);
            }
        }

        final int Expected0 = 30;
        final int Result0 =
                taskList.getReducedTasks(taskList.getTopLevelTasks()).size();

        assertEquals(Expected0, Result0);

        // Zero tasklist
        project = ProjectManager.createProject("Project 1",
                CalendarDate.yesterday(), CalendarDate.tomorrow());
        taskList = new TaskListImpl(project);

        final int Expected1 = 0;
        final int Result1 =
                taskList.getReducedTasks(taskList.getTopLevelTasks()).size();

        assertEquals(Expected1, Result1);

        // Tasklist all in reduced
        project = ProjectManager.createProject("Project 2",
                CalendarDate.yesterday(), CalendarDate.tomorrow());
        taskList = new TaskListImpl(project);


        for (int i = 0; i < 50; i++) {
            taskList.createTask(CalendarDate.today(), CalendarDate.today(),
                    "Test: " + i, 0, 0, "", null, true);
        }

        final int Expected2 = 50;
        final int Result2 =
                taskList.getReducedTasks(taskList.getTopLevelTasks()).size();

        assertEquals(Expected2, Result2);

        // Tasklist none in reduced
        project = ProjectManager.createProject("Project 2",
                CalendarDate.yesterday(), CalendarDate.tomorrow());
        taskList = new TaskListImpl(project);


        for (int i = 0; i < 50; i++) {
            taskList.createTask(CalendarDate.today(), CalendarDate.today(),
                    "Test: " + i, 0, 0, "", null, false);
        }

        final int Expected3 = 0;
        final int Result3 =
                taskList.getReducedTasks(taskList.getTopLevelTasks()).size();

        assertEquals(Expected3, Result3);

        // Tasklist one in reduced
        project = ProjectManager.createProject("Project 2",
                CalendarDate.yesterday(), CalendarDate.tomorrow());
        taskList = new TaskListImpl(project);


        for (int i = 0; i < 49; i++) {
            taskList.createTask(CalendarDate.today(), CalendarDate.today(),
                    "Test: " + i, 0, 0, "", null, false);
        }

        taskList.createTask(CalendarDate.today(), CalendarDate.today(),
                "Reduced Test", 0, 0, "", null, true);

        final int Expected4 = 1;
        final int Result4 =
                taskList.getReducedTasks(taskList.getTopLevelTasks()).size();

        assertEquals(Expected4, Result4);


        // Tasklist one not in reduced
        project = ProjectManager.createProject("Project 2",
                CalendarDate.yesterday(), CalendarDate.tomorrow());
        taskList = new TaskListImpl(project);


        for (int i = 0; i < 49; i++) {
            taskList.createTask(CalendarDate.today(), CalendarDate.today(),
                    "Test: " + i, 0, 0, "", null, true);
        }

        taskList.createTask(CalendarDate.today(), CalendarDate.today(), "Non"
                + "-Reduced Test", 0, 0, "", null, false);

        final int Expected5 = 49;
        final int Result5 =
                taskList.getReducedTasks(taskList.getTopLevelTasks()).size();

        assertEquals(Expected5, Result5);

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
        Project project = ProjectManager.createProject("Project 0",
                CalendarDate.yesterday(), CalendarDate.tomorrow());

        TaskList instrTodoList = new TaskListImpl(project);
        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        true);
            } else {
                instrTodoList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        false);
            }
        }

        TaskList taskList = new TaskListImpl(project);
        for (int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        true);
            } else {
                taskList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        false);
            }
        }

        FileStorage storage = new FileStorage();


        TaskList openInstrTodoList = storage.openInstrTodoList(project);
        final int Expected0 = 0;
        final int Result0 = openInstrTodoList.getTopLevelTasks().size();
        assertEquals(Expected0, Result0);

        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);

        TaskList openTaskList = storage.openTaskList(project);
        final int Expected1 = 500;
        final int Result1 = openTaskList.getTopLevelTasks().size();
        assertEquals(Expected1, Result1);

        openInstrTodoList = storage.openInstrTodoList(project);
        final int Expected2 = 50;
        final int Result2 = openInstrTodoList.getTopLevelTasks().size();
        assertEquals(Expected2, Result2);

        storage.removeProjectStorage(project);


        // Zero instrTodoList
        project = ProjectManager.createProject("Project 1",
                CalendarDate.yesterday(), CalendarDate.tomorrow());

        instrTodoList = new TaskListImpl(project);

        taskList = new TaskListImpl(project);
        for (int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        true);
            } else {
                taskList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        false);
            }
        }

        storage = new FileStorage();


        openInstrTodoList = storage.openInstrTodoList(project);
        final int Expected4 = 0;
        final int Result4 = openInstrTodoList.getTopLevelTasks().size();
        assertEquals(Expected4, Result4);

        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);

        openTaskList = storage.openTaskList(project);
        final int Expected5 = 500;
        final int Result5 = openTaskList.getTopLevelTasks().size();
        assertEquals(Expected5, Result5);

        openInstrTodoList = storage.openInstrTodoList(project);
        final int Expected6 = 0;
        final int Result6 = openInstrTodoList.getTopLevelTasks().size();
        assertEquals(Expected6, Result6);

        storage.removeProjectStorage(project);


        // Zero taskList
        project = ProjectManager.createProject("Project 2",
                CalendarDate.yesterday(), CalendarDate.tomorrow());

        instrTodoList = new TaskListImpl(project);
        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        true);
            } else {
                instrTodoList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        false);
            }
        }

        taskList = new TaskListImpl(project);

        storage = new FileStorage();


        openInstrTodoList = storage.openInstrTodoList(project);
        final int Expected7 = 0;
        final int Result7 = openInstrTodoList.getTopLevelTasks().size();
        assertEquals(Expected7, Result7);

        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);

        openTaskList = storage.openTaskList(project);
        final int Expected8 = 0;
        final int Result8 = openTaskList.getTopLevelTasks().size();
        assertEquals(Expected8, Result8);

        openInstrTodoList = storage.openInstrTodoList(project);
        final int Expected9 = 50;
        final int Result9 = openInstrTodoList.getTopLevelTasks().size();
        assertEquals(Expected9, Result9);

        storage.removeProjectStorage(project);


        // Tasks long ago 
        project = ProjectManager.createProject("Project 3",
                CalendarDate.yesterday(), CalendarDate.tomorrow());

        final CalendarDate Start_Date_ = new CalendarDate(1, 1, 1900);
        final CalendarDate End_Date_ = new CalendarDate(1, 1, 1910);


        instrTodoList = new TaskListImpl(project);
        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(Start_Date_, End_Date_, "Test: " + i,
                        0, 0, "", null, true);
            } else {
                instrTodoList.createTask(Start_Date_, End_Date_, "Test: " + i,
                        0, 0, "", null, false);
            }
        }

        taskList = new TaskListImpl(project);
        for (int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(Start_Date_, End_Date_, "Test: " + i, 0, 0,
                        "", null, true);
            } else {
                taskList.createTask(Start_Date_, End_Date_, "Test: " + i, 0, 0,
                        "", null, false);
            }
        }

        storage = new FileStorage();


        openInstrTodoList = storage.openInstrTodoList(project);
        final int Expected10 = 0;
        final int Result10 = openInstrTodoList.getTopLevelTasks().size();
        assertEquals(Expected10, Result10);

        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);

        openTaskList = storage.openTaskList(project);
        final int Expected11 = 500;
        final int Result11 = openTaskList.getTopLevelTasks().size();
        assertEquals(Expected11, Result11);

        openInstrTodoList = storage.openInstrTodoList(project);
        final int Expected12 = 50;
        final int Result12 = openInstrTodoList.getTopLevelTasks().size();
        assertEquals(Expected12, Result12);

        storage.removeProjectStorage(project);


        // Tasks in the future
        project = ProjectManager.createProject("Project 1",
                CalendarDate.yesterday(), CalendarDate.tomorrow());

        final CalendarDate Start_Date_1 = new CalendarDate(1, 1, 3000);
        final CalendarDate End_Date_1 = new CalendarDate(1, 1, 4000);

        instrTodoList = new TaskListImpl(project);
        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(Start_Date_1, End_Date_1, "Test: " + i,
                        0, 0, "", null, true);
            } else {
                instrTodoList.createTask(Start_Date_1, End_Date_1, "Test: " + i,
                        0, 0, "", null, false);
            }
        }

        taskList = new TaskListImpl(project);
        for (int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(Start_Date_1, End_Date_1, "Test: " + i, 0,
                        0, "", null, true);
            } else {
                taskList.createTask(Start_Date_1, End_Date_1, "Test: " + i, 0,
                        0, "", null, false);
            }
        }

        storage = new FileStorage();


        openInstrTodoList = storage.openInstrTodoList(project);
        final int Expected13 = 0;
        final int Result13 = openInstrTodoList.getTopLevelTasks().size();
        assertEquals(Expected13, Result13);

        storage.storeInstrTodoList(instrTodoList, project);
        storage.storeTaskList(taskList, project);

        openTaskList = storage.openTaskList(project);
        final int Expected14 = 500;
        final int Result14 = openTaskList.getTopLevelTasks().size();
        assertEquals(Expected14, Result14);

        openInstrTodoList = storage.openInstrTodoList(project);
        final int Expected15 = 50;
        final int Result15 = openInstrTodoList.getTopLevelTasks().size();
        assertEquals(Expected15, Result15);

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
        Project project = ProjectManager.createProject("Project 0",
                CalendarDate.yesterday(), CalendarDate.tomorrow());

        TaskList instrTodoList = new TaskListImpl(project);
        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        true);
            } else {
                instrTodoList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        false);
            }
        }

        TaskList taskList = new TaskListImpl(project);
        for (int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        true);
            } else {
                taskList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        false);
            }
        }

        Storage storage = CurrentStorage.get();
        storage.storeTaskList(taskList, project);
        storage.storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);

        CurrentProject.currentTaskType =
                CurrentProject.TaskType.INSTR_TODO_LIST;
        TaskList getInstrTodoList = CurrentProject.getTaskList();
        final long Expected0 = 50;
        final long Result0 = getInstrTodoList.getTopLevelTasks().size();

        assertEquals(Expected0, Result0);

        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        TaskList getTaskList = CurrentProject.getTaskList();
        final int Expected1 = 500;
        final int Result1 = getTaskList.getTopLevelTasks().size();
        assertEquals(Expected1, Result1);

        storage.removeProjectStorage(project);


        // Zero instrTodoList
        project = ProjectManager.createProject("Project 0",
                CalendarDate.yesterday(), CalendarDate.tomorrow());

        instrTodoList = new TaskListImpl(project);
        taskList = new TaskListImpl(project);
        for (int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        true);
            } else {
                taskList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        false);
            }
        }

        storage.storeTaskList(taskList, project);
        storage.storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);

        CurrentProject.currentTaskType =
                CurrentProject.TaskType.INSTR_TODO_LIST;
        getInstrTodoList = CurrentProject.getTaskList();
        final long Expected2 = 0;
        final long Result2 = getInstrTodoList.getTopLevelTasks().size();

        assertEquals(Expected2, Result2);

        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        getTaskList = CurrentProject.getTaskList();
        final int Expected3 = 500;
        final int Result3 = getTaskList.getTopLevelTasks().size();
        assertEquals(Expected3, Result3);

        storage.removeProjectStorage(project);

        // Zero taskList
        project = ProjectManager.createProject("Project 0",
                CalendarDate.yesterday(), CalendarDate.tomorrow());

        instrTodoList = new TaskListImpl(project);
        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        true);
            } else {
                instrTodoList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        false);
            }
        }


        storage.storeTaskList(taskList, project);
        storage.storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);

        CurrentProject.currentTaskType =
                CurrentProject.TaskType.INSTR_TODO_LIST;
        getInstrTodoList = CurrentProject.getTaskList();
        final long Expected4 = 50;
        final long Result4 = getInstrTodoList.getTopLevelTasks().size();

        assertEquals(Expected4, Result4);

        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        getTaskList = CurrentProject.getTaskList();
        final int Expected6 = 0;
        final int Result6 = getTaskList.getTopLevelTasks().size();
        assertEquals(Expected6, Result6);

        storage.removeProjectStorage(project);

        // Tasks long ago
        project = ProjectManager.createProject("Project 0",
                CalendarDate.yesterday(), CalendarDate.tomorrow());

        final CalendarDate Start_Date_ = new CalendarDate(1, 1, 1900);
        final CalendarDate End_Date_ = new CalendarDate(1, 1, 1910);

        instrTodoList = new TaskListImpl(project);
        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(Start_Date_, End_Date_, "Test: " + i,
                        0, 0, "", null, true);
            } else {
                instrTodoList.createTask(Start_Date_, End_Date_, "Test: " + i,
                        0, 0, "", null, false);
            }
        }

        taskList = new TaskListImpl(project);
        for (int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(Start_Date_, End_Date_, "Test: " + i, 0, 0,
                        "", null, true);
            } else {
                taskList.createTask(Start_Date_, End_Date_, "Test: " + i, 0, 0,
                        "", null, false);
            }
        }

        storage.storeTaskList(taskList, project);
        storage.storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);

        CurrentProject.currentTaskType =
                CurrentProject.TaskType.INSTR_TODO_LIST;
        getInstrTodoList = CurrentProject.getTaskList();
        final long Expected7 = 50;
        final long Result7 = getInstrTodoList.getTopLevelTasks().size();

        assertEquals(Expected7, Result7);

        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        getTaskList = CurrentProject.getTaskList();
        final int Expected8 = 500;
        final int Result8 = getTaskList.getTopLevelTasks().size();
        assertEquals(Expected8, Result8);

        storage.removeProjectStorage(project);

        // Tasks in the future
        project = ProjectManager.createProject("Project 0",
                CalendarDate.yesterday(), CalendarDate.tomorrow());

        final CalendarDate Start_Date_1 = new CalendarDate(1, 1, 3000);
        final CalendarDate End_Date_1 = new CalendarDate(1, 1, 4000);

        instrTodoList = new TaskListImpl(project);
        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(Start_Date_1, End_Date_1, "Test: " + i,
                         0, 0, "", null, true);
            } else {
                instrTodoList.createTask(Start_Date_1, End_Date_1, "Test: " + i,
                         0, 0, "", null, false);
            }
        }

        taskList = new TaskListImpl(project);
        for (int i = 0; i < 500; i++) {
            if (i < 300) {
                taskList.createTask(Start_Date_1, End_Date_1, "Test: " + i, 0,
                        0, "", null, true);
            } else {
                taskList.createTask(Start_Date_1, End_Date_1, "Test: " + i, 0,
                        0, "", null, false);
            }
        }

        storage.storeTaskList(taskList, project);
        storage.storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);

        CurrentProject.currentTaskType =
                CurrentProject.TaskType.INSTR_TODO_LIST;
        getInstrTodoList = CurrentProject.getTaskList();
        final long Expected9 = 50;
        final long Result9 = getInstrTodoList.getTopLevelTasks().size();

        assertEquals(Expected9, Result9);

        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        getTaskList = CurrentProject.getTaskList();
        final int Expected10 = 500;
        final int Result10 = getTaskList.getTopLevelTasks().size();
        assertEquals(Expected10, Result10);

        storage.removeProjectStorage(project);
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
        Project project = ProjectManager.createProject("Project 0",
                CalendarDate.yesterday(), CalendarDate.tomorrow());


        TaskList instrTodoList = new TaskListImpl(project);
        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                instrTodoList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        true);
            } else {
                instrTodoList.createTask(CalendarDate.today(),
                        CalendarDate.today(), "Test: " + i, 0, 0, "", null,
                        false);
            }
        }

        Task instrTodoTask = instrTodoList.createTask(CalendarDate.today(),
                CalendarDate.today(), "Separate Todo", 0, 0, "", null, true);
        instrTodoList.createTask(CalendarDate.today(), CalendarDate.today(),
                "Separate Subtodo", 0, 0, "", instrTodoTask.getID(), true);

        TaskList taskList = new TaskListImpl(project);
        for (int i = 0; i < 500; i++) {
            taskList.createTask(CalendarDate.today(), CalendarDate.today(),
                    "Test: " + i, 0, 0, "", null, true);
        }

        Task task = taskList.createTask(CalendarDate.today(),
                CalendarDate.today(), "Separate Test", 0, 0, "", null, true);
        taskList.createTask(CalendarDate.today(), CalendarDate.today(),
                "Separate Subtest", 0, 0, "", task.getID(), true);

        Storage storage = CurrentStorage.get();
        storage.storeTaskList(taskList, project);
        storage.storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);
        Context.put("SHOW_REDUCED_ONLY", false);

        // Check that the task table model has 501 children
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        TaskTableModel taskTableModel = new TaskTableModel();
        taskTableModel.fireUpdateCache();
        final long Expected0 = 501;
        final long Result0 = taskTableModel.getChildCount(project);
        assertTrue(Expected0 == Result0);

        // Check that the task table model has 1 child
        taskTableModel.fireUpdateCache();
        final long Expected1 = 1;
        final long Result1 = taskTableModel.getChildCount(task);
        assertTrue(Expected1 == Result1);

        // Check that the task table model has 51 children
        CurrentProject.currentTaskType =
                CurrentProject.TaskType.INSTR_TODO_LIST;
        taskTableModel.fireUpdateCache();
        final long Expected2 = 51;
        final long Result2 = taskTableModel.getChildCount(project);
        assertTrue(Expected2 == Result2);

        // Check that the task table model has 31 children
        Context.put("SHOW_REDUCED_ONLY", true);
        taskTableModel.fireUpdateCache();
        final long Expected3 = 31;
        final long Result3 = taskTableModel.getChildCount(project);
        assertTrue(Expected3 == Result3);

        // Check that the task table model has 1 child
        taskTableModel.fireUpdateCache();
        final long Expected4 = 1;
        final long Result4 = taskTableModel.getChildCount(instrTodoTask);
        assertTrue(Expected4 == Result4);

        storage.removeProjectStorage(project);

        // Zero instrTodoList and taskList
        project = ProjectManager.createProject("Project 1",
                CalendarDate.yesterday(), CalendarDate.tomorrow());


        instrTodoList = new TaskListImpl(project);

        taskList = new TaskListImpl(project);

        storage.storeTaskList(taskList, project);
        storage.storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);
        Context.put("SHOW_REDUCED_ONLY", false);

        // Check that the task table model has 501 children
        CurrentProject.currentTaskType = CurrentProject.TaskType.DEFAULT;
        taskTableModel = new TaskTableModel();
        taskTableModel.fireUpdateCache();
        final long Expected5 = 0;
        final long Result5 = taskTableModel.getChildCount(project);
        assertTrue(Expected5 == Result5);

        // Check that the task table model has 51 children
        CurrentProject.currentTaskType =
                CurrentProject.TaskType.INSTR_TODO_LIST;
        taskTableModel.fireUpdateCache();
        final long Expected7 = 0;
        final long Result7 = taskTableModel.getChildCount(project);
        assertTrue(Expected7 == Result7);

        // Check that the task table model has 31 children
        Context.put("SHOW_REDUCED_ONLY", true);
        taskTableModel.fireUpdateCache();
        final long Expected8 = 0;
        final long Result8 = taskTableModel.getChildCount(project);
        assertTrue(Expected8 == Result8);

        storage.removeProjectStorage(project);
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
        Project project = ProjectManager.createProject("Project 3",
                CalendarDate.yesterday(), CalendarDate.tomorrow());


        TaskList instrTodoList = new TaskListImpl(project);

        final Task instrTodoTask0 =
                instrTodoList.createTask(CalendarDate.today(),
                CalendarDate.today(), "Todo 0", 0, 0, "", null, true);
        final Task instrTodoTask1 =
                instrTodoList.createTask(CalendarDate.today(),
                CalendarDate.today(), "Todo 1", 0, 0, "", null, true);
        final Task instrTodoTask2 =
                instrTodoList.createTask(CalendarDate.today(),
                CalendarDate.today(), "Todo 2", 0, 0, "", null, true);
        final Task subtask0 = instrTodoList.createTask(CalendarDate.today(),
                CalendarDate.today(), "Subtodo 0", 0, 0, "",
                instrTodoTask1.getID(), true);
        final Task subtask1 = instrTodoList.createTask(CalendarDate.today(),
                CalendarDate.today(), "Subtodo 1", 0, 0, "",
                instrTodoTask1.getID(), true);

        Storage storage = CurrentStorage.get();
        storage.storeInstrTodoList(instrTodoList, project);
        CurrentProject.set(project);


        // Check that the 3rd child of project is instrTodoTask2
        CurrentProject.currentTaskType =
                CurrentProject.TaskType.INSTR_TODO_LIST;
        TaskTableModel taskTableModel = new TaskTableModel();
        taskTableModel.fireUpdateCache();
        final Task Expected1 = instrTodoTask2;
        final Task Result1 = (Task) taskTableModel.getChild(project, 2);
        assertEquals(Expected1, Result1);

        // Check that the 2nd child of instrTodoTask1 is subtask1
        taskTableModel.fireUpdateCache();
        final Task Expected2 = subtask1;
        final Task Result2 = (Task) taskTableModel.getChild(instrTodoTask1, 1);
        assertEquals(Expected2, Result2);

        storage.removeProjectStorage(project);
    }
}

