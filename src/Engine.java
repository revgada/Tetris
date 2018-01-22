import java.util.TimerTask;

public class Engine extends TimerTask {

    Board board;
    private int tickCounter = 0; // Counts every tick
    private int tickLimit = 50; // Size of how big tickLimit can get before dropping the piece.

    public Engine()
    {
        board = new Board(12, 24);
    }

    /***
     * Method: nextGame
     *
     * Description: Invokes the update and repaint methods on the game board
     */
    public void nextGameTick()
    {
        board.update();
        board.repaint();
    }


    @Override
    public void run ()
    {
        tickCounter++;

        if (tickCounter % tickLimit == 0)
        {
            nextGameTick();
        }

        if (board.speedUpGame)
        {
            tickLimit = tickLimit * 9 / 10;
            board.speedUpGame = false;
        }
    }
}
