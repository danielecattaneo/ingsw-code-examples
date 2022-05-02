package it.polimi.ingsw.example.server;

import java.io.Serializable;


public class IncomingMessage implements Serializable
{
  private String sender;
  private String message;


  public IncomingMessage(String sender, String message)
  {
    this.sender = sender;
    this.message = message;
  }


  public String getMessage()
  {
    return message;
  }


  public String getSender()
  {
    return sender;
  }
}
