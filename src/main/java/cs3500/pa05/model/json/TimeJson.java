package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.AmPm;
import cs3500.pa05.model.Time;

/**
 * a Json representation of a time
 *
 * @param hour   Integer representing the hour (1-12)
 * @param minute Integer representing the minute (0-59)
 * @param amPm   Enumeration of either AM or PM
 */
public record TimeJson(
    @JsonProperty("hour") int hour,
    @JsonProperty("minute") int minute,
    @JsonProperty("amPm") AmPm amPm
) {

  /**
   * Translates the Json representation to a Time object
   *
   * @return a Time object with the same parameters as the Json
   */
  public Time toTime() {
    return new Time(hour, minute, amPm);
  }
}
