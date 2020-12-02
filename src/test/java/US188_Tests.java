package test.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.GregorianCalendar;
import java.util.Locale;

import main.java.memoranda.LectureList;
import main.java.memoranda.LectureListImpl;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.ResourcesListImpl;
import main.java.memoranda.Task;
import main.java.memoranda.TaskList;
import main.java.memoranda.TaskListImpl;

import main.java.memoranda.date.CalendarDate;

import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Storage;
import main.java.memoranda.util.Util;

import memoranda.util.JsonBuilder;
import org.junit.Before;
import org.junit.Test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//ref : https://cliftonlabs.github.io/json-simple/target/apidocs/index.html

public class US188_Tests {


    private String path = Util.getEnvDir() + "/Data.json";
    public JSONObject testCourse;
    
    @setup
    public void setUp() {
        /**
         * Create sample course with values
         * run the exporter
         * compate the values exported to the values put in
         */

        String id = "COURSE_ID_1_TEST_ONLY";
        String title = "TEST_COURSE_TITLE_33";
        CalendarDate startDate = new CalendarDate(3, 11, 2020);
        CalendarDate endDate = new CalendarDate(18, 2, 2021);
        CalendarDate finalDate = new CalendarDate(16, 2, 2021);
        Project course = ProjectManager.createProject(id, title, startDate, endDate, finalDate);

        CurrentProject.set(course);

        setUpAssignment(course);
        setUpDefTask(course);
        setUpInstTodo(course);
        setUpLectureTask(course);
        setUpResources(course);
        setUpStudent(course);
        setUpTaGraderTodo(course);

        JsonBuilder jsonBuiler = new JsonBuilder();
        jsonBuiler.exportMemoranda();

        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(path);
        JSONObject root = (JSONObject) jsonParser.parse(reader);

        JsonArray coursesArray = (JSONArray) root.get("courses");
        testCourse = coursesArray.get(0);
    }

    //DefaultTask
    public void setUpDefTask(Project course) {
        TaskList defaultTaskList = new TaskListImpl(course);
        String name = "DEFAULT_TASK_1";
        String type = "TYPE_21";
        CalendarDate taskDate = new CalendarDate(13, 11, 2020);
        Task defTaskOne = defaultTaskList.createSingleEventTask(name, taskDate, type);

    }
    //Lectures
    public void setUpLectureTask(Project course) {
        LectureList lectureList = new LectureListImpl(course);

        CalendarDate lectureDate = new CalendarDate(9, 12, 2020);
        Calendar startTimeCal = new GregorianCalendar(2020, 12, 9, 5, 30);
        Calendar endTimeCal = new GregorianCalendar(2020, 12, 9, 7, 30);
        String topic = "LECTURE_TEST_TOPIC";

        lectureList.createLecture(date, startTimeCal, endTimeCal, topic);

        CurrentStorage.get().storeLectureList(lectureList, course);
    }

    //Assignment
    public void setUpAssignment(Project course) { 
        TaskList assignmentList = new TaskListImpl(course);

        String id = "ASSIGNEMNT_ID_3434";
        CalendarDate startDate = new CalendarDate(14, 12, 2020);
        CalendarDate endDate = new CalendarDate(15, 12, 2020);
        String text = "ASSIGNMENT_TEXT";
        int priority = 3;
        long effort = 9004493;
        String description = "DESCRIPTION_3432";
        String parentTaskId = "DEF_PARENT_ID_98";
        Boolean isInReduced = false;

        Task assignTask = assignmentList.createTask(startDate, endDate, text, priority, effort, description, parentTaskId, isInReduced);
        assignmentList.setTaskId(assignTask, id);
        CurrentStorage.get().storeAssignList(assignmentList, course);
    }

    //Instructor Todos
    public void setUpInstTodo(Project course) {
        TaskList instrTodoList = new TaskListImpl(course);

        String id = "INSTRUCTOR_ID_55";
        CalendarDate startDate = new CalendarDate(13, 11, 2020);
        CalendarDate endDate = new CalendarDate(14, 11, 2020);
        String text = "INSTRUCTOR_TEXT";
        int priority = 4;
        long effort = 2223;
        String description = "DESCRIPTION_INSTRUCTOR";
        String parentTaskId = "DEF_PARENT_ID_009";
        Boolean isInReduced = false;

        Task instTask = instrTodoList.createTask(startDate, endDate, text, priority, effort, description, parentTaskId, isInReduced);
        instrTodoList.setTaskId(instTask, id);

        CurrentStorage.get().storeInstrTodoList(instrTodoList, course);
    }

    //Grader Todos
    public void setUpTaGraderTodo(Project course) {
        TaskList taGraderTodoList = new TaskListImpl(course);
        
        String id = "TA_TODO_ID_001";
        CalendarDate startDate = new CalendarDate(9, 12, 2020);
        CalendarDate endDate = new CalendarDate(9, 12, 2020);
        String text = "ASSIGNMENT_TEXT";
        int priority = 3;
        long effort = 9004493;
        String description = "DESCRIPTION_3432";
        String parentTaskId = "DEF_PARENT_ID_98";
        Boolean isInReduced = false;

        Task taTask = taGraderTodoList.createTask(startDate, endDate, text, priority, effort, description, parentTaskId, isInReduced);
        taGraderTodoList.setTaskId(taTask, id);

        CurrentStorage.get().storeTaTodoList(taGraderTodoList, course);
    }

    //Student Todos      
    public void setUpStudent(Project course) {
        TaskList studentTodoList = new TaskListImpl(course);

        String id = "STUDENT_TODO_55";
        CalendarDate startDate = new CalendarDate(16, 1, 2021);
        CalendarDate endDate = new CalendarDate(19, 1, 2021);
        String text = "STUDENT_TEXT";
        int priority = 3;
        long effort = 343;
        String description = "DESCRIPTION_5455553";
        String parentTaskId = "DEF_PARENT_ID_31";
        Boolean isInReduced = false;

        Task studentTask = studentTodoList.createTask(startDate, endDate, text, priority, effort, description, parentTaskId, isInReduced);
        studentTodoList.setTaskId(studentTask, id);

        CurrentStorage.get().storeStudentTodo(studentTodoList, course);
    }

    //Resources  
    public void setUpResources(Project course) {
        ResourcesList resourcesList = new ResourcesListImpl(course);

        String path = "/path/to/nearest/file/look.txt";
        boolean isInternetShortcut = false;
        boolean isProjectFile = true;

        resourcesList.addResource(path, isInternetShortcut, isProjectFile);

        CurrentStorage.get().storeResourcesList(resourcesList, course);
    }

    private String dateToString(CalendarDate date) {
        if (date != null) {
            return date.toString().replace('/', '-');
        }
        return null;
    }
    

    @Test public void testCourseData() {

        String id = "COURSE_ID_1_TEST_ONLY";
        String title = "TEST_COURSE_TITLE_33";
        CalendarDate startDate = new CalendarDate(3, 11, 2020);
        CalendarDate endDate = new CalendarDate(18, 2, 2021);
        CalendarDate finalDate = new CalendarDate(16, 2, 2021);

        assertEquals(id, testCourse.getString("id"));
        assertEquals(title, testCourse.getString("title"));

        assertEquals(dateToString(startDate), testCourse.getString("startDate"));
        assertEquals(dateToString(endDate), testCourse.getString("endDate"));
        assertEquals(dateToString(finalDate), testCourse.getString("finalDate"));

    }

    @Test public void testDefaultTask() {

    }

    @Test public void testLectures() {

    }

    @Test public void testAssignments() {

    }

    @Test public void testInstTodo() {

    }

    @Test public void testTaTodo() {

    }

    @Test public void testStudentTodo() {

    }

    @Test public void testResources() {

    }


    
}
