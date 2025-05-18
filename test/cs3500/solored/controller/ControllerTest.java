package cs3500.solored.controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Tests the controller functions properly.
 */
public class ControllerTest {

  /**
   * Throws IllegalArgumentException if the appendable is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    Readable input = new StringReader(" ");
    Appendable output = null;
    SoloRedTextController testController = new SoloRedTextController(input, output);
  }

  /**
   * Throws IllegalArgumentException if readable is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    Readable input = null;
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
  }

  /**
   * Throws IllegalArgumentExceptio if the deck is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testDeckNullInPlayGame() {
    Readable input = new StringReader(" ");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, null, false, 3, 5);
  }

  /**
   * Throws IllegalArgumentException if the model and deck is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testModelNullInPlayGame() {
    Readable input = new StringReader(" ");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    testController.playGame(null, null, false, 3, 5);
  }

  /**
   * Throws IllegalStateException if numPalette is not valid.
   */
  @Test(expected = IllegalStateException.class)
  public void testInvalidPalettesPlayGame() {
    Readable input = new StringReader(" ");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 0, 5);
  }

  /**
   * Throws IllegalArgumentException if handSize is not valid.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHandSizePlayGame() {
    Readable input = new StringReader(" ");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, -1);

  }

  /**
   * Tests canvas quitting game.
   */
  @Test
  public void testQuitCanvasHandNum() {
    Readable input = new StringReader("canvas q");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, 5);
    String expected = "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: " + model.numOfCardsInDeck()
            + "\n" + "Game quit!\n" + "State of game when quit:\n" + "Canvas: R\n" + "P1: R1\n"
            + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: "
            + model.numOfCardsInDeck() + "\n";
    Assert.assertEquals(expected, output.toString());
  }

  /**
   * Tests pallete quitting game.
   */
  @Test
  public void testQuitHandNumPalette() {
    Readable input = new StringReader("palette 2 q");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, 5);
    String expected = "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: " + model.numOfCardsInDeck()
            + "\n" + "Game quit!\n" + "State of game when quit:\n" + "Canvas: R\n" + "P1: R1\n"
            + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: "
            + model.numOfCardsInDeck() + "\n";
    Assert.assertEquals(expected, output.toString());
  }

  /**
   * Tests quitting the gamefor palette choice.
   */
  @Test
  public void testQuitPaletteNumPalette() {
    Readable input = new StringReader("palette q");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, 5);
    String expected = "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: " + model.numOfCardsInDeck()
            + "\n" + "Game quit!\n" + "State of game when quit:\n" + "Canvas: R\n" + "P1: R1\n"
            + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: "
            + model.numOfCardsInDeck() + "\n";
    Assert.assertEquals(expected, output.toString());
  }

  /**
   * Tests for invalid hand index when doing canvas during the game.
   */
  @Test
  public void testInvalidHandIndexChangeCanvas() {
    Readable input = new StringReader("canvas 7 q");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, 5);
    String expected = "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: " + model.numOfCardsInDeck()
            + "\n" + "Invalid move. Try again. Invalid hand index.\n" + "Canvas: R\n"
            + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n"
            + "Number of cards in deck: " + model.numOfCardsInDeck() + "\n" + "Game quit!\n"
            + "State of game when quit:\n" + "Canvas: R\n" + "P1: R1\n"
            + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: "
            + model.numOfCardsInDeck() + "\n";
    Assert.assertEquals(expected, output.toString());
  }

  /**
   * Tests for invalid hand index when playing to palette during the game.
   */
  @Test
  public void testInvalidHandIndexCardToPalette() {
    Readable input = new StringReader("palette 2 7 q");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, 5);
    String expected = "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: " + model.numOfCardsInDeck()
            + "\n" + "Invalid move. Try again. Invalid palette or hand index.\n" + "Canvas: R\n"
            + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n"
            + "Number of cards in deck: " + model.numOfCardsInDeck() + "\n" + "Game quit!\n"
            + "State of game when quit:\n" + "Canvas: R\n" + "P1: R1\n"
            + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: "
            + model.numOfCardsInDeck() + "\n";
    Assert.assertEquals(expected, output.toString());
  }

  /**
   * Tests for invalid palette index when playing to palette during the game.
   */
  @Test
  public void testInvalidPalIndexCardToPalette() {
    Readable input = new StringReader("palette 7 2 q");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, 5);
    String expected = "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: " + model.numOfCardsInDeck()
            + "\n" + "Invalid move. Try again. Invalid palette or hand index.\n" + "Canvas: R\n"
            + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n"
            + "Number of cards in deck: " + model.numOfCardsInDeck() + "\n" + "Game quit!\n"
            + "State of game when quit:\n" + "Canvas: R\n" + "P1: R1\n"
            + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: "
            + model.numOfCardsInDeck() + "\n";
    Assert.assertEquals(expected, output.toString());
  }

  /**
   * Tests for playing a card to a winning palette.
   */
  @Test
  public void testPlayToWinningPalette() {
    Readable input = new StringReader("palette 3 2 q");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, 5);
    String expected = "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: " + model.numOfCardsInDeck()
            + "\n" + "Invalid move. Try again. Palette is currently winning.\n" + "Canvas: R\n"
            + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n"
            + "Number of cards in deck: " + model.numOfCardsInDeck() + "\n" + "Game quit!\n"
            + "State of game when quit:\n" + "Canvas: R\n" + "P1: R1\n"
            + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: "
            + model.numOfCardsInDeck() + "\n";
    Assert.assertEquals(expected, output.toString());
  }

  /**
   * Tests for invalid commands.
   */
  @Test
  public void testInvalidCommand() {
    Readable input = new StringReader("asd q");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, 5);
    String expected = "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: " + model.numOfCardsInDeck()
            + "\n" + "Invalid command. Try again.\n" + "Canvas: R\n"
            + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n"
            + "Number of cards in deck: " + model.numOfCardsInDeck() + "\n" + "Game quit!\n"
            + "State of game when quit:\n" + "Canvas: R\n" + "P1: R1\n"
            + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: "
            + model.numOfCardsInDeck() + "\n";
    Assert.assertEquals(expected, output.toString());
  }

  /**
   * Tests playing to canvas multiple times in a row.
   */
  @Test
  public void testMultiplePlayToCanvas() {
    Readable input = new StringReader("canvas 2 canvas 2 q");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, 5);
    String expected = "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: " + model.numOfCardsInDeck()
            + "\n" + "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R6 R7 O1\n"
            + "Number of cards in deck: " + model.numOfCardsInDeck() + "\n"
            + "Invalid move. Try again. Can't play to canvas again.\n"
            + "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R6 R7 O1\n" + "Number of cards in deck: " + model.numOfCardsInDeck()
            + "\n" + "Game quit!\n"
            + "State of game when quit:\n" + "Canvas: R\n" + "P1: R1\n"
            + "P2: R2\n" + "> P3: R3\n" + "Hand: R4 R6 R7 O1\n" + "Number of cards in deck: "
            + model.numOfCardsInDeck() + "\n";
    Assert.assertEquals(expected, output.toString());
  }

  /**
   * Throws IllegalStateException if it cannot read from the Readable while the game is in progress.
   */
  @Test(expected = IllegalStateException.class)
  public void testReadablePlayGame() {
    Readable input = new StringReader("palette 2 3");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 0, 5);
  }

  /**
   * Throws IllegalStateException if it cannot append from the Appendable while the
   * game is in progress.
   */
  @Test(expected = IllegalStateException.class)
  public void testAppendablePlayGame() {
    try {
      throw new IOException();
    }
    catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  /**
   * Tests a successfull loss is working correctly.
   */
  @Test
  public void testSuccessfulPlayGameLoss() {
    Readable input = new StringReader("palette 1 4 palette 2 4");
    Appendable output = new StringBuilder();
    SoloRedTextController testController = new SoloRedTextController(input, output);
    SoloRedGameModel model = new SoloRedGameModel();
    testController.playGame(model, model.getAllCards(), false, 3, 5);
    String expected = "Canvas: R\n" + "P1: R1\n" + "P2: R2\n" + "> P3: R3\n"
            + "Hand: R4 R5 R6 R7 O1\n" + "Number of cards in deck: 27" + "\n" + "Canvas: R\n"
            + "> P1: R1 R7\n" + "P2: R2\n" + "P3: R3\n" + "Hand: R4 R5 R6 O1 O2\n"
            + "Number of cards in deck: " + model.numOfCardsInDeck() + "\n" + "Canvas: R\n"
            + "> P1: R1 R7\n" + "P2: R2 O1\n" + "P3: R3\n" + "Hand: R4 R5 R6 O2\n"
            + "Number of cards in deck: " + model.numOfCardsInDeck() + "\n" + "Game lost.\n"
            + "Canvas: R\n" + "> P1: R1 R7\n" + "P2: R2 O1\n" + "P3: R3\n" + "Hand: R4 R5 R6 O2\n"
            + "Number of cards in deck: " + model.numOfCardsInDeck() + "\n";
    Assert.assertEquals(expected, output.toString());
  }



}
