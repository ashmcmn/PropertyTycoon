package frontend;
/**
 * @author Connor van Graan
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Board extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Dictionary<Integer, double[]> cells;
    Dictionary<Integer, ImageView> cells2;
    int cell = 0;
    int players;
    Image icon;
    Dictionary<String, Node> labels;

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public Pane setCells() throws MalformedURLException, IOException {
        cells = new Hashtable<Integer, double[]>();
        cells2 = new Hashtable<Integer, ImageView>();

        Image im = null;
        Pane g = new Pane();

        ImageView it = new ImageView(icon);
        it.setLayoutX(45);
        it.setLayoutY(670);
        it.setStyle("-sfx-font-size: 20; -fx-border-color: black");
        it.setFitHeight(25);
        it.setFitWidth(25);
        g.getChildren().add(it);

        for (int i = 1; i < 10; i++) {
            ImageView iv = new ImageView(im);
            int x = 638 - (i * 55);
            double[] d = {45, x};
            cells.put((Integer) i, d);
            iv.setLayoutX(45);
            iv.setLayoutY(x);
            iv.setStyle("-sfx-font-size: 20; -fx-border-color: black");
            iv.setFitHeight(25);
            iv.setFitWidth(25);
            iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    double currentX = iv.getLayoutX();
                    double currentY = iv.getLayoutY();
                    System.out.println(String.format("%f,%f", currentX, currentY));
                }
            });
            cells2.put((Integer) i, iv);
            g.getChildren().add(iv);
        }

        ImageView ix = new ImageView(im);
        ix.setLayoutX(32);
        ix.setLayoutY(50);
        ix.setStyle("-sfx-font-size: 20; -fx-border-color: black");
        ix.setFitHeight(25);
        ix.setFitWidth(25);
        cells2.put(10, ix);
        g.getChildren().add(ix);

        for (int i = 0; i < 10; i++) {
            ImageView iv = new ImageView(im);
            int x = 162 + (i * 66);
            double[] d = {x, 53};
            cells.put(i + 10, d);
            iv.setLayoutX(x);
            iv.setLayoutY(53);
            iv.setStyle("-sfx-font-size: 20; -fx-border-color: black");
            iv.setFitHeight(25);
            iv.setFitWidth(25);
            cells2.put((Integer) i + 11, iv);
            g.getChildren().add(iv);
        }

        for (int i = 0; i < 10; i++) {
            ImageView iv = new ImageView(im);
            int x = 143 + (i * 55);
            double[] z = {807, x};
            cells.put(i + 21, z);
            iv.setLayoutX(807);
            iv.setLayoutY(x);
            iv.setStyle("-sfx-font-size: 20; -fx-border-color: black");
            iv.setFitHeight(25);
            iv.setFitWidth(25);
            cells2.put((Integer) i + 21, iv);
            g.getChildren().add(iv);
        }

        for (int i = 0; i < 9; i++) {
            ImageView iv = new ImageView(im);
            int x = 690 - (i * 66);
            double[] z = {x, 665};
            cells.put(i + 31, z);
            iv.setLayoutX(x);
            iv.setLayoutY(665);
            iv.setStyle("-sfx-font-size: 20; -fx-border-color: black");
            iv.setFitHeight(25);
            iv.setFitWidth(25);
            cells2.put((Integer) i + 31, iv);
            g.getChildren().add(iv);
        }

        //Cell for jail
        ImageView iy = new ImageView(im);
        iy.setLayoutX(83);
        iy.setLayoutY(80);
        iy.setStyle("-sfx-font-size: 20; -fx-border-color: black");
        iy.setFitHeight(25);
        iy.setFitWidth(25);
        g.getChildren().add(iy);


        return g;
    }




    //updating location for role
    public void updateCell(Pane g) throws MalformedURLException, IOException {
        InputStream u = new URL("https://i.imgur.com/Nwj4hfi.png").openStream();
        Image icon = new Image(u);
        ImageView k = (ImageView) g.getChildren().get(cell);
        k.setImage(null);
        g.getChildren().set(cell, k);

        cell += (int) (Math.random() * ((12 - 2) + 1)) + 2;
        System.out.println(cell);
        if (cell > 39) {
            cell = cell - 40;
        }
        System.out.println("cell:" + cell);
        ImageView j = (ImageView) g.getChildren().get(cell);
        j.setImage(icon);
        g.getChildren().set(cell, j);

    }

    //Sends player icon to jail
    public void goToJail(Pane g) {
        ImageView k = (ImageView) g.getChildren().get(cell);
        k.setImage(null);
        g.getChildren().set(cell, k);
        cell = 40;
        System.out.println(cell);
        ImageView j = (ImageView) g.getChildren().get(cell);
        j.setImage(icon);
        g.getChildren().set(cell, j);
    }

    //Sends player icon to Go
    public void toGo(Pane g) {
        ImageView k = (ImageView) g.getChildren().get(cell);
        k.setImage(null);
        g.getChildren().set(cell, k);
        cell = 0;
        System.out.println(cell);
        ImageView j = (ImageView) g.getChildren().get(cell);
        j.setImage(icon);
        g.getChildren().set(cell, j);
    }

    public Pane setUpUI(Pane grid) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();
        // need to set these so they scale
        double w = width / 1.51777;
        double h = height / 2.56;


        Button roll = new Button("");
        roll.setLayoutX(w);
        roll.setLayoutY(h);
        roll.setPrefSize(215, 70);
        roll.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        roll.setOnAction((ActionEvent e) -> {
            try {
                updateCell(grid);
            } catch (IOException ex) {
                Logger.getLogger(PTUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.getChildren().add(roll);

        Button auc = new Button("");
        auc.setLayoutX(w + 236);
        auc.setLayoutY(h);
        auc.setPrefSize(215, 70);
        auc.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        auc.setOnAction((ActionEvent e) -> {
            setMoney(100);
        });
        grid.getChildren().add(auc);

        Button mana = new Button("");
        mana.setLayoutX(w);
        mana.setLayoutY(h + 88);
        mana.setPrefSize(215, 70);
        mana.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        mana.setOnAction((ActionEvent e) -> {
            try {
                updateCell(grid);
            } catch (IOException ex) {
                Logger.getLogger(PTUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.getChildren().add(mana);

        Button purchase = new Button("");
        purchase.setLayoutX(w + 236);
        purchase.setLayoutY(h + 88);
        purchase.setPrefSize(215, 70);
        //purchase.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        purchase.setOnAction((ActionEvent e) -> {
            try {
                updateCell(grid);
            } catch (IOException ex) {
                Logger.getLogger(PTUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.getChildren().add(purchase);

        Button end = new Button("");
        end.setLayoutX(w);
        end.setLayoutY(h + 176);
        end.setPrefSize(215, 70);
        //end.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        end.setOnAction((ActionEvent e) -> {
            try {
                updateCell(grid);
            } catch (IOException ex) {
                Logger.getLogger(PTUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.getChildren().add(end);

        Button menu = new Button("");
        menu.setLayoutX(w + 236);
        menu.setLayoutY(h + 176);
        menu.setPrefSize(215, 70);
        //menu.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        menu.setOnAction((ActionEvent e) -> {
            try {
                updateCell(grid);
            } catch (IOException ex) {
                Logger.getLogger(PTUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.getChildren().add(menu);

        return grid;
    }

    public Pane setLabels(Pane grid) {
        labels = new Hashtable<>();

        Label money = new Label("Money");
        labels.put("money", money);
        money.setStyle("-sfx-font-size: 200; -fx-border-color: black;  -fx-font-weight: bold"); //-fx-background-color:GREEN;
        //money.setTextFill(Color.WHITE);
        money.setAlignment(Pos.CENTER);
        money.setLayoutX(904);
        money.setLayoutY(202);
        money.setPrefSize(150, 60);
        grid.getChildren().add(money);


        ImageView pi = new ImageView(icon);
        labels.put("pi", pi);
        pi.setLayoutX(1250); //player icon
        pi.setLayoutY(188);
        pi.setFitWidth(60);
        pi.setFitHeight(70);
        grid.getChildren().add(pi);

        Label name = new Label("name");
        labels.put("name", name);
        name.setLayoutX(900);
        name.setLayoutY(18);
        name.setPrefSize(150, 60);
        name.setStyle("-sfx-font-size: 500;  -fx-font-weight: bold");// -fx-border-color: black;
        grid.getChildren().add(name);


        return grid;
    }

    public void setMoney(int x) {
        Label m = (Label) labels.get("money");
        m.setText(String.valueOf(x)); //set to getPlayer money
    }


    @Override
    public void start(Stage stage) throws MalformedURLException, IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        System.out.println(screenBounds);
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();
        InputStream u = new URL("https://i.imgur.com/Nwj4hfi.png").openStream();
        Image image = new Image(u);
        setIcon(image);
        Pane grid = setCells();
        grid = setUpUI(grid);
        grid = setLabels(grid);


        InputStream f = new URL("https://imgur.com/ZZ60PBC.png").openStream();
        //InputStream g = new URL("https://i.imgur.com/Nwj4hfi.png").openStream();


        //adjust to 768 when there is the correct aspect ratio
        Image i = new Image("https://imgur.com/ZZ60PBC.png", 1366, 720, false, true, true);
        //new Image()

        //Image i = new Image(f, 1366, 720, false, true);


        BackgroundImage myBI = new BackgroundImage(i, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        Scene scene = new Scene(grid, screenBounds.getWidth(), screenBounds.getHeight());
        grid.setBackground(new Background(myBI));
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Property Tycoon");
        stage.setIconified(false);
        stage.setResizable(false);
        stage.show();


        /**
         * @param args the command line arguments

        public static void main(String[] args) {
        launch(args);
        }*/
    }
}

