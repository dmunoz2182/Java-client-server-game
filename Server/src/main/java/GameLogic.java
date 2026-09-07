import java.util.ArrayList;
import java.util.Collections;


// In the game logic class here we store all the possible words for each category and
//since they are stored here we have functions here that also check and set up the word
// to be random for a certain category
public class GameLogic {
    String[] Animals = {"shark", "snake", "monkey", "hippo", "giraffe", "wolf", "deer", "duck", "beaver"};
    String[] Brands = {"dior", "nike", "gucci", "adidas", "apple", "levis", "disney", "rebook", "prada"};
    String[] Movies = {"scream", "jaws", "shazam", "minions", "ted", "hellboy", "heat", "rush", "jigsaw"};

    ArrayList<String> categoryAnimals = new ArrayList<>();
    ArrayList<String> categoryBrands = new ArrayList<>();
    ArrayList<String> categoryMovies = new ArrayList<>();

    // adds all the possible options for a word in a random order and stores them to
    // their according category
    public void initCategories (){
        for(int i = 0; i < 9; i++) {
            categoryAnimals.add(Animals[i]);
            categoryBrands.add(Brands[i]);
            categoryMovies.add(Movies[i]);
        }
        Collections.shuffle(categoryAnimals);
        Collections.shuffle(categoryBrands);
        Collections.shuffle(categoryMovies);
    }

    //Gets a random word from the list that has been made already and sets the number of
    // total guesses that the user gets for the word
    public String getRandomWord(String cat, int lives){
        if (cat.equals("Animals")){
            return categoryAnimals.get(lives);
        }
        else if (cat.equals("Brands")){
            return categoryBrands.get(lives);
        }
        else if (cat.equals("Movies")){
            return categoryMovies.get(lives);
        }
        return "-1";
    }

    //when this function is called we have the actual word and what has been found by the user
    // of the word, with the letter that they are guessing next
    // so that we check the whole word and see what has been found
    // and if it is complete or not sending a true or false back
    public boolean checkUserGuess(String word, char userGuess, String showCaseWord){
        boolean temp = false;
        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) == userGuess){
                char[] tempS = showCaseWord.toCharArray();
                tempS[i] = userGuess;
                showCaseWord = String.valueOf(tempS);
                temp = true;
            }
        }
        if (temp){
            return true;
        } else{
            return false;
        }
    }

    // doing the same as the function above except this time we send back the indexes
    // that the letter was found in for the server to show
    public String checkUserGuess2(String word, char userGuess, String showCaseWord, ArrayList<Integer> indexes){
        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) == userGuess){
                char[] tempS = showCaseWord.toCharArray();
                indexes.add(i);
                tempS[i] = userGuess;
                showCaseWord = String.valueOf(tempS);
            }
        }
        return showCaseWord;
    }

    // this function checks if the user has lost the game in the
    //  case that they use up all their lives for one category
    // and to which category that they lost it in
    public void checkUserLostGame(GameData data){
        if (data.categoryAnimalsLives == 0){
            data.userLost = true;
        }
        else if (data.categoryBrandsLives == 0){
            data.userLost = true;
        }
        else if(data.categoryMoviesLives == 0){
            data.userLost = true;
        }
    }

    // this function does the same as the one above but checks to see if the
    // user won all categories of the game
    // to send a won game true back
    public void checkUserWonGame(GameData data){
        if ((data.animalsWin) && (data.brandsWin) && (data.moviesWin)){
            data.userWin = true;
        }
    }

    // this function checks to see if the user found the right word
    // without them losing all their guesses
    public boolean checkUserWon(String word, String showCaseWord){
        if (word.equals(showCaseWord)){
            return true;
        }
        return false;
    }

    // this function checks to see if the user lost the round and used up all their guesses
    public boolean checkUserLost(GameData gameData){
        if (gameData.wordGuessLives == 0){
            return true;
        }
        return false;
    }

    //Based on the length of the word the function will replace it to be "-"
    // so that the user sees what parts of the words that they don't have yet
    public String initShowcaseWord(String word){
        StringBuilder tempWord = new StringBuilder();
        for (int i =0; i < word.length(); i++){
            tempWord.append("-");
        }
        return tempWord.toString();
    }

    // if the word is found then based on the category we set that the category is won
    public void setCatWin(String cat, GameData data){
        if (cat.equals("Animals")){
            data.animalsWin = true;
        }
        else if (cat.equals("Brands")){
            data.brandsWin = true;
        }
        else if (cat.equals("Movies")){
            data.moviesWin = true;
        }
    }

    // This function decreases the lives that have been used based on playing the game on a
    // certain category
    public void decreaseLives(String cat, GameData data){
        if (cat.equals("Animals")){
            data.categoryAnimalsLives -= 1;
        }
        else if (cat.equals("Brands")){
            data.categoryBrandsLives -= 1;
        }
        else if (cat.equals("Movies")){
            data.categoryMoviesLives -= 1;
        }
    }
}
