package cs3500.solored.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.view.hw02.RedGameView;
import cs3500.solored.view.hw02.SoloRedGameTextView;

/**
 * A text-based controller for the Solo Red game, handling input/output
 * and game logic flow.
 */
public class SoloRedTextController implements RedGameController {

  private final Readable rd;
  private final Appendable ap;
  private boolean quitGame;
  private boolean lostGame;
  private boolean wonGame;
  private boolean invalidInput;
  private Scanner scan;

  /**
   * Constructs a SoloRedTextController with the given input and output streams.
   */
  public SoloRedTextController(Readable rd, Appendable ap) {
    if (rd == null) {
      throw new IllegalArgumentException("Argument can't be null.");
    }

    if (ap == null) {
      throw new IllegalArgumentException("Argument can't be null.");
    }

    this.rd = rd;
    this.ap = ap;
    this.quitGame = false;
    this.lostGame = false;
    this.wonGame = false;
    this.invalidInput = false;
  }

  @Override
  public <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle,
                                        int numPalettes, int handSize) {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null.");
    }

    if (deck == null) {
      throw new IllegalArgumentException("The deck cannot be null.");
    }

    if (numPalettes < 2) {
      throw new IllegalStateException("The palettes must be more than 2.");
    }

    if (handSize <= 0) {
      throw new IllegalArgumentException("Hand size has to be more than 0.");
    }

    RedGameView view = new SoloRedGameTextView(model, ap);
    scan = new Scanner(this.rd);

    model.startGame(deck, shuffle, numPalettes, handSize);
    //System.out.println(model.getAllCards());
    while (!quitGame && !lostGame && !wonGame) {
      round(model, view);
    }


  }

  private <C extends Card> void round(RedGameModel<C> model, RedGameView view) {
    try {
      view.render();
      ap.append("\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");

      String canvOrPal = getNextString(model, view);
      if (!quitGame && !lostGame && !wonGame ) {
        if (canvOrPal.equals("canvas")) {
          changeCanvas(model, view);
        }

        else if (canvOrPal.equals("palette")) {
          cardToPalette(model, view);
          if (!quitGame && !lostGame && !wonGame && !invalidInput) {
            model.drawForHand();
          }
        }

        else {
          ap.append("Invalid command. Try again.\n");
        }


        if (model.isGameOver()) {
          if (model.isGameWon()) {
            ap.append("Game won.");
            ap.append("\n");
            view.render();
            ap.append("\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");
            wonGame = true;
          }
        }
      }
    }
    catch (NoSuchElementException e) {
      throw new IllegalStateException(e.getMessage());
    }
    catch (IOException e) {
      throw new RuntimeException("Invalid input");
    }
  }

  private <C extends Card> void changeCanvas(RedGameModel<C> model, RedGameView view)  {

    try {
      if (!quitGame && !lostGame && !wonGame) {
        try {
          int inputtedCard = getNextInt2(model, view);
          try {
            model.playToCanvas(inputtedCard - 1);
          }
          catch (IllegalArgumentException e) {
            if (!quitGame && !lostGame && !wonGame) {
              ap.append("Invalid move. Try again. Invalid hand index.\n");
              invalidInput = true;
            }
          }
          catch (IllegalStateException e) {
            ap.append("Invalid move. Try again. Can't play to canvas again.\n");
            invalidInput = true;
          }
        }

        catch (IllegalArgumentException ignore) { //ignored

        }

        if (model.isGameOver()) {
          if (model.isGameWon()) {
            ap.append("Game won.");
            ap.append("\n");
            view.render();
            ap.append("\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");
            wonGame = true;
          }
        }

      }
    }
    catch (IOException e) {
      throw new RuntimeException("Invalid input");
    }

  }

  private  <C extends Card> void cardToPalette(RedGameModel<C> model, RedGameView view) {
    try {
      if (!quitGame && !lostGame && !wonGame) {
        int paletteIndex = getNextInt2(model, view);

        if (!quitGame && !lostGame && !wonGame) {
          int handCardIndex = getNextInt2(model, view);

          try {
            model.playToPalette(paletteIndex - 1, handCardIndex - 1);
          }

          catch (IllegalStateException e) {
            ap.append("Invalid move. Try again. Palette is currently winning.\n");
            invalidInput = true;
          }

          catch (IllegalArgumentException e) {
            if (!quitGame && !lostGame && !wonGame) {
              ap.append("Invalid move. Try again. Invalid palette or hand index.\n");
              invalidInput = true;
            }
          }

          if (model.isGameOver()) {

            if (model.isGameWon()) {
              ap.append("Game won.");
              ap.append("\n");
              view.render();
              ap.append("\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");
              wonGame = true;
            }

            else {
              view.render();
              ap.append("\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");

              ap.append("Game lost.");
              ap.append("\n");
              view.render();
              ap.append("\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");
              lostGame = true;
            }
          }
        }
      }
    }
    catch (IOException e) {
      throw new RuntimeException("Invalid input");
    }
  }

  private <C extends Card> String getNextString(RedGameModel<C> model, RedGameView view) {
    String input = scan.next();
    if (input.equalsIgnoreCase("q")) {
      quitGame = true;
      gameQuit(model, view);
      return "";
    }
    return input;

  }

  private <C extends Card> int getNextInt2(RedGameModel<C> model, RedGameView view) {
    boolean validInputflag = true;

    while (validInputflag) {
      String input = scan.next();
      if (input.equalsIgnoreCase("q")) {
        quitGame = true;
        gameQuit(model, view);
        break;
      }

      try {
        if (Integer.parseInt(input) >= 0) {
          validInputflag = false;
          return Integer.parseInt(input);
        }
      }

      catch (IllegalArgumentException ignore) { //ignored

      }
    }
    return -1;
  }

  private <C extends Card> void gameQuit(RedGameModel<C> model, RedGameView view) {
    try {
      ap.append("Game quit!\n");
      ap.append("State of game when quit:\n");
      view.render();
      ap.append("\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");
    }
    catch (IOException e) {
      throw new RuntimeException("Invalid input");
    }

  }
}
