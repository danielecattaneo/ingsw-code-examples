package it.polimi.ingsw.example.server.messages;

import it.polimi.ingsw.example.core.InvalidGuessException;
import it.polimi.ingsw.example.core.Mastermind;
import it.polimi.ingsw.example.server.ClientHandler;

import java.io.IOException;


public class GuessMsg extends CommandMsg
{
  private int numberToGuess;


  public GuessMsg(int numberToGuess)
  {
    this.numberToGuess = numberToGuess;
  }


  @Override
  public void processMessage(ClientHandler clientHandler) throws IOException
  {
    Mastermind game = clientHandler.getGame();
    GuessAnswerMsg answerMsg;

    if (game.isGuessCorrect(numberToGuess)) {
      answerMsg = new GuessAnswerMsg(this, GuessAnswerMsg.Status.CORRECT, null);
    } else {
      try {
        String result = game.checkGuess(numberToGuess);
        answerMsg = new GuessAnswerMsg(this, GuessAnswerMsg.Status.INCORRECT, result);
      } catch (InvalidGuessException e) {
        answerMsg = new GuessAnswerMsg(this, GuessAnswerMsg.Status.INVALID, null);
      }
    }

    clientHandler.sendAnswerMessage(answerMsg);
  }
}
