package memoranda.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import memoranda.Lecture;
import memoranda.LectureList;
import memoranda.Project;
import memoranda.ProjectManager; 
import memoranda.ResourcesList;
import memoranda.Resource;
import memoranda.Task;
import memoranda.TaskList;
import memoranda.ui.ExceptionDialog;
import memoranda.date.CalendarDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonBuilder {

    private String outputPath;

    public JsonBuilder() {
        outputPath = Util.getEnvDir() + "/Data.json";
    }

    public void exportMemoranda() {

        JSONObject root = new JSONObject();
        addCourses(root);

        try {
            FileWriter file = new FileWriter(outputPath);
            file.write(root.toJSONString());
            file.close();
            System.out.println("[DEBUG] JSON file written");
        } catch (IOException io) {
            new ExceptionDialog(io, "Could not export memoranda to JSON file.", "");
        }
    }

    private void addCourses(JSONObject root) {

        Collection<Project> allCourses = ProjectManager.getAllProjects();

        JSONArray courses = new JSONArray();
        
        for (Project course : allCourses) {

            JSONObject courseObject = new JSONObject();

            addCourseData(courseObject, course);
            addDefaultTaskArray(courseObject, course); 
            addLecturesArray(courseObject, course); 
            addAssignmentsArray(courseObject, course);
            addInstructorTodoArray(courseObject, course);
            addTaGraderTodoArray(courseObject, course); 
            addStudentTodoArray(courseObject, course); 
            addResourcesArray(courseObject, course);

            courses.add(courseObject);
        }

        root.put("courses", courses);
    }

    private void addCourseData(JSONObject courseObject, Project course) {

        courseObject.put("courseId", course.getID());
        courseObject.put("startDate", dateToString(course.getStartDate()));
        courseObject.put("endDate", dateToString(course.getEndDate()));
        courseObject.put("finalDate", dateToString(course.getFinalDate()));
        courseObject.put("title", course.getTitle());
        courseObject.put("description", course.getDescription());
    }

    private void addDefaultTaskArray(JSONObject courseObject, Project course) {
        
        JSONArray taskArray = new JSONArray();

        TaskList taskList = CurrentStorage.get().openTaskList(course);

        Collection<Task> tasks = taskList.getTopLevelTasks();

        for (Task t : tasks) {
            addDefaultTask(taskArray, t);
        }

        courseObject.put("defaultTasks", taskArray);
    }

    private void addDefaultTask(JSONArray taskArray, Task task) {

        JSONObject singleTask = new JSONObject();

        singleTask.put("text", task.getText());
        singleTask.put("name", task.getName());
        singleTask.put("date", dateToString(task.getStartDate()));
        singleTask.put("repeating", false);
        singleTask.put("frequency", null);
        singleTask.put("type", task.getType());
        singleTask.put("endRepeatingDate", dateToString(task.getEndDate()));

        taskArray.add(singleTask);
    }

    private void addLecturesArray(JSONObject courseObject, Project course) {
        
        JSONArray lectureArray = new JSONArray();

        LectureList lectureList = CurrentStorage.get().openLectureList(course);

        Collection<Lecture> lectures = lectureList.getAllLectures();

        for (Lecture l : lectures) {
            addLectures(lectureArray, l);
        }

        courseObject.put("lectures", lectureArray);
    }

    private void addLectures(JSONArray lecturesArray, Lecture lecture) {
        
        JSONObject lectureObject = new JSONObject();

        lectureObject.put("topic", lecture.getTopic());
        lectureObject.put("repeat", false);
        lectureObject.put("startDate", dateToString(lecture.getDate()));
        lectureObject.put("startTime", lecture.getStartTimeString());
        lectureObject.put("endTime", lecture.getEndTimeString());

        lecturesArray.add(lectureObject);
    }

    private void addAssignmentsArray(JSONObject courseObject, Project course) {

        JSONArray assignmentsArray = new JSONArray();

        TaskList assignList = CurrentStorage.get().openAssignList(course);

        Collection<Task> assignments = assignList.getTopLevelTasks();

        for (Task a : assignments) {
            addTask(assignmentsArray, a);
        }

        courseObject.put("assignments", assignmentsArray);
    }


    private void addInstructorTodoArray(JSONObject courseObject, Project course) {
        
        JSONArray instTodo = new JSONArray();

        TaskList instList = CurrentStorage.get().openInstrTodoList(course);

        Collection<Task> todos = instList.getTopLevelTasks();

        for (Task t : todos) {
            addTask(instTodo, t);
        }

        courseObject.put("instructorTodos", instTodo);
    }

    private void addTaGraderTodoArray(JSONObject courseObject, Project course) {

        JSONArray taGradeTodo = new JSONArray();

        TaskList taList = CurrentStorage.get().openTaTodoList(course);

        Collection<Task> todos = taList.getTopLevelTasks();

        for (Task t : todos) {
            addTask(taGradeTodo, t);
        }

        courseObject.put("taGraderTodos", taGradeTodo);
    }

    private void addStudentTodoArray(JSONObject courseObject, Project course) {

        JSONArray studentTodo = new JSONArray();

        TaskList studentList = CurrentStorage.get().openStudentTodo(course);

        Collection<Task> todos = studentList.getTopLevelTasks();

        for (Task t : todos) {
            addTask(studentTodo, t);
        }

        courseObject.put("studentTodos", studentTodo);
        
    }

    private void addResourcesArray(JSONObject courseObject, Project course) {
        
        JSONArray resourceArray = new JSONArray();

        ResourcesList resourceList = CurrentStorage.get().openResourcesList(course);

        Collection<Resource> resources = resourceList.getAllResources();

        for (Resource r : resources) {
            addResource(resourceArray, r);
        }

        courseObject.put("resources", resourceArray);
    }

    private void addResource(JSONArray resourceArray, Resource resource) {
        
        JSONObject resourceObject = new JSONObject();

        resourceObject.put("path", resource.getPath());
        resourceObject.put("isInternetShortcut", resource.isInetShortcut());
        resourceObject.put("isProjectFile", resource.isProjectFile());

        resourceArray.add(resourceObject);
    }

    private void addTask(JSONArray taskArray, Task task) {

        JSONObject singleTask = new JSONObject();

        singleTask.put("id", task.getID());
        singleTask.put("startDate", dateToString(task.getStartDate()));
        singleTask.put("endDate", dateToString(task.getEndDate()));
        singleTask.put("text", task.getText());
        singleTask.put("priority", task.getPriority());
        singleTask.put("effort", task.getEffort());
        singleTask.put("description", task.getDescription());
        singleTask.put("parentId", task.getParentId());
        singleTask.put("isInReduced", task.getIsInReduced());

        taskArray.add(singleTask);
    }

    private String dateToString(CalendarDate date) {
        if (date != null) {
            return date.toString().replace('/', '-');
        }
        return null;
    }

}
