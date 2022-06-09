package deck;

import java.util.*;
import exceptions.*;

public class Deck {
  private ArrayList<Card> cards;

  public Deck(int nbcards) throws InvalidCardFormatException {
    ArrayList<Card> temp = new ArrayList<Card>();

    for (int value = 1; value <= 13; value++) {
      for (int rank = 1; rank <= 4; rank++) {
        temp.add(new Card(value, rank));
      }
    }

    Collections.shuffle(temp, new Random());

    cards = new ArrayList<Card>();

    for (int i = 0; i < nbcards; i++) {
      cards.add(temp.get(i));
    }
  }

  public int getSize() {
    return cards.size();
  }

  public Card drawCard() {
    return cards.remove(0);
  }

}
