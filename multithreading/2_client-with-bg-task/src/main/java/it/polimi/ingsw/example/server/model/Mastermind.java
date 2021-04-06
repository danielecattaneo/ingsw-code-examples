package it.polimi.ingsw.example.server.model;

import java.util.Random;


/**
 * The main model class for the Mastermind game.
 */
public class Mastermind
{
  public final static int NUM_DIGITS = 5;

  private Random random = new Random();
  private int secretNumber;


  /**
   * Create a game initialized with a new secret number.
   */
  public Mastermind()
  {
    newGame();
  }


  /**
   * Reset the secret number.
   */
  public void newGame()
  {
    secretNumber = random.nextInt(((int)Math.pow(10, NUM_DIGITS)) - 1);
  }


  /**
   * Check if a given number matches the secret number.
   * @param guess The number to check.
   * @return True if it matches the secret, false otherwise.
   */
  public boolean isGuessCorrect(int guess)
  {
    return guess == secretNumber;
  }


  /**
   * Returns a hint string for a given number.
   * @param guess The guessed number.
   * @return The hint string for the given number number.
   * @throws InvalidGuessException If the given number is negative or too large.
   */
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
