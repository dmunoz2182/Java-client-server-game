import java.io.Serializable;
// Here in this class we declare the class as a serializable to be able to call upon
// any objects that we created and manipulate them as the game progresses for each client
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