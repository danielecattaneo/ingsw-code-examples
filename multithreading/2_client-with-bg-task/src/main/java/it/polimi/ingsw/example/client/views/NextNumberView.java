package it.polimi.ingsw.example.client.views;

import it.polimi.ingsw.example.server.messages.GuessMsg;

import java.util.Scanner;


/**
 * View that handles asking a user the next guess.
 */
public class NextNumberView extends View
{
  @Override
  public void run()
  {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Guess a 5-digit number!");
    int guess = Integer.parseInt(scanner.nextLine());

    GuessMsg guessMsg = new GuessMsg(guess);
    getOwner().getServerHandler().sendCommandMessage(guessMsg);
  }
}
