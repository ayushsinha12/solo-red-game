package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.GameState;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Implements the advanced rules of the games within this class.
 */
public class AdvancedSoloRedGameModel extends SoloRedGameModel {

  private boolean drawMultipleCards;

  public AdvancedSoloRedGameModel() {
    super();
    this.drawMultipleCards = false;
  }

  /**
   * Play the given card from the hand to the canvas.
   * This changes the rules of the game for all palettes.
   * The method can only be called once per turn.
   *
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   * @throws IllegalStateException if the game has not started or the game is over
   * @throws IllegalArgumentException if cardIdxInHand < 0
   *     or greater/equal to the number of cards in hand
   * @throws IllegalStateException if this method was already called once in a given turn
   * @throws IllegalStateException if there is exactly one card in hand
   */
  @Override
  public void playToCanvas(int cardIdxInHand) {
    super.playToCanvas(cardIdxInHand);
    int canvCardNum = hand.get(cardIdxInHand).getNumber();
    if (canvCardNum > this.getPalette(winningPaletteIndex()).size()) {
      this.drawMultipleCards = true;
    }

  }


  /**
   * Draws one card from the deck unless the card number put on the canvas is greater
   * than the winning palette size OR until the deck is empty, whichever occurs first.
   * Newly drawn cards are added to the end of the hand (far-right conventionally).
   * SIDE-EFFECT: Allows the player to play to the canvas again.
   *
   * @throws IllegalStateException if the game has not started or the game is over
   */
  @Override
  public void drawForHand() {
    if (GameState.DURING_GAME != gameState) {
      throw new IllegalStateException("The game is not running.");
    }

    if (!this.deck.isEmpty() && this.hand.size() - 1 <= this.handSize) {
      this.hand.add(this.deck.remove(0));

      if (!this.deck.isEmpty() && this.drawMultipleCards && this.hand.size() - 1 <= this.handSize &&
              this.getPalette(this.winningPaletteIndex()).size() < this.getCanvas().getNumber()) {
        this.hand.add(this.deck.remove(0));
        this.drawMultipleCards = false;
      }
    }

  }

}
