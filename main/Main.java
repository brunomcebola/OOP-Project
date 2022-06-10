package main;

import deck.*;

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

          System.out.println("Sinulation mode");
          System.out.println("----------");
          System.out.println("credit: " + credit);
          System.out.println("bet: " + bet);
          System.out.println("nbdeals: " + nbdeals);

          break;

        case "-c":
          System.out.println("Deck test mode");
          System.out.println("----------");

          // Card card = new Card(2, 1);
          // System.out.println(card);

          Deck deck = new Deck();
          String[] cards = { "AS", "TC" };
          deck.upload(cards);

          System.out.println(deck.withdrawCard());
          System.out.println(deck.withdrawCard());
          System.out.println(deck.withdrawCard());

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
