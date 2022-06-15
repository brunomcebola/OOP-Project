package cards;

import exceptions.cardExcepetions.*;

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

  public Card(int value, int rank)
      throws InvalidCardValueException, InvalidCardRankException, InvalidCardValueAndRankException {
    boolean invalidValue = false;
    boolean invalidRank = false;

    // Check if specified card value is valid
    if (value < 1 || value > 13)
      invalidValue = true;

    // Check if specified card rank is valid
    if (rank < 1 || rank > 4)
      invalidRank = true;

    if (invalidValue && invalidRank)
      throw new InvalidCardValueAndRankException();

    if (invalidValue)
      throw new InvalidCardValueException();

    if (invalidRank)
      throw new InvalidCardRankException();

    this.value = value;
    this.rank = rank;
  }

  public Card(char value, char rank)
      throws InvalidCardValueException, InvalidCardRankException, InvalidCardValueAndRankException {
    this(valueToInt(value), rankToInt(rank));

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

  // Class Methods

  static private int rankToInt(char rank) throws InvalidCardRankException {
    if (rank == 'S')
      return 1;

    if (rank == 'H')
      return 2;

    if (rank == 'C')
      return 3;

    if (rank == 'D')
      return 4;

    throw new InvalidCardRankException();
  }

  static private int valueToInt(char value) throws InvalidCardValueException {
    if (value == 'A')
      return 1;

    if (value == 'J')
      return 2;

    if (value == 'Q')
      return 3;

    if (value == 'K')
      return 4;

    if (value == 'T')
      return 10;

    if (value - '0' >= 2 && value - '0' <= 9)
      return value - '0';

    throw new InvalidCardValueException();
  }
}
