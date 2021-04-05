package it.polimi.ingsw.example.server.messages;

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
}
