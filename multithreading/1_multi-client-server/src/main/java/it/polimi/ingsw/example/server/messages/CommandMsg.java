package it.polimi.ingsw.example.server.messages;

import it.polimi.ingsw.example.server.ClientHandler;

import java.io.IOException;


/**
 * A command message sent to the server.
 */
public abstract class CommandMsg extends NetworkMessage
{
  /**
   * Method invoked in the server to process the message.
   */
  public abstract void processMessage(ClientHandler clientHandler) throws IOException;
}
