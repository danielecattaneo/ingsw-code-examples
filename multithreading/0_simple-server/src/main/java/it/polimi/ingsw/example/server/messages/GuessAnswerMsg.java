package it.polimi.ingsw.example.server.messages;

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


  public GuessAnswerMsg(CommandMsg parent, Status guessStatus, String answer)
  {
    super(parent);
    this.guessStatus = guessStatus;
    this.answer = answer;
  }


  public Status getGuessStatus()
  {
    return guessStatus;
  }


  public String getAnswer()
  {
    return answer;
  }
}
