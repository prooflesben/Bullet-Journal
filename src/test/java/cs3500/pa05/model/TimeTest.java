package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa05.model.json.TimeJson;
import org.junit.jupiter.api.Test;

/**
 * a testing class for the Time class
 */
class TimeTest {

  /**
   * testing if the initialization of a time works properly and to-strings properly
   */
  @Test
  public void testInitialization() {
    Time t = new Time(12, 40, AmPm.PM);

    assertEquals(12, t.getHour());
    assertEquals(40, t.getMinute());

    assertEquals("12:40 pm", t.toString());
  }

  /**
   * testing if adding minutes works properly to a time
   */
  @Test
  public void testValidity() {
    Time t = new Time(12, 35, AmPm.PM);

    assertTrue(t.validTime());
    assertFalse(new Time(15, 35, AmPm.PM).validTime());
    assertFalse(new Time(-2, 30, AmPm.PM).validTime());
    assertTrue(new Time(12, 30, AmPm.PM).validTime());
    assertFalse(new Time(2, -30, AmPm.PM).validTime());
    assertFalse(new Time(2, 90, AmPm.PM).validTime());

    TimeJson actualJson = t.toJson();
    TimeJson expectedJson = new TimeJson(12, 35, AmPm.PM);

    assertEquals(expectedJson.hour(), actualJson.hour());
    assertEquals(expectedJson.minute(), actualJson.minute());
    assertEquals(expectedJson.amPm(), actualJson.amPm());

    assertTrue(Time.validDuration(
        new Time(0, 0, AmPm.AM),
        new Time(0, 0, AmPm.PM)
    ));

    assertTrue(Time.validDuration(
        new Time(0, 0, AmPm.AM),
        new Time(5, 0, AmPm.AM)
    ));

    assertFalse(Time.validDuration(
        new Time(5, 0, AmPm.AM),
        new Time(0, 0, AmPm.AM)
    ));
    assertFalse(Time.validDuration(
        new Time(5, 0, AmPm.PM),
        new Time(12, 0, AmPm.PM)
    ));

    assertTrue(Time.validDuration(
        new Time(1, 1, AmPm.AM),
        new Time(1, 2, AmPm.AM)));

    assertFalse(Time.validDuration(
        new Time(1, 3, AmPm.AM),
        new Time(1, 0, AmPm.AM)));

    assertTrue(Time.validDuration(
        new Time(1, 0, AmPm.PM),
        new Time(5, 0, AmPm.PM)
    ));

    assertFalse(Time.validDuration(
        new Time(0, 0, AmPm.PM),
        new Time(0, 0, AmPm.AM)
    ));

    assertFalse(Time.validDuration(
        new Time(3, 30, AmPm.PM), new Time(3, 20, AmPm.PM)));

    assertThrows(IllegalArgumentException.class,
        () -> Time.getDuration(
            new Time(0, 0, AmPm.PM),
            new Time(0, 0, AmPm.AM)),
        "Invalid duration");

    assertEquals(15, Time.getDuration(
        new Time(0, 0, AmPm.PM),
        new Time(0, 15, AmPm.PM)
    ).getMinute());

    Time expectedDuration = new Time(13, 20, AmPm.PM);
    Time actualDuration = Time.getDuration(
        new Time(11, 30, AmPm.AM),
        new Time(12, 50, AmPm.PM)
    );

    assertEquals(expectedDuration.toString(), actualDuration.toString());
  }
}