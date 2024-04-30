package cs3500.pa05.model.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa05.model.AmPm;
import cs3500.pa05.model.Time;
import org.junit.jupiter.api.Test;

/**
 * testing class for the TimeJson record
 */
class TimeJsonTest {

  /**
   * testing if the initialization of a TimeJson works properly
   */
  @Test
  public void testInitialization() {
    TimeJson tj = new TimeJson(1, 2, AmPm.AM);

    assertEquals(1, tj.hour());
    assertEquals(2, tj.minute());
    assertEquals(AmPm.AM, tj.amPm());

    Time expectedTime = new Time(1, 2, AmPm.AM);
    Time actualTime = tj.toTime();

    assertEquals(expectedTime.toString(), actualTime.toString());
  }
}