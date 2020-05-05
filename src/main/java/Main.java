import controller.MainController;
import javafx.event.EventHandler;
import view.GameView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Main extends Application {

    private final double SIZEMOD = 0.8;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        MainController controller = new MainController(primaryStage);
        GameView view = new GameView(controller, screenBounds.getWidth()*SIZEMOD, screenBounds.getHeight()*SIZEMOD);
        controller.setView(view);

        Scene scene = new Scene(view, screenBounds.getWidth()*SIZEMOD, screenBounds.getHeight()*SIZEMOD);
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("Property Tycoon");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}