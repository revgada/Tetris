import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Board extends JPanel implements KeyListener {

    private int [] [] gameField;
    private final int gameFieldWidth;
    private final int gameFieldHeight;
    private Image offscreen = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private static final int SQUARE_SIZE = 30;

    private LinkedList<Integer> pieceQueue = new LinkedList<Integer> ();
    private int [] [] currentPiece = new int [1][1];
    private int [] [] nextPiece;
    private int [] [] heldPiece = new int [1][1];

    private boolean isHeld = false; //whether the player has already used hold on this block.
    boolean speedUpGame = false; // Speeds up the game after currentScore % 4

    private int currentScore = 0;

    private static final Color[] colorsOfPieces = new Color [] { new Color (0Xff0000), new Color (0x8c1aff), new Color (0x00ff00), new Color (0x1a53ff), new Color (0xffff00), new Color (0xff4000), new Color (0x33ffff), new Color(0xff0066)};

    private int tetrisPieceX = 0;
    private int tetrisPieceY = 0;
    private final int [] [] [] tetrisPieces = new int [][][] {
            {{1, 0},
             {1, 0},
             {1, 1}},

            {{2, 2},
             {2, 0},
             {2, 0}},

            {{0, 3, 0},
             {0, 3, 0},
             {0, 3, 0},
             {0, 3, 0}},

            {{0, 4},
             {4, 4},
             {4, 0}},

            {{5, 0},
             {5, 5},
             {0, 5}},

            {{6, 6},
             {6, 6}},

            {{7, 0},
             {7, 7},
             {7, 0}} };

    public Board(int width, int height)
    {
        this.gameFieldWidth = width;
        this.gameFieldHeight = height;
        this.gameField = new int [width] [height];

        this.createPiece();
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        //pass.
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_UP)
            rotateCurrentBlock();
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            pieceDrop();
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            translateCurrentBlock(-1);
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            translateCurrentBlock(1);
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
            while (!pieceDrop());
        else if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            holdBlock();
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        //pass
    }
}
