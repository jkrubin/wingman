
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;


public class TankTest {
        
    public static void main(String argv[]) {
        GameEvents events = new GameEvents();   
        final TankExe demo = new TankExe(960,704);
        demo.init();
        JFrame f = new JFrame("Tank Game");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(960,726));
        f.setVisible(true);
        f.setResizable(false);
        f.setFocusable(true);
        f.addKeyListener(events);
        demo.start(events);
    }
}
