package cs3500.solored.model.hw02;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * Testing the PlayingCard class.
 */
public class PlayingCardTest {

  /**
   * Verifies that an IllegalArgumentException is thrown for colors outside of
   * 'R', 'O', 'B', 'I', or 'V'.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidColorError() {
    Cards testCard = new Cards('W', 1);
  }

  /**
   * Verifies that an IllegalArgumentException is thrown for inputs less than 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorInvalidNumberError() {
    Cards testCard = new Cards('R', 0);
  }


  /**
   * Verifies the compare method's functionality when provided with a null PlayingCard.
   */
  @Test
  public void testCompareNullThatCard() {
    Cards cardA = new Cards('A', 2);
    Cards cardNull = null;

    Cards expected = cardA;

    Assert.assertEquals(expected, cardA.compare(cardNull));
  }

  /**
   * Confirms the constructor functions correctly with valid inputs.
   */
  @Test
  public void testConstructorValid() {
    Cards card = new Cards('I', 4);

    int numExp = 4;
    char colorExp = 'I';

    Assert.assertEquals(numExp, card.getNumber());
    Assert.assertEquals(colorExp, card.getColor());
  }

  /**
   * Confirms that the compare method functions correctly for numerical comparisons.
   */
  @Test
  public void testCompareNumbers() {
    Cards bTwoCard = new Cards('B', 2);
    Cards bOneCard = new Cards('B', 1);

    Cards expected = bTwoCard;

    Assert.assertEquals(expected, bOneCard.compare(bTwoCard));
  }

  /**
   * Verifies that the compare method operates correctly when comparing colors
   * with equal numerical values.
   */
  @Test
  public void testCompareColors() {
    Cards cardO = new Cards('O', 4);
    Cards cardI = new Cards('I', 4);

    Cards expected = cardO;

    Assert.assertEquals(expected, cardI.compare(cardO));
  }

  /**
   * Confirms that the getNumber method functions correctly.
   */
  @Test
  public void testGetNumber() {
    Cards cardOne = new Cards('B', 1);

    int expected = 1;

    Assert.assertEquals(expected, cardOne.getNumber());
  }

  /**
   * Confirms that the getColor method functions as intended.
   */
  @Test
  public void testGetColor() {
    Cards cardR = new Cards('R', 2);

    char expected = 'R';

    Assert.assertEquals(expected, cardR.getColor());
  }


  /**
   * Confirms that the hashCode method operates correctly.
   */
  @Test
  public void testHashCode() {
    Cards cardRThree = new Cards('R', 3);
    Cards cardRThreeTwo = new Cards('R', 3);

    HashSet<Cards> set = new HashSet<>();
    set.add(cardRThree);

    Assert.assertTrue(set.contains(cardRThreeTwo));

    Assert.assertEquals(cardRThree.hashCode(), cardRThreeTwo.hashCode());
  }

  /**
   * Verifies that the toString method operates correctly.
   */
  @Test
  public void testToString() {
    Cards exCard = new Cards('I', 3);

    String expected = "I3";

    Assert.assertEquals(expected, exCard.toString());
  }

  /**
   * Confirms that the equals method functions as intended.
   */
  @Test
  public void testEquals() {
    Cards cardBFour = new Cards('B', 4);
    Cards cardBFourTwo = new Cards('B', 4);

    boolean expected = true;

    Assert.assertEquals(expected, cardBFour.equals(cardBFourTwo));
  }

}