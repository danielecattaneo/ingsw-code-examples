package it.polimi.ingsw.example.client;

import it.polimi.ingsw.example.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client
{
  public static void main( String[] args )
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

      /* write a String to the server, and then get a String back */
      String str = scanner.nextLine();
      while (!"".equals(str)) {
        output.writeObject(str);
        String newStr = (String)input.readObject();
        System.out.println(newStr);
        str = scanner.nextLine();
      }
    } catch (IOException e) {
      System.out.println("server has died");
    } catch (ClassCastException | ClassNotFoundException e) {
      System.out.println("protocol violation");
    }

    try {
      server.close();
    } catch (IOException e) { }
  }
}
