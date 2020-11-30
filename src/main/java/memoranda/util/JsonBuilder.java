package main.java.memoranda.util;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import main.java.memoranda.ProjectManager; 
import java.util.Vector;
import main.java.memoranda.Project;

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
            addTaskArray(courseObject, course); 
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

    private void addTaskArray(JSONObject courseObject, Project course) {
        
        JSONArray taskArray = new JSONArray();

        TaskList taskList = CurrentStorage.get().openTaskList(course);

        Vector<Task> tasks = taskList.getTopLevelTasks();

        for (Task t : tasks) {
            addTask(taskArray, t);
        }

        courseObject.put("tasks", taskArray);
    }

    private void addTask(JSONArray taskArray, Task task) {

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

    /**
     * topic": "Spaceships",
        "startDate": "2020-01-12T09:00:00.000-07:00",
        "endDate": "2020-01-12T10:00:00.000-07:00",
        "repeat": true
     */
    private void addLectures(JSONArray lecturesArray, Lecture lecture) {
        
        JSONObject lectureObject = new JSONObject();

        lectureObject.put("topic", lecture.getTopic());
        lectureObject.put("startDate", lecture.getDate());
        lectureObject.put("endDate", lecture.get
        lectureObject.put("repeat",


        lectureArray.add(lectureObject);
    }

    private void addLAssignmentsArray(JSONObject courseObject, Project course) {
        /**
         * TODO: 
         */
    }

    private void addAssignments(JSONArray lecturesArray) {
        /**
         * TODO:
         */
    }

    private void addInstructorTodoArray(JSONObject courseObject, Project course) {
        /**
         * TODO: 
         */
    }

    private void addInstructorTodo(JSONArray lecturesArray) {
        /**
         * TODO:
         */
    }

    private void addTaGraderTodoArray(JSONObject courseObject, Project course) {
        /**
         * TODO: 
         */
    }

    private void addTaGraderTodo(JSONArray lecturesArray) {
        /**
         * TODO:
         */
    }

    private void addStudentTodoArray(JSONObject courseObject, Project course) {
        /**
         * TODO: 
         */
    }

    private void addStudentTodo(JSONArray lecturesArray) {
        /**
         * TODO:
         */
    }

    private void addResourcesArray(JSONObject courseObject, Project course) {
        /**
         * TODO: 
         */
    }

    private void addResources(JSONArray lecturesArray) {
        /**
         * TODO:
         */
    }

    
}
