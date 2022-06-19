package game;

import java.util.ArrayList;

import cards.*;
import exceptions.gameExceptions.*;

public abstract class Game implements GameInterface {
  protected int bet;
  protected int currCredits;

  protected Deck deck;
  protected Hand hand;
  protected Statistics stats;

  public Game(int credits) throws Exception {
    this.bet = 0;

    this.hand = new Hand();
    this.deck = new Deck();

    this.currCredits = credits;

    this.stats = new Statistics(credits);
  }

  protected abstract ArrayList<Card> getCardsList() throws Exception;

  public final void createDeck() throws Exception {
    this.deck.uploadCards(this.getCardsList());
  }

  public final void shuffleDeck() throws Exception {
    this.deck.shuffle();
  }

  public final void dealHand() throws Exception {
    // create hand
    for (int i = 0; i < 5; i++)
      this.hand.appendCard(this.deck.drawCard());
  }

  public final void swapCards(ArrayList<Integer> swapId) throws Exception {
    for (int s : swapId)
      this.hand.swapCard(s, this.deck.drawCard());
  }

  public final void placeBet(int bet) throws Exception {
    if (bet > this.currCredits || bet < 1 || bet > 5)
      throw new InvalidBetValueException();

    this.bet = bet;
    this.currCredits -= bet;

    this.stats.registerBet(bet);
  }

  public final void saveStatistics(int handSel) {
    this.stats.registerHand(handSel);
  }

  public final void printStatistics() {
    System.out.println(stats);
  }

  public final void printCredits() {
    System.out.println("Current credits: " + this.currCredits);
  }

  public final void endRound() {
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

    int gain = 0;

    switch (this.analyseHand()) {
      case 1:
        if (bet == 5)
          gain = 4000;
        else
          gain = bet * 250;
        break;

      case 2:
      case 5:
        gain = bet * 50;
        break;

      case 3:
        gain = bet * 160;
        break;

      case 4:
        gain = bet * 80;
        break;

      case 6:
        gain = bet * 10;
        break;

      case 7:
        gain = bet * 7;
        break;

      case 8:
        gain = bet * 5;
        break;

      case 9:
        gain = bet * 3;
        break;

      case 10:
      case 11:
        gain = bet;

        break;

    }

    this.currCredits += gain;

    this.stats.registerGain(gain);
  }

  // Private Instance Methods

  private int analyseHand() {
    return 1;
  }

  // TODO: delete below - test porpuses only

  public void printHand() {
    int i = 1;
    System.out.println("Hand:");
    for (Card c : this.hand.getAllCards()) {
      System.out.println(i++ + ": " + c);
    }
    System.out.println();
  }

}
