package memoranda.util;

import main.java.memoranda.date.CalendarDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.Project;
import main.java.memoranda.ProjectManager;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.ResourcesListImpl;
import main.java.memoranda.TaskList;
import main.java.memoranda.TaskListImpl;

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
        String id = (String) course.get("courseId");
        String title = (String) course.get("title");
        main.java.memoranda.date.CalendarDate startDate = (CalendarDate) course.get("startDate");
        main.java.memoranda.date.CalendarDate endDate = (CalendarDate) course.get("endDate");
        main.java.memoranda.date.CalendarDate finalDate = (CalendarDate) course.get("finalDate");

        Project courseProject = ProjectManager.createProject(id, title, startDate, endDate, finalDate);
        Storage storage = CurrentStorage.get();


        // TODO: Load Free Days

        // TODO: Load Holidays

        // TODO: Load Breaks

        // Load Lectures

        // Load Assignments
        JSONArray assignments = (JSONArray) course.get("assignments");
        TaskList assignmentList = new TaskListImpl(courseProject);
        for(Object assignment : assignments){
            loadAssignment((JSONObject) assignment, assignmentList);
        }
        storage.storeAssignList(assignmentList, courseProject);

        // Load Instructor Todos
        JSONArray instructorTodos = (JSONArray) course.get("instructorTodos");
        TaskList instrTodoList = new TaskListImpl(courseProject);
        for(Object instructorTodo : instructorTodos){
            loadInstructorTodo((JSONObject) instructorTodo, instrTodoList);
        }
        storage.storeInstrTodoList(instrTodoList, courseProject);

        // Load TA/Grader Todos
        JSONArray taGraderTodos = (JSONArray) course.get("taGraderTodos");
        TaskList taGraderTodoList = new TaskListImpl(courseProject);
        for(Object taGraderTodo : taGraderTodos){
            loadTAGraderTodo((JSONObject) taGraderTodo, taGraderTodoList);
        }
        // TODO: Create method in FileStorage.java for storing TA/Grader todo lists.

        // Load Student Todos
        JSONArray studentTodos = (JSONArray) course.get("studentTodos");
        TaskList studentTodoList = new TaskListImpl(courseProject);
        for(Object studentTodo : studentTodos){
            loadStudentTodo((JSONObject) studentTodo, studentTodoList);
        }
        storage.storeStudentTodo(studentTodoList, courseProject);

        // TODO: Load Sprints

        // Load Resources
        JSONArray resources = (JSONArray) course.get("resources");
        ResourcesList resourcesList = new ResourcesListImpl(courseProject);
        for(Object resource : resources){
            loadResource((JSONObject) resource, courseProject);
        }
        storage.storeResourcesList(resourcesList, courseProject);

    }

    private void loadResource(JSONObject resource, main.java.memoranda.Project courseProject) {
    }

    private void loadStudentTodo(JSONObject studentTodo, TaskList studentTodoList) {
    }

    private void loadTAGraderTodo(JSONObject taGraderTodo, TaskList taGraderTodoList) {
    }

    private void loadInstructorTodo(JSONObject instructorTodo, TaskList instrTodoList) {
    }

    private void loadAssignment(JSONObject assignment, TaskList assignmentList){
        // Pull vars from JSON
        String id; // TODO Need to create method to verify id format, and another to assign ID to task.
        CalendarDate startDate;
        CalendarDate endDate;
        String text;
        int priority;
        long effort;
        String description;
        String parentTaskId;

        // TODO: Create a new task using the task list.
        //    Task task = Task createTask(
        //          CalendarDate startDate,
        //          CalendarDate endDate,
        //          String text,
        //          int priority,
        //          long effort,
        //          String description,
        //          String parentTaskId);


        // TODO: Create a public verifyId method in Util, if returns false, throw exception?

        // TODO: Create a public setId method in Task/TaskImpl
        // task.setId(String id);
    }






}
