package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.json.EventJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * testing class for the Event class
 */
class EventTest {
  Time startTime;
  Time endTime;

  /**
   * Method to initialize data
   */
  @BeforeEach
  void init() {
    startTime = new Time(12, 30, AmPm.AM);
    endTime = new Time(12, 35, AmPm.AM);
  }

  /**
   * testing if the duration constructor works properly
   */
  @Test
  public void testDurationInitialization() {
    Event e = new Event("Name", "Description", Weekday.SUNDAY, startTime, 5);

    assertEquals("Name", e.getName());
    assertEquals("Description", e.getDescription());
    assertEquals(Weekday.SUNDAY, e.getWeekday());
    assertEquals(startTime.getHour(), e.getStartTime().getHour());
    assertEquals(startTime.getMinute(), e.getStartTime().getMinute());

    //making sure that adding 5 minutes to 12:30 brings it to 12:35
    assertEquals(endTime.getHour(), e.getEndTime().getHour());
    assertEquals(endTime.getMinute(), e.getEndTime().getMinute());

    EventJson expectedJson = new EventJson("Name", "Description", Weekday.SUNDAY,
        startTime.toJson(), endTime.toJson());
    assertEquals(expectedJson.toString(), e.toJson().toString());

    assertEquals("""
        Name
        - SUNDAY
        - 12:30 am
        - 12:35 am
        - Description
        """, e.toString());
  }

  /**
   * testing if the exact constructor works properly
   */
  @Test
  public void testExactInitialization() {
    Event e = new Event("Name", "Description", Weekday.SUNDAY, startTime, endTime);

    assertEquals("Name", e.getName());
    assertEquals("Description", e.getDescription());
    assertEquals(startTime.getHour(), e.getStartTime().getHour());
    assertEquals(startTime.getMinute(), e.getStartTime().getMinute());

    //making sure that adding 5 minutes to 12:30 brings it to 12:35
    assertEquals(endTime.getHour(), e.getEndTime().getHour());
    assertEquals(endTime.getMinute(), e.getEndTime().getMinute());
  }
}