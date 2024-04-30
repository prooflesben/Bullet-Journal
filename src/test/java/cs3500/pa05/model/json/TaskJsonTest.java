package cs3500.pa05.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.Weekday;
import org.junit.jupiter.api.Test;

/**
 * testing class for the TaskJson record
 */
class TaskJsonTest {

  /**
   * testing if the initialization of a TaskJson works properly
   */
  @Test
  public void testInitialization() {
    TaskJson tj = new TaskJson("Name", "Description", Weekday.SUNDAY, true);

    assertEquals("Name", tj.name());
    assertEquals("Description", tj.description());
    assertEquals(Weekday.SUNDAY, tj.weekday());
    assertTrue(tj.complete());

    assertFalse(tj.containsName("blah"));
    assertTrue(tj.startsWithName("Na"));
    assertTrue(tj.containsDescription("iption"));
  }
}