package game;

import java.util.ArrayList;

import cards.*;
import exceptions.gameExceptions.*;

public abstract class Game implements GameInterface {
  private int bet;
  private int currCredits;

  private Deck deck;
  protected Hand hand;
  private Statistics stats;

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
      throw new InvalidBetValueException(bet);

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
    int gain = 0;

    int handClassification = this.hand.classify();

    switch (handClassification) {
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

    this.stats.registerHand(handClassification);

    this.hand = new Hand();
  }

  // TODO: delete below - test porpuses only

  public void printHand() {
    int i = 1;
    System.out.println("Hand:");
    for (Card c : this.hand.getAllCards()) {
      System.out.println(i++ + ": " + c);
    }
  }

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
