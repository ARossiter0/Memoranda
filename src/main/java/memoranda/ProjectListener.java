package memoranda;

/*$Id: ProjectListener.java,v 1.3 2004/01/30 12:17:41 alexeya Exp $*/
public interface ProjectListener {
	/**
	 * Execute this when a project is changed. 
	 * @param prj the project
	 * @param nl the notes associated with the project
	 * @param tl the task list associated with the project
	 * @param t2 the instructor todo list associated with the project
	 * @param rl the resources list associated with the project
	 */
  void projectChange(Project prj, NoteList nl, LectureList tl, TaskList t2, TaskList s1, TaskList s2, ResourcesList rl);
  void projectWasChanged();
}