package cards;

import java.util.*;
import exceptions.cardExcepetions.*;
import exceptions.deckExceptions.*;

public class Hand extends CardGroup {
  public Hand() {
    super();
  }

  public void funcaoRuiValor() {

  }

  public void swapCard(int position, Card card) {
    this.cards.set(position, card);
  }
}
