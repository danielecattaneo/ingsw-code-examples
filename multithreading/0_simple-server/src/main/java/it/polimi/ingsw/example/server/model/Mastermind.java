package it.polimi.ingsw.example.server.model;

import java.util.Random;


public class Mastermind
{
  public final static int NUM_DIGITS = 5;

  private Random random = new Random();
  private int secretNumber;


  public Mastermind()
  {
    newGame();
  }


  public void newGame()
  {
    secretNumber = random.nextInt(((int)Math.pow(10, NUM_DIGITS)) - 1);
  }


  public boolean isGuessCorrect(int guess)
  {
    return guess == secretNumber;
  }


  public String checkGuess(int guess) throws InvalidGuessException
  {
    StringBuilder sb = new StringBuilder();

    if (guess < 0 || guess >= (int)Math.pow(10, NUM_DIGITS))
      throw new InvalidGuessException();

    int secretTemp = secretNumber;
    int guessTemp = guess;
    for (int i=0; i<NUM_DIGITS; i++) {
      int secretDigit = secretTemp % 10;
      int guessDigit = guessTemp % 10;

      if (secretDigit > guessDigit) {
        sb.insert(0, "+");
      } else if (secretDigit < guessDigit) {
        sb.append("-");
      }

      secretTemp /= 10;
      guessTemp /= 10;
    }

    return sb.toString();
  }
}
