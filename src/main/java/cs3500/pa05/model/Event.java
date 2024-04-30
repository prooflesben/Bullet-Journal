package cs3500.pa05.model;

import cs3500.pa05.model.json.EventJson;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


/**
 * An Event in a Bujo week.
 */
public final class Event extends VBox {
  private final String name;
  private final String description;
  private final Weekday weekday;
  private final Time startTime;
  private final Time endTime;
  private TextFlow nameBox;
  private TextFlow descBox;
  private TextFlow startTimeBox;
  private TextFlow endTimeBox;
  private VBox innerBox;

  /**
   * creates an event with a given duration
   * MOST LIKELY USED FOR JSON CREATION (as it has the duration)
   *
   * @param name        Name of the event
   * @param description Description of the event
   * @param weekday     Weekday the event is occurring on
   * @param startTime   Starting time of the event
   * @param duration    Duration of the event (in minutes)
   */
  public Event(String name, String description, Weekday weekday, Time startTime, int duration) {
    this.name = name;
    this.description = description;
    this.weekday = weekday;
    this.startTime = startTime;
    this.endTime = new Time(startTime.getHour(), startTime.getMinute(), startTime.getAmPm());
    this.endTime.addMinutes(duration);
    init();
  }


  /**
   * creates an event with a given endtime
   *
   * @param name        Name of the event
   * @param description Description of the event
   * @param weekday     Weekday the event is occuring on
   * @param startTime   Starting time of the event
   * @param endTime     Ending time of the event
   */
  public Event(String name, String description, Weekday weekday, Time startTime, Time endTime) {
    this.name = name;
    this.description = description;
    this.weekday = weekday;
    this.startTime = startTime;
    this.endTime = endTime;
    init();
  }

  /**
   * To json event json.
   *
   * @return the event json
   */
  public EventJson toJson() {
    return new EventJson(name, description, weekday, startTime.toJson(), endTime.toJson());
  }

  /**
   * Method to initialize all the fields in the VBox
   */
  private void init() {
    nameBox = new TextFlow();
    innerBox = new VBox();
    descBox = new TextFlow();
    startTimeBox = new TextFlow();
    endTimeBox = new TextFlow();
    innerBox.setStyle("-fx-background-color: #C8E9B9;");
    nameBox.getChildren().add(new Text(this.name));
    nameBox.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
    innerBox.getChildren().add(nameBox);
    if (description != null) {
      processDesc();
      innerBox.getChildren().add(descBox);
    }
    endTimeBox.getChildren().add(new Text(endTime.toString()));
    endTimeBox.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

    startTimeBox.getChildren().add(new Text(startTime.toString()));
    startTimeBox.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");

    innerBox.getChildren().addAll(startTimeBox, endTimeBox);

    innerBox.setPrefWidth(100);
    this.setPadding(new Insets(5, 5, 5, 5));
    this.getChildren().add(innerBox);
  }

  /**
   * Process descrption
   */
  private void processDesc() {
    String[] words = description.split(" ");

    for (String word : words) {
      if (word.startsWith("http://") || word.startsWith("https://")) {
        Hyperlink hyperlink = new Hyperlink(word);
        hyperlink.setOnAction(event -> openUrl(hyperlink.getText()));
        hyperlink.setFont(new Font(6));
        descBox.getChildren().add(hyperlink);
      } else {
        // Create a regular Text node for other words
        Text text = new Text(word + " ");
        text.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
        descBox.getChildren().add(text);
      }
    }
    descBox.setMaxWidth(100);
    descBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
  }

  /**
   * Open url.
   *
   * @param url the url
   */
  private void openUrl(String url) {
    try {
      Desktop.getDesktop().browse(new URI(url));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  /**
   * returns the name of the event
   *
   * @return a String representation of the name
   */
  public String getName() {
    return name;
  }

  /**
   * returns the description of the event
   *
   * @return a String representation of the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * returns the Weekday that the event occurs on
   *
   * @return the Weekday the event occurs on
   */
  public Weekday getWeekday() {
    return weekday;
  }

  /**
   * returns the starting time of the event
   *
   * @return the starting time of the event
   */
  public Time getStartTime() {
    return startTime;
  }

  /**
   * returns the ending time of the event
   *
   * @return the ending time of the event
   */
  public Time getEndTime() {
    return endTime;
  }

  /**
   * creates a string representation of the Event
   *
   * @return a String representation of the event
   */
  public String toString() {
    return this.name + "\n"
       + "- " + this.weekday.toString() + "\n"
       + "- " + this.startTime.toString() + "\n"
       + "- " + this.endTime.toString() + "\n"
       + "- " + this.description + "\n";
  }


}
