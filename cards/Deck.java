package cards;

import java.util.*;
import exceptions.cardExcepetions.*;
import exceptions.deckExceptions.*;
import exceptions.cardGroupExceptions.*;

public class Deck extends CardGroup {
  private int nextCard;

  public Deck() {
    super();

    this.nextCard = 0;
  }

  public void generateCards()
      throws InvalidCardValueException, InvalidCardRankException, InvalidCardValueAndRankException {

    this.cards = new ArrayList<Card>();

    for (int value = 1; value <= 13; value++) {
      for (int rank = 1; rank <= 4; rank++) {
        this.cards.add(new Card(value, rank));
      }
    }

    this.nextCard = 0;
  }

  public void shuffle() {
    Collections.shuffle(this.cards, new Random());
    this.nextCard = 0;
  }

  public Card drawCard() throws EmptyDeckException {
    try {
      Card tmpCard = this.getCard(this.nextCard);
      this.nextCard++;
      return tmpCard;
    } catch (InvalidPositionException e) {
      throw new EmptyDeckException();
    }
  }

}
