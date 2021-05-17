/*
     Author: Moose OMalley
       Date: 17-May-2021
     GitHub: https://github.com/MooseValley/Math---Gambling-with-Martingale-Strategy
    License: MIT License.  (Use code for whatever you want, but give me credit).
Description: Explore Martingale Strategy betting strategy for American and European Roulette Wheels.
Inspiration: Gambling with the Martingale Strategy - Numberphile, https://www.youtube.com/watch?v=zTsRGQj6VT4

*/
import java.security.SecureRandom;

public class MartingaleStrategy
{
   // American Roulette 0, 00, 1-35
   // European Roulette 0, 1-36
   public static final double CHANCE_OF_RED_WIN_AMERICAN = 1.0 * 35 / 37 / 2;
   public static final double CHANCE_OF_RED_WIN_EUROPEAN = 1.0 * 36 / 37 / 2;
   public static final double ORIGINAL_BANK_BALANCE      = 100.0;
   public static final double ORIGINAL_BET_AMOUNT        = 1.0;

   public static double chanceOfRedWin = CHANCE_OF_RED_WIN_AMERICAN;
   public static double currentBankBalance;
   public static double currentBetAmount;
   public static double maxBankBalance;

   public static SecureRandom generator = new SecureRandom();


   public static int playUntilBankrupt ()
   {
      int    iterations = 0;
      String resultStr  = "";

      currentBankBalance = ORIGINAL_BANK_BALANCE;
      currentBetAmount   = ORIGINAL_BET_AMOUNT;

      currentBankBalance = currentBankBalance - currentBetAmount;

      while (currentBankBalance > 0)
      {
         iterations++;

         double rand = generator.nextDouble();

         if (rand < chanceOfRedWin) // Have we WON ???
         {
            currentBankBalance = currentBankBalance + ORIGINAL_BET_AMOUNT;

            resultStr = "WIN: " + ORIGINAL_BET_AMOUNT;

            currentBetAmount   = ORIGINAL_BET_AMOUNT;
         }
         else
         {
            // We lost.  Double the bet.
            currentBankBalance = currentBankBalance - currentBetAmount;

            resultStr = "LOSS: " + currentBetAmount;

            currentBetAmount   = 2 * currentBetAmount;
         }

         if (maxBankBalance < currentBankBalance)
            maxBankBalance = currentBankBalance;

         //System.out.println (iterations + ". " +  resultStr + "   Balance: " + currentBankBalance);

      }

      //System.out.println ("-> Bankrupt after: " + iterations + " hands. " +
      //                    "   Balance: " + String.format ("%.2f", currentBankBalance) );

      return iterations;
   }


   public static void runSimulationForWheel (String wheelTypeStr)
   {
      if (wheelTypeStr.equals("AMERICAN") == true)
      {
         chanceOfRedWin = CHANCE_OF_RED_WIN_AMERICAN;
      }
      else if (wheelTypeStr.equals("EUROPEAN") == true)
      {
         chanceOfRedWin = CHANCE_OF_RED_WIN_EUROPEAN;
      }
      else
      {
         System.out.println ("ERROR: unknown Roulette Wheel type: '" + wheelTypeStr + "'.");
         System.exit(-1); // ERROR.
      }

      final int NUM_GAMES      = 100_000;

      int    totalIterations = 0;
      int    minIterations   = Integer.MAX_VALUE;
      int    maxIterations   = 0;
      double avgIterations   = 0.0;

      maxBankBalance = 0.0;

      System.out.println ();
      System.out.println ("Martingale Strategy for " + String.format ("%,d", NUM_GAMES) + " games of " + wheelTypeStr + " Roulette:" );
      //System.out.println ();

      for (int k = 0; k < NUM_GAMES; k++)
      {
         int iterations = playUntilBankrupt ();

         totalIterations += iterations;

         if (minIterations > iterations)
            minIterations = iterations;

         if (maxIterations < iterations)
            maxIterations = iterations;
      }

      avgIterations = totalIterations / NUM_GAMES;

      //System.out.println ();
      System.out.println ("* Hands to go Bankrupt: "      +
                          ", Min: "     + String.format ("%,d",  minIterations) +
                          ", Average: " + String.format ("%.0f", avgIterations) +
                          ", Max: "     + String.format ("%,d",  maxIterations) +
                          ", Higest bank balance achieved: $" + String.format ("%.2f", maxBankBalance)
                          );

   }


   public static void main (String[] args)
   {
      runSimulationForWheel ("AMERICAN");
      System.out.println ();

      runSimulationForWheel ("EUROPEAN");
      System.out.println ();
   }
}