package it.polimi.ingsw.example.client;

import it.polimi.ingsw.example.server.Server;
import it.polimi.ingsw.example.server.messages.GuessAnswerMsg;
import it.polimi.ingsw.example.server.messages.GuessMsg;
import it.polimi.ingsw.example.server.messages.NewGameMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;


public class Client
{
  public static void main(String[] args)
  {
    Scanner scanner = new Scanner(System.in);

    System.out.println("IP address of server?");
    String ip = scanner.nextLine();

    /* open a connection to the server */
    Socket server;
    try {
      server = new Socket(ip, Server.SOCKET_PORT);
    } catch (IOException e) {
      System.out.println("server unreachable");
      return;
    }
    System.out.println("Connected");

    try {
      ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
      ObjectInputStream input = new ObjectInputStream(server.getInputStream());

      System.out.println("Guess a 5-digit number!");
      boolean stop = false;
      while (!stop) {
        int guess = Integer.parseInt(scanner.nextLine());
        GuessMsg guessMsg = new GuessMsg(guess);
        output.writeObject(guessMsg);

        GuessAnswerMsg answerMsg = (GuessAnswerMsg)input.readObject();
        GuessAnswerMsg.Status guessStatus = answerMsg.getGuessStatus();
        if (guessStatus == GuessAnswerMsg.Status.INCORRECT) {
          System.out.println("Try again! Current tally: " + answerMsg.getAnswer());
        } else if (guessStatus == GuessAnswerMsg.Status.INVALID) {
          System.out.println("Invalid guess! Try again!");
        } else {
          System.out.println("You won!");
          System.out.println("New game? (Y/N) ");
          String line = scanner.nextLine();
          if (line.length() > 0 && line.charAt(0) == 'Y') {
            output.writeObject(new NewGameMsg());
          } else {
            stop = true;
          }
        }
      }
    } catch (IOException e) {
      System.out.println("server has died");
    } catch (ClassNotFoundException | ClassCastException e) {
      System.out.println("protocol violation");
    }

    try {
      server.close();
    } catch (IOException e) { }
  }
}
