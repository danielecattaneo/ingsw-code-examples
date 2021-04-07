package it.polimi.ingsw.example.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.*;


public class ServerAdapter
{
  private Socket server;
  private ObjectOutputStream outputStm;
  private ObjectInputStream inputStm;
  private ExecutorService executionQueue = Executors.newSingleThreadExecutor();


  public ServerAdapter(Socket server) throws IOException
  {
    this.server = server;
    this.outputStm = new ObjectOutputStream(server.getOutputStream());
    this.inputStm = new ObjectInputStream(server.getInputStream());
  }


  public void stop()
  {
    executionQueue.execute(() -> {
      try {
        server.close();
      } catch (IOException e) { }
    });
    executionQueue.shutdown();
  }


  public Future<String> requestConversion(String input)
  {
    return executionQueue.submit(() -> {
      outputStm.writeObject(input);
      return (String)inputStm.readObject();
    });
  }
}
