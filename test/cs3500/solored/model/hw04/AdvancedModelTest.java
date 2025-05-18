package cs3500.solored.model.hw04;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.solored.model.hw02.Cards;

/**
 * Tests the AdvancedSoloRedGame class.
 */
public class AdvancedModelTest {

  /**
   * Tests to see if an IllegalStateException is thrown when canvas is plated to twice in a row.
   */
  @Test(expected = IllegalStateException.class)
  public void testAlreadyPlayedToCanvas() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(),false,3,5);
    exModel.playToCanvas(2);
    exModel.playToCanvas(1);
  }

  /**
   * Tests to see if an IllegalStateException is thrown when canvas is plated to twice in a row.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToCanvasInvalidInput() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(),false,3,5);
    exModel.playToCanvas(-1);
  }

  /**
   * Confirms that an IllegalArgumentException is thrown when a player attempts to
   * play their last card to the canvas.
   */
  @Test(expected = IllegalStateException.class)
  public void testPlayToCanvasLastCardInHand() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    List<Cards> deck = new ArrayList<>();
    deck.add(new Cards('R', 1));
    deck.add(new Cards('R', 2));
    deck.add(new Cards('R', 3));

    deck.add(new Cards('R', 4));
    deck.add(new Cards('R', 5));
    deck.add(new Cards('R', 6));


    exModel.startGame(deck, false, 3, 3);
    exModel.playToPalette(0,0);
    exModel.playToPalette(1,0);
    exModel.playToCanvas(0);
  }

  /**
   * Verifies that the playToCanvas method functions correctly.
   */
  @Test
  public void testPlayToCanvasValid() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);

    Cards prevCanvasCard = exModel.getCanvas();
    exModel.playToCanvas(2);
    Cards newCanvasCard = exModel.getCanvas();

    Assert.assertNotEquals(prevCanvasCard, newCanvasCard);
  }

  /**
   * Verifies that the playToCanvas and DrawForHand method functions correctly after
   * playing a card with number bigger than the amount of cards in winning palette.
   */
  @Test
  public void testPlayToCanvasAndDrawForHandValidAdvancedVersion() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    List<Cards> deck = new ArrayList<>();
    deck.add(new Cards('O', 4));
    deck.add(new Cards('B', 5));
    deck.add(new Cards('R', 4));
    deck.add(new Cards('B', 6));
    deck.add(new Cards('B', 1));
    deck.add(new Cards('V', 7));
    deck.add(new Cards('I', 1));
    deck.add(new Cards('I', 2));
    deck.add(new Cards('I', 3));
    deck.add(new Cards('I', 4));
    deck.add(new Cards('I', 5));
    deck.add(new Cards('I', 6));
    deck.add(new Cards('I', 7));
    deck.add(new Cards('O', 1));
    exModel.startGame(deck, false, 3, 5);


    exModel.playToPalette(0, 0);
    exModel.drawForHand();
    exModel.playToCanvas(0);
    exModel.playToPalette(1, 0);
    exModel.drawForHand();
    System.out.println(exModel.getHand());
    int expected = 4;
    Assert.assertEquals(expected, exModel.getHand().size());
  }

  /**
   * Verifies that the drawForHand method functions correctly.
   */
  @Test
  public void testDrawForHandValid() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    exModel.playToCanvas(2);
    int prevHandSize = exModel.getHand().size();
    exModel.drawForHand();
    int newHandSize = exModel.getHand().size();

    Assert.assertNotEquals(prevHandSize, newHandSize);
  }

  /**
   * Tests to see startGame will throw an IllegalArgumentException if the number of
   * palettes is invalid in an AdvancedSoloRedGameModel.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWrongPalette() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(),false,0,5);
  }

  /**
   * Tests to see startGame will throw an IllegalArgumentException if the number of
   * cards in the hand is invalid in an AdvancedSoloRedGameModel.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWrongHandSize() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(),false,3,-1);
  }

  /**
   * Tests to see if startGame will throw an IllegalArgumentException if the number of
   * cards in the deck is invalid in an AdvancedSoloRedGameModel.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWrongDeckSize() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    List<Cards> deck = new ArrayList<>();
    deck.add(new Cards('B', 1));
    deck.add(new Cards('B', 2));
    exModel.startGame(deck,false,3,-1);
  }

  /**
   * Verifies that the getHand method operates correctly in an AdvancedSoloRedGameModel.
   */
  @Test
  public void testGetHand() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 6);

    int handSize = 6;

    Assert.assertEquals(handSize, exModel.getHand().size());
  }

  /**
   * Verifies that the isGameOver method operates correctly.
   */
  @Test
  public void testIsGameOver() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    Assert.assertFalse(exModel.isGameOver());
  }

  /**
   * Throws an IllegalArgumentException for playToPalette if the palette index is invalid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPalettePaletteIndexError() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    exModel.playToPalette(-1, 2);
  }

  /**
   * Throws an IllegalArgumentException for playToPalette if the hand index is invalid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayToPaletteHandIndexError() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 4);
    exModel.playToPalette(2, -1);
  }

  /**
   * Verifies that the playToPalette method correctly plays a card to the palette.
   */
  @Test
  public void testPlayToPaletteValid() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), false, 3, 5);

    int expected = exModel.getPalette(0).size() + 1;

    exModel.playToPalette(0, 2);

    Assert.assertEquals(expected, exModel.getPalette(0).size());
  }

  /**
   * Makes sure that if the game is begun while attempting to start the
   * game, an IllegalStateException is thrown.
   */
  @Test(expected = IllegalStateException.class)
  public void testStartGameWhileGameStarted() {
    AdvancedSoloRedGameModel exModel = new AdvancedSoloRedGameModel();
    exModel.startGame(exModel.getAllCards(), true, 5, 6);
    exModel.startGame(exModel.getAllCards(), true, 5, 6);
  }

  //tests shuffling
}
