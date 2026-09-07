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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// control 2 class is used for it to work with the fxml file of menu because with the menu
// scene we have the categories of what the user can use and the amount of lives that they have
// we also declare some of the values from the file to be able to change aspects of the game
// or react to the buttons pressed to know when we can switch the scenes
public class MenuController implements Initializable{

    @FXML
    public AnchorPane root;
    @FXML
    public Label animalLives, brandLives, movieLives, overallWin, overallLose;
    @FXML
    public Button animalButton, brandButton, moviesButton, leaveG, runAgain;
    @FXML
    public Text roundWinL, roundLossL;

    private Stage stage;
    private Scene scene;

    //Here we initialize wha isn't already put in the game such as the amount of lives the user
    // has for each category adn what buttons they aren't able to use
    //And in any cases in which the client wins the game or loses all buttons are disabled and new ones appear
    // giving the user the option to play again or exit the game
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (ClientConnectionController.client.data.roundLost){
            roundLossL.setOpacity(1);
            roundWinL.setOpacity(0);
        }
        if (ClientConnectionController.client.data.roundWon){
            roundLossL.setOpacity(0);
            roundWinL.setOpacity(1);
        }
        animalLives.setText(String.valueOf(ClientConnectionController.client.data.categoryAnimalsLives));
        brandLives.setText(String.valueOf(ClientConnectionController.client.data.categoryBrandsLives));
        movieLives.setText(String.valueOf(ClientConnectionController.client.data.categoryMoviesLives));
        if(ClientConnectionController.client.data.userWin){
            roundWinL.setOpacity(0);
            overallWin.setOpacity(1);
            overallLose.setOpacity(0);
            runAgain.setDisable(false);
            leaveG.setDisable(false);
            runAgain.setOpacity(1);
            leaveG.setOpacity(1);
            animalButton.setDisable(true);
            brandButton.setDisable(true);
            moviesButton.setDisable(true);
        }
        if(ClientConnectionController.client.data.userLost){
            roundLossL.setOpacity(0);
            overallWin.setOpacity(0);
            overallLose.setOpacity(1);
            runAgain.setDisable(false);
            leaveG.setDisable(false);
            runAgain.setOpacity(1);
            leaveG.setOpacity(1);
            animalButton.setDisable(true);
            brandButton.setDisable(true);
            moviesButton.setDisable(true);
        }
        disableButtons();
    }

    // In the case the user presses the game, we exit the screen entirely
    public void setLeaveG(ActionEvent en) throws IOException{
        Platform.exit();
        System.exit(0);
    }

    // we reset all the data and labels with the buttons for the game to start over again
    public void setPlayAgain(ActionEvent en) throws IOException{
        enableButtons();
        ClientConnectionController.client.data = new GameData();
        roundWinL.setOpacity(0);
        roundLossL.setOpacity(0);
        overallWin.setOpacity(0);
        overallLose.setOpacity(0);

        runAgain.setDisable(true);
        leaveG.setDisable(true);
        runAgain.setOpacity(0);
        leaveG.setOpacity(0);
        animalLives.setText(String.valueOf(ClientConnectionController.client.data.categoryAnimalsLives));
        brandLives.setText(String.valueOf(ClientConnectionController.client.data.categoryBrandsLives));
        movieLives.setText(String.valueOf(ClientConnectionController.client.data.categoryMoviesLives));
        ClientConnectionController.client.data.restart = true;
        ClientConnectionController.client.send(ClientConnectionController.client.data);
    }

    // in the case that the user presses the animal button, and it's not disabled
    // then the screen will change and the game data will be updated for the next screen to load in
    public void animButton(ActionEvent en) throws IOException {
        ClientConnectionController.client.data.userChosenCategory = "Animals";
        ClientConnectionController.client.data.gameActive = true;
        ClientConnectionController.client.data.catButtonClicked = true;
        ClientConnectionController.client.data.roundLost = false;
        ClientConnectionController.client.data.roundWon = false;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/inGame.fxml"));
        Parent root = loader.load();
        GameController ctr3 = loader.getController();
        stage = (Stage)((Node)en.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ClientConnectionController.client.send(ClientConnectionController.client.data);
    }

    // in the case that the user presses the brand button, and it's not disabled
    // then the screen will change and the game data will be updated for the next screen to load in
    public void braButton(ActionEvent en) throws IOException {
        ClientConnectionController.client.data.userChosenCategory = "Brands";
        ClientConnectionController.client.data.gameActive = true;
        ClientConnectionController.client.data.catButtonClicked = true;
        ClientConnectionController.client.data.roundLost = false;
        ClientConnectionController.client.data.roundWon = false;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/inGame.fxml"));
        Parent root = loader.load();
        GameController ctr3 = loader.getController();
        stage = (Stage)((Node)en.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ClientConnectionController.client.send(ClientConnectionController.client.data);
    }

    // in the case that the user presses the movie button, and it's not disabled
    // then the screen will change and the game data will be updated for the next screen to load in
    public void movButton(ActionEvent en) throws IOException {
        ClientConnectionController.client.data.userChosenCategory = "Movies";
        ClientConnectionController.client.data.gameActive = true;
        ClientConnectionController.client.data.catButtonClicked = true;
        ClientConnectionController.client.data.roundLost = false;
        ClientConnectionController.client.data.roundWon = false;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/inGame.fxml"));
        Parent root = loader.load();
        GameController ctr3 = loader.getController();
        stage = (Stage)((Node)en.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ClientConnectionController.client.send(ClientConnectionController.client.data);
    }

    // based on if the user won the category then they cannot enter anymore again on the game
    public void disableButtons(){
        if (ClientConnectionController.client.data.animalsWin){
            animalButton.setDisable(true);
        }
        if (ClientConnectionController.client.data.brandsWin){
            brandButton.setDisable(true);
        }
        if (ClientConnectionController.client.data.moviesWin){
           moviesButton.setDisable(true);
        }
    }

    // this function is used for when the user decides to play gain all the buttons are enabled again
    public void enableButtons(){
        animalButton.setDisable(false);
        brandButton.setDisable(false);
        moviesButton.setDisable(false);
    }

}
