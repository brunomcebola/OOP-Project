package game;

import java.util.ArrayList;

import cards.*;
import exceptions.gameExceptions.*;

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
    }
    {
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

    if (this.hand.getSize() == 0) 
      throw new InvalidAdviceException();

    if ((hold = this.hand.checkStraightFlush(5)) != null) {
      if (this.verbose)
        System.out.println("1. Straight flush, four of a kind, royal flush");

      return holdToSwap(hold); // 1
    }

    if ((hold = this.hand.checkFourOfAKind()) != null) {
      if (this.verbose)
        System.out.println("1. Straight flush, four of a kind, royal flush");

      return holdToSwap(hold); // 1
    }

    if ((hold = this.hand.checkRoyalFlush(5)) != null) {
      if (this.verbose)
        System.out.println("1. Straight flush, four of a kind, royal flush");

      return holdToSwap(hold); // 1
    }

    if ((hold = this.hand.checkRoyalFlush(4)) != null) {
      if (this.verbose)
        System.out.println("2. 4 to a royal flush");

      return holdToSwap(hold); // 2
    }

    if ((hold = this.hand.checkThreeAces()) != null) {
      if (this.verbose)
        System.out.println("3. Three aces");

      return holdToSwap(hold); // 3
    }

    if ((hold = this.hand.checkStraight()) != null) {
      if (this.verbose)
        System.out.println("4. Straight, flush, full house");

      return holdToSwap(hold); // 4
    }

    if ((hold = this.hand.checkFlush('N', 5)) != null) {
      if (this.verbose)
        System.out.println("4. Straight, flush, full house");

      return holdToSwap(hold); // 4
    }

    if ((hold = this.hand.checkFullHouse()) != null) {
      if (this.verbose)
        System.out.println("4. Straight, flush, full house");

      return holdToSwap(hold); // 4
    }

    if ((hold = this.hand.checkThreeOfAKind()) != null) {
      if (this.verbose)
        System.out.println("5. Three of a kind (except aces)");

      return holdToSwap(hold); // 5
    }

    if ((hold = this.hand.checkStraightFlush(4)) != null) {
      if (this.verbose)
        System.out.println("6. 4 to a straight flush");

      return holdToSwap(hold); // 6
    }

    if ((hold = this.hand.checkTwoPair()) != null) {
      if (this.verbose)
        System.out.println("7. Two pair");

      return holdToSwap(hold); // 7
    }

    if ((hold = this.hand.checkHighPair()) != null) {
      if (this.verbose)
        System.out.println("8. High pair");

      return holdToSwap(hold); // 8
    }

    if ((hold = this.hand.checkFlush('N', 4)) != null) {
      if (this.verbose)
        System.out.println("9. 4 to a flush");

      return holdToSwap(hold); // 9
    }

    if ((hold = this.hand.checkRoyalFlush(3)) != null) {
      if (this.verbose)
        System.out.println("10. 3 to a royal flush");

      return holdToSwap(hold); // 10
    }

    if ((hold = this.hand.checkOusideStraight(4)) != null) {
      if (this.verbose)
        System.out.println("11. 4 to an outside straight");

      return holdToSwap(hold); // 11
    }

    if ((hold = this.hand.checkPair()) != null) {
      if (this.verbose)
        System.out.println("12. Low pair");

      return holdToSwap(hold); // 12
    }

    if ((hold = this.hand.checkAKQJUnsuited()) != null) {
      if (this.verbose)
        System.out.println("13. AKQJ unsuited");

      return holdToSwap(hold); // 13 AKQJ unsuited
    }

    if ((hold = this.hand.checkThreeToStraightFlush(1)) != null) {
      if (this.verbose)
        System.out.println("14. 3 to a straight flush (type 1)");

      return holdToSwap(hold); // 14
    }

    if ((hold = this.hand.checkFourToInsideWithHigh(3)) != null) {
      if (this.verbose)
        System.out.println("15. 4 to an inside straight with 3 high cards");

      return holdToSwap(hold); // 15
    }

    if ((hold = this.hand.checkQJS()) != null) {
      if (this.verbose)
        System.out.println("16. QJ suited");

      return holdToSwap(hold); // 16 QJ suited
    }

    if ((hold = this.hand.checkFlush('H', 2)) != null) {
      if (this.verbose)
        System.out.println("17. 3 to a flush with 2 high cards");

      return holdToSwap(hold); // 17
    }

    if ((hold = this.hand.checkTwoSHC()) != null) {
      if (this.verbose)
        System.out.println("18. 2 suited high cards");

      return holdToSwap(hold); // 18 Two suited high cards
    }

    if ((hold = this.hand.checkFourToInsideWithHigh(2)) != null) {
      if (this.verbose)
        System.out.println("19. 4 to an inside straight with 2 high cards");

      return holdToSwap(hold); // 19
    }

    if ((hold = this.hand.checkThreeToStraightFlush(2)) != null) {
      if (this.verbose)
        System.out.println("20. 3 to a straight flush (type 2)");
      return holdToSwap(hold); // 20
    }

    if ((hold = this.hand.checkFourToInsideWithHigh(1)) != null) {
      if (this.verbose)
        System.out.println("21. 4 to an inside straight with 1 high card");

      return holdToSwap(hold); // 21
    }

    if ((hold = this.hand.checkKQJUnsuited()) != null) {
      if (this.verbose)
        System.out.println("22. KQJ unsuited");

      return holdToSwap(hold); // 22
    }

    if ((hold = this.hand.checkPairSuits(11, 10, 'S')) != null) {
      if (this.verbose)
        System.out.println("23. JT suited");

      return holdToSwap(hold); // 23 check JT suited
    }

    if ((hold = this.hand.checkPairSuits(12, 11, 'U')) != null) {
      if (this.verbose)
        System.out.println("24. QJ unsuited");

      return holdToSwap(hold); // 24 check QJ unsuited
    }

    if ((hold = this.hand.checkFlush('H', 1)) != null) {
      if (this.verbose)
        System.out.println("25. 3 to a flush with 1 high card");

      return holdToSwap(hold); // 25
    }

    if ((hold = this.hand.checkPairSuits(12, 10, 'S')) != null) {
      if (this.verbose)
        System.out.println("26. QT suited");

      return holdToSwap(hold); // 26 check QT suited
    }

    if ((hold = this.hand.checkThreeToStraightFlush(3)) != null) {
      if (this.verbose)
        System.out.println("27. 3 to a straight flush (type 3)");

      return holdToSwap(hold); // 27 type 3
    }

    if ((hold = this.hand.checkPairSuits(13, 11, 'U')) != null) {
      if (this.verbose)
        System.out.println("28. KQ, KJ unsuited");

      return holdToSwap(hold); // 28 check KJ unsuited
    }
    if ((hold = this.hand.checkPairSuits(13, 12, 'U')) != null) {
      if (this.verbose)
        System.out.println("28. KQ, KJ unsuited");

      return holdToSwap(hold); // 28 check KQ unsuited
    }

    if ((hold = this.hand.checkAce()) != null) {
      if (this.verbose)
        System.out.println("29. Ace");

      return holdToSwap(hold); // 29
    }

    if ((hold = this.hand.checkPairSuits(13, 10, 'S')) != null) {
      if (this.verbose)
        System.out.println("30. KT suited");

      return holdToSwap(hold); // 30
    }

    if ((hold = this.hand.checkForRoyalCard()) != null) {
      if (this.verbose)
        System.out.println("31. Jack, Queen or King");

      return holdToSwap(hold); // 31
    }

    if ((hold = this.hand.checkInsideStraight(4, false)) != null) {
      if (this.verbose)
        System.out.println("32. 4 to an inside straight with no high cards");

      return holdToSwap(hold); // 32
    }

    if ((hold = this.hand.checkFlush('N', 3)) != null) {
      if (this.verbose)
        System.out.println("33. 3 to a flush with no high cards");

      return holdToSwap(hold); // 33
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
