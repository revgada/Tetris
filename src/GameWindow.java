import javax.swing.*;

public class GameWindow extends JFrame
{
    private GamePanel gamePanel = new GamePanel();

    public GameWindow()
    {
        this.setVisible (true);
        this.setSize (700, 900);
        this.add (gamePanel.engine.board);
        this.addKeyListener(gamePanel.engine.board);
    }

}