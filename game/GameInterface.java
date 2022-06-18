package game;

import java.util.ArrayList;

interface GameInterface {
  public void generateDeck() throws Exception;

  public void shuffleDeck() throws Exception;

  public void placeBet(int bet) throws Exception;

  public void dealHand() throws Exception;

  public void swapCards(ArrayList<Integer> swapId) throws Exception;

  public void endRound() throws Exception;

  public void printStatistics() throws Exception;

  public void printCredits() throws Exception;

  // public ArrayList<Integer> getAdvice() throws Exception;
}
