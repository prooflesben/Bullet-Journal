package cs3500.pa05.model;


import cs3500.pa05.model.json.TaskJson;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * class representing a tasks that needs to be completed within a BUJO
 */
public final class Task extends VBox {
  private final String name;
  private final String description;
  private final Weekday weekday;
  private final List<Task> cloneBuddies;
  private boolean complete;
  private TextFlow nameBox;
  private TextFlow descBox;
  private VBox innerBox;

  /**
   * Creates an incomplete tasks with a given name, description, and day
   *
   * @param name        Name of the tasks
   * @param description Description of the tasks
   * @param weekday     Weekday the tasks occurs on
   */
  public Task(String name, String description, Weekday weekday) {
    this.name = name;
    this.description = description;
    this.weekday = weekday;
    this.complete = false;
    this.cloneBuddies = new ArrayList<>();
    init();
  }

  /**
   * Instantiates a new Task.
   *
   * @param name        the name
   * @param description the description
   * @param weekday     the weekday
   * @param complete    the complete
   */
  public Task(String name, String description, Weekday weekday, boolean complete) {
    this.name = name;
    this.description = description;
    this.weekday = weekday;
    this.complete = complete;
    this.cloneBuddies = new ArrayList<>();
    init();
  }

  /**
   * Instantiates a new Task.
   *
   * @param name        the name
   * @param description the description
   * @param weekday     the weekday
   * @param complete    the complete
   * @param buddy       the buddy
   */
  public Task(String name, String description, Weekday weekday, boolean complete, Task buddy) {
    this.name = name;
    this.description = description;
    this.weekday = weekday;
    this.complete = complete;
    this.cloneBuddies = new ArrayList<>();
    cloneBuddies.add(buddy);
    init();
  }

  /**
   * To json task json.
   *
   * @return the task json
   */
  public TaskJson toJson() {
    return new TaskJson(name, description, weekday, complete);
  }


  /**
   * Method to initialize all of the elements in the vbox
   */
  public void init() {
    nameBox = new TextFlow();
    descBox = new TextFlow();
    innerBox = new VBox();

    nameBox.getChildren().add(new Text(this.name));
    nameBox.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
    innerBox.getChildren().add(nameBox);
    if (description != null) {
      processDesc();
      innerBox.getChildren().add(descBox);
    }
    innerBox.setStyle("-fx-background-color: #D55F5F;");
    innerBox.setStyle("-fx-background-color: #D55F5F;");

    innerBox.setPrefWidth(100);
    this.setPadding(new Insets(5, 5, 5, 5));
    this.getChildren().add(innerBox);

    if (complete) {
      innerBox.setStyle("-fx-background-color: #66A95B");
    }
  }

  /**
   * Process description.
   */
  private void processDesc() {
    String[] words = description.split(" ");

    for (String word : words) {
      if (word.startsWith("http://") || word.startsWith("https://")) {
        Hyperlink hyperlink = new Hyperlink(word);
        hyperlink.setOnAction(event -> openUrl(hyperlink.getText()));
        descBox.getChildren().add(hyperlink);
      } else {
        // Create a regular Text node for other words
        Text text = new Text(word + " ");
        text.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
        descBox.getChildren().add(text);
      }
    }
    descBox.setMaxWidth(100);
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
   * returns the name of the tasks
   *
   * @return a String representation of the tasks's name
   */
  public String getName() {
    return name;
  }

  /**
   * returns the description of the tasks
   *
   * @return a String representation of the tasks's description
   */
  public String getDescription() {
    return description;
  }

  /**
   * returns the weekday the tasks is set to be on
   *
   * @return the weekday the tasks is set to be on
   */
  public Weekday getWeekday() {
    return weekday;
  }

  /**
   * marks the tasks as completed if not, or not completed if it is
   */
  public void changeComplete() {

    complete = !complete;
    if (complete) {
      innerBox.setStyle("-fx-background-color: #66A95B");
    } else {
      innerBox.setStyle("-fx-background-color: #D55F5F;");
    }
    for (Task task : cloneBuddies) {
      task.buddyComplete();
    }
  }

  /**
   * Buddy complete.
   */
  void buddyComplete() {
    complete = !complete;
    if (complete) {
      innerBox.setStyle("-fx-background-color: #66A95B");
    } else {
      innerBox.setStyle("-fx-background-color: #D55F5F;");
    }
  }


  /**
   * returns if the tasks is complete or not
   *
   * @return true if the tasks is marked as "complete"
   */
  public boolean isComplete() {
    return complete;
  }

  /**
   * To string string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return this.name + "\n"
        + "- " + this.weekday.toString() + "\n"
        + "- " + this.description + "\n"
        + "- " + this.complete + "\n";
  }


  /**
   * Clone task with and connects the clone so they both get completed at the same time
   *
   * @return the task
   */
  public Task cloneBuddy() {
    Task buddy = new Task(this.name, this.description, this.weekday, this.complete, this);
    this.cloneBuddies.add(buddy);
    return buddy;
  }
}
