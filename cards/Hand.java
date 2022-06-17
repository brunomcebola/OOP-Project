package cards;

public class Hand extends CardGroup {
  public Hand() {
    super();
  }

  public void swapCard(int position, Card card) {
    this.cards.set(position, card);
  }

  public void appendCard(Card card) {
    this.cards.add(card);
  }
}
