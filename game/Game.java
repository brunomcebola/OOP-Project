package game;

import java.util.ArrayList;

import cards.*;
import exceptions.game.*;

public abstract class Game implements GameInterface {
  private int bet;
  private int lastBet;
  private int currCredits;

  private Deck deck;
  private Hand hand;
  private Statistics stats;

  private boolean hasDealt;
  private boolean hasSwap;

  private boolean verbose;

  /**
   * The constructor of the class.
   * 
   * @param credits credits that the user begins with
   */
  public Game(int credits) throws Exception {
    this.bet = 0;
    this.lastBet = 0;

    this.hasDealt = false;
    /**
     * > This function swaps the cards in the player's hand with new cards from the
     * deck
     * 
     * @param swapId an array of integers that represent the index of the cards to
     *               be swapped
     */
    this.hasSwap = false;

    this.verbose = false;

    this.hand = new Hand();
    this.deck = new Deck();

    this.currCredits = credits;

    this.stats = new Statistics(credits);
  }

  /**
   * This function returns an ArrayList of Card objects.
   * 
   * @return An ArrayList of Card objects.
   */
  protected abstract ArrayList<Card> getCardsList() throws Exception;

  /**
   * This function sets the verbose flag to the value of the parameter.
   * 
   * @param sel The selection to set.
   */
  public final void setVerbose(boolean sel) {
    this.verbose = sel;
  }

  /**
   * This function creates a deck of cards and uploads.
   */
  public final void createDeck() throws Exception {
    this.deck.uploadCards(this.getCardsList());
  }

  /**
   * This function shuffles the deck.
   */
  public final void shuffleDeck() throws Exception {
    this.deck.shuffle();
  }

  /**
   * A bet cannot be placed after the deal, if another already made, if the bet
   * value is not between 1
   * and 5 credits, or if the bet value is higher than the current credits.
   * 
   * The first thing we do is check if the player has dealt. If so, we throw an
   * exception
   * 
   * @param bet The amount of credits to bet
   */
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
      throw new IllegalBetException();

    // The bet value cannot be higher than the current credits
    if (bet > this.currCredits)
      throw new IllegalBetException();

    this.bet = bet;
    this.lastBet = bet;

    this.currCredits -= bet;

    this.stats.registerBet(bet);

    if (this.verbose)
      System.out.println("player is betting " + this.bet);
  }

  /**
   * If the last bet was 0, place a bet of 5, otherwise place a bet of the last
   * bet
   */
  public final void placeBet() throws Exception {
    if (lastBet == 0) {
      this.placeBet(5);
    } else {
      this.placeBet(lastBet);
    }

  }

  /**
   * Deal the player a hand of five cards, but only if the player has placed a bet
   * and hasn't dealt
   * yet.
   * 
   * The first thing we do is check to see if the player has placed a bet. If not,
   * we throw an
   * exception
   */
  public final void dealHand() throws Exception {
    // cannot deal until bet is placed
    if (this.bet == 0)
      throw new InvalidDealException();

    // can only deal once per round
    if (this.hasDealt)
      throw new InvalidDealException();

    int i = 0;
    while (i < 5) {
      Card tmpCard = this.deck.drawCard();

      try {
        this.hand.appendCard(tmpCard);
        i++;
      } catch (Exception e) {
        if (this.verbose)
          System.out.println(e.getMessage());
      }
    }

    this.hasDealt = true;

    if (this.verbose)
      System.out.println("player's hand " + this.hand);
  }

  /**
   * This function swaps the cards in the player's hand with new cards from the
   * deck
   * 
   * @param swapId an array of integers that represent the index of the cards to
   *               be swapped
   */
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

    for (int s : swapId) {
      int i = 0;
      while (i == 0) {
        Card tmpCard = this.deck.drawCard();

        try {
          this.hand.swapCard(s, tmpCard);
          i++;
        } catch (Exception e) {
          if (this.verbose)
            System.out.println(e.getMessage());
        }
      }
    }

    if (this.verbose)
      System.out.println("player's hand " + this.hand);
  }

  /**
   * This function saves the statistics of the hand selected by the user.
   * 
   * @param handSel The index of the hand that was selected.
   */
  public final void saveStatistics(int handSel) {
    this.stats.registerHand(handSel);
  }

  /**
   * Prints the statistics to the console.
   */
  public final void printStatistics() {
    System.out.println(stats);
  }

  /**
   * This function prints the player's current credit.
   */
  public final void printCredits() {
    System.out.println("player's credit is " + this.currCredits);
  }

  /**
   * If the player has dealt, then calculate the gain based on the hand class and add it to the
   * player's credits, register the gain and hand class in the player's statistics, reset the bet,
   * hand, and hasDealt, and if verbose is true, print out the hand class and the player's credits
   */
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
    }
    if (handClass >= 6) {
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

  /**
   * It searches for all the 33 important hands in this type of variant and
   * returns
   * the indexes of the cards that are going to be swapped. This functions uses a
   * lot
   * of functions from another package that were made taking into account of the
   * importance of each hand, for example, when it started checking for an A, it
   * takes
   * into account that Pairs were already checked, therefore if there is an Ace it
   * assumes
   * that the Ace is alone.
   *
   * @return An Integer ArrayList, swap, with the indexes of the cards that are
   *         going to be swapped
   */
  public ArrayList<Integer> getAdvice() throws InvalidAdviceException {

    ArrayList<Integer> hold = new ArrayList<Integer>();
    ArrayList<Integer> swap = new ArrayList<Integer>();

    // only gives advice if there is a hand
    if (this.hand.getSize() == 0)
      throw new InvalidAdviceException();

    if ((hold = this.hand.checkStraightFlush(5)) != null) {
      return holdToSwap(hold, this.verbose); // 1
    }

    if ((hold = this.hand.checkFourOfAKind()) != null) {
      return holdToSwap(hold, this.verbose); // 1
    }

    if ((hold = this.hand.checkRoyalFlush(5)) != null) {
      return holdToSwap(hold, this.verbose); // 1
    }

    if ((hold = this.hand.checkRoyalFlush(4)) != null) {
      return holdToSwap(hold, this.verbose); // 2
    }

    if ((hold = this.hand.checkThreeAces()) != null) {
      return holdToSwap(hold, this.verbose); // 3
    }

    if ((hold = this.hand.checkStraight()) != null) {
      return holdToSwap(hold, this.verbose); // 4
    }

    if ((hold = this.hand.checkFlush('N', 5)) != null) {
      return holdToSwap(hold, this.verbose); // 4
    }

    if ((hold = this.hand.checkFullHouse()) != null) {
      return holdToSwap(hold, this.verbose); // 4
    }

    if ((hold = this.hand.checkThreeOfAKind()) != null) {
      return holdToSwap(hold, this.verbose); // 5
    }

    if ((hold = this.hand.checkStraightFlush(4)) != null) {
      return holdToSwap(hold, this.verbose); // 6
    }

    if ((hold = this.hand.checkTwoPair()) != null) {
      return holdToSwap(hold, this.verbose); // 7
    }

    if ((hold = this.hand.checkHighPair()) != null) {
      return holdToSwap(hold, this.verbose); // 8
    }

    if ((hold = this.hand.checkFlush('N', 4)) != null) {
      return holdToSwap(hold, this.verbose); // 9
    }

    if ((hold = this.hand.checkRoyalFlush(3)) != null) {
      return holdToSwap(hold, this.verbose); // 10
    }

    if ((hold = this.hand.checkOusideStraight(4)) != null) {
      return holdToSwap(hold, this.verbose); // 11
    }

    if ((hold = this.hand.checkPair()) != null) {
      return holdToSwap(hold, this.verbose); // 12
    }

    if ((hold = this.hand.checkAKQJUnsuited()) != null) {
      return holdToSwap(hold, this.verbose); // 13
    }

    if ((hold = this.hand.checkThreeToStraightFlush(1)) != null) {
      return holdToSwap(hold, this.verbose); // 14
    }

    if ((hold = this.hand.checkFourToInsideWithHigh(3)) != null) {
      return holdToSwap(hold, this.verbose); // 15
    }

    if ((hold = this.hand.checkQJS()) != null) {
      return holdToSwap(hold, this.verbose); // 16
    }

    if ((hold = this.hand.checkFlush('H', 2)) != null) {
      return holdToSwap(hold, this.verbose); // 17
    }

    if ((hold = this.hand.checkTwoSHC()) != null) {
      return holdToSwap(hold, this.verbose); // 18
    }

    if ((hold = this.hand.checkFourToInsideWithHigh(2)) != null) {
      return holdToSwap(hold, this.verbose); // 19
    }

    if ((hold = this.hand.checkThreeToStraightFlush(2)) != null) {
      return holdToSwap(hold, this.verbose); // 20
    }

    if ((hold = this.hand.checkFourToInsideWithHigh(1)) != null) {
      return holdToSwap(hold, this.verbose); // 21
    }

    if ((hold = this.hand.checkKQJUnsuited()) != null) {
      return holdToSwap(hold, this.verbose); // 22
    }

    if ((hold = this.hand.checkPairSuits(11, 10, 'S')) != null) {
      return holdToSwap(hold, this.verbose); // 23 check JT suited
    }

    if ((hold = this.hand.checkPairSuits(12, 11, 'U')) != null) {
      return holdToSwap(hold, this.verbose); // 24 check QJ unsuited
    }

    if ((hold = this.hand.checkFlush('H', 1)) != null) {
      return holdToSwap(hold, this.verbose); // 25
    }

    if ((hold = this.hand.checkPairSuits(12, 10, 'S')) != null) {
      return holdToSwap(hold, this.verbose); // 26 check QT suited
    }

    if ((hold = this.hand.checkThreeToStraightFlush(3)) != null) {
      return holdToSwap(hold, this.verbose); // 27 type 3
    }

    if ((hold = this.hand.checkPairSuits(13, 11, 'U')) != null) {
      return holdToSwap(hold, this.verbose); // 28 check KJ unsuited
    }
    if ((hold = this.hand.checkPairSuits(13, 12, 'U')) != null) {
      return holdToSwap(hold, this.verbose); // 28 check KQ unsuited
    }

    if ((hold = this.hand.checkAce()) != null) {
      return holdToSwap(hold, this.verbose); // 29
    }

    if ((hold = this.hand.checkPairSuits(13, 10, 'S')) != null) {
      return holdToSwap(hold, this.verbose); // 30
    }

    if ((hold = this.hand.checkForRoyalCard()) != null) {
      return holdToSwap(hold, this.verbose); // 31
    }

    if ((hold = this.hand.checkInsideStraight(4, false)) != null) {
      return holdToSwap(hold, this.verbose); // 32
    }

    if ((hold = this.hand.checkFlush('N', 3)) != null) {
      return holdToSwap(hold, this.verbose); // 33
    }

    swap.add(0);
    swap.add(1);
    swap.add(2);
    swap.add(3);
    swap.add(4);

    if (this.verbose)
      System.out.println("34. Discard everything");

    return swap; // discard all
  }

  /**
   * Creates the indexes of the cards wanted to be swaped based on the indexes of
   * the
   * card that are wanted to be hold
   * 
   * @param hold list of the indexes that are going to be hold
   * @return ArrayList<Integer>, indexes of the cards that are going to be swapped
   */
  public static ArrayList<Integer> holdToSwap(ArrayList<Integer> hold, boolean verbose) {
    ArrayList<Integer> swap = new ArrayList<Integer>();

    swap.add(0);
    swap.add(1);
    swap.add(2);
    swap.add(3);
    swap.add(4);

    for (int h : hold) {
      swap.remove(Integer.valueOf(h));
    }

    if (verbose) {
      StringBuilder str = new StringBuilder();

      str.append("player should hold cards ");

      for (int s : swap) {
        str.append(s + " ");
      }

      System.out.println(str.toString());
    }

    return swap;
  }

}
