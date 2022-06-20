package game;

import java.util.ArrayList;

interface GameInterface {
  /**
   * This function creates a deck of cards
   */
  public void createDeck() throws Exception;

  /**
   * This function shuffles the deck.
   */
  public void shuffleDeck() throws Exception;

  /**
   * This function places a bet, and throws an exception if the bet is invalid.
   * 
   * @param bet The amount of money you want to bet.
   */
  public void placeBet(int bet) throws Exception;

  /**
   * This function deals the hand.
   */
  public void dealHand() throws Exception;

  /**
   * This function is used to swap cards in the player's hand
   * 
   * @param swapId The id of the cards to be swapped.
   */
  public void swapCards(ArrayList<Integer> swapId) throws Exception;

  /**
   * This function is called at the end of each round to calculate the gain and
   * finish the round
   */
  public void endRound() throws Exception;

  /**
   * This function prints the statistics of the current game.
   */
  public void printStatistics() throws Exception;

  /**
   * This function prints the credits to the screen.
   */
  public void printCredits() throws Exception;

  /**
   * This function advices on what play to do.
   * 
   * @return An ArrayList of Integers
   */
  public ArrayList<Integer> getAdvice() throws Exception;
}
