 Homework 3 Changes
- instantiateDeck(): I changed it by making a temp deck and returning it because it initially was
  just adding the cards to the original deck and was creating a repeat of cards.
- startGame(): Switched the first if statement to (deck == null || deck.isEmpty()) instead of its
  reverse because it would not throw the IllegalArgumentException properly if the deck was null.
  Added a containsDuplicates method.
- Added an if-else statement in printCardsInPalette() so it doesn't print a space at the end of
  its output.
- Added an if-else statement in printHand() so it doesn't print a space at the end of its output.

Homework 4 Changes
- in SoloRedGameModel, I made the canvas, hand, gameState, handSize, and deck variables protected
  instead of private that way they could be accessed by the AdvancedSoloRedGameModel and only that
  class. Other classes can still not access those variables.
- changed initializing all my global variables from the constructor to the startGame method except
  random because if I have it in the constructor it would be duplicate code and serves no purpose.
  Keeping it only in the constructor previously was giving me various errors.
- in violetCanvasWinningPal() I changed the bestCard and bestUnderFourPal variable from null
  to a Violet 1 because they will always lose or be equal to itself and will avoid a
  NullPointerException

