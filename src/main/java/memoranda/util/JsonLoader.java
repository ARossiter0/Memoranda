package memoranda.util;

import main.java.memoranda.date.CalendarDate;
import org.apache.xpath.operations.Bool;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import main.java.memoranda.Project;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.ResourcesListImpl;
import main.java.memoranda.TaskList;
import main.java.memoranda.TaskListImpl;

import main.java.memoranda.date.CalendarDate;

import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Storage;

public class JsonLoader {

    public void loadFromJSON(){

        String path = main.java.memoranda.util.Util.getEnvDir() + "/Data.json";
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(path))
        {
            //Read JSON file
            JSONObject programObject = (JSONObject) jsonParser.parse(reader);

            // TODO Load Context

            // Load Courses
            JSONArray courses = (JSONArray) programObject.get("courses");
            for(Object course : courses){
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

    private void loadCourse(JSONObject course){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH);

        String id = (String) course.get("courseId");

        if(ProjectManager.getProject(id) != null){
            return;
        }

        String title = (String) course.get("title");
        CalendarDate startDate = null;
        CalendarDate endDate = null;
        CalendarDate finalDate = null;
        try {
            startDate = new CalendarDate(df.parse((String) course.get("startDate")));
            endDate = new CalendarDate(df.parse((String) course.get("endDate")));
            finalDate = new CalendarDate(df.parse((String) course.get("finalDate")));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


        Project courseProject = ProjectManager.createProject(id, title, startDate, endDate, finalDate);
        Storage storage = CurrentStorage.get();


        // TODO: Load Free Days

        // TODO: Load Holidays

        // TODO: Load Breaks

        // Load Lectures

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
            // TODO: Create method in FileStorage.java for storing TA/Grader todo lists.
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

        if(resources != null) {
            for (Object resource : resources) {
                loadResource((JSONObject) resource, courseProject);
            }
            storage.storeResourcesList(resourcesList, courseProject);
        }

    }

    private void loadResource(JSONObject resource, main.java.memoranda.Project courseProject) {
        // Pull vars from JSON
        String path = (String) resource.get("path");
        boolean isInternetShortcut = (Boolean) resource.get("isInternetShortcut");
        boolean isProjectFile = (Boolean) resource.get("isProjectFile");

        // Get resources list for project
        ResourcesList resourcesList = CurrentStorage.get().openResourcesList(courseProject);

        // Add resource to resource list
        resourcesList.addResource(path, isInternetShortcut, isProjectFile);
    }

    private void loadTask(JSONObject taskJSON, TaskList taskList){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH);

        // Pull vars from JSON
        String id = (String) taskJSON.get("id"); // TODO Need to create method to verify id format, and another to assign ID to task.

        //CalendarDate startDate = (CalendarDate) taskJSON.get("startDate");
        CalendarDate startDate = null;
        CalendarDate endDate = null;

        try {
            startDate = new CalendarDate(df.parse((String) taskJSON.get("startDate")));
            endDate = new CalendarDate(df.parse((String) taskJSON.get("endDate")));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        String text = (String) taskJSON.get("text");
        int priority = ((Long) taskJSON.get("priority")).intValue();
        long effort = (Long) taskJSON.get("effort");
        String description = (String) taskJSON.get("description");
        String parentTaskId = (String) taskJSON.get("parentId");
        boolean isInReduced = (Boolean) taskJSON.get("isInReduced");

        // Create the new task in the tasklist
        main.java.memoranda.Task task = taskList.createTask(startDate, endDate, text, priority, effort, description, parentTaskId, isInReduced);

        // TODO: Create a public verifyId method in Util, if returns false, throw exception?

        // Set the id of the task
        taskList.setTaskId(task, id);
    }






}
