package it.polimi.ingsw.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ChatServer implements Runnable
{
  private List<ClientHandler> clients = new ArrayList<>();


  @Override
  public void run()
  {
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
        ClientHandler clientHandler = new ClientHandler(client, this);
        synchronized (this) {
          clients.add(clientHandler);
        }
        Thread clientThread = new Thread(clientHandler);
        clientThread.start();
      } catch (IOException e) {
        System.out.println("connection dropped");
      }
    }
  }


  synchronized private void addClient(ClientHandler clientHandler)
  {
    clients.add(clientHandler);
  }


  synchronized public void removeClient(ClientHandler clientHandler)
  {
    clients.remove(clientHandler);
  }


  synchronized public void broadcastMessage(IncomingMessage message)
  {
    for (ClientHandler client: clients) {
      client.sendMessage(message);
    }
  }


  public static void main(String[] args)
  {
    ChatServer server = new ChatServer();
    server.run();
  }
}
