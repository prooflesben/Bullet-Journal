package cs3500.pa05.controller;

import static cs3500.pa05.model.Time.validDuration;

import cs3500.pa05.model.AmPm;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Time;
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
 * The type Event controller.
 */
public final class EventController implements Icontroller {
  /**
   * The Weekday select.
   */
  @FXML
  private ComboBox<String> weekdaySelect;
  /**
   * The Start hour.
   */
  @FXML
  private ComboBox<Integer> startHour;
  /**
   * The Start minute.
   */
  @FXML
  private ComboBox<Integer> startMinute;
  /**
   * The Hr select.
   */
  @FXML
  private ComboBox<Integer> hrSelect;
  /**
   * The Min select.
   */
  @FXML
  private ComboBox<Integer> minSelect;
  /**
   * The Start am pm.
   */
  @FXML
  private ComboBox<String> startAmPm;
  /**
   * The Submit.
   */
  @FXML
  private Button submit;
  /**
   * The Description box.
   */
  @FXML
  private TextArea descriptionBox;
  /**
   * The Name box.
   */
  @FXML
  private TextField nameBox;
  /**
   * The Event label.
   */
  @FXML
  private Label eventLabel;


  /**
   * Method to  handle when the submit button is pressed
   *
   * @param stage      Stage where the prompt is shown
   * @param controller Controller to handle the week view
   */
  private void handleSubmit(Stage stage, WeekController controller) {
    String name = nameBox.getText();
    String weekdayString = weekdaySelect.getValue();
    Integer startHourValue = startHour.getValue();
    Integer startMinuteValue = startMinute.getValue();
    String startAmPmValue = startAmPm.getValue();
    String desc = descriptionBox.getText();
    Integer hrAmt = hrSelect.getValue();
    Integer minAmt = minSelect.getValue();

    if (validSelection()) {
      AmPm startAm = AmPm.valueOf(startAmPmValue.toUpperCase());

      Time startTime = new Time(startHourValue, startMinuteValue, startAm);
      Time endTime = new Time(startHourValue, startMinuteValue, startAm);
      endTime.addHour(hrAmt);
      endTime.addMinutes(minAmt);

      if (!validDuration(startTime, endTime) || !endTime.validTime()) {
        eventLabel.setText("Please enter a valid time duration.");
        return;
      }

      Weekday weekday = Weekday.valueOf(weekdayString.toUpperCase());

      Event resultEvent = new Event(name, desc, weekday, startTime, endTime);
      controller.addEvent(resultEvent);
      stage.close();
    } else {
      eventLabel.setText("Please enter all the necessary info.");
    }
  }

  /**
   * Checks if selection is valid
   *
   * @return the boolean
   */
  private boolean validSelection() {
    boolean result = weekdaySelect.getValue() != null;
    result = result && nameBox.getCharacters() != null;
    result = result && startHour.getValue() != null;
    result = result && startMinute.getValue() != null;
    result = result && startAmPm.getValue() != null;
    result = result && hrSelect.getValue() != null;
    result = result && minSelect.getValue() != null;
    return result;
  }

  /**
   * Init weekday combo boxes
   */
  private void initWeekday() {
    String[] weekdays =
      {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    weekdaySelect.getItems().addAll(weekdays);
  }

  /**
   * Inits the time combo boxes
   */
  private void initTime() {
    Integer[] hours = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    Integer[] minutes = {0, 15, 30, 45};
    String[] amPm = {"am", "pm"};

    startHour.getItems().addAll(hours);
    startMinute.getItems().addAll(minutes);
    startAmPm.getItems().addAll(amPm);

    hrSelect.getItems().add(0);
    hrSelect.getItems().addAll(hours);
    minSelect.getItems().addAll(minutes);
  }

  /**
   * Create new event.
   *
   * @param controller the controller
   * @param stage      the stage
   */
  public void createNewEvent(WeekController controller, Stage stage) {
    start(stage);
    initTime();
    initWeekday();
    submit.setOnAction(event -> handleSubmit(stage, controller));
  }


  /**
   * The main entry point for all JavaFX applications.
   * The start method is called after the init method has returned,
   * and after the system is ready for the application to begin running.
   *
   * <p>
   * NOTE: This method is called on the JavaFX Application Thread.
   * </p>
   *
   * @param stage the primary stage for this application, onto which
   *              the application scene can be set.
   *              Applications may create other stages, if needed, but they will not be
   *              primary stages.
   */
  private void start(Stage stage) {
    BujoView bujoView = new BujoView();
    try {
      // load and place the view's scene onto the stage
      stage.setScene(bujoView.loadEvent(this));
      stage.show();
    } catch (IllegalStateException exc) {
      throw new RuntimeException("Unable to load GUI.");
    }
  }
}

