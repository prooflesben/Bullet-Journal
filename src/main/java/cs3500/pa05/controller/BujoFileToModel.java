package cs3500.pa05.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.json.WeekRecord;
import cs3500.pa05.view.BujoView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * CLass to turn a bujo file to our model
 */
public final class BujoFileToModel {
  @FXML
  private HBox fileChooserBox;
  @FXML
  private VBox vbox2;
  @FXML
  private PasswordField passwordField;
  private File selectedFile;
  private Stage stage;

  /**
   * Init file chooser button.
   *
   * @param openStage    the open stage
   * @param oldBujoStage the old bujo stage
   * @return the button
   */
  public Button initFileChooser(Stage openStage, Stage oldBujoStage) {

    FileChooser fileChooser = new FileChooser();
    Button openButton = new Button("Select a File");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Bujo Files",
        "*.bujo"));
    openButton.setOnAction(e -> {
      File selectedFile = fileChooser.showOpenDialog(openStage);
      if (selectedFile != null) {
        this.selectedFile = selectedFile;
        Stage newStage = new Stage();
        this.openPasswordScreen(newStage);
        openStage.close();
        oldBujoStage.close();
      }
    });
    return openButton;
  }

  /**
   * Open password screen.
   *
   * @param stage the stage
   */
  public void openPasswordScreen(Stage stage) {
    BujoView bujoView = new BujoView();
    PasswordController passwordController = new PasswordController(stage, selectedFile);
    stage.setScene(bujoView.loadPasswordScreen(passwordController));
    passwordController.init();
    stage.show();
  }


  /**
   * Creates the save file screen and outputs a button a controller to use for the save file
   *
   * @param savePopup  the save popup
   * @param controller the controller
   * @return the button
   */
  public Button saveFile(Popup savePopup, WeekController controller) {

    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Bujo Files", "*.bujo"));
    Button openButton = new Button("Select a File");
    openButton.setOnAction(e -> {
      savePopup.hide();
      File selectedPoint = fileChooser.showSaveDialog(savePopup);
      controller.setFile(selectedPoint);
      controller.save();

    });
    return openButton;
  }


  /**
   * Method to open a new Bujo file
   *
   * @param openStage    the open stage
   * @param oldBujoStage the old bujo stage
   * @param selectedFile the selected file
   */
  public void openFile(Stage openStage, Stage oldBujoStage, File selectedFile) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String fileContent = Files.readString(selectedFile.toPath());
      WeekRecord weekRecord = objectMapper.readValue(fileContent, WeekRecord.class);
      openStage.close();
      oldBujoStage.close();
      Stage newStage = new Stage();
      WeekController weekController = new WeekController(weekRecord, newStage, this.selectedFile);
      weekController.start(newStage);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
