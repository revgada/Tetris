import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;

public class Board extends JPanel implements KeyListener {

    private int [] [] gameField;
    private final int gameFieldWidth;
    private final int gameFieldHeight;
    private Image offscreen = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private static final int SQUARE_SIZE = 30;

    /* PieceQueue hold numbers from 1-7 which correspond to pieces in tetrisPieces
    * so it always cycles the same figures.
    */
    private LinkedList<Integer> pieceQueue = new LinkedList<Integer> ();


    private int [] [] currentPiece = new int [1][1];
    private int [] [] nextPiece;
    private int [] [] heldPiece = new int [1][1];

    private boolean isHeld = false;
    boolean speedUpGame = false;

    private int currentScore = 0;

    //Colors of individual pieces
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

    /***
     *
     * @param width Number of squares horizontally
     * @param height Number of squares vertically
     */
    public Board(int width, int height)
    {
        this.gameFieldWidth = width;
        this.gameFieldHeight = height;
        this.gameField = new int [width] [height];

        this.createPiece();
    }


    /***
     * Method: pieceDrop
     *
     * @return Whether to drop the piece or not
     */
    private boolean pieceDrop()
    {
        tetrisPieceY += 1;
        if (hasCollided(currentPiece, tetrisPieceX, tetrisPieceY))
        {
            tetrisPieceY -= 1;
            return true;
        }
        return false;
    }

    /***
     * Method rotateCurrentBlock
     *
     * Description: Rotates the current block 90 degrees
     */
    private void rotateCurrentBlock()
    {
        int [] [] copy = currentPiece.clone();
        currentPiece = rotate(currentPiece);
        if (hasCollided(currentPiece, tetrisPieceX, tetrisPieceY))
        {
            currentPiece = copy;
        }
    }


    /***
     * Method: translateCurrentBlock
     *
     * @param direction Direction in which to translate the current block
     *                  + translates right
     *                  - translates left
     */
    private void translateCurrentBlock(int direction)
    {
        tetrisPieceX += direction;
        if (hasCollided(currentPiece, tetrisPieceX, tetrisPieceY))
        {
            tetrisPieceX -= direction;
        }
    }

    /***
     * Method: update
     *
     * Description: Updates the game by calling a method to create a new piece
     */
    public void update ()
    {
        if (this.pieceDrop())
        {
            this.createPiece();
        }
    }

    /***
     * Method: holdBlock
     *
     * Description: Hold the current piece and replaces the currentPiece with a new piece
     */
    public void holdBlock ()
    {
        if (isHeld)
        {
            return;
        }
        if (this.heldPiece.length == 1 && this.heldPiece[0].length == 1) //if it has its initial value
        {
            this.heldPiece = this.currentPiece;
            this.currentPiece = new int[1][1];
            this.createPiece();
        }
        else
        {
            int [] [] temp = this.currentPiece;
            this.currentPiece = this.heldPiece;
            this.heldPiece = temp;
        }
        this.tetrisPieceX = this.gameFieldWidth / 2;
        this.tetrisPieceY = 0;
        this.isHeld = true;
    }


    /***
     * Method: createPiece
     *
     * Description: Checks for loss, then checks whether the line has been filled,
     * and creates a new piece if needed.
     */
    private void createPiece()
    {
        // Checks whether the game is over
        if (hasCollided(currentPiece, tetrisPieceX, tetrisPieceY))
        {
            System.out.println("Game over. Your current score is " + currentScore);
        }

        for (int i = 0; i < currentPiece.length; i++)
        {
            for (int j = 0; j < currentPiece[i].length; j++)
            {
                if (currentPiece[i] [j] != 0)
                {
                    gameField[i + tetrisPieceX] [j + tetrisPieceY] = currentPiece[i] [j];
                }
            }
        }

        // Checks whether the bottom line has been filled
        boolean filled;
        for (int j = this.gameFieldHeight - 1; j > 0; j--)
        {
            filled = true;
            for (int i = 0; i < this.gameFieldWidth; i++)
            {
                if (gameField[i][j] == 0)
                {
                    filled = false;
                    break;
                }
            }

            if (filled)
            {
                currentScore++;

                if (currentScore % 4 == 0)
                {
                    speedUpGame = true;
                }

                for (int i = 0; i < this.gameFieldWidth; i++)
                {
                    for (int k = j; k > 0; k--)
                    {
                        gameField[i][k] = gameField[i][k - 1];
                    }
                }
                j++;
            }
        }


        /**Adds a new piece to pieceQueue if is has less then 2 members,
         * and sets the currentPiece to a new piece.
         */

        if (pieceQueue.size() <= 1)
        {
            for (int i = 0; i < 7; i++)
            {
                pieceQueue.add (i);
            }
            currentPiece = tetrisPieces[pieceQueue.remove()].clone();
            Collections.shuffle(pieceQueue);
        }
        else
        {
            currentPiece = tetrisPieces[pieceQueue.remove()].clone();
        }
        tetrisPieceX = gameFieldWidth / 2 - 2;
        tetrisPieceY = 0;

        nextPiece = tetrisPieces[pieceQueue.element()];

        this.isHeld = false;
    }


    /***
     * Method: hasCollided
     *
     * @param piece Piece to check collision for
     * @param pieceX X coordinate of the piece
     * @param pieceY Y coordinate of the piece
     * @return Whether the piece has collided with another piece
     */
    private boolean hasCollided(int [] [] piece, int pieceX, int pieceY)
    {
        for (int i = 0; i < piece.length; i++)
        {
            for (int j = 0; j < piece[0].length; j++)
            {
                if (piece[i][j] != 0)
                {
                    if (0 > i + pieceX || i + pieceX >= this.gameFieldWidth || 0 > j + pieceY || j + pieceY >= this.gameFieldHeight)
                    {
                        return true;
                    }
                    if (gameField[pieceX + i] [pieceY + j] != 0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /***
     * Method: paint
     *
     * @param graphics
     * Description: Invoked by Swing to draw components
     */
    @Override
    public void paint (Graphics graphics)
    {
        if (this.getSize().width != this.offscreen.getWidth(null) || this.getSize().getHeight() != this.offscreen.getHeight(null))
        {
            this.offscreen = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        }


        this.paintBuffer(offscreen.getGraphics());
        graphics.drawImage(offscreen,0,0,null);
    }

    /***
     * Method: paintBuffer
     *
     * @param graphics
     * Description: Draws the grid, pieces on the board, current piece, next piece, and held piece
     */
    public void paintBuffer (Graphics graphics)
    {

        // Draw the grid. First draws horizontal lines, the the vertical ones
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, this.gameFieldWidth * SQUARE_SIZE, this.gameFieldHeight * SQUARE_SIZE);

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < this.gameFieldWidth; i++)
        {
            for (int j = 0; j < this.gameFieldHeight; j++)
            {
                graphics.drawLine (i * SQUARE_SIZE, 0, i * SQUARE_SIZE, this.gameFieldHeight * SQUARE_SIZE);
                graphics.drawLine (0, j * SQUARE_SIZE, this.gameFieldWidth * SQUARE_SIZE, j * SQUARE_SIZE);
            }
        }

        // Draws the pieces on the board
        drawPiece(graphics, gameField, 0, 0);


        // Draws the current block
        for (int i = 0; i < currentPiece.length; i++)
        {
            for (int j = 0; j < currentPiece[0].length; j++)
            {
                if (currentPiece[i] [j] != 0)
                {
                    graphics.setColor (colorsOfPieces[currentPiece[i] [j]]);
                    graphics.fillRect ((i + tetrisPieceX) * SQUARE_SIZE, (j + tetrisPieceY) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    graphics.setColor (Color.BLACK);
                    graphics.drawRect((i + tetrisPieceX) * SQUARE_SIZE, (j + tetrisPieceY) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }


        // Where to draw the next block
        int offsetX = (this.gameFieldWidth + 1) * SQUARE_SIZE;
        int offsetY = SQUARE_SIZE;

        // Draws the next block
        graphics.setColor(Color.WHITE);
        graphics.drawString("Next block", offsetX, offsetY - 10);
        drawPiece(graphics, nextPiece, offsetX, offsetY);

        // Draws the held block
        offsetY += SQUARE_SIZE * 5;
        graphics.setColor(Color.WHITE);
        graphics.drawString("Held block", offsetX, offsetY - 10);
        drawPiece(graphics, heldPiece, offsetX, offsetY);

        // Draws the current score
        graphics.setColor(Color.WHITE);
        graphics.drawString("Lines: " + currentScore, offsetX, offsetY + SQUARE_SIZE * 5);
    }


    /***
     * Method drawPiece
     *
     * @param graphics
     * @param block The block to draw
     * @param offsetX Offset from the start of the grid horizontally
     * @param offsetY Offset from the start of the grid vertically
     * Description: Draws a block
     */
    private void drawPiece(Graphics graphics, int [] [] block, int offsetX, int offsetY)
    {
        for (int i = 0; i < block.length; i++)
        {
            for (int j = 0; j < block[i].length; j++)
            {
                if (block [i] [j] != 0)
                {
                    graphics.setColor(colorsOfPieces[block [i] [j]]);
                    graphics.fillRect(i * SQUARE_SIZE + offsetX, j * SQUARE_SIZE + offsetY, SQUARE_SIZE, SQUARE_SIZE);
                    graphics.setColor(Color.BLACK);
                    graphics.drawRect(i * SQUARE_SIZE + offsetX, j * SQUARE_SIZE + offsetY, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
    }


    /***
     * Method: rotate
     *
     * @param piece The piece to rotate
     * @return The rotated piece
     * Description: Rotates the block 90 degrees
     */
    private static int [] [] rotate (int [] [] piece)
    {
        //Use the rotation matrix [[0, 1], [-1, 0]]
        int [] [] result = new int [piece[0].length] [piece.length];
        for (int i = 0; i < piece[0].length; i++)
        {
            for (int j = 0; j < piece.length; j++)
            {
                result [i] [j] = piece[j] [piece[0].length - i - 1];
            }
        }
        return result;
    }


    @Override
    public void keyTyped(KeyEvent e)
    {
        // Do nothing
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
        // Do nothing
    }
}
