package it.polimi.ingsw.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class  DiceBag
{
  List<Dice> dice = new ArrayList<Dice>();


  public DiceBag(int size)
  {
    fill(size);
  }


  private void fill(int size)
  {
    int nextColorIdx = 0;
    for (int i = 0; i < size; i++) {
      Dice d = new Dice(Color.values()[nextColorIdx]);
      this.dice.add(d);
      nextColorIdx = (nextColorIdx + 1) % Color.values().length;
    }
  }


  public int getSize()
  {
    return dice.size();
  }


  public Dice draw()
  {
    int count = dice.size();
    if (count == 0)
      return null;

    Random rand = new Random();
    int index = rand.nextInt(count);
    Dice d = dice.get(index);
    this.dice.remove(d);
    return d;
  }


  @Override
  public String toString()
  {
    StringBuilder stringBuilder = new StringBuilder();
    int count = dice.size();
    stringBuilder.append("# elements = " + count + "\n");

    int i = 0;
    for (Dice d: dice) {
      if (i != 0) {
        if (i % 10 == 0)
          stringBuilder.append("\n");
        else
          stringBuilder.append(", ");
      }
      stringBuilder.append(d.toString());
      i++;
    }

    return stringBuilder.toString();
  }


  public void dump()
  {
    System.out.println(this);
  }
}

