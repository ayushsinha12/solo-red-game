package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * Controller interface for the Solo Red game. Manages game execution.
 */
public interface RedGameController {
  /**
   * Starts and manages the game flow.
   */
  <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle,
                                 int numPalettes, int handSize);
}
