package it.polimi.ingsw.example.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;


public class ChatClientApplication extends Application
{
  private static ChatClientApplication currentApplication;
  private Stage primaryStage;
  private ServerHandler serverHandler;


  // When using IntelliJ, don't run the application from here, use the main
  // method in JavaFXMain
  public static void main(String[] args)
  {
    launch(args);
  }


  @Override
  public void start(Stage primaryStage)
  {
    currentApplication = this;
    this.primaryStage = primaryStage;
    primaryStage.setOnCloseRequest((event) -> {
      serverHandler.closeConnection();
      System.out.println("closing!!");
    });
    this.serverHandler = new ServerHandler();
    this.serverHandler.setConnectionClosedObserver(() -> {
      Platform.runLater(() -> {
        Alert alert = new Alert(Alert.AlertType.ERROR, "The connection was closed.", ButtonType.OK);
        alert.showAndWait();
        switchToLoginScene();
      });
    });
    switchToLoginScene();
  }


  public static ChatClientApplication getCurrentApplication()
  {
    return currentApplication;
  }


  public ServerHandler getServerHandler()
  {
    return serverHandler;
  }


  public void switchToLoginScene()
  {
    Parent root;
    try {
      root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    Scene sc = new Scene(root);
    primaryStage.setScene(sc);
    primaryStage.sizeToScene();
    primaryStage.show();
  }


  public void switchToChatScene()
  {
    Parent root;
    try {
      root = FXMLLoader.load(getClass().getResource("/ChatScene.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    Scene sc = new Scene(root);
    primaryStage.setScene(sc);
    primaryStage.sizeToScene();
  }
}
