package game;

import java.util.stream.IntStream;

public class Statistics {
  private int bet;
  private int gain;
  private int[] stats;
  private final int credits;

  public Statistics(int credits) {
    this.bet = 0;

    this.gain = 0;

    this.credits = credits;

    this.stats = new int[10];
  }

  public void registerBet(int bet) {
    this.bet += bet;
  }

  public void registerGain(int gain) {
    this.gain += gain;
  }

  public void registerHand(int hand) {
    this.stats[hand - 1] += 1;

  }

  @Override
  public String toString() {
    return ("Hand                  Nb   " + "\n" +
        "------------------------------" + "\n" +
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
        "------------------------------" + "\n" +
        "Total                 " + IntStream.of(this.stats).sum() + "\n" +
        "------------------------------" + "\n" +
        "Credit             " + this.currCredits() + " (" + this.getTheoReturn() + "%)");
  }

  private int currCredits() {
    return this.credits - this.bet + this.gain;
  }

  private double getTheoReturn() {
    return (this.gain / this.bet) * 100;
  }
}
