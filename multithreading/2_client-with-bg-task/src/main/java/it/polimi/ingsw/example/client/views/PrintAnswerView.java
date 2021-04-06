package it.polimi.ingsw.example.client.views;

import it.polimi.ingsw.example.server.messages.GuessAnswerMsg;
import it.polimi.ingsw.example.server.messages.NewGameMsg;

import java.util.Scanner;


public class PrintAnswerView extends View
{
  private GuessAnswerMsg answerMsg;


  public PrintAnswerView(GuessAnswerMsg answerMsg)
  {
    this.answerMsg = answerMsg;
  }


  @Override
  public void run()
  {
    View nextView = new NextNumberView();

    GuessAnswerMsg.Status guessStatus = answerMsg.getGuessStatus();
    if (guessStatus == GuessAnswerMsg.Status.INCORRECT) {
      System.out.println("Try again! Current tally: " + answerMsg.getAnswer());
    } else if (guessStatus == GuessAnswerMsg.Status.INVALID) {
      System.out.println("Invalid guess! Try again!");
    } else {
      System.out.println("You won!");
      System.out.println("New game? (Y/N) ");
      Scanner scanner = new Scanner(System.in);
      String line = scanner.nextLine().toUpperCase();
      if (line.length() > 0 && line.charAt(0) == 'Y') {
        getOwner().getServerHandler().sendCommandMessage(new NewGameMsg());
      } else {
        getOwner().terminate();
        nextView = null;
      }
    }

    if (nextView != null)
      getOwner().transitionToView(nextView);
  }
}
