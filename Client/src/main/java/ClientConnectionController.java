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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// This control is used to manage the first screen that the user uses
// In which they have to input a port number to the server and in the case that the port number
// is not the right one then they get a pop-up that tells them to make sure that they get the right one
public class ClientConnectionController implements Initializable {
    @FXML
    public AnchorPane root;
    @FXML
    public Button Button1;
    @FXML
    public TextField Text1;
    @FXML
    public Label portL;
    static int port;
    public static Client client;
    private Stage stage;
    private Scene scene;

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    // when the user enters a port number a connections is made as a client and to do it we use a try so that
    // in the case it doesn't work we tell the user that they need to try again
    // otherwise the menu screen and their control are loaded in
    public void Enter(ActionEvent en) throws IOException{
        try{
            port = Integer.parseInt(Text1.getText());
            portL.setOpacity(1);
            client = new Client(data2 -> {
                Platform.runLater(()->{
                    client.data = (GameData) data2;

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

                    // System.out.println(client.data.gameShowcaseWord);
                });
            },port);
            client.start();
        } catch(Exception e){
            System.out.println("Something Wrong With the connection");
        }

    }
}
