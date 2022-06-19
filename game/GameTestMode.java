package game;

import java.util.ArrayList;

import cards.*;

public class GameTestMode extends Game {
  public GameTestMode(int credits) throws Exception {
    super(credits);
  }

  protected ArrayList<Card> getCardsList() throws Exception {
    return Card.generateAllCards();
  }
}
