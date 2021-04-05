package it.polimi.ingsw.example.client;

import it.polimi.ingsw.example.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class Client implements Runnable
{
  public static void main( String[] args )
  {
    Client client = new Client();
    client.run();
  }


  @Override
  public void run()
  {
    Scanner scanner = new Scanner(System.in);

    System.out.println("IP address of server?");
    String ip = scanner.nextLine();

    Socket server;
    try {
      server = new Socket(ip, Server.SOCKET_PORT);
    } catch (IOException e) {
      System.out.println("server unreachable");
      return;
    }
    System.out.println("Connected");

    ServerAdapter serverAdapter;
    try {
      serverAdapter = new ServerAdapter(server);
    } catch (IOException e) {
      System.out.println("could not contact server");
      return;
    }

    String str = scanner.nextLine();
    while (!"".equals(str)) {
      Future<String> stringFuture = serverAdapter.requestConversion(str);
      String response = null;

      int seconds = 0;
      while (response == null) {
        System.out.println("been waiting for " + seconds + " seconds");
        try {
          response = stringFuture.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | TimeoutException e) {
        } catch (ExecutionException e) {
          System.out.println("server not reachable");
          return;
        }
        seconds++;
      }

      System.out.println(response);
      str = scanner.nextLine();
    }

    serverAdapter.stop();
  }
}
