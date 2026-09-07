import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameLogicTest {
    public GameLogic game;
    public GameData gameData;

    @BeforeEach
    public void setup(){            // set up the objects to test
        game = new GameLogic();
        gameData = new GameData();
    }

    @Test
    public void initTest(){     //this makes sure all the list of words for the categories are initialized
        game.initCategories();
        assertEquals(9, game.Brands.length);
        assertEquals(9, game.Movies.length);
        assertEquals(9, game.Animals.length);
    }

    @Test
    public void checkUserGuessTest1(){      //checks to see that the method to check wether they guessed right works
        assertEquals(true, game.checkUserGuess("something", 's', "---------"));
    }

    @Test
    public void checkUserGuessTest2(){     //checks to see that the method to check wether they guessed right works
        assertEquals(false, game.checkUserGuess("something", 'l', "---------"));
    }

    @Test
    public void checkUserGuess2Test1(){ //checks to make sure that the user's guess on the character is correct and updates the word
        ArrayList<Integer> something = new ArrayList<>();
        assertEquals("-h---", game.checkUserGuess2("shark", 'h', "-----", something));
    }

    @Test
    public void checkUserGuess2Test2(){ //checks to make sure that the user's guess on the character is correct and updates the word
        ArrayList<Integer> something = new ArrayList<>();
        assertEquals("sna-e", game.checkUserGuess2("snake", 'e', "sna--", something));
    }

    @Test
    public void initShowcaseWordTest1(){ //creates a blank word to send over to the client when the game starts
        assertEquals("----", game.initShowcaseWord("deer"));
    }

    @Test
    public void initShowcaseWordTest2(){ //creates a blank word to send over to the client when the game starts
        assertEquals("---------", game.initShowcaseWord("something"));
    }

    @Test
    public void checkUserWonTest1(){ // this checks that the user won by guessing the word properly
        assertEquals(true, game.checkUserWon("hello", "hello"));
    }

    @Test
    public void checkUserWonTest2(){ // this checks that the user won by guessing the word properly
        assertEquals(false, game.checkUserWon("hello", "he--o"));
    }

    @Test
    public void checkUserLostTest1(){ // this checks that the user lost by the word being wrong or incomplete
        assertEquals(false, game.checkUserLost(gameData));
    }

    @Test
    public void checkUserLostTest2(){ // this checks that the user lost by the word being wrong or incomplete
        gameData.wordGuessLives = 0;
        assertEquals(true, game.checkUserLost(gameData));
    }
}
