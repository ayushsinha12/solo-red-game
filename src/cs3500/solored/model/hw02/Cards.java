package cs3500.solored.model.hw02;

import java.util.Objects;

/**
 *The Cards class represents a playing card used in the Solo Red game.
 * Each card has a color and a number associated with it.
 */
public class Cards implements Card {

  char color;
  int number;

  /**
   * Constructs a Cards object with the specified color and number.
   */
  public Cards(char color, int number) {
    if (number < 1 || number > 7) {
      throw new IllegalArgumentException("The number you have inputed is not valid and must "
              + "be between 1 and 7");
    }

    if (!(color == 'R' || color == 'O' || color == 'B' || color == 'I' || color == 'V')) {
      throw new IllegalArgumentException("The color you have inputed is not valid");
    }

    this.color = color;
    this.number = number;
  }

  // Constructs a toString() for a Cards in the game with the proper format
  @Override
  public String toString() {
    return color + "" + number;
  }

  //Checks if two Cards are equals
  @Override
  public boolean equals(Object card) {
    return this == card || (this.color == ((Cards) card).getColor()
            && this.number == ((Cards) card).getNumber());
  }

  // Calculates a hash code value for the object.
  @Override
  public int hashCode() {
    return Objects.hash(this.color, this.number);
  }

  //Returns the numbers of a card
  public int getNumber() {
    return number;
  }

  //Returns the color of a card
  public char getColor() {
    return color;
  }

  /**
   * Compares the cards first by number and if the numbers are the same, it compares colors.
   */
  // at the end
  public Cards compare(Cards other) {
    String rankedColors = "ROBIV";
    if (other == null) {
      return this;
    }

    else if (this.number < other.number) {
      return other;
    }

    else if (this.number > other.number) {
      return this;
    }

    else if (rankedColors.indexOf(this.color) < rankedColors.indexOf(other.color)) {
      return this;
    }
    return other;
  }
}
