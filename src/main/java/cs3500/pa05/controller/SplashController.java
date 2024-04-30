package cs3500.pa05.controller;


import cs3500.pa05.model.json.WeekRecord;
import cs3500.pa05.view.BujoView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * Controller to handle the initial welcome screen
 */
public final class SplashController implements Icontroller {

  private final Stage stage;
  private final WeekController weekController;
  @FXML
  private Button newWeek;
  @FXML
  private Button openExisting;
  @FXML
  private PasswordField passwordField;
  @FXML
  private PasswordField newPassword;
  @FXML
  private PasswordField confirmPassword;
  @FXML
  private Button submitPassword;

  /**
   * Instantiates a new Splash controller.
   *
   * @param weekRecord the week record
   * @param stage      the stage
   */
  public SplashController(WeekRecord weekRecord, Stage stage) {
    this.stage = stage;
    this.weekController = new WeekController(weekRecord, stage);
  }

  /**
   * Method to initialize buttons
   */
  public void init() {
    openExisting.setOnAction(e -> weekController.handleOpen());
    newWeek.setOnAction(e -> this.promptPassword());
  }

  /**
   * Method to prompt password
   */
  public void promptPassword() {
    BujoView bujoView = new BujoView();
    PasswordController passwordController = new PasswordController(stage);

    try {
      stage.setScene(bujoView.loadPasswordPrompt(passwordController));
      passwordController.setNewPassword();

      stage.show();
    } catch (IllegalStateException exc) {
      throw new RuntimeException("Unable to load GUI.");
    }
  }
}
