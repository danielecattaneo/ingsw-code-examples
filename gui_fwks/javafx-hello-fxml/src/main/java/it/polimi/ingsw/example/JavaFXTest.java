package it.polimi.ingsw.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;


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
    Parent root;
    try {
      root = FXMLLoader.load(getClass().getResource("/TestScene.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    Scene sc = new Scene(root);
    primaryStage.setScene(sc);
    primaryStage.sizeToScene();
    primaryStage.show();
  }
}
