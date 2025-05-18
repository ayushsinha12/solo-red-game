package cs3500.solored;

import java.io.InputStreamReader;
import cs3500.solored.controller.RedGameController;
import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * Class for running the whole program.
 */
public class SoloRed {

  /**
   * Main method that creates the model by reading it in from the args.
   */
  public static void main(String[] args) {
    try {
      RedGameModel model = createModel(args[0]);
      Readable readable = new InputStreamReader(System.in);
      Appendable appendable = System.out;
      RedGameController redGameController = new SoloRedTextController(readable, appendable);

      //for testing purposes
      //List<Cards> deck = new ArrayList<>();
      /*deck.add(new Cards('B', 1));
      deck.add(new Cards('B', 2));
      deck.add(new Cards('B', 3));
      deck.add(new Cards('B', 4));
      deck.add(new Cards('B', 5));
      deck.add(new Cards('B', 6));
      deck.add(new Cards('R', 6));
      deck.add(new Cards('B', 7));
      deck.add(new Cards('R', 7));*/


      redGameController.playGame(model, model.getAllCards(), true, 3, 4);
    }
    catch (IllegalStateException ignored) {
      //appendable.append("Invalid state");
    }

  }

  private static RedGameModel createModel(String arg) {
    switch (arg) {
      case "basic":
        return RedGameCreator.createGame(RedGameCreator.GameType.BASIC);
      case "advanced":
        return RedGameCreator.createGame(RedGameCreator.GameType.ADVANCED);
      default:
        throw new IllegalArgumentException("Unknown GameType: ");
    }
  }
}
