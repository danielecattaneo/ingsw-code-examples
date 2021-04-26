package it.polimi.ingsw.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class JavaFXTest extends Application
{
  // When using IntelliJ, don't run the application from here, use the main
  // method in JavaFXMain
  public static void main(String[] args)
  {
    launch(args);
  }


  @Override
  public void start(Stage primaryStage)
  {
    Label l = new Label("Hello world!");
    l.setAlignment(Pos.CENTER);

    AnchorPane.setBottomAnchor(l, 30.0);
    AnchorPane.setTopAnchor(l, 30.0);
    AnchorPane.setLeftAnchor(l, 30.0);
    AnchorPane.setRightAnchor(l, 30.0);
    AnchorPane pane = new AnchorPane(l);

    Scene scene = new Scene(pane);

    primaryStage.setScene(scene);
    primaryStage.sizeToScene();
    primaryStage.show();
  }
}
