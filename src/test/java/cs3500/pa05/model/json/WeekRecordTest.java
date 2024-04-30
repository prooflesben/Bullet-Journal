package cs3500.pa05.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.AmPm;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.Weekday;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * testing class for the WeekRecord record
 */
class WeekRecordTest {

  /**
   * ensuring that the initialization of the WeekRecord works properly
   */
  @Test
  public void testInitialization() {
    List<TaskJson> tjl = new ArrayList<>();
    TaskJson newTask = new TaskJson("name", "description", Weekday.SUNDAY, false);
    TaskJson extraTask = new TaskJson("name", "description", Weekday.SUNDAY, true);
    tjl.add(newTask);
    tjl.add(extraTask);

    List<EventJson> ejl = new ArrayList<>();
    EventJson onlyEvent = new EventJson("event", "description", Weekday.SUNDAY,
        new TimeJson(1, 1, AmPm.PM),
        new TimeJson(2, 3, AmPm.AM));
    ejl.add(onlyEvent);

    WeekRecord wr = new WeekRecord("Week Name", tjl, ejl, 2, 2, "notes", "pw");
    //checking that the WeekRecord has the directly correct values
    assertEquals("Week Name", wr.name());

    //we do this because it returns a different object than original
    assertEquals(2, wr.maxEvents());
    assertEquals(2, wr.maxTasks());
    assertEquals("notes", wr.notesAndQuotes());
    assertEquals("pw", wr.password());

    assertTrue(wr.sameExceptCompleteness(newTask, extraTask));
    //since 1 of the 2 tasks are complete, it should be 50%.
    assertEquals(0.5, wr.percentOfTasksComplete());

    //adding something at the start of the task list
    wr.addTask(newTask.toTask());

    //if it works properly, there should only be one element, as it removes only the first task
    assertEquals(2, wr.tasks().size());

    //adding the same event to the event list
    wr.addEvent(onlyEvent.toEvent());

    //if it works properly, there should only be one element
    assertEquals(1, wr.events().size());

    List<Task> taskList = wr.taskList();
    assertEquals(2, taskList.size());
    assertEquals("name", taskList.get(0).getName());
    List<Event> eventList = wr.eventList();
    assertEquals(1, eventList.size());
    assertEquals("event", eventList.get(0).getName());

    WeekRecord record = new WeekRecord("name", new ArrayList<>(),
        new ArrayList<>(), 4, 4, "", "");

    assertEquals(0.0, record.percentOfTasksComplete());

    record.addEvent(new Event("name", "", Weekday.MONDAY,
        new Time(3, 30, AmPm.AM), 20));

    assertEquals(1, record.events().size());
  }
}