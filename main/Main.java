package main;

import java.util.ArrayList;

import game.*;

public class Main {
  public static void main(String args[]) {

    int bet = 0;
    int credit = 0;
    int nbdeals = 0;
    String cmd_file = "";
    String card_file = "";

    try {
      /*
       * if (args.length != 4) {
       * throw new InvalidNumberOfArgumentsException();
       * }
       */

      switch (args[0]) {
        case "-d":
          credit = Integer.parseInt(args[1]);
          cmd_file = args[2];
          card_file = args[3];

          System.out.println("Debug mode");
          System.out.println("----------");
          System.out.println("credit: " + credit);
          System.out.println("cmd_file: " + cmd_file);
          System.out.println("card_file: " + card_file);

          break;

        case "-s":
          credit = Integer.parseInt(args[1]);
          bet = Integer.parseInt(args[2]);
          nbdeals = Integer.parseInt(args[3]);

          System.out.println("Simulation mode");
          System.out.println("----------");
          System.out.println("credit: " + credit);
          System.out.println("bet: " + bet);
          System.out.println("nbdeals: " + nbdeals);

          break;

        case "-t":
          System.out.println("Game test");
          System.out.println("----------");

          GameDebug game = new GameDebug(5, "test/cmd-file.txt", "test/card-file.txt");

          game.createDeck();

          System.out.println();

          game.printDeck();

          System.out.println();

          game.printCmds();

          /*
           * Game.createDeck();
           * 
           * Game.shuffleDeck();
           * 
           * Game.printCredits();
           * 
           * Game.placeBet(5);
           * 
           * Game.printCredits();
           * 
           * Game.dealHand();
           * 
           * Game.printHand();
           * 
           * // ArrayList<Integer> swap = Game.getAdvice();
           * 
           * // Game.swapCards(swap);
           * 
           * // Game.printHand();
           * 
           * Game.endRound();
           * 
           * Game.printCredits();
           * 
           * Game.printStatistics();
           */

          break;

        default:
          System.out.println("Unknown mode");
          break;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
