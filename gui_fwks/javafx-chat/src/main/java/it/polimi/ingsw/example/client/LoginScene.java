package it.polimi.ingsw.example.client;

import com.sun.javafx.PlatformUtil;
import it.polimi.ingsw.example.server.LoginMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import javax.swing.*;


public class LoginScene
{
  public TextField usernameBox;
  public TextField serverIpBox;
  public TextField serverPortBox;
  public Button connectBtn;


  public void initialize()
  {
    serverIpBox.setText("127.0.0.1");
    serverPortBox.setText("5555");
  }


  public void connectButtonClicked(ActionEvent event)
  {
    if (usernameBox.getText().length() == 0) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "Insert a valid username!", ButtonType.OK);
      alert.showAndWait();
      return;
    }

    String ip = serverIpBox.getText();
    int port;
    try {
      port = Integer.parseInt(serverPortBox.getText());
    } catch (NumberFormatException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR, "The port number is not valid!", ButtonType.OK);
      alert.showAndWait();
      return;
    }

    ChatClientApplication app = ChatClientApplication.getCurrentApplication();
    ServerHandler sh = app.getServerHandler();
    connectBtn.setDisable(true);
    sh.setConnectionCompleteObserver((ok) -> {
      Platform.runLater(() -> {
        if (!ok) {
          Alert alert = new Alert(Alert.AlertType.ERROR, "Connection not successful...", ButtonType.OK);
          alert.showAndWait();
          connectBtn.setDisable(false);
        } else {
          sh.sendMessage(new LoginMessage(usernameBox.getText()));
          sh.setConnectionCompleteObserver(null);
          app.switchToChatScene();
        }
      });
    });
    app.getServerHandler().attemptConnection(ip, port);
  }
}
