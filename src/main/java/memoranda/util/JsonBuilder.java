package main.java.memoranda.util;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import main.java.memoranda.ProjectManager; 
import java.util.Vector;
import main.java.memoranda.Project;
import main.java.memoranda.ResourcesList;
import main.java.memoranda.Resource;

public class JsonBuilder {

    public JsonBuilder() {

    }

    public void exportMemoranda() {
        JSONObject root = new JSONObject();
        addCourses(root);

    }
    

    private void addCourses(JSONObject root) {

        Vector<Project> allCourses = ProjectManager.getAllProjects();

        JSONArray courses = new JSONArray();
        
        for (Project course : allCourses) {

            JSONObject courseObject = new JSONObject();

            addCourseData(courseObject, course);
            addDefaultTaskArray(courseObject, course); 
            addLecturesArray(courseObject, course); 
            addLAssignmentsArray(courseObject, course);
            ddInstructorTodoArray(courseObject, course);
            addTaGraderTodoArray(courseObject, course); 
            addStudentTodoArray(courseObject, course); 
            addResourcesArray(courseObject, course);

            courses.add(courseObject);
        }

        root.put("courses", courses);
    }

    private void addCourseData(JSONObject courseObject, Project course) {

        courseObject.put("courseId", course.getID());
        courseObject.put("startDate", course.getStartDate());
        courseObject.put("endDate", course.getEndDate());
        courseObject.put("finalDate", course.getFinalDate());
        courseObject.put("title", course.getTitle());
        courseObject.put("description", course.getDescription());
    }

    private void addDefaultTaskArray(JSONObject courseObject, Project course) {
        
        JSONArray taskArray = new JSONArray();

        TaskList taskList = CurrentStorage.get().openTaskList(course);

        Vector<Task> tasks = taskList.getTopLevelTasks();

        for (Task t : tasks) {
            addDefaultTask(taskArray, t);
        }

        courseObject.put("defaultTasks", taskArray);
    }

    private void addDefaultTask(JSONArray taskArray, Task task) {

        JSONObject singleTask = new JSONObject();

        singleTask.put("text", task.getText());
        singleTask.put("date", task.getStartDate());
        singleTask.put("repeating", false);
        singleTask.put("frequency", null);
        singleTask.put("type", task.getType());
        singleTask.put("endRepeatingDate", task.getEndDate());

        taskArray.add(singleTask);
    }

    private void addLecturesArray(JSONObject courseObject, Project course) {
        
        JSONArray lectureArray = new JSONArray();

        LectureList lectureList = CurrentStorage.get().openLectureList(course);

        Vector lectures = lectureList.getAllLectures();

        for (Lecture l : lectures) {
            addLectures(lectureArray, l);
        }

        courseObject.put("lectures", lectureArray);
    }

    private void addLectures(JSONArray lecturesArray, Lecture lecture) {
        
        JSONObject lectureObject = new JSONObject();

        lectureObject.put("topic", lecture.getTopic());
        lectureObject.put("repeat", false);
        lectureObject.put("startDate", lecture.getDate());
        lectureObject.put("startTime", lecture.getStartTimeString());
        lectureObject.put("endTime", lecture.getEndTimeString());

        lecturesArray.add(lectureObject);
    }

    private void addAssignmentsArray(JSONObject courseObject, Project course) {

        JSONArray assignmentsArray = new JSONArray();

        TaskList assignList = CurrentStorage.get().openAssignList()

        Vector<Task> assignments = assignList.getTopLevelTasks();

        for (Task a : assignments) {
            addTask(assignmentsArray, a);
        }

        courseObject.put("assignments", assignmentsArray);
    }


    private void addInstructorTodoArray(JSONObject courseObject, Project course) {
        
        JSONArray instTodo = new JSONArray();

        TaskList instList = CurrentStorage.get.openInstrTodoList();

        Vector<Task> todos = instList.getTopLevelTasks();

        for (Task t : todos) {
            addTask(instTodo, t);
        }

        courseObject.put("instructorTodos", instTodo);
    }

    private void addTaGraderTodoArray(JSONObject courseObject, Project course) {

        //This is on hold for US 170 / TA graders
    }

    private void addStudentTodoArray(JSONObject courseObject, Project course) {

        JSONArray studentTodo = new JSONArray();

        TaskList studentList = CurrentStorage.get.openStudentTodo(course);

        Vector<Task> todos = studentList.getTopLevelTasks();

        for (Task t : todos) {
            addTask(studentTodo, t);
        }

        courseObject.put("studentTodos", studentTodo);
        
    }

    private void addResourcesArray(JSONObject courseObject, Project course) {
        
        JSONArray resourceArray = new JSONArray();

        ResourcesList resourceList = CurrentStorage.get().openResourcesList(course);

        Vector<Resource> resources = resourceList.getAllResources();

        for (Resource r : resources) {
            addResource(resourceArray, r)
        }

        courseObject.put("resources", resourceArray);
    }

    private void addResources(JSONArray resourceArray, Resource resource) {
        
        JSONObject resourceObject = new JSONObject();

        resourceObject.put("path", resource.getPath());
        resourceObject.put("isInternetShortcut", resource.isInetShortcut());
        resourceObject.put("isProjectFile", resource.isProjectFile());

        resourceArray.add(resourceObject);
    }

    private void addTask(JSONArray taskArray, Task task) {

        JSONObject singleTask = new JSONObject();

        singleTask.put("id", task.getID());
        singleTask.put("startDate", task.getStartDate());
        singleTask.put("endDate", task.getEndDate());
        singleTask.put("text", task.getText());
        singleTask.put("priority", task.getPriority());
        singleTask.put("effort", task.getEffort());
        singleTask.put("description", task.getDescription());
        singleTask.put("parentId", task.getParentId());
        singleTask.put("isInReduced", task.getIsInReduced());

        taskArray.add(singleTask);
    }

    
}
