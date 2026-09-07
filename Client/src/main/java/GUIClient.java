import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class GUIClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Read file fxml and draw interface.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/GUIClient.fxml"));
            primaryStage.setTitle("My Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch(Exception e) {
            System.out.println("Something wrong starting the game");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
