package game;

import java.util.stream.IntStream;

import cards.*;
import exceptions.gameExceptions.*;

public abstract class Game {
  protected int bet;
  protected Hand hand;
  protected int[] stats;
  protected int currCredits;
  protected final int initCredits;

  private Deck deck;

  public Game(int credits) throws Exception {
    this.hand = new Hand();

    this.stats = new int[10];

    this.bet = 0;

    this.initCredits = credits;
    this.currCredits = credits;

    this.deck = this.generateDeck();
  }

  protected abstract Deck generateDeck() throws Exception;

  public abstract void playRound() throws Exception;

  // Game commands

  protected final void shuffleDeck() {
    this.deck.shuffle();
  }

  // d - Deal command

  protected final void dealHand() throws Exception {
    // create hand
    for (int i = 0; i < 5; i++)
      hand.appendCard(deck.drawCard());
  }

  // h - Hold (swap) command

  protected final void swapCards(int[] swap) throws Exception {
    for (int s : swap)
      hand.swapCard(s, deck.drawCard());
  }

  // b - Bet command

  protected final void placeBet(int bet) throws InvalidBetValueException {
    if (bet > this.currCredits || bet < 1 || bet > 5)
      throw new InvalidBetValueException();

    this.bet = bet;
    this.currCredits -= bet;
  }

  // $ - Credit command

  protected final void printCredits() {
    System.out.println("Current credits: " + this.currCredits);
  }

  // Statistics command

  protected final void saveStatistics(int sel) {
    this.stats[sel] += 1;
  }

  protected final void printStatistics() {
    System.out.println("Hand                  Nb   ");
    System.out.println("---------------------------");
    System.out.println("Jacks or Better       " + this.stats[0]);
    System.out.println("Two Pair              " + this.stats[1]);
    System.out.println("Three of a Kind       " + this.stats[2]);
    System.out.println("Straight              " + this.stats[3]);
    System.out.println("Flush                 " + this.stats[4]);
    System.out.println("Full house            " + this.stats[5]);
    System.out.println("Four of a Kind        " + this.stats[6]);
    System.out.println("Straight Flush        " + this.stats[7]);
    System.out.println("Royal Flush           " + this.stats[8]);
    System.out.println("Other                 " + this.stats[9]);
    System.out.println("---------------------------");
    System.out.println("Total                 " + IntStream.of(this.stats).sum());
    System.out.println("---------------------------");
    System.out.println("Credit             " + this.currCredits + " (" + this.getTheoReturn() + "%)");
  }

  // Finalize round command

  protected final void endRound() {
    /*
     * 1 -> Royal Flush
     * 2 -> Straight Flush
     * 3 -> Four Aces
     * 4 -> Four 2–4
     * 5 -> Four 5–K
     * 6 -> Full House
     * 7 -> Flush
     * 8 -> Straight
     * 9 -> Three of a Kind
     * 10 -> Two Pair
     * 11 -> Jacks or Better
     */

    int winnings = 0;

    switch (this.analyseHand()) {
      case 1:
        if (bet != 5)
          winnings = 4000;
        else
          winnings = bet * 250;
        break;

      case 2:
      case 5:
        winnings = bet * 50;
        break;

      case 3:
        winnings = bet * 160;
        break;

      case 4:
        winnings = bet * 80;
        break;

      case 6:
        winnings = bet * 10;
        break;

      case 7:
        winnings = bet * 7;
        break;

      case 8:
        winnings = bet * 5;
        break;

      case 9:
        winnings = bet * 3;
        break;

      case 10:
      case 11:
        winnings = bet;

        break;

    }

    this.currCredits += winnings;

  }

  // Private Instance Methods

  protected final double getTheoReturn() {
    return (this.currCredits * 100) / this.initCredits;
  }

  private int analyseHand() {
    return 1;
  }

}
