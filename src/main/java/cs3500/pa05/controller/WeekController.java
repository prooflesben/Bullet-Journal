package cs3500.pa05.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Weekday;
import cs3500.pa05.model.json.WeekRecord;
import cs3500.pa05.view.BujoView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * A controller which handles the week view
 */
public final class WeekController extends Application implements Icontroller {
  private final Stage stage;
  private final List<Task> tasks;
  private final List<Event> events;
  private WeekRecord weekJson;
  @FXML
  private ProgressBar taskBar;
  @FXML
  private TextField taskSearch;
  @FXML
  private TextField weekName;
  @FXML
  private TextArea notesAndQuotes;
  @FXML
  private Button newWeek;
  @FXML
  private Button newEvent;
  @FXML
  private Button newTask;
  @FXML
  private Button maxEvents;
  @FXML
  private Button maxTasks;
  @FXML
  private Button save;
  @FXML
  private Button open;
  @FXML
  private VBox taskQueue;
  @FXML
  private VBox sundayTasksBox;
  @FXML
  private VBox mondayTasksBox;
  @FXML
  private VBox tuesdayTasksBox;
  @FXML
  private VBox wednesdayTasksBox;
  @FXML
  private VBox thursdayTasksBox;
  @FXML
  private VBox fridayTasksBox;
  @FXML
  private VBox saturdayTasksBox;
  @FXML
  private VBox sundayEventsBox;
  @FXML
  private VBox mondayEventsBox;
  @FXML
  private VBox tuesdayEventsBox;
  @FXML
  private VBox wednesdayEventsBox;
  @FXML
  private VBox thursdayEventsBox;
  @FXML
  private VBox fridayEventsBox;
  @FXML
  private VBox saturdayEventsBox;
  @FXML
  private HBox fileChooserBox;
  @FXML
  private Button popupButton;
  @FXML
  private Label popupLabel;
  @FXML
  private Label fileLabel;
  @FXML
  private Button upButtonTask;
  @FXML
  private Button downButtonTask;
  @FXML
  private Button submitMaxTasks;
  @FXML
  private Label taskAmount;
  @FXML
  private Button upButtonEvent;
  @FXML
  private Button downButtonEvent;
  @FXML
  private Button submitMaxEvent;
  @FXML
  private Label eventAmount;
  private int taskInt;
  private int eventInt;
  @FXML
  private Scene scene;
  @FXML
  private Label maxTaskLabel;
  @FXML
  private Label maxEventLabel;
  @FXML
  private Label sundayRemaining;
  @FXML
  private ProgressBar sundayBar;
  @FXML
  private Label mondayRemaining;
  @FXML
  private ProgressBar mondayBar;
  @FXML
  private Label tuesdayRemaining;
  @FXML
  private ProgressBar tuesdayBar;
  @FXML
  private Label wednesdayRemaining;
  @FXML
  private ProgressBar wednesdayBar;
  @FXML
  private Label thursdayRemaining;
  @FXML
  private ProgressBar thursdayBar;
  @FXML
  private Label fridayRemaining;
  @FXML
  private ProgressBar fridayBar;
  @FXML
  private Label saturdayRemaining;
  @FXML
  private ProgressBar saturdayBar;
  @FXML
  private PasswordField passwordField;
  @FXML
  private Button setPassword;
  private File selectedFile;
  private List<Task> tasksInQueue;
  @FXML
  private Button exitButton;

  /**
   * Creates a WeekJson without a predetermined file
   *
   * @param weekJson WeekJson
   * @param stage    The Stage to be set on
   */
  public WeekController(WeekRecord weekJson, Stage stage) {
    this.weekJson = weekJson;
    this.stage = stage;
    taskInt = weekJson.maxTasks();
    eventInt = weekJson.maxEvents();
    tasks = weekJson.taskList();
    events = weekJson.eventList();
    this.tasksInQueue = new ArrayList<>();
  }

  /**
   * creates a new WeekController with a FilePath
   *
   * @param weekJson WeekJson
   * @param stage    The Stage to be set on
   * @param file     The file to be saved to
   */
  public WeekController(WeekRecord weekJson, Stage stage, File file) {
    this(weekJson, stage);
    this.selectedFile = file;
    this.tasksInQueue = new ArrayList<>();
  }

  /**
   * places an event in the eventList, removing any duplicates
   */
  private void placeEvents() {
    for (Event event : events) {
      addEvent(event);
    }
  }

  /**
   * places a task in the taskList, removing any duplicates
   */
  private void placeTask() {
    for (Task task : tasks) {
      addTask(task);
    }
  }

  /**
   * filters a list of tasks by those that partially match
   *
   * @param tasks  Task list to analyze
   * @param string String to check if matches
   * @return True if the substring is contained in the name or description
   */
  private List<Task> filterByContains(List<Task> tasks, String string) {
    List<Task> tasksStartWithString = new ArrayList<>();
    List<Task> tasksContainStringName = new ArrayList<>();
    List<Task> tasksContainStringDescription = new ArrayList<>();

    for (int i = 0; i < tasks.size(); i += 1) {
      if (tasks.get(i).toJson().containsName(string)) {
        tasksContainStringName.add(tasks.remove(i));
      }
    }

    for (int i = 0; i < tasks.size(); i += 1) {
      if (tasks.get(i).toJson().startsWithName(string)) {
        tasksStartWithString.add(tasks.remove(i));
      }
    }

    for (int i = 0; i < tasks.size(); i += 1) {
      if (tasks.get(i).toJson().containsDescription(string)) {
        tasksContainStringDescription.add(tasks.remove(i));
      }
    }

    List<Task> allSearchMatches = new ArrayList<>();
    allSearchMatches.addAll(tasksStartWithString);
    allSearchMatches.addAll(tasksContainStringName);
    allSearchMatches.addAll(tasksContainStringDescription);

    return allSearchMatches;
  }

  /**
   * initialize the entire scene with the selected WeekJson if full, otherwise set to a default
   */
  @FXML
  public void init() {
    weekName.setText(this.weekJson.name());
    notesAndQuotes.setText(this.weekJson.notesAndQuotes());
    if (taskInt == Integer.MAX_VALUE) {
      maxTaskLabel.setText("Max Tasks: ");
    } else {
      maxTaskLabel.setText("Max Tasks: " + taskInt);
    }
    if (eventInt == Integer.MAX_VALUE) {
      maxEventLabel.setText("Max Events: ");
    } else {
      maxEventLabel.setText("Max Events: " + eventInt);
    }
    taskBar.setProgress(this.weekJson.percentOfTasksComplete());
    taskBar.setStyle("-fx-accent: #A0CE8B;");
    taskSearch.setOnKeyPressed(e -> this.handleTaskSearch());

    newWeek.setOnAction(e -> this.handleNewWeek());
    newEvent.setOnAction(e -> this.handleNewEvent());
    newTask.setOnAction(e -> this.handleNewTask());
    maxEvents.setOnAction(e -> this.handleSetMaxEvents());
    maxTasks.setOnAction(e -> this.handleMaxTasks());
    save.setOnAction(e -> this.save());
    open.setOnAction(e -> this.handleOpen());
    setPassword.setOnAction(e -> this.handleSetPassword());

    initShortcuts();
    placeEvents();
    placeTask();
  }

  /**
   * handle all keyboard shortcuts that the user could create
   */
  private void initShortcuts() {
    KeyCombination saveShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
    KeyCombination openShortcut = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
    KeyCombination taskShortcut = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
    KeyCombination eventShortcut = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
    KeyCombination newWeekShortcut = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    KeyCombination maxEventsShortcut =
        new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
    KeyCombination maxTaskShortcut =
        new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
    KeyCombination passwordShortcut =
        new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);

    scene.setOnKeyPressed(event -> {
      if (passwordShortcut.match(event)) {
        handleSetPassword();
      }
      if (saveShortcut.match(event)) {
        save();
      }
      if (maxEventsShortcut.match(event)) {
        handleSetMaxEvents();
      }
      if (maxTaskShortcut.match(event)) {
        handleMaxTasks();
      }
      if (openShortcut.match(event)) {
        handleOpen();
      }
      if (taskShortcut.match(event)) {
        handleNewTask();
      }
      if (eventShortcut.match(event)) {
        handleNewEvent();
      }
      if (newWeekShortcut.match(event)) {
        handleNewWeek();
      }
    });
  }

  /**
   * when the user wants to set a different password, prompt the user for a new password
   */
  private void handleSetPassword() {
    this.weekJson = new WeekRecord(this.weekName.getText(), this.weekJson.tasks(),
        this.weekJson.events(), this.weekJson.maxEvents(), this.weekJson.maxTasks(),
        this.notesAndQuotes.getText(), this.weekJson.password());
    PasswordController passwordController = new PasswordController(stage, weekJson);
    BujoView bujoView = new BujoView();
    Stage newStage = new Stage();
    try {
      // load and place the view's scene onto the stage
      newStage.setScene(bujoView.loadPasswordPrompt(passwordController));
      passwordController.setNewPassword();

      // render the stage
      newStage.show();
    } catch (IllegalStateException exc) {
      throw new RuntimeException("Unable to load GUI.");
    }
  }

  /**
   * When the user types a character into the TaskSearch bar, filter out all of the tasks
   * that don't apply to the filter.
   */
  @FXML
  private void handleTaskSearch() {
    this.taskQueue.getChildren().clear();
    for (Task task : tasksInQueue) {
      this.taskQueue.getChildren().add(task);
    }
    String search = this.taskSearch.getText();
    ObservableList<Node> potentialTaskList = this.taskQueue.getChildren();
    List<Task> tasksList = new ArrayList<>();
    for (Node node : potentialTaskList) {
      if (node instanceof Task) {
        tasksList.add((Task) node);
      }
    }
    List<Task> tasksInSearch = this.filterByContains(tasksList, search);

    this.taskQueue.getChildren().clear();
    for (Task task : tasksInSearch) {
      this.taskQueue.getChildren().add(task);

      task.setOnMouseClicked(e -> {
        task.changeComplete();
        updateProgress();
      });

      this.weekJson.addTask(task);
    }
  }

  /**
   * When the user presses the "New Week" button,
   * create a new week with another user-generated password
   */
  @FXML
  private void handleNewWeek() {
    //this.stage.close();

    SplashController splashController = new SplashController(weekJson, new Stage());
    splashController.promptPassword();

  }

  /**
   * If the user presses the "New Event" button, display a popup to the user requesting
   * event information
   */
  @FXML
  private void handleNewEvent() {
    Stage stage = new Stage();
    EventController controller = new EventController();
    controller.createNewEvent(this, stage);
  }

  /**
   * If the user presses the "New Task" button, display a popup to the user requesting
   * task information
   */
  @FXML
  private void handleNewTask() {
    Stage stage = new Stage();
    TaskController controller = new TaskController();
    controller.createNewTask(stage, this);
  }

  /**
   * When the user wants to set the maximum amount of events in a day, prompt the user for a number
   * and update corresponding values
   */
  @FXML
  private void handleSetMaxEvents() {
    this.eventInt = 0;
    Popup popup = new Popup();
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(
        "MaxEvents.fxml"));
    loader.setController(this);
    Scene s = null;
    try {
      s = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    popup.getContent().add(s.getRoot());
    submitMaxEvent.setOnAction(event -> {
      int amountOfTask = Integer.parseInt(eventAmount.getText());
      if (amountOfTask < 0) {
        showPopup("Please enter a non negative event number");
      } else {
        popup.hide();
        maxEventLabel.setText("Max Events: " + eventInt);
      }

    });
    upButtonEvent.setOnAction(e -> {
      eventInt += 1;
      eventAmount.setText(String.valueOf(eventInt));
    });

    downButtonEvent.setOnAction(e -> {
      eventInt -= 1;
      eventAmount.setText(String.valueOf(eventInt));
    });

    exitButton.setOnAction(e -> popup.hide());
    popup.show(stage);
  }

  /**
   * When the user wants to set the maximum amount of tasks in a day, prompt the user for a number
   * and update corresponding values
   */
  @FXML
  private void handleMaxTasks() {
    this.taskInt = 0;
    Popup popup = new Popup();
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MaxTasks.fxml"));
    loader.setController(this);
    Scene s = null;
    try {
      s = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    popup.getContent().add(s.getRoot());
    submitMaxTasks.setOnAction(event -> {
      int amountOfTask = Integer.parseInt(taskAmount.getText());
      if (amountOfTask < 0) {
        showPopup("Please enter a non negative tasks number");
      } else {
        popup.hide();
        maxTaskLabel.setText("Max Tasks: " + taskInt);
      }

    });
    upButtonTask.setOnAction(e -> {
      taskInt += 1;
      taskAmount.setText(String.valueOf(taskInt));
    });

    downButtonTask.setOnAction(e -> {
      taskInt -= 1;
      taskAmount.setText(String.valueOf(taskInt));
    });
    exitButton.setOnAction(e -> popup.hide());


    popup.show(stage);
  }

  /**
   * displays a popup to the user with a supplied message
   *
   * @param message Message to be displayed to the user
   */
  private void showPopup(String message) {
    Popup popup = new Popup();
    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("popup.fxml"));
    loader.setController(this);
    Scene s = null;
    try {
      s = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    popup.getContent().add(s.getRoot());
    popupLabel.setText(message);
    popup.show(stage);
    popupButton.setOnAction(e -> popup.hide());

  }

  /**
   * Saves the entire weekJson to the selected file (given by the user or defaulted through ops)
   */
  @FXML
  public void save() {
    ObjectMapper objectMapper = new ObjectMapper();

    this.weekJson = new WeekRecord(this.weekName.getText(), this.weekJson.tasks(),
        this.weekJson.events(), this.weekJson.maxEvents(), this.weekJson.maxTasks(),
        this.notesAndQuotes.getText(), this.weekJson.password());

    String jsonString = "";

    if (selectedFile != null) {
      FileWriter fileWriter = new FileWriter();
      Path path = Path.of(selectedFile.getPath());

      try {
        jsonString = objectMapper.writeValueAsString(this.weekJson);

      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }

      fileWriter.writeBujoString(path, jsonString);
      showPopup("Saved!");
    } else {
      handleUnsavedFile();
    }
  }

  /**
   * When the user isn't rewriting a file and is instead operating under a new week and they
   * press the "Save" button, prompt the user for a place to save the file.
   */
  private void handleUnsavedFile() {
    BujoView bujoView = new BujoView();

    Popup savePopup = new Popup();
    // load and place the view's scene onto the stage
    Scene saveScene = bujoView.load(this, "Open");
    savePopup.getContent().add(saveScene.getRoot());
    BujoFileToModel bujoFileToModel = new BujoFileToModel();
    Button chooserButton = bujoFileToModel.saveFile(savePopup, this);
    Button exitButton = new Button("Exit");
    chooserButton.setStyle("-fx-font-family: Futura; -fx-border-color: black; "
        + "-fx-background-color: #FBFBF5; ");
    exitButton.setStyle("-fx-font-family: Futura; -fx-border-color: black; "
        + "-fx-background-color: #FBFBF5; ");
    exitButton.setOnAction(e -> savePopup.hide());
    this.fileLabel.setText("Select a place to save the file.");
    this.fileChooserBox.getChildren().add(chooserButton);
    this.fileChooserBox.getChildren().add(exitButton);
    savePopup.show(this.stage);
  }

  /**
   * when the user clicks the "Open" button, prompt the user for a file through a popup
   * and render the new week
   */
  @FXML
  protected void handleOpen() {

    BujoView bujoView = new BujoView();
    Stage stage = new Stage();

    try {
      // load and place the view's scene onto the stage
      stage.setScene(bujoView.load(this, "Open"));
      BujoFileToModel bujoFileToModel = new BujoFileToModel();
      Button chooserButton = bujoFileToModel.initFileChooser(stage, this.stage);
      chooserButton.setStyle("-fx-font-family: Futura; -fx-border-color: black; "
          + "-fx-background-color: #FBFBF5; ");
      this.fileChooserBox.getChildren().add(chooserButton);

      // render the stage
      stage.show();
    } catch (IllegalStateException exc) {
      throw new RuntimeException();
    }
    // take that file name and pass it into apply in the file to fxml function object
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
   **/
  @Override
  public void start(Stage stage) {

    BujoView bujoView = new BujoView();

    try {
      // load and place the view's scene onto the stage
      stage.setScene(bujoView.loadWeek(this));
      this.init();
      // render the stage
      stage.show();
    } catch (IllegalStateException exc) {
      throw new RuntimeException();
    }
  }

  /**
   * determines if the current task-VBox has reached the maximum amount of tasks
   *
   * @param box Task-VBox to be checked
   * @return True if the size is greater than or equal to the maximum amount of tasks
   */
  private boolean checkMaxTask(VBox box) {
    return box.getChildren().size() >= taskInt;
  }

  /**
   * given a task, determine if it overloads the specific day's maximum amount of tasks,
   * then add onclick events to the main task (to display information) and the
   * buddy task (to mark the task as complete or not), and updates all daily boxes
   *
   * @param task Task looking to be added
   */
  public void addTask(Task task) {
    task.setOnMouseClicked(event -> {
      String completeness = task.isComplete() ? "Yes" : "No";
      showPopup("Title: " + task.getName() + "\nDescription: " + task.getDescription()
          + "\nSet to be on: " + task.getWeekday().toString()
          + "\nMarked as complete? " + completeness);
      //display a popup displaying the task and the task information
    });

    Weekday day = task.getWeekday();
    if (day.equals(Weekday.SUNDAY)) {
      if (checkMaxTask(sundayTasksBox)) {
        showPopup("You have entered the max amounts of tasks.");
        return;
      }
      sundayTasksBox.getChildren().add(task);
    }

    if (day.equals(Weekday.MONDAY)) {
      if (checkMaxTask(mondayTasksBox)) {
        showPopup("You have entered the max amounts of tasks.");
        return;
      }
      mondayTasksBox.getChildren().add(task);
    }

    if (day.equals(Weekday.TUESDAY)) {
      if (checkMaxTask(tuesdayTasksBox)) {
        showPopup("You have entered the max amounts of tasks.");
        return;
      }
      tuesdayTasksBox.getChildren().add(task);
    }

    if (day.equals(Weekday.WEDNESDAY)) {
      if (checkMaxTask(wednesdayTasksBox)) {
        showPopup("You have entered the max amounts of tasks.");
        return;
      }
      wednesdayTasksBox.getChildren().add(task);
    }

    if (day.equals(Weekday.THURSDAY)) {
      if (checkMaxTask(thursdayTasksBox)) {
        showPopup("You have entered the max amounts of tasks.");
        return;
      }
      thursdayTasksBox.getChildren().add(task);
    }

    if (day.equals(Weekday.FRIDAY)) {
      if (checkMaxTask(fridayTasksBox)) {
        showPopup("You have entered the max amounts of tasks.");
        return;
      }
      fridayTasksBox.getChildren().add(task);
    }

    if (day.equals(Weekday.SATURDAY)) {
      if (checkMaxTask(saturdayTasksBox)) {
        showPopup("You have entered the max amounts of tasks.");
        return;
      }
      saturdayTasksBox.getChildren().add(task);
    }
    Task differentTask = task.cloneBuddy();

    differentTask.setOnMouseClicked(e -> {
      differentTask.changeComplete();
      this.weekJson.addTask(differentTask);
      updateProgress();
      updateDailyBoxes();
    });

    updateDailyBoxes();

    this.weekJson.addTask(differentTask);
    taskQueue.getChildren().add(differentTask);
    this.tasksInQueue.add(differentTask);
  }

  /**
   * updates the progress of the overall taskBar
   */
  private void updateProgress() {
    double totalTasks = taskQueue.getChildren().size();
    double completeTasks = 0.0;
    for (Node n : taskQueue.getChildren()) {
      String[] splitString = n.toString().split("- ");
      if (splitString[splitString.length - 1].contains("true")) {
        completeTasks += 1.0;
      }
    }

    taskBar.setProgress(completeTasks / totalTasks);
  }

  private boolean checkMaxEvent(VBox box) {
    return box.getChildren().size() >= eventInt;
  }

  /**
   * updates all the daily totals for the tasks completed and tasks remaining.
   */
  private void updateDailyBoxes() {
    updateRemainingTask(sundayTasksBox, sundayRemaining, sundayBar);
    updateRemainingTask(mondayTasksBox, mondayRemaining, mondayBar);
    updateRemainingTask(tuesdayTasksBox, tuesdayRemaining, tuesdayBar);
    updateRemainingTask(wednesdayTasksBox, wednesdayRemaining, wednesdayBar);
    updateRemainingTask(thursdayTasksBox, thursdayRemaining, thursdayBar);
    updateRemainingTask(fridayTasksBox, fridayRemaining, fridayBar);
    updateRemainingTask(saturdayTasksBox, saturdayRemaining, saturdayBar);
  }

  /**
   * given a certain box, updates the entire daily value of remainingTasks and the progress bar
   *
   * @param box            Box to receive tasks from
   * @param remainingLabel Label object containing a number representing tasks remaining
   * @param bar            Progress bar for the tasks left to be completed
   */
  private void updateRemainingTask(VBox box, Label remainingLabel, ProgressBar bar) {
    double totalTasks = box.getChildren().size();
    double completeTasks = 0.0;
    for (Node n : box.getChildren()) {
      String[] splitString = n.toString().split("- ");
      if (splitString[splitString.length - 1].contains("true")) {
        completeTasks += 1.0;
      }
    }
    remainingLabel.setText(String.valueOf((int) (totalTasks - completeTasks)));

    bar.setProgress(completeTasks / totalTasks);
    bar.setStyle("-fx-accent: #A0CE8B;");
  }

  /**
   * checks if date the event takes place on hasn't been maxed out, then adds the event to
   * the event list
   *
   * @param event Event looking to be added
   */
  public void addEvent(Event event) {
    event.setOnMouseClicked(clickEvent ->
        showPopup("Title: " + event.getName() + "\nDescription: " + event.getDescription()
           + "\nSet to be on: " + event.getWeekday().toString() + "\nStarts at: "
           + event.getStartTime().toString() + "\nEnds at: " + event.getEndTime().toString())
    );

    Weekday day = event.getWeekday();
    if (day.equals(Weekday.SUNDAY)) {
      if (checkMaxEvent(sundayEventsBox)) {
        showPopup("You have entered the max amounts of events.");
        return;
      }
      sundayEventsBox.getChildren().add(event);
    }

    if (day.equals(Weekday.MONDAY)) {
      if (checkMaxEvent(mondayEventsBox)) {
        showPopup("You have entered the max amounts of events.");
        return;
      }
      mondayEventsBox.getChildren().add(event);
    }

    if (day.equals(Weekday.TUESDAY)) {
      if (checkMaxEvent(tuesdayEventsBox)) {
        showPopup("You have entered the max amounts of events.");
        return;
      }
      tuesdayEventsBox.getChildren().add(event);
    }

    if (day.equals(Weekday.WEDNESDAY)) {
      if (checkMaxEvent(wednesdayEventsBox)) {
        showPopup("You have entered the max amounts of events.");
        return;
      }
      wednesdayEventsBox.getChildren().add(event);
    }

    if (day.equals(Weekday.THURSDAY)) {
      if (checkMaxEvent(thursdayEventsBox)) {
        showPopup("You have entered the max amounts of events.");
        return;
      }
      thursdayEventsBox.getChildren().add(event);
    }

    if (day.equals(Weekday.FRIDAY)) {
      if (checkMaxEvent(fridayEventsBox)) {
        showPopup("You have entered the max amounts of events.");
        return;
      }
      fridayEventsBox.getChildren().add(event);
    }

    if (day.equals(Weekday.SATURDAY)) {
      if (checkMaxEvent(saturdayEventsBox)) {
        showPopup("You have entered the max amounts of events.");
        return;
      }
      saturdayEventsBox.getChildren().add(event);
    }
    this.weekJson.addEvent(event);
  }

  /**
   * sets the file to be a specific file
   *
   * @param file File to update the selected file
   */
  public void setFile(File file) {
    this.selectedFile = file;
  }
}

