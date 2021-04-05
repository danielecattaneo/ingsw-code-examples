package it.polimi.ingsw.example.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;


public class ServerAdapter
{
  private ScheduledExecutorService taskQueue = Executors.newSingleThreadScheduledExecutor();

  private Socket server;
  private ObjectOutputStream outputStm;
  private ObjectInputStream inputStm;

  private List<ServerObserver> observers = new ArrayList<>();


  public ServerAdapter(Socket server)
  {
    this.server = server;
    taskQueue.execute(() -> {
      try {
        outputStm = new ObjectOutputStream(server.getOutputStream());
        inputStm = new ObjectInputStream(server.getInputStream());
      } catch (IOException e) {
        handleCommunicationError(e);
      }
    });
  }


  public void addObserver(ServerObserver observer)
  {
    taskQueue.execute(() -> observers.add(observer));
  }


  public void removeObserver(ServerObserver observer)
  {
    taskQueue.execute(() -> observers.remove(observer));
  }


  private void notifyObservers(Consumer<ServerObserver> lambda)
  {
    for (ServerObserver observer: observers) {
      lambda.accept(observer);
    }
  }


  private void handleCommunicationError(Exception cause)
  {
    notifyObservers((ServerObserver obs) -> obs.communicationErrorOccurred(cause));
    try {
      server.close();
    } catch (IOException e) { }
    taskQueue.shutdown();
  }


  public void stop()
  {
    taskQueue.execute(() -> {
      try {
        server.close();
      } catch (IOException e) { }
    });
    taskQueue.shutdown();
  }


  public void requestConversion(String input)
  {
    taskQueue.execute(() -> {
      /* send the string to the server and get the new string back */
      String newStr;
      try {
        outputStm.writeObject(input);
        newStr = (String)inputStm.readObject();
      } catch (IOException | ClassNotFoundException e) {
        handleCommunicationError(e);
        return;
      }

      /* notify the observers that we got the string */
      String finalNewStr = newStr;
      notifyObservers((ServerObserver obs) -> obs.didReceiveConvertedString(input, finalNewStr));
    });
  }
}
