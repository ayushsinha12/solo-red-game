package cs3500.solored.view.hw02;

import java.io.IOException;
import java.util.List;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * Represents the text-based view of the Solo Red game.
 * Implements the {@link RedGameView} interface to provide a text output
 * representation of the game state.
 */
public class SoloRedGameTextView implements RedGameView {

  /**
   * Constructs a SoloRedGameTextView with the specified game model.
   */
  private final RedGameModel<?> model;
  private Appendable out;

  /**
   * Constructs a text view for the Solo Red game with the specified game model.
   */
  public SoloRedGameTextView(RedGameModel<?> model) {
    this.model = model;
  }

  /**
   * Constructs a text view for the Solo Red game with the specified game model and output.
   */
  public SoloRedGameTextView(RedGameModel<?> model, Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("Output appendable can not be null");
    }
    this.model = model;
    this.out = out;
  }

  /**
   * Outputs the state of the model using Appendable.
   */
  @Override
  public void render() {
    try {
      this.out.append(this.toString());
    }
    catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * Outputs the state of the model.
   */
  @Override
  public String toString() {
    return "Canvas: " + this.model.getCanvas().toString().substring(0, 1) + "\n"
            + this.printPalettes(this.model.winningPaletteIndex())
            + this.printHand();
  }

  /**
   * Outputs the current state of the palettes.
   *
   * @return a string of the current game state's palettes
   */
  private String printPalettes(int winningPaletteIndex) {
    String palsPrinted = "";

    for (int i = 0; i < this.model.numPalettes(); i++) {

      if (winningPaletteIndex == i) {
        palsPrinted += "> ";
      }

      palsPrinted += "P" + (i + 1) + ": " + this.printCardsInPalette(i) + "\n";
    }
    return palsPrinted;
  }

  /**
   * Returns a representation of a specific palette of the current game state.
   *
   * @param paletteIdx the specific palette to return a string of
   * @return a string of a specific palette of the current game state
   */
  private String printCardsInPalette(int paletteIdx) {
    List<?> palette = this.model.getPalette(paletteIdx);
    String paletteCards = "";

    for (int i = 0; i < palette.size(); i++) {
      if (i == palette.size() - 1) {
        paletteCards += palette.get(i).toString();
      }

      else {
        paletteCards += palette.get(i).toString() + " ";
      }
    }

    return paletteCards;
  }

  /**
   * Returns a representation the current state of the player's hand.
   *
   * @return a string of the current player's hand
   */
  private String printHand() {
    String handOutput = "Hand: ";
    for (int i = 0; i < this.model.getHand().size(); i++) {
      if (i == this.model.getHand().size() - 1) {
        handOutput +=  this.model.getHand().get(i).toString();
      }
      else {
        handOutput +=  this.model.getHand().get(i).toString() + " ";
      }
    }
    return handOutput;
  }

  private String numOfCardsInDeck() {
    return "\n " + this.model.numOfCardsInDeck() + " ";
    // change newline if j
  }


}