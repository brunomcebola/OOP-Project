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

  public void playRound() throws Exception {
    this.shuffleDeck();

    this.placeBet(5);

    this.dealHand();

    int swap[] = { 1, 2 };

    this.swapCards(swap);

    this.endRound();
  }
}
