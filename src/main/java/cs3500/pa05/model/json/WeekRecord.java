package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * To represent Week in a bujo spread.
 */
public record WeekRecord(
    @JsonProperty("name") String name,
    @JsonProperty("tasks") List<TaskJson> tasks,
    @JsonProperty("events") List<EventJson> events,
    @JsonProperty("maxEvents") int maxEvents,
    @JsonProperty("maxTasks") int maxTasks,
    @JsonProperty("notesAndQuotes") String notesAndQuotes,
    @JsonProperty("password") String password

) {

  /**
   * Adds a task to this list of taskJsons
   *
   * @param task the task to be added
   */
  public void addTask(Task task) {
    TaskJson taskJson = task.toJson();

    for (TaskJson t : tasks) {
      if (this.sameExceptCompleteness(t, taskJson)) {
        tasks.remove(t);
        tasks.add(taskJson);
        break;
      }
    }
    if (!this.tasks.contains(taskJson)) {
      this.tasks.add(taskJson);
    }
  }

  /**
   * determines if two tasks have the same name, description and weekday
   *
   * @param orig the first task to be checked
   * @param elem the second task to be checked
   * @return true if the two tasks have the exact same contents, except for completeness
   */
  public boolean sameExceptCompleteness(TaskJson orig, TaskJson elem) {
    return (orig.name().equals(elem.name()) && orig.description().equals(elem.description())
        && orig.weekday().equals(elem.weekday()) && orig.complete() != elem.complete());
  }

  /**
   * Adds an event to this list of eventJsons
   *
   * @param event the event to be added
   */
  public void addEvent(Event event) {

    EventJson eventJson = event.toJson();

    if (!this.events.contains(eventJson)) {
      this.events.add(eventJson);
    }
  }

  /**
   * Percentage of tasks complete.
   *
   * @return the int percentage of tasks complete.
   */
  public double percentOfTasksComplete() {
    double numTasksComplete = 0;
    double totalTasks = 0;

    if (tasks.size() == 0) {
      return 0;
    } else {
      totalTasks = tasks.size();
    }

    for (TaskJson taskJson : this.tasks) {
      if (taskJson.complete()) {
        numTasksComplete += 1;
      }
    }
    return (numTasksComplete / totalTasks);
  }

  /**
   * produces a list of tasks that the week contains
   *
   * @return an ArrayList of tasks for the week
   */
  public List<Task> taskList() {
    ArrayList<Task> tasks = new ArrayList<>();
    for (TaskJson task : this.tasks) {
      tasks.add(task.toTask());
    }
    return tasks;
  }


  /**
   * produces a list of events that the week contains
   *
   * @return an ArrayList of events for the week
   */
  public List<Event> eventList() {
    ArrayList<Event> event = new ArrayList<>();
    for (EventJson task : this.events) {
      event.add(task.toEvent());
    }
    return event;
  }
}
