import javax.swing.*;
import java.util.Timer;

public class GamePanel extends JPanel {

    Engine engine;

    public GamePanel()
    {
        engine = new Engine();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(engine, 0, 10);
    }
}
