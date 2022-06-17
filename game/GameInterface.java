package game;

interface GameInterface {
  public void setPayTable() throws Exception;

  public void shuffleDeck() throws Exception;

  public void placeBet(int bet) throws Exception;

  public void dealHand() throws Exception;

  public void swapCards(int[] swapId) throws Exception;

  public void endRound() throws Exception;

  public void printStatistics() throws Exception;

  public void printCredits() throws Exception;
}
