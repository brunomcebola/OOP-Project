package cards;

import java.util.*;

import exceptions.cardExcepetions.*;
import exceptions.cardGroupExceptions.*;

public abstract class CardGroup {
  protected ArrayList<Card> cards;

  public CardGroup() {
    cards = new ArrayList<Card>();
  }

  public final void uploadCards(ArrayList<Card> cardsList)
      throws InvalidCardValueException, InvalidCardRankException,
      InvalidCardValueAndRankException, DuplicateCardException {

    this.cards = new ArrayList<Card>();

    for (Card c : cardsList) {
      if (this.cards.contains(c))
        throw new DuplicateCardException(c.toString());

      this.cards.add(c);
    }
  }

  public final int getSize() {
    return cards.size();
  }

  public final ArrayList<Card> getAllCards() {
    return cards;
  }

  public final Card getCard(int position) throws InvalidPositionException {
    if (position >= cards.size())
      throw new InvalidPositionException();

    return cards.get(position);
  }

  /**
   * Reaganges the Hand (ArrayList<Card>) in a way that its sorted
   * by is value. Notice that the Ace has value of 1
   *
   * @return ArrayList<Card> which is the hand sorted
   */
  public ArrayList<Card> getSortedCards() {
    ArrayList<Card> sorted = cards;
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
