package it.polimi.ingsw.example.client;

import it.polimi.ingsw.example.server.IncomingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class ServerHandler
{
  private Thread thread;
  private Socket server;
  private ObjectOutputStream output;
  private ObjectInputStream input;
  private Consumer<Boolean> connectionCompleteObserver;
  private Consumer<IncomingMessage> messageArrivedObserver;
  private List<IncomingMessage> delayedMessages = new ArrayList<>();
  private Runnable connectionClosedObserver;


  synchronized public void setConnectionCompleteObserver(Consumer<Boolean> connectionCompleteObserver)
  {
    this.connectionCompleteObserver = connectionCompleteObserver;
  }


  synchronized public void setMessageArrivedObserver(Consumer<IncomingMessage> messageArrivedObserver)
  {
    this.messageArrivedObserver = messageArrivedObserver;
    for (IncomingMessage msg: delayedMessages) {
      messageArrivedObserver.accept(msg);
    }
    delayedMessages.clear();
  }


  synchronized public void setConnectionClosedObserver(Runnable connectionClosedObserver)
  {
    this.connectionClosedObserver = connectionClosedObserver;
  }


  synchronized public void notifyConnectionComplete(boolean success)
  {
    if (connectionCompleteObserver != null)
      connectionCompleteObserver.accept(success);
  }


  synchronized public void notifyMessageArrived(IncomingMessage msg)
  {
    if (messageArrivedObserver != null)
      messageArrivedObserver.accept(msg);
    else
      delayedMessages.add(msg);
  }


  synchronized public void notifyConnectionClosed()
  {
    if (connectionClosedObserver != null)
      connectionClosedObserver.run();
  }


  public void attemptConnection(String ip, int port)
  {
    thread = new Thread(() -> connectionThread(ip, port));
    thread.start();
  }


  private void connectionThread(String ip, int port)
  {
    boolean success = openConnection(ip, port);
    notifyConnectionComplete(success);
    if (!success)
      return;

    try {
      while (true) {
        /* this loop terminates when the socket is closed */
        IncomingMessage msg = (IncomingMessage) input.readObject();
        notifyMessageArrived(msg);
      }
    } catch (IOException e) {
      System.out.println(e);
    } catch (ClassNotFoundException | ClassCastException e) {
      System.out.println("protocol violation");
    }

    closeConnection();
  }


  synchronized private boolean openConnection(String ip, int port)
  {
    try {
      server = new Socket(ip, port);
    } catch (IOException e) {
      return false;
    }

    try {
      output = new ObjectOutputStream(server.getOutputStream());
      input = new ObjectInputStream(server.getInputStream());
    } catch (IOException e) {
      output = null;
      input = null;
      try {
        server.close();
      } catch (IOException e2) { }
      return false;
    }

    return true;
  }


  synchronized public void sendMessage(Object o)
  {
    assert(output != null);
    try {
      output.writeObject(o);
      output.flush();
      output.reset();
    } catch (IOException e) { }
  }


  synchronized public void closeConnection()
  {
    if (!server.isClosed()) {
      try {
        server.close();
      } catch (IOException e) { }
      notifyConnectionClosed();
    }
    output = null;
    input = null;
    thread = null;
  }


  synchronized public boolean isConnected()
  {
    return thread != null;
  }
}
