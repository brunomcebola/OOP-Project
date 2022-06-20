package cards;

import java.util.*;

import exceptions.cardExcepetions.*;
import exceptions.cardGroupExceptions.*;

public abstract class CardGroup {
  protected ArrayList<Card> cards;
  /**
     * Constructor of the Class
     */
  public CardGroup() {
    cards = new ArrayList<Card>();
  }

  /**
   * This function takes an ArrayList of Cards and adds them to the cards ArrayList.
   * 
   * @param cardsList The list of cards to be uploaded to the deck.
   */
  public final void uploadCards(ArrayList<Card> cardsList)
      throws InvalidCardValueException, InvalidCardRankException,
      InvalidCardValueAndRankException {

    this.cards = new ArrayList<Card>();

    for (Card c : cardsList) {
      this.cards.add(c);
    }
  }

  /**
   * Returns the number of cards in the deck.
   * 
   * @return The size of the cards arraylist.
   */
  public final int getSize() {
    return cards.size();
  }

  /**
   * This function returns a copy of the cards array.
   * 
   * @return An ArrayList of Card objects.
   */
  public final ArrayList<Card> getAllCards() {
    return cards;
  }

  /**
   * If the position is invalid, throw an exception.
   * 
   * @param position The position of the card in the hand.
   * @return A card object
   */
  public final Card getCard(int position) throws InvalidPositionException {
    if (position >= cards.size())
      throw new InvalidPositionException();

    return cards.get(position);
  }

  /**
     * Override of the toString method that returns a 
     * string representation of the CardGroup object.
     */
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();

    for (Card card : this.cards) {
      str.append(card + " ");
    }

    return str.toString();
  }

  /**
   * Reaganges the Hand (ArrayList<Card>) in a way that its sorted
   * by is value. Notice that the Ace has value of 1
   *
   * @return ArrayList<Card> which is the hand sorted
   */
  public ArrayList<Card> getSortedCards() {
    ArrayList<Card> sorted = new ArrayList<Card>(cards);
    Collections.sort(sorted, new Comparator<Card>() {
      @Override
      public int compare(Card card1, Card card2) {
        if (card1.getValue() > card2.getValue()) {
          return 1;
        }
        return -1;
      }
    });

    return sorted;
  }

}
