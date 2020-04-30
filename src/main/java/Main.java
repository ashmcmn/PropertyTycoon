import controller.MainController;
import view.GameView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        MainController controller = new MainController(primaryStage);
        GameView view = new GameView(controller, screenBounds.getWidth()*0.8, screenBounds.getHeight()*0.8);
        controller.setView(view);

        Scene scene = new Scene(view, screenBounds.getWidth()*0.8, screenBounds.getHeight()*0.8);
        primaryStage.setTitle("Property Tycoon");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}