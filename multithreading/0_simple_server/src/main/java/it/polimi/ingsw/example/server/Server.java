package it.polimi.ingsw.example.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server
{
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
        handleClientConnection(socket.accept());
      } catch (IOException e) {
        System.out.println("connection dropped");
      }
    }
  }


  private static void handleClientConnection(Socket client) throws IOException
  {
    System.out.println("Connected to " + client.getInetAddress());

    ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
    ObjectInputStream input = new ObjectInputStream(client.getInputStream());

    try {
      while (true) {
        /* read a String from the stream and write an uppercase string in response */
        Object next = input.readObject();
        String str = (String)next;
        output.writeObject(str.toUpperCase());
      }
    } catch (ClassNotFoundException | ClassCastException e) {
      System.out.println("invalid stream from client");
    }

    client.close();
  }
}
