package it.polimi.ingsw.example.server.messages;

import it.polimi.ingsw.example.client.ServerHandler;
import it.polimi.ingsw.example.server.ClientHandler;

import java.io.IOException;
import java.util.UUID;


/**
 * A message representing an answer to a command.
 */
public abstract class AnswerMsg extends NetworkMessage
{
  UUID parentIdentifier;


  /**
   * Initializes the answer message.
   * @param parent The CommandMsg being answered.
   */
  public AnswerMsg(CommandMsg parent)
  {
    this.parentIdentifier = parent.getIdentifier();
  }


  /**
   * Returns the identifier of the message being answered.
   * @return The UUID of the answered message.
   */
  public UUID getParentIdentifier()
  {
    return parentIdentifier;
  }


  /**
   * Method invoked in the client to process the message.
   */
  public abstract void processMessage(ServerHandler serverHandler) throws IOException;
}
