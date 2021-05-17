import java.security.SecureRandom;

public class MartingaleStrategy
{
   // American Roulette 0, 00, 1-35
   // European Roulette 0, 1-36
   public static double CHANCE_OF_RED_WIN_AMERICAN = 1.0 * 35 / 37 / 2;
   public static double CHANCE_OF_RED_WIN_EUROPEAN = 1.0 * 36 / 37 / 2;
   public static double CHANCE_OF_RED_WIN = CHANCE_OF_RED_WIN_AMERICAN;

   public static double ORIGINAL_BANK_BALANCE = 100.0;
   public static double ORIGINAL_BET_AMOUNT   = 1.0;

   public static double currentBankBalance;
   public static double currentBetAmount;


   public static void main (String[] args)
   {
      SecureRandom generator = new SecureRandom();
      int    iterations = 0;
      String resultStr  = "";

      currentBankBalance = ORIGINAL_BANK_BALANCE;
      currentBetAmount   = ORIGINAL_BET_AMOUNT;

      currentBankBalance = currentBankBalance - currentBetAmount;

      while (currentBankBalance > 0)
      {
         iterations++;

         double rand = generator.nextDouble();

         if (rand < CHANCE_OF_RED_WIN) // Have we WON ???
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

         //System.out.println (iterations + ". " +  resultStr + "   Balance: " + currentBankBalance);

      }

      System.out.println ("Game Over: " + iterations + ". " +  "   Balance: " + currentBankBalance);
   }
}