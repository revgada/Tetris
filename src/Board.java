import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JPanel implements KeyListener {
    private int [] [] gameField;
    private final int gameFieldWidth;
    private final int gameFieldHeight;

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
