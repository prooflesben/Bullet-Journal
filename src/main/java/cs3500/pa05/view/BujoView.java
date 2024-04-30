package cs3500.pa05.view;

import cs3500.pa05.controller.Icontroller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * The type Bujo view.
 */
public final class BujoView {
  private final FXMLLoader loader;

  /**
   * Instantiates a new Bujo view.
   */
  public BujoView() {
    this.loader = new FXMLLoader();
  }

  /**
   * Loads a week view from a Bujo week layout.
   *
   * @param controller the controller
   * @return the layout
   */
  public Scene loadWeek(Icontroller controller) {
    this.loader.setLocation(getClass().getClassLoader().getResource("WeekView.fxml"));
    this.loader.setController(controller);

    // load the layout
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }

  /**
   * Load splash screen scene.
   *
   * @param controller the controller
   * @return the scene
   */
  public Scene loadSplashScreen(Icontroller controller) {
    this.loader.setLocation(getClass().getClassLoader().getResource("SplashScreen.fxml"));
    this.loader.setController(controller);

    // load the layout
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }

  /**
   * Load password screen scene.
   *
   * @param controller the controller
   * @return the scene
   */
  public Scene loadPasswordScreen(Icontroller controller) {
    this.loader.setLocation(getClass().getClassLoader().getResource("PasswordScreen.fxml"));
    this.loader.setController(controller);

    // load the layout
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }

  /**
   * Load password prompt scene.
   *
   * @param controller the controller
   * @return the scene
   */
  public Scene loadPasswordPrompt(Icontroller controller) {
    this.loader.setLocation(getClass().getClassLoader().getResource("PasswordPrompt.fxml"));
    this.loader.setController(controller);

    // load the layout
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }


  /**
   * Load event scene.
   *
   * @param controller the controller
   * @return the scene
   */
  public Scene loadEvent(Icontroller controller) {
    this.loader.setController(controller);
    this.loader.setLocation(getClass().getClassLoader().getResource("NewEvent.fxml"));
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }


  /**
   * Load tasks scene.
   *
   * @param controller the controller
   * @return the scene
   */
  public Scene loadTask(Icontroller controller) {
    this.loader.setController(controller);
    this.loader.setLocation(getClass().getClassLoader().getResource("NewTask.fxml"));
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }

  /**
   * Load a week scene.
   *
   * @param controller the controller
   * @param fxmlName   the fxml name
   * @return the scene
   */
  public Scene load(Icontroller controller, String fxmlName) {
    this.loader.setLocation(getClass().getClassLoader().getResource(
        fxmlName + ".fxml"));
    this.loader.setController(controller);

    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
