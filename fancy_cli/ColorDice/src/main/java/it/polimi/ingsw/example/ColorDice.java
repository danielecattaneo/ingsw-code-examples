package it.polimi.ingsw.example;


public class ColorDice
{
  public static void main(String[] args)
  {
    DiceBag bag = new DiceBag(18);
    bag.dump();

    System.out.println("--------");
    while (bag.getSize() > 0) {
      Dice d = bag.draw();
      d.roll();

      System.out.println("Next dice: " + d);

      bag.dump();
    }
  }
}
