package cards;

import java.util.*;

import exceptions.cardExcepetions.*;
import exceptions.cardGroupExceptions.*;

public abstract class CardGroup {
  protected ArrayList<Card> cards;

  public CardGroup() {
    cards = new ArrayList<Card>();
  }

  public final void uploadCards(String[] cards)
      throws InvalidCardValueException, InvalidCardRankException,
      InvalidCardValueAndRankException, DuplicateCardException {

    this.cards = new ArrayList<Card>();

    Card tempCard;

    for (String card : cards) {
      tempCard = new Card(card.charAt(0), card.charAt(1));

      if (this.cards.contains(tempCard))
        throw new DuplicateCardException();

      this.cards.add(tempCard);
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

}
