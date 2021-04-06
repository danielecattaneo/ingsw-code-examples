package it.polimi.ingsw.example.client;

import it.polimi.ingsw.example.client.views.IdleView;
import it.polimi.ingsw.example.client.views.NextNumberView;
import it.polimi.ingsw.example.client.views.View;
import it.polimi.ingsw.example.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


/**
 * Client for the Mastermind game.
 */
public class Client implements Runnable
{
  private ServerHandler serverHandler;
  private boolean shallTerminate;
  private View nextView;
  private View currentView;


  public static void main(String[] args)
  {
    /* Instantiate a new Client */
    Client client = new Client();
    client.run();
  }


  @Override
  public void run()
  {
    Scanner scanner = new Scanner(System.in);

    System.out.println("IP address of server?");
    String ip = scanner.nextLine();

    /* Open connection to the server and start a thread for handling
     * communication. */
    Socket server;
    try {
      server = new Socket(ip, Server.SOCKET_PORT);
    } catch (IOException e) {
      System.out.println("server unreachable");
      return;
    }
    serverHandler = new ServerHandler(server, this);
    Thread serverHandlerThread = new Thread(serverHandler);
    serverHandlerThread.start();

    nextView = new NextNumberView();
    runViewStateMachine();

    serverHandler.stop();
  }


  public ServerHandler getServerHandler()
  {
    return serverHandler;
  }


  private void runViewStateMachine()
  {
    boolean stop;

    synchronized (this) {
      stop = shallTerminate;
      currentView = nextView;
      nextView = null;
    }
    while (!stop) {
      if (currentView == null) {
        currentView = new IdleView();
      }
      currentView.setOwner(this);
      currentView.run();

      synchronized (this) {
        stop = shallTerminate;
        currentView = nextView;
        nextView = null;
      }
    }
  }


  public synchronized void transitionToView(View newView)
  {
    this.nextView = newView;
    currentView.stopInteraction();
  }


  public synchronized void terminate()
  {
    if (!shallTerminate) {
      shallTerminate = true;
      currentView.stopInteraction();
    }
  }
}
