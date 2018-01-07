import javax.swing.*;
import java.awt.event.KeyListener;

public class Board extends JPanel {
    private int [] [] gameField;
    private final int gameFieldWidth;
    private final int gameFieldHeight;

    public Board(int width, int height)
    {
//		printArray (tetrisPieces[0]);
//		printArray (rotate (tetrisPieces[0]));

        this.gameFieldWidth = width;
        this.gameFieldHeight = height;
        this.gameField = new int [width] [height];

        this.createPiece();
    }

}
