package it.polimi.ingsw.example.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * A class that represents the client inside the server.
 */
public class ClientHandler implements Runnable
{
  private Socket client;
  private ObjectOutputStream output;
  private ObjectInputStream input;
  private ChatServer server;


  /**
   * Initializes a new handler using a specific socket connected to
   * a client.
   * @param client The socket connection to the client.
   */
  ClientHandler(Socket client, ChatServer server)
  {
    this.client = client;
    this.server = server;
  }


  /**
   * Connects to the client and runs the event loop.
   */
  @Override
  public void run()
  {
    try {
      output = new ObjectOutputStream(client.getOutputStream());
      input = new ObjectInputStream(client.getInputStream());
    } catch (IOException e) {
      System.out.println("could not open connection to " + client.getInetAddress());
      return;
    }

    System.out.println("Connected to " + client.getInetAddress());

    try {
      handleClientConnection();
    } catch (IOException e) {
      System.out.println("client " + client.getInetAddress() + " connection dropped");
      e.printStackTrace();
    }

    try {
      client.close();
    } catch (IOException e) { }
    server.removeClient(this);
  }


  /**
   * An event loop that receives messages from the client and processes
   * them in the order they are received.
   * @throws IOException If a communication error occurs.
   */
  private void handleClientConnection() throws IOException
  {
    Object next;

    try {
      System.out.println("Waiting for login");
      next = input.readObject();
      LoginMessage login = (LoginMessage)next;
      String username = login.getUsername();
      System.out.println("Login received! Welcome to " + username);
      server.broadcastMessage(new IncomingMessage("system", "Welcome, " + username + "!"));

      while (true) {
        next = input.readObject();
        OutgoingMessage message = (OutgoingMessage)next;
        server.broadcastMessage(new IncomingMessage(username, message.getMessage()));
      }
    } catch (ClassNotFoundException | ClassCastException e) {
      System.out.println("invalid stream from client");
    }
  }


  synchronized void sendMessage(IncomingMessage msg)
  {
    try {
      output.writeObject(msg);
    } catch (IOException e) { }
  }
}
