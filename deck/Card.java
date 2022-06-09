package deck;

import exceptions.*;

/*
 * Card explanation:
 * 
 * value => 1, 2, ..., 12, 13 maps to A, 2, ..., Q, K
 * rank => 1, 2, 3, 4 maps to Spades, Hearts, Clubs, Diamonds
 * 
 */

public class Card {
  private static final String[] ranks = { "Spades", "Hearts", "Clubs", "Diamonds" };

  private final int value;
  private final int rank;

  public Card(int value, int rank) throws InvalidCardFormatException {

    if (value < 1 || value > 13 || rank < 1 || rank > 4) {
      throw new InvalidCardFormatException();
    }

    this.value = value;
    this.rank = rank;
  }

  public String toString() {
    if (value >= 2 && value <= 10)
      return Integer.toString(value) + " of " + ranks[rank - 1];

    if (value == 1)
      return "Ace" + " of " + ranks[rank - 1];

    if (value == 11)
      return "Jack" + " of " + ranks[rank - 1];

    if (value == 12)
      return "Queen" + " of " + ranks[rank - 1];

    return "King" + " of " + ranks[rank - 1];

  }

  @Override
  public int hashCode() {
    int res = 1;
    res = res * 31 + Math.min(value, rank);
    res = res * 31 + Math.max(value, rank);
    return res;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (!(obj instanceof Card))
      return false;

    Card other = (Card) obj;

    if (rank != other.rank)
      return false;

    if (value != other.value)
      return false;

    return true;
  }
}
