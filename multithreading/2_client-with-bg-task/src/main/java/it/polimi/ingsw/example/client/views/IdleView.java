package it.polimi.ingsw.example.client.views;

/**
 * Fallback view used when there are no other views to be
 * displayed.
 */
public class IdleView extends View
{
  /** Characters used for the spinner animation */
  private static final String SPINNER = "\\|/-\\|/-";
  /** Ascii backspace character */
  private static final String BACKSPACE = "\010";


  @Override
  public void run()
  {
    synchronized (this) {
      try {
        this.wait(100);
      } catch (InterruptedException e) {}

      int spinnerIdx = 0;
      while (!shouldStopInteraction()) {
        String lastWaitMessage = SPINNER.charAt(spinnerIdx) + " Please wait...";
        System.out.print(lastWaitMessage);
        spinnerIdx = (spinnerIdx + 1) % SPINNER.length();

        try {
          this.wait(500);
        } catch (InterruptedException e) {}

        /* Erase the last wait message */
        for (int i=0; i<lastWaitMessage.length(); i++)
          System.out.print(BACKSPACE);
      }
    }
  }
}
