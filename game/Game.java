package game;

import java.util.ArrayList;

import cards.*;
import exceptions.gameExceptions.*;

public abstract class Game implements GameInterface {
  private int bet;
  private int lastBet;
  private int currCredits;

  private Deck deck;
  protected Hand hand;
  private Statistics stats;

  private boolean hasDealt;
  private boolean hasSwap;

  private boolean verbose;

  public Game(int credits) throws Exception {
    this.bet = 0;
    this.lastBet = 0;

    this.hasDealt = false;
    this.hasSwap = false;

    this.verbose = false;

    this.hand = new Hand();
    this.deck = new Deck();

    this.currCredits = credits;

    this.stats = new Statistics(credits);
  }

  protected abstract ArrayList<Card> getCardsList() throws Exception;

  public final void setVerbose(boolean sel) {
    this.verbose = sel;
  }

  public final void createDeck() throws Exception {
    this.deck.uploadCards(this.getCardsList());
  }

  public final void shuffleDeck() throws Exception {
    this.deck.shuffle();
  }

  public final void placeBet(int bet) throws Exception {
    // A bet cannot be placed after the deal
    if (this.hasDealt) {
      throw new InvalidBetException();
    }

    // A bet cannot be placed if another already made
    if (this.bet != 0) {
      throw new InvalidBetException();
    }

    // The bet value must be between 1 and 5 credits
    if (bet < 1 || bet > 5)
      throw new InvalidBetException();

    // The bet value cannot be higher than the current credits
    if (bet > this.currCredits)
      throw new InvalidBetException();

    this.bet = bet;
    this.lastBet = bet;

    this.currCredits -= bet;

    this.stats.registerBet(bet);

    if (this.verbose)
      System.out.println("player is betting " + bet);
  }

  public final void placeBet() throws Exception {
    if (lastBet == 0) {
      this.placeBet(5);
    } else {
      this.placeBet(lastBet);
    }

  }

  public final void dealHand() throws Exception {
    // cannot deal until bet is placed
    if (this.bet == 0)
      throw new InvalidDealException();

    // can only deal once per round
    if (this.hasDealt)
      throw new InvalidDealException();

    for (int i = 0; i < 5; i++)
      this.hand.appendCard(this.deck.drawCard());

    this.hasDealt = true;

    if (this.verbose)
      System.out.println("player's hand " + this.hand);
  }

  public final void swapCards(ArrayList<Integer> swapId) throws Exception {
    // Cannot swap cards before they are dealt
    if (this.hasDealt == false)
      throw new InvalidHoldException();

    // Can only swap cards once
    if (this.hasSwap)
      throw new InvalidHoldException();

    // cannot swap more than the existing cards
    if (swapId.size() > 5)
      throw new InvalidHoldException();

    for (int s : swapId)
      this.hand.swapCard(s, this.deck.drawCard());

    if (this.verbose)
      System.out.println("player's hand " + this.hand);
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
    if (this.hasDealt == false)
      return;

    int gain = 0;

    int handClass = this.hand.classify();

    switch (handClass) {
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

    if (handClass >= 3 && handClass <= 5) {
      handClass = 3;
    } else if (handClass >= 6) {
      handClass -= 2;
    }

    this.currCredits += gain;

    this.stats.registerGain(gain);

    this.stats.registerHand(handClass);

    this.bet = 0;

    this.hand = new Hand();

    this.hasDealt = false;

    if (this.verbose) {
      String[] hands = { "ROYAL FLUSH", "STRAIGHT FLUSH", "FOUR OF A KIND", "FULL HOUSE", "FLUSH", "STRAIGHT",
          "THREE OF  KIND", "TWO PAIR", "JACKS OR BETTER" };

      if (gain != 0)
        System.out.println("player wins with a " + hands[handClass - 1] + " and his credit is " + this.currCredits);
      else
        System.out.println("player loses and his credits is " + this.currCredits);

    }
  }

  // TODO: delete below - test porpuses only

  public void printDeck() {
    int i = 1;
    System.out.println("Deck (" + this.deck.getSize() + "):");
    for (Card c : this.deck.getAllCards()) {
      System.out.println(i++ + ": " + c);
    }
  }

  // Static methods

  /**
   * Creates the indexes of the cards wanted to be swaped based on the indexes of
   * the
   * card that are wanted to be hold
   * 
   * @param hold list of the indexes that are going to be hold
   * @return ArrayList<Integer>, indexes of the cards that are going to be swapped
   */
  public static ArrayList<Integer> holdToSwap(ArrayList<Integer> hold) {
    ArrayList<Integer> swap = new ArrayList<Integer>();

    swap.add(0);
    swap.add(1);
    swap.add(2);
    swap.add(3);
    swap.add(4);

    for (int h : hold) {
      swap.remove(Integer.valueOf(h));
    }

    return swap;
  }

}
