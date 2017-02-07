
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;


public class WingmanTest {
        
    public static void main(String argv[]) {
        GameEvents events = new GameEvents();
        final WingmanExe demo = new WingmanExe(640,480);
        demo.init();
        JFrame f = new JFrame("Scrolling Shooter");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(640, 480));
        f.setVisible(true);
        f.setResizable(false);
        f.setFocusable(true);
        f.addKeyListener(events);
        demo.start(events);
    }
}
