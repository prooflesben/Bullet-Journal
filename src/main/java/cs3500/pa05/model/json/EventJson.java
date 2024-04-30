package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Weekday;

/**
 * a Json representation of an Event
 *
 * @param name        Name of the event
 * @param description Description of the event
 * @param weekday     Weekday the event is on
 * @param startTime   The starting time of the event
 * @param endTime     The ending time of the event
 */
public record EventJson(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("weekday") Weekday weekday,
    @JsonProperty("startTime") TimeJson startTime,
    @JsonProperty("endTime") TimeJson endTime
) {

  /**
   * translates the EventJson to an event
   *
   * @return an Event
   */
  public Event toEvent() {
    return new Event(name, description, weekday, startTime.toTime(), endTime.toTime());
  }


}
