/*
  @author Connor van Graan
 */

package frontend;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.xml.transform.sax.SAXSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Board extends Application {

    Dictionary<Integer, double[]> cells;
    Dictionary<Integer, ImageView> cells2;
    int cell = 0;
    int players;
    Image icon;
    Dictionary<String, Node> labels;
    Pane grid;
    double height;
    double width;


    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    //sets up the cells on the board
    public Pane setCells() {
        cells = new Hashtable<>();
        cells2 = new Hashtable<>();


        Pane g = new Pane();

        //Go cell
        ImageView im = new ImageView(icon);
        im.setLayoutX(0.02928257686*width);// (40/1366) * width
        im.setLayoutY(0.91796875*height); // (705/768) * height
        im.setStyle("-sfx-font-size: 20; -fx-border-color: black");
        im.setFitHeight(25);
        im.setFitWidth(25);
        g.getChildren().add(im);

        //Left column cells
        for (int i = 1; i < 10; i++) {
            ImageView iv = new ImageView(icon);
            double y = (0.86458333333*height) - (i * (0.07552083333*height)); // (664/768) & (58/768)

            iv.setLayoutX(0.03660322108*width); //50/1366
            iv.setLayoutY(y);
            iv.setStyle("-sfx-font-size: 20; -fx-border-color: black");
            iv.setFitHeight(25);
            iv.setFitWidth(25);
            iv.setOnMouseClicked(e -> {
                double currentX = iv.getLayoutX();
                double currentY = iv.getLayoutY();
                System.out.println(String.format("%f,%f", currentX, currentY));
            });

            g.getChildren().add(iv);
        }

        //Jail cell
        ImageView ix = new ImageView(icon);
        ix.setLayoutX(0.02342606149*width); // 32/1366
        ix.setLayoutY(0.05208333333*height); // 40/768

        ix.setStyle("-sfx-font-size: 20; -fx-border-color: black");
        ix.setFitHeight(25);
        ix.setFitWidth(25);
        g.getChildren().add(ix);

        //Top row
        for (int i = 0; i < 9; i++) {
            ImageView iv = new ImageView(icon);
            double x = (0.11786237188*width) + (i * (0.04868228404*width)); // (161//1366) & (66.5/768)

            iv.setLayoutX(x);
            iv.setLayoutY(0.06640625*height);
            iv.setStyle("-sfx-font-size: 20; -fx-border-color: black");
            iv.setFitHeight(25);
            iv.setFitWidth(25);
            g.getChildren().add(iv);
        }

        //Community parking
        ImageView iz = new ImageView(icon);
        iz.setLayoutX(0.59077598828*width); // 807/1366
        iz.setLayoutY(0.05208333333*height); // 40/768

        iz.setStyle("-sfx-font-size: 20; -fx-border-color: black");
        iz.setFitHeight(25);
        iz.setFitWidth(25);
        g.getChildren().add(iz);

        //Right column
        for (int i = 0; i < 9; i++) {
            ImageView iv = new ImageView(icon);

            double y = (0.18229166666*height) + (i * (0.07552083333*height)); // (664/768) & (58/768)

            iv.setLayoutX(0.58638360175*width);
            iv.setLayoutY(y);
            iv.setStyle("-sfx-font-size: 20; -fx-border-color: black");
            iv.setFitHeight(25);
            iv.setFitWidth(25);
            g.getChildren().add(iv);
        }

        //Go to jail cell
        ImageView ii = new ImageView(icon);
        ii.setLayoutX(0.59077598828*width); // 807/1366
        ii.setLayoutY(0.91796875*height); // 705/768

        ii.setStyle("-sfx-font-size: 20; -fx-border-color: black");
        ii.setFitHeight(25);
        ii.setFitWidth(25);
        g.getChildren().add(ii);

        //Bottom row
        for (int i = 0; i < 9; i++) {
            ImageView iv = new ImageView(icon);
            //int x = 690 - (i * 66);
            double x = (0.50512445095*width) - (i * (0.04868228404*width)); // (161//1366) & (66.5/768)

            iv.setLayoutX(x);
            iv.setLayoutY(0.90494791666*height);
            iv.setStyle("-sfx-font-size: 20; -fx-border-color: black");
            iv.setFitHeight(25);
            iv.setFitWidth(25);
            g.getChildren().add(iv);
        }

        //Cell for jail
        ImageView iy = new ImageView(icon);
        iy.setLayoutX(0.06076134699*width);
        iy.setLayoutY(0.10416666666*height);
        iy.setStyle("-sfx-font-size: 20; -fx-border-color: black");
        iy.setFitHeight(25);
        iy.setFitWidth(25);
        g.getChildren().add(iy);
        return g;
    }

    //updating location for role
    public void updateCell() throws MalformedURLException, IOException {
        InputStream u = new URL("https://i.imgur.com/Nwj4hfi.png").openStream();
        Image icon = new Image(u);

        ImageView k = (ImageView) grid.getChildren().get(cell);
        k.setImage(null);
        grid.getChildren().set(cell, k);

        cell += (int) (Math.random() * ((12 - 2) + 1)) + 2;
        System.out.println(cell);
        if (cell > 39) {
            cell = cell - 40;
        }
        System.out.println("cell:" + cell);
        ImageView j = (ImageView) grid.getChildren().get(cell);
        j.setImage(icon);
        grid.getChildren().set(cell, j);

    }

    //Sends player icon to jail
    public void goToJail() {
        ImageView k = (ImageView) grid.getChildren().get(cell);
        k.setImage(null);
        grid.getChildren().set(cell, k);
        cell = 40;
        System.out.println(cell);
        ImageView j = (ImageView) grid.getChildren().get(cell);
        j.setImage(icon);
        grid.getChildren().set(cell, j);
    }

    //Sends player icon to Go
    public void toGo() {
        ImageView k = (ImageView) grid.getChildren().get(cell);
        k.setImage(null);
        grid.getChildren().set(cell, k);
        cell = 0;
        System.out.println(cell);
        ImageView j = (ImageView) grid.getChildren().get(cell);
        j.setImage(icon);
        grid.getChildren().set(cell, j);
    }

    //Sets up the user interface
    public Pane setUpUI() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();
        // need to set these so they scale
        double w = width / 1.51777;
        double h = height / 2.56;
        System.out.println(w);
        System.out.println(h);

        w=896;
        h=302;

        int hgap=93;
        int wgap=234;

        Button roll = new Button("");
        roll.setLayoutX(896);
        roll.setLayoutY(302);
        roll.setPrefSize(215, 70);
        //roll.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        roll.setOnAction((ActionEvent e) -> {
            try {
                updateCell();
            } catch (IOException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.getChildren().add(roll);

        Button auc = new Button("");
        auc.setLayoutX(w + wgap);
        auc.setLayoutY(h);
        auc.setPrefSize(215, 70);
        //auc.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        auc.setOnAction((ActionEvent e) -> setMoney(100));
        grid.getChildren().add(auc);

        Button mana = new Button("");
        mana.setLayoutX(w);
        mana.setLayoutY(h + hgap);
        mana.setPrefSize(215, 70);
        //mana.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        mana.setOnAction((ActionEvent e) -> {
            try {
                updateCell();
            } catch (IOException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.getChildren().add(mana);

        Button purchase = new Button("");
        purchase.setLayoutX(w + wgap);
        purchase.setLayoutY(h + hgap);
        purchase.setPrefSize(215, 70);
        //purchase.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        purchase.setOnAction((ActionEvent e) -> {
            try {
                updateCell();
            } catch (IOException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.getChildren().add(purchase);

        Button end = new Button("");
        end.setLayoutX(w);
        end.setLayoutY(h + (hgap*2));
        end.setPrefSize(215, 70);
        //end.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        end.setOnAction((ActionEvent e) -> {
            try {
                updateCell();
            } catch (IOException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.getChildren().add(end);

        Button menu = new Button("");
        menu.setLayoutX(w + wgap);
        menu.setLayoutY(h + (hgap*2));
        menu.setPrefSize(215, 70);
        //menu.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;");
        menu.setOnAction((ActionEvent e) -> {
            try {
                updateCell();
            } catch (IOException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.getChildren().add(menu);

        return grid;
    }

    //Sets ups the labels
    public Pane setLabels() {
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
    public void start(Stage stage) throws IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        System.out.println(screenBounds);
        width = screenBounds.getWidth();
        height = screenBounds.getHeight();

        InputStream u = new URL("https://i.imgur.com/Nwj4hfi.png").openStream();
        Image image = new Image(u);
        setIcon(image);

        grid = setCells();
        grid = setUpUI();
        grid = setLabels();


        InputStream f = new URL("https://imgur.com/ZZ60PBC.png").openStream();


        //adjust to 768 when there is the correct aspect ratio
        Image i = new Image("https://imgur.com/ZZ60PBC.png", 1366, 766, false, true, true);

        //Image i = new Image(f, 1366, 720, false, true);


        BackgroundImage myBI = new BackgroundImage(i, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        grid.setBackground(new Background(myBI));

        //screenBounds.getWidth(), screenBounds.getHeight()
        Scene scene = new Scene(grid, 1366, 768);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setTitle("Property Tycoon");
        stage.setIconified(false);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

