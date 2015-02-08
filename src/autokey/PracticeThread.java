package autokey;

import java.util.Scanner;

/**
 *  A class to practice threading
 */
public class PracticeThread
   extends Thread
{
   private static final int SLEEP_TIME_IN_MILLIS = 4000;
   
   public void run()
   {
      Scanner input = new Scanner(System.in);
      System.out.print("What would you like to echo? (enter some text:) ");
      String toEcho = input.nextLine();
      try
      {
         Thread.sleep(SLEEP_TIME_IN_MILLIS);
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
      System.out.println(toEcho);
   }

   public static void main(String[] args)
   {
      new PracticeThread().start();
   }
}

