package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.json.EventJson;
import cs3500.pa05.model.json.TaskJson;
import cs3500.pa05.model.json.WeekRecord;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * Class to handle password related activites
 */
public final class PasswordController implements Icontroller {

  private final Stage stage;
  private final File selectedFile;
  private final WeekRecord weekJson;
  @FXML
  private PasswordField passwordField;
  @FXML
  private PasswordField newPassword;
  @FXML
  private PasswordField confirmPassword;
  @FXML
  private Button submitPassword;
  @FXML
  private Button enterPassword;
  @FXML
  private Label warningLabel;
  @FXML
  private Button exitButton;

  /**
   * Constructor to initialize the class
   *
   * @param stage        Stage where the password activities will be shown
   * @param selectedFile File where the bujo file belongs
   */
  public PasswordController(Stage stage, File selectedFile) {
    this.stage = stage;
    this.selectedFile = selectedFile;
    List<TaskJson> tasksJsons = new ArrayList<>();
    List<EventJson> eventJsons = new ArrayList<>();

    weekJson = new WeekRecord("New Week", tasksJsons,
        eventJsons, Integer.MAX_VALUE, Integer.MAX_VALUE, "", "");
  }

  /**
   * Constructor to initialize the class
   *
   * @param stage Stage where the password activities will be shown
   */
  public PasswordController(Stage stage) {
    this.stage = stage;
    this.selectedFile = null;
    List<TaskJson> tasksJsons = new ArrayList<>();
    List<EventJson> eventJsons = new ArrayList<>();

    weekJson = new WeekRecord("New Week", tasksJsons,
        eventJsons, Integer.MAX_VALUE, Integer.MAX_VALUE, "", "");
  }

  /**
   * Constructor to initialize the class
   *
   * @param stage  Stage where the password activities will be shown
   * @param record Record that holds the weeks info
   */
  public PasswordController(Stage stage, WeekRecord record) {
    this.stage = stage;
    weekJson = record;
    selectedFile = null;
  }

  /**
   * Method to initialize the needed buttons
   */
  public void init() {
    enterPassword.setOnAction(e -> handlePassword());
  }


  /**
   * Method that handles entering a password
   */
  private void handlePassword() {
    String password = this.passwordField.getText();
    ObjectMapper objectMapper = new ObjectMapper();
    String fileContent = null;
    try {
      fileContent = Files.readString(selectedFile.toPath());
      WeekRecord weekRecord = objectMapper.readValue(fileContent, WeekRecord.class);

      if (password.equals(weekRecord.password())) {
        BujoFileToModel bujoFileToModel = new BujoFileToModel();
        bujoFileToModel.openFile(new Stage(), stage, selectedFile);
        stage.close();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Method to set a new Password
   */
  public void setNewPassword() {
    submitPassword.setOnAction(e -> setPassword());
    exitButton.setOnAction(e -> stage.close());
  }

  /**
   * Method to set a new password
   */
  private void setPassword() {
    String origPass = newPassword.getText();
    String confirmedPass = confirmPassword.getText();

    if (origPass.equals(confirmedPass)) {

      WeekRecord weekRecord = new WeekRecord(weekJson.name(), weekJson.tasks(),
          weekJson.events(), weekJson.maxEvents(), weekJson.maxTasks(),
          weekJson.notesAndQuotes(), origPass);
      Stage newStage = new Stage();
      WeekController weekController = new WeekController(weekRecord, newStage);
      weekController.start(newStage);
      stage.close();

    } else {
      warningLabel.setText("Passwords must match");
      warningLabel.setStyle("-fx-text-fill: D55F5F; -fx-font-size: 16px;");
    }
  }
}
