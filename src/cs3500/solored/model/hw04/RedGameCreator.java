package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Class that helps create the specific model for the types of game.
 */
public class RedGameCreator {

  /**
   * The possible types of games available.
   */
  public enum GameType {
    BASIC,
    ADVANCED
  }

  /**
   * Actually creates the specfic game based on the input.
   */
  public static RedGameModel createGame(GameType type) {
    switch (type) {
      case BASIC:
        return new SoloRedGameModel();
      case ADVANCED:
        return new AdvancedSoloRedGameModel();
      default:
        throw new IllegalArgumentException("Unknown GameType: " + type);
    }
  }
}
