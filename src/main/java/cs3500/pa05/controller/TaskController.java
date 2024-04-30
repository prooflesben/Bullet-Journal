package cs3500.pa05.controller;

import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.view.BujoView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * A Task controller to handle new tasks
 */
public final class TaskController implements Icontroller {
  @FXML
  private ComboBox<String> weekdaySelect;
  @FXML
  private Button submit;
  @FXML
  private TextArea descriptionBox;
  @FXML
  private TextField nameBox;
  @FXML
  private Label eventLabel;


  /**
   * Handle what should happen when the submit button is clicked
   *
   * @param stage      the stage for the window
   * @param controller the controller which is managing the main window
   */
  private void handleSubmit(Stage stage, WeekController controller) {
    String name = nameBox.getText();
    String weekdayString = weekdaySelect.getValue();
    String desc = descriptionBox.getText();

    if (validSelection()) {
      Weekday weekday = Weekday.valueOf(weekdayString.toUpperCase());

      Task resultTask = new Task(name, desc, weekday);
      controller.addTask(resultTask);
      stage.close();

    } else {
      eventLabel.setText("Please enter all the necessary info.");
    }
  }

  /**
   * Create new task.
   *
   * @param stage      the stage
   * @param controller the controller
   */
  public void createNewTask(Stage stage, WeekController controller) {
    start(stage);
    initWeekday();
    submit.setOnAction(event -> handleSubmit(stage, controller));
  }


  /**
   * Method to initialize the comboBox of weekdays
   */
  private void initWeekday() {
    String[] weekdays =
      {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    weekdaySelect.getItems().addAll(weekdays);
  }

  /**
   * Method to tell if the selection is valid
   *
   * @return the boolean
   */
  private boolean validSelection() {
    boolean result = true;
    result = result && weekdaySelect.getValue() != null;
    result = result && nameBox.getCharacters() != null;
    return result;
  }


  /**
   * Method to start the javafx screen
   *
   * @param stage the stage
   */
  private void start(Stage stage) {
    BujoView bujoView = new BujoView();

    try {
      // load and place the view's scene onto the stage
      stage.setScene(bujoView.loadTask(this));
      stage.show();
    } catch (IllegalStateException exc) {
      throw new RuntimeException("Unable to load GUI.");
    }
  }
}
