package it.polimi.ingsw.example.client;

import it.polimi.ingsw.example.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class Client implements Runnable, ServerObserver
{
  private ScheduledExecutorService taskQueue = Executors.newSingleThreadScheduledExecutor();
  private Scanner scanner;
  private ServerAdapter serverAdapter;

  ScheduledFuture nextTimePrintTask = null;


  public static void main( String[] args )
  {
    /* Instantiate a new Client which will also receive events from
     * the server by implementing the ServerObserver interface */
    Client client = new Client();
    client.run();
  }


  @Override
  public void run()
  {
    scanner = new Scanner(System.in);

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

    /* Create the adapter that will allow communication with the server
     * in background, and start running its thread */
    serverAdapter = new ServerAdapter(server);
    serverAdapter.addObserver(this);

    taskQueue.execute(() -> nextString());
  }


  private void nextString()
  {
    String str = scanner.nextLine();
    if ("".equals(str)) {
      stop();
      return;
    }

    serverAdapter.requestConversion(str);
    nextTimePrintTask = taskQueue.schedule(() -> printNextTimeout(0), 0, TimeUnit.SECONDS);
  }


  private void printNextTimeout(int seconds)
  {
    System.out.println("been waiting for " + seconds + " seconds");
    nextTimePrintTask = taskQueue.schedule(() -> printNextTimeout(seconds+1), 1, TimeUnit.SECONDS);
  }


  @Override
  public void didReceiveConvertedString(String oldStr, String newStr)
  {
    taskQueue.execute(() -> {
      if (nextTimePrintTask != null)
        nextTimePrintTask.cancel(false);
      System.out.println(newStr);
      nextString();
    });
  }


  @Override
  public void communicationErrorOccurred(Exception cause)
  {
    taskQueue.execute(() -> {
      System.out.println("communication error");
      cause.printStackTrace();
      stop();
    });
  }


  private void stop()
  {
    serverAdapter.stop();
    taskQueue.shutdown();
  }
}
