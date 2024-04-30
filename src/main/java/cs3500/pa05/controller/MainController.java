package cs3500.pa05.controller;

import cs3500.pa05.model.json.EventJson;
import cs3500.pa05.model.json.TaskJson;
import cs3500.pa05.model.json.WeekRecord;
import cs3500.pa05.view.BujoView;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * The Main controller.
 */
public final class MainController extends Application {

  /**
   * Starts the GUI for a bujo week view.
   *
   * @param stage the JavaFX stage to add elements to
   */
  @Override
  public void start(Stage stage) {

    List<TaskJson> tasksJsons = new ArrayList<>();
    List<EventJson> eventJsons = new ArrayList<>();

    WeekRecord weekRecord = new WeekRecord("Week1", tasksJsons,
        eventJsons, Integer.MAX_VALUE, Integer.MAX_VALUE, "", "password");

    SplashController controller = new SplashController(weekRecord, stage);
    BujoView bujoView = new BujoView();

    try {
      stage.setScene(bujoView.loadSplashScreen(controller));

      controller.init();
      stage.show();
    } catch (IllegalStateException exc) {
      throw new RuntimeException();
    }
  }
}
