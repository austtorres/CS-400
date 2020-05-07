//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: HelloFX
// Files: Main.java
// Course: CS400 Fall 2019
//
// Author: Austin Torres
// Email: artorres3@wisc.edu
// Lecturer's Name: Deppeler
// Lecture Number: 001
//
// Description of Program: This program is meant to teach the basics of JavaFX
// including:
//
// Learn how to download, install, and configure JavaFX for use with Eclipse
// Learn how to create and edit a simple JavaFX application
// Learn what a BorderLayout is and how to use it in a GUI program
// Learn how to build an executable.jar file
// Learn how to build a Java ARchive (jar) file
// Learn how to run a Java FX program from the command line
// Learn how to create a zip file that can be submitted.
//
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: (name of your pair programming partner)
// Partner Email: (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: N/A
// Online Sources:
//
// Here is where I learned how to implement a color picker
// https://www.tutorialspoint.com/javafx/javafx_ui_controls.htm
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

/**
 * Main class runs the JavaFX program and allows it to be displayed
 * 
 * @author Austin Torres
 *
 */
public class Main extends Application {
  // Fields for the height and Width of the window
  private static final int WINDOW_WIDTH = 1200;
  private static final int WINDOW_HEIGHT = 600;
  private static final String APP_TITLE = "HelloFX";

  @Override
  /**
   * Set up and display scene
   * 
   */
  public void start(Stage primaryStage) throws Exception {
    // Main layout is Border Pane example (top,left,center,right,bottom)
    BorderPane root = new BorderPane();

    // Set each panel
    root.setTop(getTopLabel(root));

    root.setLeft(getLeftComboBox(root));

    root.setRight(getRightColorPicker(root));

    root.setCenter(getCenterImage(root));

    root.setBottom(getBottomButton(root));

    // Create scene with given specifications
    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    // Set the stage
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

  /**
   * Add label to top panel
   * 
   * @param root BorderPane being passed in
   * 
   * @return pane
   */
  public Label getTopLabel(BorderPane root) {

    // Creates the new label
    Label topLabel = new Label("CS400 MyFirstJavaFX");

    // Dimensions of label
    topLabel.setPrefHeight(100);
    topLabel.prefWidthProperty().bind(root.widthProperty());

    // define max font size you need
    final double MAX_FONT_SIZE = 30.0;

    // set to Label
    topLabel.setFont(new Font(MAX_FONT_SIZE));

    // Style of label
    topLabel.setStyle(
        "-fx-border-style: dotted; -fx-border-width: 0 0 1 0; -fx-font-weigth:bold;");
    topLabel.setAlignment(Pos.BASELINE_CENTER);
    return topLabel;
  }


  /**
   * Add drop down menu to left panel
   * 
   * @param BorderPane
   * 
   * @return pane
   */
  public ComboBox<String> getLeftComboBox(BorderPane root) {

    // Combo box allows user to select which word best describes photo
    ComboBox<String> comboBox = new ComboBox<String>();
    comboBox.getItems().add("Professional");
    comboBox.getItems().add("Approachable");
    comboBox.getItems().add("Fashionable");
    comboBox.getItems().add("Academic");
    comboBox.getItems().add("Hirable");

    // Dimensions
    comboBox.setPrefWidth(250);
    comboBox.prefHeightProperty().bind(root.heightProperty().subtract(120));

    // Style
    comboBox.setStyle(
        "-fx-border-style: dotted; -fx-border-width: 0 0 1 0; -fx-font-weigth:bold;");
    return comboBox;
  }

  /**
   * Place picture of myself in center panel
   * 
   * @param root BorderPane being passed in
   * 
   * @return pane
   */
  public ImageView getCenterImage(BorderPane root) {
    // Get picture
    FileInputStream input = null;
    try {

      // Image location
      input = new FileInputStream(
          "AustinTorres.JPG");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    Image image = new Image(input);

    ImageView imageView = new ImageView(image);

    // Image dimensions
    imageView.setFitHeight(400);

    imageView.setFitWidth(600);

    return imageView;
  }

  /**
   * Add color picker to right panel
   * 
   * @param root BorderPane being passed in
   * 
   * @return pane
   */
  public ColorPicker getRightColorPicker(BorderPane root) {
    // ColorPicker is created here
    ColorPicker rightColorPicker = new ColorPicker();

    rightColorPicker.setPrefWidth(250);
    rightColorPicker.prefHeightProperty()
        .bind(root.heightProperty().subtract(120));
    rightColorPicker.setStyle(
        "-fx-border-style: dotted; -fx-border-width: 0 0 1 0; -fx-font-weigth:bold;");

    return rightColorPicker;
  }

  /**
   * Add button to bottom panel
   * 
   * @param root BorderPane being passed in
   * 
   * @return pane
   */
  public Button getBottomButton(BorderPane root) {

    // Create button
    Button bottomButton = new Button("Done");

    // Dimensions
    bottomButton.setPrefHeight(100);
    bottomButton.prefWidthProperty().bind(root.widthProperty());

    // Style
    bottomButton.setStyle(
        "-fx-border-style: dotted; -fx-border-width: 0 0 1 0; -fx-font-weigth:bold;");

    // Alignment
    bottomButton.setAlignment(Pos.BASELINE_CENTER);
    return bottomButton;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }
}
