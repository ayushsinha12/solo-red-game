package cs3500.solored.model.hw02;

import java.util.Comparator;

/**
 * This is a comparator for the comparison of two Cards which first compares
 * numbers to see which card is better. If the the numbers are the same it compares colors
 * and finds the more valuable card.
 *
 */
public class CardComparator implements Comparator<Cards> {

  /**
   * Compares two Cards which first compares numbers to see which card is better.
   * If the the numbers are the same it compares colors and finds the more valuable card.
   */
  @Override
  public int compare(Cards o1, Cards o2) {
    String rankedColors = "ROBIV";

    if (o1.getNumber() == o2.getNumber()) {
      return (rankedColors.indexOf(o1.getColor()) - rankedColors.indexOf(o2.getColor())) * -1;
    }

    else {
      return (o1.getNumber() - o2.getNumber());
    }
  }
}
