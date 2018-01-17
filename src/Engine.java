import java.util.TimerTask;

public class Engine extends TimerTask {

    Board board;
    private int tickCounter = 0; //Counts every tick
    private int tickLimit = 50; //How big tickCounter can get before we drop the piece.

    public Engine()
    {
        board = new Board(12, 24);
    }

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
