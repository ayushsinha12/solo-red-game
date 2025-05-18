package cs3500.solored.view.hw02;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import cs3500.solored.model.hw02.Cards;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * A test class for the SoloRedGameTextView class.
 */
public class ViewTest {

  /**
   * Tests the toString() method to make sure it is functional.
   */
  @Test
  public void testRedTextViewToString() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    SoloRedGameTextView output = new SoloRedGameTextView(exModel);

    List<Cards> deck = List.of(new Cards('R', 2),
            new Cards('I', 2), new Cards('V', 2),
            new Cards('V', 3), new Cards('B', 3),
            new Cards('I', 4), new Cards('B', 1),
            new Cards('B', 7), new Cards('B', 5));

    exModel.startGame(deck, false, 4, 2);
    String expected = "Canvas: R\n"
            + "P1: R2\n"
            + "P2: I2\n"
            + "P3: V2\n"
            + "> P4: V3\n"
            + "Hand: B3 I4";
    Assert.assertEquals(expected, output.toString());
  }

}
