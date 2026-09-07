import java.io.Serializable;
// The game data class is to be able to keep track of instances that happen throughout the game
// we are able to use them in other classes to be able to communicate with the server in updating events and how many
// attempts a user might have in a category or if they have lost any rounds
public class GameData implements Serializable{
    String gameShowcaseWord = ""; //ask about
    String userChosenCategory = "";
    boolean gameActive = false;
    boolean userGuessRight = false;
    char UserGuess;
    int wordGuessLives = 6;
    int categoryAnimalsLives = 3;
    int categoryBrandsLives = 3;
    int categoryMoviesLives = 3;
    boolean animalsWin = false;
    boolean brandsWin = false;
    boolean moviesWin = false;
    boolean userLost = false;
    boolean userWin = false;
    boolean roundLost = false;
    boolean roundWon = false;
    boolean catButtonClicked = false;
    boolean enterButtonClicked = false;
    boolean restart = false;
}