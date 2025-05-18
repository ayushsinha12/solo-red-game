package cs3500.solored.model.hw02;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * A test class for the SoloRedGameModel class.
 */
public class ModelTest {

  /**
   * Makes sure that in the event that the random object is null,
   * an IllegalArgumentException is produced.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstrNullRandom() {
    SoloRedGameModel exModel = new SoloRedGameModel(null);
  }

  /**
   * If the hand size is less than or equal to zero, an IllegalArgumentException is thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameHandSizeError() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 0);
  }


  /**
   * makes sure that if the number of palettes is less than or equal to zero, an
   * IllegalArgumentException is produced.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGamePalettesError() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, -1, 4);
  }

  /**
   * Makes sure that if the deck is null, an IllegalArgumentException is thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testlStartGameNullDeck() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(null, true, 2, 1);
  }

  /**
   * makes sure that if there are duplicate cards in the deck, an
   * IllegalArgumentException is thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGameModelStartGameDeckHasDuplicates() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('R', 1),
            new Cards('V', 1));
    exModel.startGame(possCards, false, 4, 1);

  }

  /**
   * Makes sure startGame works with valid parameters.
   */
  @Test
  public void testStartGameValid() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    //System.out.println(exModel.getAllCards());
    //System.out.println(exModel);
    exModel.startGame(exModel.getAllCards(), true, 2, 7);
    int expected = 7;

    Assert.assertEquals(expected, exModel.getHand().size());
  }

  /*@Test
  public void testDuplicates() {
    SoloRedGameModel exModel = new SoloRedGameModel();

  }*/

  /**
   * Throws an IllegalArgumentException if the palette index is less than 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPalettePaletteIndexError() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    exModel.playToPalette(-1, 2);
  }

  /**
   * If there are not enough cards in the deck, an IllegalArgumentException is thrown.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartNotEnoughCardsInDeck() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('O', 2),
            new Cards('V', 2));

    exModel.startGame(possCards, false, 5, 1);
  }


  /**
   * Throws an IllegalStateException if the palette index matches the winning palette index.
   */
  @Test(expected = IllegalStateException.class)
  public void testPaletteWinningPaletteIndexError() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(
            new Cards('R', 6),
            new Cards('R', 5),
            new Cards('R', 4),
            new Cards('R', 7),
            new Cards('R', 3),
            new Cards('R', 2),
            new Cards('R', 1));

    exModel.startGame(possCards, false, 3, 4);
    exModel.playToPalette(0, 0);
  }

  /**
   * Verifies that the playToPalette method correctly plays a card to the palette.
   */
  @Test
  public void testPlayToPaletteValid() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 5);

    int expected = exModel.getPalette(0).size() + 1;

    exModel.playToPalette(0, 2);

    Assert.assertEquals(expected, exModel.getPalette(0).size());
  }

  /**
   * Throws an IllegalArgumentException if the card index is less than 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteCardIndexError() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    exModel.playToPalette(2, -1);
  }

  /**
   * Verifies that an IllegalStateException is thrown when attempting to play
   * to the canvas consecutively.
   */
  @Test(expected = IllegalStateException.class)
  public void testCanvasAlreadyPlayedCanvasInTurn() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    exModel.playToCanvas(2);
    exModel.playToCanvas(1);
  }

  /**
   * Confirms that an IllegalArgumentException is thrown when a player attempts to
   * play their last card to the canvas.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasLastCardInHand() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> deck = new ArrayList<>();
    deck.add(new Cards('R', 1));
    deck.add(new Cards('B', 2));
    deck.add(new Cards('C', 3));
    deck.add(new Cards('D', 4));
    deck.add(new Cards('E', 5));


    exModel.startGame(deck, false, 3, 4);
    exModel.playToCanvas(0);
  }

  /**
   * Confirms that an IllegalArgumentException is thrown if the card index is out of bounds
   * (less than 0 or greater than or equal to hand size).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCanvasIllegalCardIndexError() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 5);
    exModel.playToCanvas(-2);
  }

  /**
   * Verifies that the playToCanvas method functions correctly.
   */
  @Test
  public void testPlayToCanvasValid() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);

    Cards prevCanvasCard = exModel.getCanvas();
    exModel.playToCanvas(2);
    Cards newCanvasCard = exModel.getCanvas();

    Assert.assertNotEquals(prevCanvasCard, newCanvasCard);
  }

  /**
   * Verifies that the drawForHand method functions correctly.
   */
  @Test
  public void testDrawForHandValid() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    exModel.playToCanvas(2);
    int prevHandSize = exModel.getHand().size();
    exModel.drawForHand();
    int newHandSize = exModel.getHand().size();

    Assert.assertNotEquals(prevHandSize, newHandSize);
  }

  /**
   * Confirms that the winningPaletteIndex method, particularly the helper function of
   * winningPaletteRedRule, operates correctly.
   */
  @Test
  public void testGameModelWinningPaletteIndexRedRule() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('R', 7),
            new Cards('B', 7),
            new Cards('O', 5),
            new Cards('I', 1),
            new Cards('I', 2),
            new Cards('I', 3),
            new Cards('I', 4));

    exModel.startGame(possCards, false, 3, 4);

    int expected = 0;
    Assert.assertEquals(expected, exModel.winningPaletteIndex());
  }

  /**
   * Verifies that the winningPaletteIndex determines game over if the played
   * card does not result in a winning palette index.
   */
  @Test
  public void testGameModelWinningPaletteIndexGameOver() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('R', 7),
            new Cards('R', 6),
            new Cards('R', 5),
            new Cards('R', 4));
    exModel.startGame(possCards, false, 3, 1);
    exModel.playToPalette(1, 0);
    boolean expected = true;
    Assert.assertEquals(expected, exModel.isGameOver());
  }

  /**
   * Confirms that the winningPaletteIndex method, particularly the helper
   * function of the winningPaletteOrangeRule, operates as expected.
   */
  @Test
  public void winningPaletteOrangeRule() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('V', 3),
            new Cards('I', 3),
            new Cards('I', 2),
            new Cards('B', 3),
            new Cards('R', 4),
            new Cards('O', 1),
            new Cards('R', 3),
            new Cards('V', 1),
            new Cards('V', 2),
            new Cards('V', 4),
            new Cards('V', 5));

    exModel.startGame(possCards, false, 3, 4);
    exModel.playToPalette(0, 0);
    exModel.drawForHand();
    exModel.playToPalette(1, 0);
    exModel.drawForHand();
    exModel.playToCanvas(0);
    exModel.drawForHand();
    exModel.playToPalette(1, 0);
    exModel.drawForHand();
    int expected = 1;
    Assert.assertEquals(expected, exModel.winningPaletteIndex());
  }

  /**
   * Confirms that the winningPaletteIndex method, especially the helper function of the
   * winningPaletteBlueRule, functions correctly.
   */
  @Test
  public void testblueCanvasWinningPal() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('R', 1),
            new Cards('R', 2),
            new Cards('R', 3),
            new Cards('B', 4),
            new Cards('R', 5),
            new Cards('B', 3),
            new Cards('O', 4),
            new Cards('I', 2),
            new Cards('I', 3),
            new Cards('I', 4),
            new Cards('I', 5),
            new Cards('I', 6));


    exModel.startGame(possCards, false, 3, 4);
    exModel.playToPalette(0, 0);
    exModel.drawForHand();
    exModel.playToPalette(1, 0);
    exModel.drawForHand();
    exModel.playToCanvas(0);
    exModel.drawForHand();
    exModel.playToPalette(2, 0);
    exModel.drawForHand();
    int expected = 2;
    Assert.assertEquals(expected, exModel.winningPaletteIndex());
  }

  /**
   * Verifies that the winningPaletteIndex method, particularly the helper function of the
   * winningPaletteBlueRule, operates correctly with various cards.
   */
  @Test
  public void testWinningPaletteOrangeRuleHandin() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('V', 1),
            new Cards('I', 2),
            new Cards('R', 3),
            new Cards('O', 3),
            new Cards('B', 1),
            new Cards('V', 5),
            new Cards('I', 5),
            new Cards('B', 3),
            new Cards('R', 4));

    exModel.startGame(possCards, false, 4, 4);
    exModel.playToCanvas(0);
    exModel.drawForHand();
    exModel.playToPalette(0, 0);
    exModel.drawForHand();
    exModel.playToPalette(1, 0);
    int expected = 1;
    Assert.assertEquals(expected, exModel.winningPaletteIndex());
  }

  /**
   * Confirms that the winningPaletteIndex method, specifically the helper
   * function of the winningPaletteIndigoRule, functions correctly.
   */
  @Test
  public void testIndigoCanvasWinningPal() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('V', 1),
            new Cards('I', 2),
            new Cards('I', 3),
            new Cards('V', 3),
            new Cards('V', 4),
            new Cards('R', 6),
            new Cards('I', 1),
            new Cards('B', 3),
            new Cards('B', 2));

    exModel.startGame(possCards, false, 4, 4);
    exModel.playToPalette(3, 0);
    exModel.drawForHand();
    exModel.playToPalette(1, 0);
    exModel.drawForHand();
    exModel.playToCanvas(0);
    exModel.drawForHand();
    int expected = 3;
    Assert.assertEquals(expected, exModel.winningPaletteIndex());
  }

  /**
   * Confirms that the winningPaletteIndex method, specifically the helper function of the
   * winningPaletteIndigoRule, operates correctly with various cards.
   */
  @Test
  public void testGameModelWinningPaletteIndexIndigoRuleHandins2() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('V', 1),
            new Cards('I', 2),
            new Cards('I', 3),
            new Cards('V', 3),
            new Cards('R', 3),
            new Cards('B', 4),
            new Cards('V', 5),
            new Cards('I', 1),
            new Cards('R', 6));

    exModel.startGame(possCards, false, 4, 4);
    exModel.playToPalette(1, 0);
    exModel.drawForHand();
    exModel.playToPalette(2, 0);
    exModel.drawForHand();
    exModel.playToPalette(3, 0);
    exModel.drawForHand();
    exModel.playToCanvas(0);
    int expected = 2;
    Assert.assertEquals(expected, exModel.winningPaletteIndex());
  }

  /**
   * Confirms that the winningPaletteIndex method, particularly the helper function of the
   * winningPaletteVioletRule, functions as intended.
   */
  @Test
  public void testvioletCanvasWinningPal() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('V', 1),
            new Cards('V', 2),
            new Cards('V', 3),
            new Cards('B', 3),
            new Cards('O', 3),
            new Cards('R', 7),
            new Cards('V', 5),
            new Cards('I', 1),
            new Cards('I', 2),
            new Cards('I', 3),
            new Cards('I', 4),
            new Cards('I', 5),
            new Cards('I', 6));

    exModel.startGame(possCards, false, 3, 4);
    exModel.playToPalette(0, 0);
    exModel.drawForHand();
    exModel.playToPalette(1, 0);
    exModel.drawForHand();
    exModel.playToPalette(2, 0);
    exModel.drawForHand();
    exModel.playToCanvas(0);
    exModel.drawForHand();

    int expected = 1;

    Assert.assertEquals(expected, exModel.winningPaletteIndex());
  }

  /**
   * Confirms that the getPalette method functions correctly.
   */
  @Test
  public void testGetPaletteValid() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    int expected = 1;
    Assert.assertEquals(expected, exModel.getPalette(0).size());
  }

  /**
   * Confirms that the numPalettes method functions correctly.
   */
  @Test
  public void testNumPalettes() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    int expected = 3;
    Assert.assertEquals(expected, exModel.numPalettes());
  }

  /**
   * Confirms that the getCanvas method functions correctly.
   */
  @Test
  public void testGetCanvas() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 4, 5);

    Cards expected = new Cards('R', 1);

    Assert.assertEquals(expected, exModel.getCanvas());
  }

  /**
   * Confirms that the isGameWon method functions as expected.
   */
  @Test
  public void testIsGameWon() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    List<Cards> possCards = List.of(new Cards('R', 1),
            new Cards('R', 2),
            new Cards('R', 3),
            new Cards('R', 4),
            new Cards('R', 7));

    exModel.startGame(possCards, false, 3, 2);
    exModel.playToPalette(0, 0);
    exModel.drawForHand();
    exModel.playToPalette(1, 0);
    Assert.assertTrue(exModel.isGameWon());
  }

  /**
   * Verifies that the getHand method operates correctly.
   */
  @Test
  public void testGetHand() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 6);

    int handSize = 6;

    Assert.assertEquals(handSize, exModel.getHand().size());

  }


  /**
   * Validates that an IllegalArgumentException is thrown for invalid palette
   * indices in the getPalette method.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetPaletteInvalidPalette() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    exModel.getPalette(-1);
  }

  /**
   * Verifies that the isGameOver method operates correctly.
   */
  @Test
  public void testIsGameOver() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    Assert.assertFalse(exModel.isGameOver());
  }

  /**
   * Verifies that the numOfCardsInDeck method operates correctly.
   */
  @Test
  public void testNumCardsInDeck() {
    SoloRedGameModel exModel = new SoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);

    int expected = 28;

    Assert.assertEquals(expected, exModel.numOfCardsInDeck());
  }

}
