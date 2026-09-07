import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// Control3 class is used for the main game screen where we play the actual game
// with this class we are able to control the buttons and textfields so that they are able to react accordingly and
// communicate with the server more accordingly
public class GameController implements Initializable{

    @FXML
    public Label catName, yesL, noL;
    @FXML
    public Label showcaseWord;
    @FXML
    public Label guessCounter;
    @FXML
    public Button Button2;
    @FXML
    public TextField textField1;
    public Scene scene;
    public Stage stage;

    // every time we enter the main game screen we initialize some labels and the category in which we enter the scene
    // with.
    // We are also creating a game data here to be able to use the data that the main control from the menu has to
    // update this control on anything from the past games and on the word that they received from the server
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientConnectionController.client.setCallBack(data -> {
            Platform.runLater(() -> { // some code to update components in scene 3
                GameData temp = (GameData) data;
                showcaseWord.setText(temp.gameShowcaseWord);
            });
        });
        catName.setText(ClientConnectionController.client.data.userChosenCategory);
        showcaseWord.setText(ClientConnectionController.client.data.gameShowcaseWord);
        guessCounter.setText("Guesses Left: " + String.valueOf(ClientConnectionController.client.data.wordGuessLives));
    }

    // This function is used to check if a user has inputted a number instead of a letter
    public boolean isNum(String letter) {
        char temp = letter.charAt(0);
        for (int i = 0; i < 10; i++) {
            if ((int)temp == i) {
                return true;
            }
        }
        return false;
    }

    // The action event once the user has inputted a letter that they would like to check if it's in the word
    // the letter is sent to the server to be able to check if the word is a correct word, and
    // it returns if it is and if the user loses a guess into trying to find the actual word
    // We're also checking if the user has lost or won the round so that we can send them back to the menu screen in
    // the case that they have found the word and not run out of lives
    public void enter(ActionEvent en) throws IOException {
        String letter = textField1.getText();
        if (letter.length() == 1 ){
        ClientConnectionController.client.data.UserGuess = letter.charAt(0);
        ClientConnectionController.client.data.enterButtonClicked = true;
        ClientConnectionController.client.data.catButtonClicked = false;
        ClientConnectionController.client.send(ClientConnectionController.client.data);
        textField1.clear();

        ClientConnectionController.client.setCallBack(data -> {
            Platform.runLater(() -> { // some code to update components in scene 3
                GameData temp = (GameData) data;
                if (temp.roundLost){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/menu.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MenuController ctr2 = loader.getController();
                    stage = (Stage)((Node)en.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                if (temp.roundWon){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/menu.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MenuController ctr2 = loader.getController();
                    stage = (Stage)((Node)en.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                if (temp.userGuessRight){
                    yesL.setOpacity(1);
                    noL.setOpacity(0);
                }
                else{
                    yesL.setOpacity(0);
                    noL.setOpacity(1);
                }
                showcaseWord.setText(temp.gameShowcaseWord);
                guessCounter.setText("Guesses Left: " + String.valueOf(ClientConnectionController.client.data.wordGuessLives));
                System.out.println(temp.gameShowcaseWord);
                });
            });
        }
        else {
            //print something to correct user
            System.out.println("Either chose a capital letter, entered a number, or left blank");
        }

    }

}
