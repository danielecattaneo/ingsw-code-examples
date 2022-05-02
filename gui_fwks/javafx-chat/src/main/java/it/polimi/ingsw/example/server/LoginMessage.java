package it.polimi.ingsw.example.server;

import java.io.Serializable;


public class LoginMessage implements Serializable
{
  private String username;


  public LoginMessage(String username)
  {
    this.username = username;
  }


  public String getUsername()
  {
    return username;
  }
}
