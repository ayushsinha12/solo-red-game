package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *  Represents the game state for the Solo Red card game.
 *  It manages the player's hand, the deck of cards, the palettes, and the canvas.
 *  This class provides methods for game setup, playing cards to palettes and
 *  the canvas, drawing cards, and determining the game state (e.g., winning palette,
 *  game over conditions).
 */
public class SoloRedGameModel implements RedGameModel<Cards> {

  private boolean ifPlayerWon;
  private List<List<Cards>> palettes;
  protected boolean alrPlayedCanvas;
  private boolean playerWin;
  private final Random random;

  protected Cards canvas;
  protected List<Cards> hand;
  protected GameState gameState = GameState.BEFORE_GAME;
  protected int handSize;
  protected List<Cards> deck;

  /**
   * Constructs a new SoloRedGameModel with a default setup.
   * Initializes an empty hand, instantiates the deck of cards, creates
   * a list for palettes, sets the canvas to a default card (Red 1),
   * and sets the hand size to 4.
   */
  public SoloRedGameModel() {
    this.random = new Random();
  }

  /**
   * Constructs a new {@code SoloRedGameModel} with a specified random generator.
   * Initializes an empty hand, instantiates the deck of cards, shuffles the deck
   * using the provided random generator, creates a list for palettes, sets the
   * canvas to a default card (Red 1), and sets the hand size to 4.
   */
  public SoloRedGameModel(Random rand) {
    if (rand == null) {
      throw new IllegalArgumentException();
    }
    /*changed initializing all my global variables from the constructor to the startGame method
    except random because if I have it in the constructor it would be duplicate code and serves
    no purpose. Keeping it only in the constructor previously was giving me various errors.*/

    this.random = rand;
  }

  /**
   * Instantiates the deck of cards for the game and returns it back.
   * The deck consists of cards in the colors Red, Orange, Blue, Indigo,
   * and Violet, each with numbers ranging from 1 to 7.
   */
  private List<Cards> instantiateDeck() {
    char[] colors = {'R', 'O', 'B', 'I', 'V'};

    List<Cards> tempDeck = new ArrayList<>();

    for (int i = 0; i < colors.length; i++) {
      for (int j = 1; j < 8; j++) {
        tempDeck.add(new Cards(colors[i], j));
      }
    }
    return tempDeck;
  }

  /**
   * Play the given card from the hand to the losing palette chosen.
   * The card is removed from the hand and placed at the far right
   * end of the palette.
   *
   * @param paletteIdx a 0-index number representing which palette to play to
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   * @throws IllegalStateException if the game has not started or the game is over
   * @throws IllegalArgumentException if paletteIdx < 0 or more than the number of palettes
   * @throws IllegalArgumentException if cardIdxInHand < 0
   *     or greater/equal to the number of cards in hand
   * @throws IllegalStateException if the palette referred to by paletteIdx is winning
   */
  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    if (GameState.DURING_GAME != gameState) {
      throw new IllegalStateException("Game is not runing");
    }

    else if (paletteIdx >= this.palettes.size() || paletteIdx < 0) {
      throw new IllegalArgumentException("The palette index inputted is not valid");
    }

    else if (cardIdxInHand >= this.hand.size() || cardIdxInHand < 0 ) {
      throw new IllegalArgumentException("The card index inputted is not valid");
    }

    else if (this.winningPaletteIndex() == paletteIdx) {
      throw new IllegalStateException("Trying to play into the winning palette, "
              + "which is prohibited");
    }

    palettes.get(paletteIdx).add(hand.remove(cardIdxInHand));
    if (paletteIdx != winningPaletteIndex()) {
      gameState = GameState.GAME_OVER;
    }


    if (hand.isEmpty() && deck.isEmpty()) {
      playerWin = true;
      gameState = GameState.GAME_OVER;
    }
    this.alrPlayedCanvas = false;
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
    if (GameState.DURING_GAME != gameState) {
      throw new IllegalStateException("Game is not running.");
    }

    else if (cardIdxInHand >= this.hand.size() || cardIdxInHand < 0) {
      throw new IllegalArgumentException("Card index is not within 0 the range.");
    }

    else if (this.alrPlayedCanvas) {
      throw new IllegalStateException("A card has already been played to the canvas in this turn.");
    }

    else if (this.hand.size() == 1) {
      throw new IllegalStateException("Not allowed to play your last card to the canvas.");
    }
    canvas = hand.remove(cardIdxInHand);
    this.alrPlayedCanvas = true;

  }

  /**
   * Draws cards from the deck until the hand is full
   * OR until the deck is empty, whichever occurs first. Newly drawn cards
   * are added to the end of the hand (far-right conventionally).
   * SIDE-EFFECT: Allows the player to play to the canvas again.
   *
   * @throws IllegalStateException if the game has not started or the game is over
   */
  @Override
  public void drawForHand() {
    if (GameState.DURING_GAME != gameState) {
      throw new IllegalStateException("The game is not running.");
    }

    /*if (containsDuplicates(this.deck)) {
      throw new IllegalArgumentException("No duplicate cards should be allowed.");
    }*/

    for (int i = this.hand.size(); i < this.handSize; i++) {
      if (!deck.isEmpty()) {
        this.hand.add(this.draw());
      }
      else {
        break;
      }
    }
  }

  //Draws a card from the top of the deck which also remmoves it
  private Cards draw() {
    if (GameState.BEFORE_GAME == gameState) {
      throw new IllegalStateException("The game is not running.");
    }

    /*if (containsDuplicates(this.deck)) {
      throw new IllegalArgumentException("No duplicate cards should be allowed.");
    }*/

    if (this.deck.isEmpty()) {
      throw new IllegalArgumentException("The deck is empty.");
    } else {
      return this.deck.remove(0);
    }
  }

  /**
   * Starts the game with the given options. The deck given is used
   * to set up the palettes and hand. Modifying the deck given to this method
   * will not modify the game state in any way.
   *
   * @param deck the cards used to set up and play the game
   * @param shuffle whether the deck should be shuffled prior to setting up the game
   * @param numPalettes number of palettes in the game
   * @param handSize the maximum number of cards allowed in the hand
   * @throws IllegalStateException if the game has started or the game is over
   * @throws IllegalArgumentException if numPalettes < 2 or handSize <= 0
   * @throws IllegalArgumentException if deck's size is not large enough to setup the game
   * @throws IllegalArgumentException if deck has non-unique cards or null cards
   */
  @Override
  public void startGame(List<Cards> deck, boolean shuffle, int numPalettes, int handSize) {
    if (gameState != GameState.BEFORE_GAME) {
      throw new IllegalStateException("The game has already started.");
    }
    if (deck == null || deck.isEmpty()) {
      throw new IllegalArgumentException("The deck can't be empty or null.");
    }
    if (numPalettes < 2) {
      throw new IllegalArgumentException("The palettes must be more than 2.");
    }
    if (deck.size() < (numPalettes + handSize)) {
      throw new IllegalArgumentException("The deck is not big enough to start.");
    }
    if (handSize <= 0) {
      throw new IllegalArgumentException("Hand size has to be more than 0.");
    }

    if (this.containsDuplicates(deck)) {
      throw new IllegalArgumentException("No duplicate cards should be allowed.");
    }

    gameState = GameState.DURING_GAME;

    this.deck = deck;

    if (shuffle) {
      Collections.shuffle(this.deck, random);
    }

    this.hand = new ArrayList<>();
    this.palettes = new ArrayList<>();
    this.canvas = new Cards('R', 1);
    this.handSize = handSize;
    this.alrPlayedCanvas = false;
    this.playerWin = false;
    this.ifPlayerWon = false;
    /*for (Card c: this.deck){
      System.out.println(c.toString());
    }*/
    //this.palettes = this.initializePalette(numPalettes);

    for (int i = 0; i < numPalettes; i++) {
      palettes.add(new ArrayList<Cards>());
    }

    for (int i = 0; i < numPalettes; i++) {
      palettes.get(i).add(deck.remove(0));
    }

    for (int i = 0; i < handSize; i++) {
      hand.add(deck.remove(0));
    }

  }



  /**
   * Returns the number of cards remaining in the deck used in the game.
   * @return the number of cards in the deck
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int numOfCardsInDeck() {
    if (gameState == GameState.BEFORE_GAME) {
      throw new IllegalStateException("Game is not running.");
    }

    return deck.size();
  }

  /**
   * Returns the number of palettes in the running game.
   * @return the number of palettes in the game
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int numPalettes() {
    if (gameState == GameState.BEFORE_GAME) {
      throw new IllegalStateException("The game is not playing.");
    }
    return palettes.size();
  }

  /**
   * Returns the index of the winning palette in the game.
   * @return the 0-based index of the winning palette
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int winningPaletteIndex() {

    if (gameState == GameState.BEFORE_GAME) {
      throw new IllegalStateException("Game is not running.");
    }

    int winningVal = -1;
    if (canvas.color == 'R') {
      winningVal = redCanvasWinningPal(palettes);
    }

    else if (canvas.color == 'O') {
      winningVal = winningPaletteOrangeRule();
    }
    else if (canvas.color == 'B') {
      winningVal = blueCanvasWinningPal();
    }

    else if (canvas.color == 'I') {
      winningVal = indigoCanvasWinningPal();
    }

    else if (canvas.color == 'V') {
      winningVal = violetCanvasWinningPal();
    }
    return winningVal;
  }

  /**
   *  Determines the winning palette index when the canvas is red.
   *  The winning palette is identified based on the highest card value in each palette.
   * @return the index of the winning palette
   */
  private int redCanvasWinningPal(List<List<Cards>> palettes) {
    List<Cards> comparing = new ArrayList<>();

    for (int i = 0; i < palettes.size(); i++) {
      Cards bestCardNum = palettes.get(i).get(0);

      for (int j = 1; j < palettes.get(i).size(); j++) {
        Cards currentCard = palettes.get(i).get(j);
        bestCardNum = currentCard.compare(bestCardNum);

      }
      comparing.add(bestCardNum);
    }
    Cards bestCardPal = comparing.get(0);

    for (int i = 1; i < comparing.size(); i++) {
      bestCardPal = comparing.get(i).compare(bestCardPal);
    }
    return comparing.indexOf(bestCardPal);
  }

  /**
   * Helper method to rank cards based on color.
   */
  private int colorRank(char color) {
    switch (color) {
      case 'R': return 5;
      case 'O': return 4;
      case 'B': return 3;
      case 'I': return 2;
      case 'V': return 1;
      default: return 0;
    }
  }


  /**
   *  Determines the index of the winning palette when the canvas color is orange.
   *  * The winning palette is identified based on the highest occurrences of any card number
   *  * present in the palettes.
   * @return the index of the winning palette
   */
  private int winningPaletteOrangeRule() {
    Cards bestCard = null;
    int bestIndex = 0;
    int cardNumHighest = 0;

    for (int i = 0; i < palettes.size(); i++) {
      int numMostFreq = 0;
      int countLimit = 0;

      for (int j = 1; j < 8; j++) {
        int tracker = this.checkOccurance(palettes.get(i), j);
        if (tracker >= countLimit) {
          numMostFreq = j;
          countLimit = tracker;
        }
      }

      Cards cardValBest = palettes.get(i).get(0);
      for (int j = 0; j < palettes.get(i).size(); j++) {
        Cards currentCard = palettes.get(i).get(j);

        if (currentCard.getNumber() == numMostFreq) {

          if (currentCard.compare(cardValBest) == cardValBest) {
            cardValBest = currentCard;
          }
        }
      }

      if (countLimit > cardNumHighest || (countLimit == cardNumHighest
              && bestCard.compare(cardValBest) == cardValBest)) {
        bestCard = cardValBest;
        cardNumHighest = countLimit;
        bestIndex = i;

      }
    }
    return bestIndex;
  }

  /**
   *Helper method to find the highest occurrence of any card number in a given palette.
   */
  private int checkOccurance(List<Cards> singlePalette, int paletteNummber) {
    int occurances = 0;

    for (int cardNum = 0; cardNum < singlePalette.size(); cardNum++) {
      if (singlePalette.get(cardNum).getNumber() == paletteNummber) {
        occurances++;
      }
    }
    return occurances;
  }

  /**
   * Determines the index of the winning palette when the canvas color is blue.
   * The winning palette is identified based on the highest count of unique colors present
   * in the palettes.
   * @return the index of the winning palette
   */
  private int blueCanvasWinningPal() {
    Cards bestCard = null;
    int highestIndex = 0;
    int colorMax = 0;

    for (int paletteNum = 0; paletteNum < palettes.size(); paletteNum++) {
      Cards paletteBestCard = null;
      List<Character> colorsAmts = new ArrayList<>();

      for (int cardNum = 0; cardNum < palettes.get(paletteNum).size(); cardNum++) {
        Cards currentCard = palettes.get(paletteNum).get(cardNum);
        if (!colorsAmts.contains(currentCard.getColor())) {
          colorsAmts.add(currentCard.getColor());
        }

        if (paletteBestCard == null) {
          paletteBestCard = currentCard;
        }

        else {
          paletteBestCard = paletteBestCard.compare(currentCard);
        }
      }

      if ((colorsAmts.size() == colorMax && paletteBestCard == bestCard.compare(paletteBestCard))
              || colorsAmts.size() > colorMax) {
        highestIndex = paletteNum;
        bestCard = paletteBestCard;
        colorMax = colorsAmts.size();
      }
    }
    return highestIndex;
  }

  /**
   * Determines the index of the winning palette when the canvas color is violet.
   * The winning palette is identified based on the highest count of cards with numbers
   * below four in the palettes.
   *
   * @return the index of the winning palette
   */
  private int violetCanvasWinningPal() {
    int underCountMaxInd = 0;
    int underCountMax = 0;
    Cards bestCard = new Cards('V', 1);

    for (int paletteNum = 0; paletteNum < palettes.size(); paletteNum++) {
      int occurance = 0;
      Cards bestUnderFourPal = new Cards('V', 1);

      for (int cardNum = 0; cardNum < palettes.get(paletteNum).size(); cardNum++) {
        if (4 > palettes.get(paletteNum).get(cardNum).getNumber()) {
          occurance++;
          bestUnderFourPal = palettes.get(paletteNum).get(cardNum).compare(bestUnderFourPal);
        }
      }

      if (underCountMax < occurance || (underCountMax == occurance
              && bestCard.compare(bestUnderFourPal) == bestUnderFourPal)) {
        underCountMaxInd = paletteNum;
        bestCard = bestUnderFourPal;
        underCountMax = occurance;
      }
    }
    return underCountMaxInd;
  }

  /**
   * Determines the index of the winning palette when the canvas color is indigo.
   *  The winning palette is identified based on the palette with the cards that
   *  form the longest run wins
   * @return the index of the winning palette
   */
  private int indigoCanvasWinningPal() {
    List<Integer> counterRun = new ArrayList<>();
    List<Cards> paletteBestCardInRun = new ArrayList<>();
    CardComparator comparator = new CardComparator();

    for (int paletteNum = 0; paletteNum < palettes.size(); paletteNum++) {
      ArrayList<Cards> palSorted = new ArrayList<>(this.palettes.get(paletteNum));

      palSorted.sort(comparator);

      int runCurrently = 1;
      int runMax = runCurrently;
      Cards runsHighestCard = palSorted.get(0);

      for (int cardIndex = 1; cardIndex < palSorted.size(); cardIndex++) {
        if (palSorted.get(cardIndex - 1).getNumber() == palSorted.get(cardIndex).getNumber() - 1) {
          runsHighestCard = palSorted.get(cardIndex);
          runCurrently++;

          if (runCurrently > runMax) {
            runMax = runCurrently;
          }
        }

        else {
          runCurrently = 1;
        }
      }
      counterRun.add(runMax);
      paletteBestCardInRun.add(runsHighestCard);
    }

    int runHighestNum = counterRun.get(0);
    int runHighIndex = 0;

    Cards palBestCard = paletteBestCardInRun.get(0);

    for (int i = 1; i < counterRun.size(); i++) {
      if (runHighestNum == counterRun.get(i)) {
        Cards currentBestCard = paletteBestCardInRun.get(i);

        if (palBestCard.compare(currentBestCard) == currentBestCard) {
          palBestCard = currentBestCard;
          runHighIndex = i;
        }
      }
      else if (counterRun.get(i) > runHighestNum) {
        runHighIndex = i;
        runHighestNum = counterRun.get(i);
        palBestCard = paletteBestCardInRun.get(i);
      }
    }
    return runHighIndex;
  }

  /**
   * Returns if the game is over as specified by the implementation.
   * @return true if the game has ended and false otherwise
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public boolean isGameOver() {
    if (GameState.BEFORE_GAME == gameState) {
      throw new IllegalStateException("Game is not being played");
    }
    return GameState.GAME_OVER == gameState;
  }

  /**
   * Returns if the game is won by the player as specified by the implementation.
   * @return true if the game has been won or false if the game has not
   * @throws IllegalStateException if the game has not started or the game is not over
   */
  @Override
  public boolean isGameWon() {
    if (gameState != GameState.GAME_OVER) {
      throw new IllegalStateException("Game has not finished.");
    }
    return ifPlayerWon;
  }

  /**
   * Returns a copy of the hand in the game. This means modifying the returned list
   * or the cards in the list has no effect on the game.
   * @return a new list containing the cards in the player's hand in the same order
   *     as in the current state of the game.
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public List getHand() {
    if (GameState.BEFORE_GAME == gameState) {
      throw new IllegalStateException("Game has not started.");
    }
    return new ArrayList<>(this.hand);
  }

  /**
   * Returns a copy of the specified palette. This means modifying the returned list
   * or the cards in the list has no effect on the game.
   * @param paletteNum 0-based index of a particular palette
   * @return a new list containing the cards in specified palette in the same order
   *     as in the current state of the game.
   * @throws IllegalStateException if the game has not started
   * @throws IllegalArgumentException if paletteIdx < 0 or more than the number of palettes
   */
  @Override
  public List getPalette(int paletteNum) {
    if (GameState.BEFORE_GAME == gameState) {
      throw new IllegalStateException("The game has not yet started.");
    }

    else if (paletteNum >= palettes.size() || paletteNum < 0) {
      throw new IllegalArgumentException("Your palette number is not valid.");
    }
    return this.palettes.get(paletteNum);
  }

  /**
   * Return the top card of the canvas.
   * Modifying this card has no effect on the game.
   * @return the top card of the canvas
   * @throws IllegalStateException if the game has not started or the game is over
   */
  @Override
  public Cards getCanvas() {
    if (gameState == GameState.BEFORE_GAME) {
      throw new IllegalStateException("The game has not yet started.");
    }
    return this.canvas;
  }

  /**
   * Get a NEW list of all cards that can be used to play the game.
   * Editing this list should have no effect on the game itself.
   * Repeated calls to this method should produce a list of cards in the same order.
   * Modifying the cards in this list should have no effect on any returned list
   * or the game itself.
   * @return a new list of all possible cards that can be used for the game
   */
  @Override
  public List<Cards> getAllCards() {
    return this.instantiateDeck();
  }

  /**
   * Checks for duplicate cards in the deck.
   *
   * @param deck the list of cards to check
   * @return true if there are duplicates in the deck, false otherwise
   */
  private boolean containsDuplicates(List<Cards> deck) {
    List<Cards> cdSeen = new ArrayList<>();

    for (int i = 0; i < deck.size(); i++) {
      if (cdSeen.contains(deck.get(i)) ) {
        return true;
      } else {
        cdSeen.add(deck.get(i));
      }
    }
    return false;
  }

}
