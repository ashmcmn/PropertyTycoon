import controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        MenuController controller = new MenuController(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuView.fxml"));
        loader.setController(controller);
        AnchorPane root = loader.load();
        controller.setView(root);

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setTitle("Property Tycoon");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.updatePlayerList();
    }


    public static void main(String[] args) {
        launch(args);
    }
}