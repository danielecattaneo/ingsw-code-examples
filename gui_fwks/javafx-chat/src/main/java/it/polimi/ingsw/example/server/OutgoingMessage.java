package it.polimi.ingsw.example.server;

import java.io.Serializable;


public class OutgoingMessage implements Serializable
{
  private String message;


  public OutgoingMessage(String message)
  {
    this.message = message;
  }


  public String getMessage()
  {
    return message;
  }
}
