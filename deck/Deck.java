package deck;

import java.util.*;
import exceptions.cardExcepetions.*;
import exceptions.deckExceptions.*;

public class Deck {
  private int nbdraws;
  private ArrayList<Card> cards;

  public Deck() {
    nbdraws = 0;
    cards = new ArrayList<Card>();
  }

  public void generate() throws InvalidCardValueException, InvalidCardRankException, InvalidCardValueAndRankException {
    nbdraws = 0;
    cards = new ArrayList<Card>();

    for (int value = 1; value <= 13; value++) {
      for (int rank = 1; rank <= 4; rank++) {
        cards.add(new Card(value, rank));
      }
    }
  }

  public void upload(String[] cards)
      throws InvalidCardValueException, InvalidCardRankException, InvalidCardValueAndRankException,
      DuplicateCardException {
    nbdraws = 0;
    this.cards = new ArrayList<Card>();

    Card tempCard;

    for (String card : cards) {
      tempCard = new Card(card.charAt(0), card.charAt(1));

      if (this.cards.contains(tempCard))
        throw new DuplicateCardException();

      this.cards.add(tempCard);
    }
  }

  public void shuffle() {
    nbdraws = 0;
    Collections.shuffle(cards, new Random());
  }

  public int getSize() {
    return cards.size();
  }

  public Card withdrawCard() throws EmptyDeckException {
    if (nbdraws == cards.size())
      throw new EmptyDeckException();

    return cards.get(nbdraws++);
  }

  public int getNbOfCardsWithdrawn() {
    return nbdraws;
  }

}
