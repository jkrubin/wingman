
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.*;
import java.applet.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class TankExe extends GameExe {

    Image ground, tank, tank2, wall, block;
    UserTank t1, t2;
    HealthBar hp, hp2;
    Scanner in;

    TankExe(int x, int y) {
        w = x;
        h = y;
    }

    /**
     * Initializes all of the variables for Tank game
     */
    public void init() {
        setBackground(Color.white);
        isGameOver = false;
        gameEvents = new GameEvents();
        allSprites = new CurrentSprites(this);

        try {
            ground = ImageIO.read(getClass().getResource("TResources/Background.png"));
            tank = ImageIO.read(getClass().getResource("TResources/Tank1.png"));
            tank2= ImageIO.read(getClass().getResource("TResources/Tank2.png"));
            wall = ImageIO.read(getClass().getResource("TResources/wall1.png"));
            block = ImageIO.read(getClass().getResource("TResources/wall2.png"));

            hp = new HealthBar(1,4,50,GameExe.geth()-100);
            hp2 = new HealthBar(2,4,GameExe.getw()-200,GameExe.geth()-100);
            t1 = new UserTank("1", tank, 50, 50, 4, hp, allSprites,
                    KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
            t2 = new UserTank("2", tank2, GameExe.getw()-114, GameExe.geth()-114, 4, hp2, allSprites,
                    KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q);
        } catch (Exception e) {
            System.out.print("No resources are found");
        }
        allSprites.addTank(t1);
        allSprites.addTank(t2);
        gameEvents.addObserver(t1);
        gameEvents.addObserver(t2);
        
        addKeyListener(gameEvents);

        //Make Barriers
        for (int i = 0; i < GameExe.getw() / wall.getWidth(this); i++) {
            allSprites.addIsland(new WingmanIsland(wall, i * wall.getWidth(this), 0, 0, null, this));
            allSprites.addIsland(new WingmanIsland(wall, i * wall.getWidth(this),
                    GameExe.geth() - (wall.getHeight(this)), 0, null, this));
        }
        for (int i = 0; i < GameExe.geth() / wall.getHeight(this); i++) {
            allSprites.addIsland(new WingmanIsland(wall, 0,i * wall.getHeight(this), 0, null, this));
            allSprites.addIsland(new WingmanIsland(wall, GameExe.getw() - (wall.getWidth(this)),
                    i * wall.getHeight(this), 0, null, this));
        }
        
        try{
            in = new Scanner(getClass().getResourceAsStream("WallPos.txt"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        while(in.hasNextInt()){
            allSprites.addWall(new Wall(block,in.nextInt(),in.nextInt(),allSprites));
        }
        sound();
    }
    
    void sound(){
        try{
        AudioClip c = Applet.newAudioClip(getClass().getResource("Resources/background.mid"));
        c.loop();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void drawBackGroundWithTileImage() {
        int TileWidth = ground.getWidth(this);
        int TileHeight = ground.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);

        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(ground, j * TileWidth,
                        i * TileHeight, TileWidth,
                        TileHeight, this);
            }
        }
    }

    /**
     * Calls allSprites to update and draw everything
     */
    public void drawDemo() {
        drawBackGroundWithTileImage();
        allSprites.updateTank();
        allSprites.drawTank();
    }
    
    public void paint(Graphics g) {
        if (bimg == null) {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(windowSize.width,
                    windowSize.height);
            g2 = bimg.createGraphics();
        }
        if (UI == null) {
            Dimension windowSize = getSize();
            UI = (BufferedImage) createImage(windowSize.width,
                    windowSize.height);
            gUI = UI.createGraphics();
        }
        if (miniMap == null) {
            Dimension windowSize = getSize();
            miniMap = (BufferedImage) createImage(windowSize.width,
                    windowSize.height);
            gMap = miniMap.createGraphics();
        }
        drawDemo();
        int x1=t1.getX();
        int y1=t1.getY();
        int x2=t2.getX();
        int y2=t2.getY();
        
        g.setColor(Color.black);
        g.fillRect(0, 0, w, h);
        
        g.drawImage(bimg,0,0,480,480,x1-240,y1-240,x1+240,y1+240,this);
        g.drawImage(bimg,480,0,960,480,x2-240,y2-240,x2+240,y2+240,this);
        g.drawImage(UI, 0, 448,960,704,0, 448,960,704, this);
        g.drawImage(miniMap,330,450,630,704,0,0,w,h, this);
    }
}
