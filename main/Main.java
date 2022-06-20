package main;

import exceptions.main.InvalidNumberOfArgumentsException;
import game.*;

public class Main {
  public static void main(String args[]) {

    int bet = 0;
    int credit = 0;
    int nbdeals = 0;
    String cmd_file = "";
    String card_file = "";

    Game game;

    try {

      if (args.length != 4) {
        throw new InvalidNumberOfArgumentsException();
      }

      switch (args[0]) {
        case "-d":
          credit = Integer.parseInt(args[1]);
          cmd_file = args[2];
          card_file = args[3];

          game = new GameDebug(credit, cmd_file, card_file);

          ((GameDebug) game).run();

          break;

        case "-s":
          credit = Integer.parseInt(args[1]);
          bet = Integer.parseInt(args[2]);
          nbdeals = Integer.parseInt(args[3]);

          game = new GameSimulator(credit, bet, nbdeals);

          ((GameSimulator) game).run();

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
