package it.polimi.ingsw.example.server.messages;

/**
 * The answer message from GuessMsg. Contains whether the guess was
 * correct or not, and the hint string if it was not correct.
 */
public class GuessAnswerMsg extends AnswerMsg
{
  public enum Status
  {
    INVALID,
    INCORRECT,
    CORRECT
  }
  Status guessStatus;
  String answer;


  /**
   * Create a new GuessAnswerMsg.
   * @param parent The message being answered.
   * @param guessStatus Whether the guess was invalid, correct or incorrect.
   * @param answer The guess string, or null if the guess was correct.
   */
  public GuessAnswerMsg(CommandMsg parent, Status guessStatus, String answer)
  {
    super(parent);
    this.guessStatus = guessStatus;
    this.answer = answer;
  }


  /**
   * The response to the guess as returned by the server.
   * @return The guess response.
   */
  public Status getGuessStatus()
  {
    return guessStatus;
  }


  /**
   * The hint string as returned by the server.
   * @return The hint string.
   */
  public String getAnswer()
  {
    return answer;
  }
}
