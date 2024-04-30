package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;

/**
 * A Json representation of a Task
 *
 * @param name        Name of the task
 * @param description Description of the task
 * @param weekday     The Weekday the task is on
 * @param complete    Boolean if the task is complete or not
 */
public record TaskJson(

    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("weekday") Weekday weekday,
    @JsonProperty("complete") boolean complete
) {

  /**
   * Translates the Json's parameters to a Task object
   *
   * @return a Task object with the same parameters as the Json
   */
  public Task toTask() {
    return new Task(name, description, weekday, complete);
  }

  /**
   * Determines if a string is contained within the name of the Json
   *
   * @param string String to check if it's inside the Json
   * @return True if the string is located inside the name
   */
  public boolean containsName(String string) {
    return this.name.contains(string);
  }

  /**
   * Determines if the name of the task starts with a specific substring
   *
   * @param string string to look for
   * @return True if the name starts with the given string
   */
  public boolean startsWithName(String string) {
    return this.name.startsWith(string);
  }

  /**
   * Determines if the description contains the given substring
   *
   * @param name Substring to check if it's inside the description
   * @return True if the supplied string is somewhere in the description.
   */
  public boolean containsDescription(String name) {
    return this.description.contains(name);
  }
}
