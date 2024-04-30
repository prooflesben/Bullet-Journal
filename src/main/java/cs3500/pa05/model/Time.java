package cs3500.pa05.model;

import cs3500.pa05.model.json.TimeJson;

/**
 * class representing a Time in a day
 */
public final class Time {
  private int hour;
  private int minute;
  private final AmPm amPm;

  /**
   * creates a time given the hours and minutes
   *
   * @param hour   a 12-hour representation of the hour
   * @param minute the minute that the task is set to be on
   * @param amPm   an enumeration of the AM or PM of the current time
   */
  public Time(int hour, int minute, AmPm amPm) {
    this.hour = hour;
    this.minute = minute;
    this.amPm = amPm;
  }

  /**
   * Determines if the start time comes before the end time
   *
   * @param startTime the starting time
   * @param endTime   the ending time
   * @return boolean representing if the start time comes before the end time
   */
  public static boolean validDuration(Time startTime, Time endTime) {
    if (startTime.getAmPm().equals(AmPm.AM)) {
      if (endTime.getAmPm().equals(AmPm.PM)) {
        return true;
      } else {
        if (startTime.getHour() < endTime.getHour()) {
          return true;
        } else if (startTime.getHour() == endTime.getHour()) {
          return startTime.getMinute() < endTime.getMinute();
        } else {
          return false;
        }
      }
    } else {
      if (endTime.getAmPm().equals(AmPm.AM)) {
        return false;
      } else if (endTime.getHour() == 12 && endTime.getAmPm().equals(AmPm.PM)) {
        return false;
      } else {
        if (startTime.getHour() < endTime.getHour()) {
          return true;
        } else if (startTime.getHour() == endTime.getHour()) {
          return startTime.getMinute() < endTime.getMinute();
        } else {
          return false;
        }
      }
    }
  }

  /**
   * Determines the duration between the start and end time if it is valid
   *
   * @param startTime the starting time
   * @param endTime   the ending time
   * @return the duration between the two times
   */
  public static Time getDuration(Time startTime, Time endTime) {
    int hours = 0;
    int minutes = 0;
    if (!validDuration(startTime, endTime)) {
      throw new IllegalArgumentException("Invalid duration");
    } else {
      if ((startTime.getAmPm().equals(AmPm.AM) && endTime.getAmPm().equals(AmPm.AM))
          || (startTime.getAmPm().equals(AmPm.PM) && endTime.getAmPm().equals(AmPm.PM))) {
        hours = endTime.getHour() - startTime.getHour();
        minutes = Math.abs(endTime.getMinute() - startTime.getMinute());
      } else {
        hours = (12 - startTime.getHour()) + endTime.getHour();
        minutes = Math.abs(endTime.getMinute() - startTime.getMinute());
      }
    }
    return new Time(hours, minutes, AmPm.PM);
  }

  public TimeJson toJson() {
    return new TimeJson(hour, minute, amPm);
  }

  /**
   * returns the amPm value of this time
   *
   * @return the AmPm value of this time
   */
  public AmPm getAmPm() {
    return this.amPm;
  }

  /**
   * returns the string representation of the current time
   *
   * @return the time as a string, EX: 23:42
   */
  @Override
  public String toString() {
    String minuteString = String.valueOf(minute);
    if (minute < 10) {
      minuteString = "0" + minuteString;
    }
    return hour + ":" + minuteString + " " + amPm.toString().toLowerCase();
  }

  /**
   * adds a given amount of minutes to the current time, adjusts the hours aswell
   *
   * @param minutes an integer representing the amount to move up by
   */
  public void addMinutes(int minutes) {
    int totalMins = minutes + minute;

    //sets the total minutes to the proper amount
    minute = totalMins % 60;
    //adds the proper amount of hours
    addHour(totalMins / 60);
  }

  /**
   * adds a set amount of hours to the time
   *
   * @param hours amount of hours to be added
   */
  public void addHour(int hours) {
    hour += hours;
  }

  /**
   * determines if the time is valid
   *
   * @return true if the hours and minutes are in their valid ranges
   */
  public boolean validTime() {
    return hour > 0 && hour <= 12 && minute >= 0 && minute <= 60;
  }

  /**
   * returns the hour of the current time
   *
   * @return the hour of the current time
   */
  public int getHour() {
    return hour;
  }

  /**
   * returns the minute of the current time
   *
   * @return the minute of the current time
   */
  public int getMinute() {
    return minute;
  }
}
