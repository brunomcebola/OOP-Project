package game;

import cards.*;

public class GameTestMode extends Game {
  public GameTestMode(int credits) throws Exception {
    super(credits);
  }

  protected Deck generateDeck() throws Exception {
    Deck deck = new Deck();

    deck.generateCards();

    return deck;
  }
}
