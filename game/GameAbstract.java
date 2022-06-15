package game;

import java.util.stream.IntStream;

import cards.*;

public abstract class GameAbstract implements GameInterface {
  protected final int initial_credits;
  protected int current_credits;
  protected Deck deck;
  protected Hand hand;
  protected int[] stats;

  public GameAbstract(int credits) {
    this.deck = new Deck();
    this.hand = new Hand();
    
    this.stats = new int[10];

    initial_credits = credits;
    current_credits = credits;
  }

  public void saveStatistics(int sel) {
    stats[sel] += 1;
  }

  public void printStatistics() {
    System.out.println("Hand                  Nb   ");
    System.out.println("---------------------------");
    System.out.println("Jacks or Better       " + stats[0]);
    System.out.println("Two Pair              " + stats[1]);
    System.out.println("Three of a Kind       " + stats[2]);
    System.out.println("Straight              " + stats[3]);
    System.out.println("Flush                 " + stats[4]);
    System.out.println("Full house            " + stats[5]);
    System.out.println("Four of a Kind        " + stats[6]);
    System.out.println("Straight Flush        " + stats[7]);
    System.out.println("Royal Flush           " + stats[8]);
    System.out.println("Other                 " + stats[9]);
    System.out.println("---------------------------");
    System.out.println("Total                 " + IntStream.of(stats).sum());
    System.out.println("---------------------------");
    System.out.println("Credit             " + current_credits + " (" + getTheoReturn() + "%)");
  }

  public final void printCredits() {
    System.out.println("Current credits: " + current_credits);
  }

  // Private Instance Methods

  private final double getTheoReturn() {
    return (current_credits * 100) / initial_credits;
  }

}
