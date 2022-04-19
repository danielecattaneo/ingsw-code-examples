package it.polimi.ingsw.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/**
 * Server for the Mastermind game.
 */
public class Server
{
  public static void main(String[] args)
  {
    /* Read the port number from System.in. In your project you
     * could also use a configuration file for the same purpose.
     *   For simplicity we are not doing any error checking when
     * parsing the port number, but in a real project you need to
     * always handle invalid inputs! */
    Scanner scanner = new Scanner(System.in);
    System.out.println("Server port?");
    int socketPort = Integer.parseInt(scanner.nextLine());

    /* Open the server socket. */
    ServerSocket socket;
    try {
      socket = new ServerSocket(socketPort);
    } catch (IOException e) {
      System.out.println("cannot open server socket");
      System.exit(1);
      return;
    }

    while (true) {
      try {
        /* accepts connections; for every connection we accept,
         * create a new Thread executing a ClientHandler */
        Socket client = socket.accept();
        ClientHandler clientHandler = new ClientHandler(client);
        clientHandler.run();
      } catch (IOException e) {
        System.out.println("connection dropped");
      }
    }
  }
}
