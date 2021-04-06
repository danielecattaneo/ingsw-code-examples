package it.polimi.ingsw.example.server;

import it.polimi.ingsw.example.server.model.Mastermind;
import it.polimi.ingsw.example.server.messages.AnswerMsg;
import it.polimi.ingsw.example.server.messages.CommandMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientHandler implements Runnable
{
  private Socket client;
  private ObjectOutputStream output;
  private ObjectInputStream input;
  private Mastermind game;


  ClientHandler(Socket client)
  {
    this.client = client;
    this.game = new Mastermind();
  }


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
    }

    try {
      client.close();
    } catch (IOException e) { }
  }


  private void handleClientConnection() throws IOException
  {
    try {
      while (true) {
        /* read commands from the client, process them, and send replies */
        Object next = input.readObject();
        CommandMsg command = (CommandMsg)next;
        command.processMessage(this);
      }
    } catch (ClassNotFoundException | ClassCastException e) {
      System.out.println("invalid stream from client");
    }
  }


  public Mastermind getGame()
  {
    return game;
  }


  public void sendAnswerMessage(AnswerMsg answerMsg) throws IOException
  {
    output.writeObject((Object)answerMsg);
  }
}
