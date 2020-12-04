package memoranda.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import memoranda.Lecture;
import memoranda.LectureList;
import memoranda.LectureListImpl;
import memoranda.Project;
import memoranda.ProjectManager;
import memoranda.ResourcesList;
import memoranda.ResourcesListImpl;
import memoranda.Task;
import memoranda.TaskList;
import memoranda.TaskListImpl;
import memoranda.date.CalendarDate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonLoader {

    /**
     * Load from the Data.json file in the .memoranda folder.
     */
    public void loadFromJson() {

        String path = memoranda.util.Util.getEnvDir() + "/Data.json";
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(path)) {
            //Read JSON file
            JSONObject programObject = (JSONObject) jsonParser.parse(reader);

            // TODO Load Context

            // Load Courses
            JSONArray courses = (JSONArray) programObject.get("courses");
            //System.out.println("[DEBUG]\n\n courses length : " + courses.size() + " \n\n");
            for (Object course : courses) {
                loadCourse((JSONObject) course);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load a course from the Data.json file.
     *
     * @param course the JSON object that represents the course.
     */
    private void loadCourse(JSONObject course) {

        //DateFormat df = new SimpleDateFormat("dd-MM-yyyy",
                //Locale.ENGLISH);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        String id = (String) course.get("courseId");
        String title = (String) course.get("title");
        System.out.println("[DEBUG] Load course " + title + " from Data.json");
        CalendarDate startDate = parseDate((String) course.get(
                "startDate"));
        CalendarDate endDate = parseDate((String) course.get(
                "endDate"));
        CalendarDate finalDate = parseDate((String) course.get(
                "finalDate"));

        if (id == null || title == null || startDate == null || endDate == null || finalDate == null){
            System.out.println("[DEBUG] Course could not be loaded!");
            return;
        }

        Project courseProject;

        if (ProjectManager.getProject(id) != null) {
            System.out.println("[DEBUG] Project " + id + " already exists!");
            courseProject = ProjectManager.getProject(id);
        } else {
            courseProject = ProjectManager.createProject(id, title,
                    startDate, endDate, finalDate);
        }
        Storage storage = CurrentStorage.get();

        // Load Default Tasks (Free Days, Holidays, Breaks)
        JSONArray defaultTasks = (JSONArray) course.get("defaultTasks");
        TaskList defaultTasksList = new TaskListImpl(courseProject);

        if (defaultTasks != null) {
            for (Object assignment : defaultTasks) {
                loadDefaultTask((JSONObject) assignment, defaultTasksList);
            }
            storage.storeTaskList(defaultTasksList, courseProject);
        }

        // Load Lectures
        JSONArray lectures = (JSONArray) course.get("lectures");
        LectureList lectureList = new LectureListImpl(courseProject);

        if (lectures != null) {
            for (Object lecture : lectures) {
                loadLecture((JSONObject) lecture, lectureList);
            }
            storage.storeLectureList(lectureList, courseProject);
        }

        // Load Assignments
        JSONArray assignments = (JSONArray) course.get("assignments");
        TaskList assignmentList = new TaskListImpl(courseProject);

        if (assignments != null) {
            for (Object assignment : assignments) {
                loadTask((JSONObject) assignment, assignmentList);
            }
            storage.storeAssignList(assignmentList, courseProject);
        }

        // Load Instructor Todos
        JSONArray instructorTodos = (JSONArray) course.get("instructorTodos");
        TaskList instrTodoList = new TaskListImpl(courseProject);

        if (instructorTodos != null) {
            for (Object instructorTodo : instructorTodos) {
                loadTask((JSONObject) instructorTodo, instrTodoList);
            }
            storage.storeInstrTodoList(instrTodoList, courseProject);
        }

        // Load TA/Grader Todos
        JSONArray taGraderTodos = (JSONArray) course.get("taGraderTodos");
        TaskList taGraderTodoList = new TaskListImpl(courseProject);

        if (taGraderTodos != null) {
            for (Object taGraderTodo : taGraderTodos) {
                loadTask((JSONObject) taGraderTodo, taGraderTodoList);
            }
            storage.storeTaTodoList(taGraderTodoList, courseProject);
            // TODO: Create method in FileStorage.java for storing TA/Grader
            //  todo lists.
        }

        // Load Student Todos
        JSONArray studentTodos = (JSONArray) course.get("studentTodos");
        TaskList studentTodoList = new TaskListImpl(courseProject);

        if (studentTodos != null) {
            for (Object studentTodo : studentTodos) {
                loadTask((JSONObject) studentTodo, studentTodoList);
            }
            storage.storeStudentTodo(studentTodoList, courseProject);
        }

        // TODO: Load Sprints

        // Load Resources
        JSONArray resources = (JSONArray) course.get("resources");
        ResourcesList resourcesList = new ResourcesListImpl(courseProject);

        if (resources != null) {
            for (Object resource : resources) {
                loadResource((JSONObject) resource, resourcesList,
                        courseProject);
            }
            storage.storeResourcesList(resourcesList, courseProject);
        }
    }

    /**
     * Load a lecture from Data.json.
     *
     * @param lectureJson
     * the JSON object for the lecture
     * @param lectureList the lecture list
     */
    private void loadLecture(JSONObject lectureJson,
              LectureList lectureList) {
        System.out.println("[DEBUG] Load lecture " + lectureJson
                + " from "
                + "Data.json");

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss.SSSX",
                Locale.ENGLISH);

        // Pull vars from JSON
        final String topic = (String) lectureJson
                .get("topic");
        boolean repeat = (Boolean) lectureJson
                .get("repeat");
        CalendarDate date = parseDate((String) lectureJson
                .get("startDate"));
        Calendar startTime = parseCalendar(date, (String) lectureJson
                .get("startTime"));
        Calendar endTime = parseCalendar(date, (String) lectureJson
                .get("endTime"));


        // Create the new lecture in the lecture list
        Lecture lecture = lectureList.createLecture(date, startTime, endTime,
                topic);


        // Set the id of the lecture
    }

    /**
     * Load a resource from Data.json.
     *
     * @param resource      the JSON object for the resource to load
     * @param resourcesList the resources list
     * @param courseProject the course
     */
    private void loadResource(JSONObject resource,
                              ResourcesList resourcesList,
                              memoranda.Project courseProject) {
        // Pull vars from JSON
        String path = (String) resource.get("path");
        System.out.println("Load resource " + path + " from Data.json");
        boolean isInternetShortcut = (Boolean) resource.get(
                "isInternetShortcut");
        boolean isProjectFile = (Boolean) resource.get("isProjectFile");

        // Add resource to resource list
        resourcesList.addResource(path, isInternetShortcut, isProjectFile);
    }

    /**
     * Load a task (assignment, to do list) from Data.json.
     *
     * @param taskJson
     * the JSON object for the task
     * @param taskList the task list
     */
    private void loadTask(JSONObject taskJson,
                TaskList taskList) {

        // Pull vars from JSON
        String id = (String) taskJson
                .get("id"); // TODO Need to create
        CalendarDate startDate = parseDate((String) taskJson
                .get("startDate"));
        CalendarDate endDate = parseDate((String) taskJson
                .get("endDate"));
        String text = (String) taskJson
                .get("text");
        int priority = ((Long) taskJson
                .get("priority")).intValue();
        long effort = (Long) taskJson
                .get("effort");
        String description = (String) taskJson
                .get("description");
        String parentTaskId = (String) taskJson
                .get("parentId");
        boolean isInReduced = (Boolean) taskJson
                .get("isInReduced");

        if (id == null){
            return;
        }

        // Create the new task in the tasklist
        Task task = taskList.createTask(startDate, endDate, text, priority,
                effort, description, parentTaskId, isInReduced);

        // TODO: Create a public verifyId method in Util, if returns false,
        //  throw exception?

        // Set the id of the task
        taskList.setTaskId(task, id);
    }

    /**
     * Load a task (free day, holiday, break) from Data.json.
     *
     * @param taskJson
     * the JSON object for the task
     * @param taskList the task list
     */
    private void loadDefaultTask(JSONObject taskJson,
                TaskList taskList) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss.SSSX",
                Locale.ENGLISH);

        // Pull vars from JSON
        String id = memoranda.util.Util.generateId();
        String name = (String) taskJson
                .get("text");
        String type = (String) taskJson
                .get("type");
        CalendarDate date = parseDate((String) taskJson
                .get("date"));


        if (id == null){
            return;
        }

        // Create the new task in the tasklist
        Task task = taskList.createSingleEventTask(name, date, type);
        taskList.setTaskId(task, id);
    }

    private CalendarDate parseDate(String dateStr){

        if (dateStr != null) {
            String[] dateArr = dateStr.split("-");
            String day = dateArr[0];
            String month = dateArr[1];
            String year = dateArr[2];

            if (day != null || month != null && year != null) {
                try {
                    int dayInt = Integer.parseInt(day);
                    int monthInt = Integer.parseInt(month);
                    int yearInt = Integer.parseInt(year);

                    return new CalendarDate(dayInt, monthInt, yearInt);
                } catch (ArithmeticException e) {
                    System.err.println("Cannot parse date into ints!");
                }
            }
        }

        return null;
    }

    private Calendar parseCalendar(CalendarDate calendarDate, String time){
        int day = calendarDate.getDay();
        int month = calendarDate.getMonth();
        int year = calendarDate.getYear();

        String[] hourMinArr = time.split(":");
        String[] morningNightArr = hourMinArr[1].split(" ");
        boolean isMorning = morningNightArr[1].equals("AM");

        try {
            int hour = Integer.parseInt(hourMinArr[0]);
            if (!isMorning){
                hour += 12;
                hour %= 24;
            }
            int min = Integer.parseInt(morningNightArr[0]);

            Calendar calendar = new GregorianCalendar();
            calendar.set(day, month, year, hour, min);
            return calendar;
        } catch (ArithmeticException e){
            System.err.println("Cannot parse time into ints!");
        }

        return new GregorianCalendar();
    }

}
