package game;

import java.util.stream.IntStream;

public class Statistics {
  private int bet;
  private int gain;
  private int[] stats;
  private final int credits;

  /**
   * The class constructor
   * 
   * @param credits initial credits of the player
   */
  public Statistics(int credits) {
    this.bet = 0;

    this.gain = 0;

    this.credits = credits;

    this.stats = new int[10];
  }

  /**
   * This function adds the bet to the total bet.
   * 
   * @param bet The amount of money the player has bet.
   */
  public void registerBet(int bet) {
    this.bet += bet;
  }

  /**
   * This function adds the value of the parameter gain to the value of the
   * instance variable gain.
   * 
   * @param gain The amount of money gained from the sale of the item.
   */
  public void registerGain(int gain) {
    this.gain += gain;
  }

  /**
   * This function takes in a hand and increments the corresponding index in the
   * stats array.
   * 
   * @param hand the hand that was played (1-3)
   */
  public void registerHand(int hand) {
    this.stats[hand - 1] += 1;

  }

  @Override
  // A method that returns a string representation of the object.
  public String toString() {
    return ("Hand                  Nb   " + "\n" +
        "--------------------------------" + "\n" +
        "Jacks or Better       " + this.stats[8] + "\n" +
        "Two Pair              " + this.stats[7] + "\n" +
        "Three of a Kind       " + this.stats[6] + "\n" +
        "Straight              " + this.stats[5] + "\n" +
        "Flush                 " + this.stats[4] + "\n" +
        "Full house            " + this.stats[3] + "\n" +
        "Four of a Kind        " + this.stats[2] + "\n" +
        "Straight Flush        " + this.stats[1] + "\n" +
        "Royal Flush           " + this.stats[0] + "\n" +
        "Other                 " + this.stats[9] + "\n" +
        "--------------------------------" + "\n" +
        "Total                 " + IntStream.of(this.stats).sum() + "\n" +
        "--------------------------------" + "\n" +
        "Credit             " + this.currCredits() + " (" + this.getTheoReturn() + "%)");
  }

  /**
   * It returns the current credits of the player.
   * 
   * @return The current credits of the player.
   */
  private int currCredits() {
    return this.credits - this.bet + this.gain;
  }

  /**
   * It returns the percentage of the bet that the player won
   * 
   * @return Theoretical return
   */
  private double getTheoReturn() {
    return ((double) this.gain / (double) this.bet) * 100;
  }
}
