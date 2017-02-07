
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class GameExe extends JApplet implements Runnable {

    private Thread thread;
    BufferedImage bimg,UI,miniMap;
    public static Graphics2D g2,gUI,gMap;
    GameEvents gameEvents;
    CurrentSprites allSprites;
    static boolean isGameOver;
    static int w,h;    

    public void init() {
    }

    public void drawBackGroundWithTileImage() {
    }

    /**
     * Calls allSprites to update and draw everything
     */
    public void drawDemo() {
    }

    /**
     * paint calls draw created the window and calls drawDemo() to refresh every
     * frame
     *
     * @param g - the graphics destination for paint to paint to
     */
    public void paint(Graphics g) {
        if (bimg == null) {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(windowSize.width,
                    windowSize.height);
            g2 = bimg.createGraphics();
        }
        drawDemo();
        g.drawImage(bimg, 0, 0, this);
    }

    /**
     * Starts the thread to run the game
     */
    public void start(GameEvents events) {
        gameEvents = events;
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void run() {
        Thread me = Thread.currentThread();
        while (thread == me && !isGameOver) {
            repaint();
            //WAIT BETWEEN FRAMES
            try {
                thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }
        }
        repaint();
    }

    /**
     * Sets isGameOver to be true, ending run()
     */
    public static void GameOver() {
        isGameOver = true;
    }
    
    public static int getw(){
        return w;
    }
    
    public static int geth(){
        return h;
    }
    
    
}
