package it.polimi.ingsw.example;

import java.util.Random;


public class Dice
{
  // U+2680 to U+2685
  private static final String[] DICE_FACES = {
    "\u2680",
    "\u2681",
    "\u2682",
    "\u2683",
    "\u2684",
    "\u2685"
  };
  private Color color;
  private String face;


  public Dice(Color color)
  {
    this.color = color;
    roll();
  }


  public Color getColor()
  {
    return color;
  }


  public void setColor(Color color)
  {
    this.color = color;
  }


  public void roll()
  {
    int count = DICE_FACES.length;
    Random rand = new Random();
    int index = rand.nextInt(count);
    this.face = DICE_FACES[index];
  }


  @Override
  public String toString()
  {
    return this.color + "[" + face + "]" + Color.RESET;
  }


  void dump()
  {
    System.out.println(this);
  }
}

