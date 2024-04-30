package cs3500.pa05.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.AmPm;
import cs3500.pa05.model.Weekday;
import org.junit.jupiter.api.Test;

/**
 * testing class for the EventJson record
 */
class EventJsonTest {

  /**
   * testing if the initialization of the EventJson works properly
   */
  @Test
  public void testInitialization() {
    TimeJson start = new TimeJson(1, 2, AmPm.AM);
    TimeJson end = new TimeJson(1, 2, AmPm.PM);
    EventJson ej = new EventJson("name", "description", Weekday.SUNDAY, start, end);

    assertEquals("name", ej.name());
    assertEquals("description", ej.description());
    assertEquals(Weekday.SUNDAY, ej.weekday());
    assertEquals(start, ej.startTime());
    assertEquals(end, ej.endTime());
  }
}