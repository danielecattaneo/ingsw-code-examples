package it.polimi.ingsw.example.client;

public interface ServerObserver
{
  void didReceiveConvertedString(String oldStr, String newStr);
}
