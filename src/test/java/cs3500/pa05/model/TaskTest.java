package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.json.TaskJson;
import org.junit.jupiter.api.Test;

/**
 * a test class for the Task class
 */
class TaskTest {

  /**
   * testing if tasks initialization works properly
   */
  @Test
  public void testTaskInitialization() {
    Task t = new Task("Name", "Description", Weekday.SUNDAY);

    assertEquals("Name", t.getName());
    assertEquals("Description", t.getDescription());
    assertEquals(Weekday.SUNDAY, t.getWeekday());
    assertFalse(t.isComplete());

    //altering the completed value of the tasks
    t.changeComplete();

    assertTrue(t.isComplete());

    assertEquals("""
        Name
        - SUNDAY
        - Description
        - true
        """, t.toString());

    TaskJson expectedJson = new TaskJson("Name", "Description", Weekday.SUNDAY, true);
    assertEquals(expectedJson.toString(), t.toJson().toString());

    assertDoesNotThrow(() -> new Task("Name", null, Weekday.FRIDAY));
  }

  /**
   * testing anything related to task-buddies
   */
  @Test
  public void testBuddies() {
    Task initialTask = new Task("Name", "Description", Weekday.SUNDAY);
    Task clonedTask = initialTask.cloneBuddy();

    assertEquals(initialTask.getName(), clonedTask.getName());
    assertEquals(initialTask.getDescription(), clonedTask.getDescription());

    Task taskWithClone = new Task(
        "Name2",
        "Description2",
        Weekday.FRIDAY,
        false,
        clonedTask);

    taskWithClone.changeComplete();
    assertTrue(taskWithClone.isComplete());
    assertTrue(clonedTask.isComplete());
  }
}