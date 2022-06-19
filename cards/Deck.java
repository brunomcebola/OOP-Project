package cards;

import java.util.*;
import exceptions.deckExceptions.*;
import exceptions.cardGroupExceptions.*;

public class Deck extends CardGroup {
  private int nextCard;

  public Deck() {
    super();

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
