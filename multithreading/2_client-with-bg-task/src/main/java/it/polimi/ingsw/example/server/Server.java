package it.polimi.ingsw.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Server for the Mastermind game.
 */
public class Server
{
  /**
   * The socket port where the server listens to client connections.
   * @implNote In a real project, this must not be a constant!
   */
  public final static int SOCKET_PORT = 7777;


  public static void main(String[] args)
  {
    ServerSocket socket;
    try {
      socket = new ServerSocket(SOCKET_PORT);
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
        Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
        thread.start();
      } catch (IOException e) {
        System.out.println("connection dropped");
      }
    }
  }
}
