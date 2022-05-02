package it.polimi.ingsw.example.client;

import it.polimi.ingsw.example.server.OutgoingMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;


/**
 *  Scene controller class for the scene TestScene
 */
public class ChatScene
{
  public TextArea logTextArea;
  public TextField textField;
  public Button sendBtn;


  public void initialize()
  {
    ChatClientApplication app = ChatClientApplication.getCurrentApplication();
    ServerHandler sh = app.getServerHandler();
    sh.setMessageArrivedObserver((msg) -> {
      Platform.runLater(() ->{
        logTextArea.appendText("<" + msg.getSender() + "> " + msg.getMessage() + "\n");
      });
    });
  }


  public void sendButtonClicked(ActionEvent event)
  {
    ChatClientApplication app = ChatClientApplication.getCurrentApplication();
    ServerHandler sh = app.getServerHandler();
    sh.sendMessage(new OutgoingMessage(textField.getText()));
  }
}
