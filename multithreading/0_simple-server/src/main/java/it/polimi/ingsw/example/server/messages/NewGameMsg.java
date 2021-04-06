package it.polimi.ingsw.example.server.messages;

import it.polimi.ingsw.example.server.ClientHandler;

import java.io.IOException;


/**
 * A message sent to request a new game to the server.
 */
public class NewGameMsg extends CommandMsg
{
  @Override
  public void processMessage(ClientHandler clientHandler) throws IOException
  {
    clientHandler.getGame().newGame();
  }
}
