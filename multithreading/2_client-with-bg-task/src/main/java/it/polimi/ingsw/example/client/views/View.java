package it.polimi.ingsw.example.client.views;

import it.polimi.ingsw.example.client.Client;


public abstract class View implements Runnable
{
  private Client owner;
  private boolean stopInteraction;


  public void setOwner(Client owner)
  {
    this.owner = owner;
  }


  public Client getOwner()
  {
    return owner;
  }


  abstract public void run();


  synchronized protected boolean shouldStopInteraction()
  {
    return stopInteraction;
  }


  public synchronized void stopInteraction()
  {
    stopInteraction = true;
    notifyAll();
  }
}
