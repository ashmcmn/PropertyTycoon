package utilities;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.board.Square;

import java.util.stream.Collectors;

public class Utilities {

    /**
     * Given a x(column) and y(row) find the index that corresponds
     *
     * @param x the x
     * @param y the y
     * @return the index
     */
    public static int coordToIndex(int x, int y) {
        int idx = -1;

        if (x == 0) {
            idx = 10 - y;
        } else if (y == 0) {
            idx = 10 + x;
        } else if (x == 10) {
            idx = 20 + y;
        } else if (y == 10) {
            idx = 30 + (10 - x);
        }

        return idx;
    }

    /**
     * Given the index of a square find its corresponding frontend grid coordinates.
     *
     * @param idx the index of square
     * @return the coord
     */
    public static int[] indexToCoord(int idx) {
        int x = -1;
        int y = -1;

        if(idx <= 10){
            x = 0;
            y = 10 - idx;
        }
        else if(idx <= 20){
            x = idx - 10;
            y = 0;
        }
        else if (idx <= 30){
            x = 10;
            y = idx - 20;
        }
        else if (idx <= 40){
            x = 10 - (idx - 30);
            y = 10;
        }

        return new int[]{x,y};
    }

    /**
     * Given a grid and indices for row and column fetch the corresponding cell.
     *
     * @param grid the grid
     * @param x    the column index
     * @param y    the row index
     * @return the node
     */
    public static Node getGridCell (GridPane grid, int x, int y){
        Node node = grid.getChildren().stream()
                .filter(c -> GridPane.getColumnIndex(c) == x && GridPane.getRowIndex(c) == y)
                .collect(Collectors.toList())
                .get(0);
        return node;
    }

}
