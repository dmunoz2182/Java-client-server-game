import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;
//This class is used to create a control for the scene that we are going to be using for the server
//declaring values in the fxml file,ports, and server
public class ServerController implements Initializable {
    @FXML
    public Button bun;
    @FXML
    public TextField portNum;
    public int port;
    public ListView listItems;
    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    // with the method function we declare that in the instance that the method is called on
    // we run to check if the connection has been made and set up all the list items and the data for the game that we are going to be
    // sending to the client later on
    public void Method(javafx.event.ActionEvent actionEvent) {
        try {
            port = Integer.parseInt(portNum.getText());
            server = new Server(data -> {
                Platform.runLater(()->{
                    if(data instanceof String) {
                        listItems.getItems().add(data.toString());
                    }
                    else if(data instanceof GameData){
                        GameData temp = (GameData) data;
                    }
                });
            },port);
            portNum.setDisable(true);
            bun.setDisable(true);
        }
        catch(Exception e) {
            System.out.println("Something is wrong with the way you input the port number");
        }
    }
}