
import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.BeforeClass;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import memoranda.LectureList;
import memoranda.LectureListImpl;
import memoranda.Project;
import memoranda.ProjectManager;
import memoranda.ResourcesList;
import memoranda.ResourcesListImpl;
import memoranda.Task;
import memoranda.TaskList;
import memoranda.TaskListImpl;
import memoranda.util.CurrentStorage;
import memoranda.util.JsonBuilder;
import memoranda.util.Util;
import memoranda.date.CalendarDate;
import memoranda.date.CurrentDate;

//ref : https://cliftonlabs.github.io/json-simple/target/apidocs/index.html

public class US188_Tests {

    public static JSONObject testCourse;

    @BeforeClass
    public static void setUp() throws IOException, ParseException {

        String path = Util.getEnvDir() + "/Data.json";
        String id = "COURSE_ID_1_TEST_ONLY";
        String title = "TEST_COURSE_TITLE_33";
        CalendarDate startDate = new CalendarDate(3, 10, 2020);
        CalendarDate endDate = new CalendarDate(18, 2, 2021);
        CalendarDate finalDate = new CalendarDate(16, 2, 2021);
        Project course = ProjectManager.createProject(id, title, startDate, endDate, finalDate);

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

        JSONArray coursesArray = (JSONArray) root.get("courses");
        
        for (Object jsonCourse : coursesArray) {
            String courseId = "";
            try {
                courseId = ((JSONObject) jsonCourse).get("courseId").toString();
            } catch (NullPointerException n) {
                continue;
            }

            if (courseId.equals(id)) {
                testCourse = (JSONObject) jsonCourse;
            }
        }
    }

    // DefaultTask
    public static void setUpDefTask(Project course) {

        TaskList defaultTaskList = new TaskListImpl(course);

        String name = "DEFAULT_TASK_1";
        String type = "TYPE_21";
        CalendarDate taskDate = new CalendarDate(6, 11, 2020);

        defaultTaskList.createSingleEventTask(name, taskDate, type);

        CurrentStorage.get().storeTaskList(defaultTaskList, course);
    }

    // Lectures
    public static void setUpLectureTask(Project course) {
        LectureList lectureList = new LectureListImpl(course);

        CalendarDate lectureDate = new CalendarDate(9, 12, 2020);
        Calendar startTimeCal = new GregorianCalendar(2020, 12, 9, 5, 30);
        Calendar endTimeCal = new GregorianCalendar(2020, 12, 9, 7, 30);
        String topic = "LECTURE_TEST_TOPIC";

        lectureList.createLecture(lectureDate, startTimeCal, endTimeCal, topic);

        CurrentStorage.get().storeLectureList(lectureList, course);
    }

    // Assignment
    public static void setUpAssignment(Project course) {
        TaskList assignmentList = new TaskListImpl(course);

        String id = "ASSIGNEMNT_ID_3434";
        CalendarDate startDate = new CalendarDate(14, 12, 2020);
        CalendarDate endDate = new CalendarDate(15, 12, 2020);
        String text = "ASSIGNMENT_TEXT";
        int priority = 3;
        long effort = 9004493;
        String description = "DESCRIPTION_3432";
        String parentTaskId = null;
        Boolean isInReduced = false;

        Task assignTask = assignmentList.createTask(startDate, endDate, text, priority, effort, description, parentTaskId, isInReduced);
        assignmentList.setTaskId(assignTask, id);
        CurrentStorage.get().storeAssignList(assignmentList, course);
    }

    //Instructor Todos
    public static void setUpInstTodo(Project course) {
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
    public static void setUpTaGraderTodo(Project course) {
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
    public static void setUpStudent(Project course) {
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
    public static void setUpResources(Project course) {
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
        CalendarDate startDate = new CalendarDate(3, 10, 2020);
        CalendarDate endDate = new CalendarDate(18, 2, 2021);
        CalendarDate finalDate = new CalendarDate(16, 2, 2021);

        assertEquals(id, testCourse.get("courseId").toString());
        assertEquals(title, testCourse.get("title").toString());

        assertEquals(dateToString(startDate), testCourse.get("startDate").toString());
        assertEquals(dateToString(endDate), testCourse.get("endDate").toString());
        assertEquals(dateToString(finalDate), testCourse.get("finalDate").toString());
    }

    @Test public void testDefaultTask() {

        String name = "DEFAULT_TASK_1";
        String type = "TYPE_21";
        CalendarDate taskDate = new CalendarDate((Date) CurrentDate.get().getDate());

        JSONArray defaultTaskArray = (JSONArray) testCourse.get("defaultTasks");
        JSONObject defaultTask = (JSONObject) defaultTaskArray.get(0);

        assertEquals(name, defaultTask.get("name").toString());
        assertEquals(type, defaultTask.get("type").toString());
        assertEquals(dateToString(taskDate), defaultTask.get("date").toString());
    }

    @Test public void testLectures() {

        CalendarDate lectureDate = new CalendarDate(9, 12, 2020);
        String startTime = "5:30 AM";
        String endTime = "7:30 AM";
        String topic = "LECTURE_TEST_TOPIC";

        JSONArray lectureArray = (JSONArray) testCourse.get("lectures");
        JSONObject testLecture = (JSONObject) lectureArray.get(0);

        assertEquals(dateToString(lectureDate), testLecture.get("startDate").toString());
        assertEquals(startTime, testLecture.get("startTime").toString());
        assertEquals(endTime, testLecture.get("endTime").toString());
        assertEquals(topic, testLecture.get("topic").toString());

    }

    @Test public void testAssignments() {

        String id = "ASSIGNEMNT_ID_3434";
        CalendarDate startDate = new CalendarDate(14, 12, 2020);
        String text = "ASSIGNMENT_TEXT";
        long effort = 9004493;
        String description = "DESCRIPTION_3432";

        JSONArray assignmentsArray = (JSONArray) testCourse.get("assignments");
        JSONObject testAssign = (JSONObject) assignmentsArray.get(0);

        assertEquals(id, testAssign.get(""));


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
