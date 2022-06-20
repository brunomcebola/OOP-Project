package cards;

import java.util.*;
import exceptions.deckExceptions.*;
import exceptions.cardGroupExceptions.*;

public class Deck extends CardGroup {
  private int nextCard;

  /**
     * Constructor of the Class
     */
  public Deck() {
    super();

    this.nextCard = 0;
  }

  /**
   * Shuffle the cards in the deck, and reset the next card to be dealt to the first card in the deck.
   */
  public void shuffle() {
    Collections.shuffle(this.cards, new Random());

    this.nextCard = 0;
  }

  /**
   * If the deck is empty, throw an EmptyDeckException, otherwise return the next card in the deck.
   * 
   * @return A card object
   */
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
