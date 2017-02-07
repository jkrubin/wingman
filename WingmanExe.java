
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

public class WingmanExe extends GameExe {

    Image sea;
    Image myPlane;
    int speed = 1, move = 0;
    Random generator = new Random(1234567);
    UserPlane m, n;
    HealthBar hp;
    HealthBar hp2;
    int gameOverTimer = 5;
    static int users;

    WingmanExe(int x, int y) {
        w = x;
        h = y;
    }

    /**
     * Initializes all of the variables for Wingman
     */
    public void init() {
        users = 2;
        setBackground(Color.white);
        Image island1, island2, island3, enemyImg;
        isGameOver = false;
        allSprites = new CurrentSprites(this);
        try {
            sea = ImageIO.read(getClass().getResource("Resources/water.png"));
            island1 = ImageIO.read(getClass().getResource("Resources/island1.png"));
            island2 = ImageIO.read(getClass().getResource("Resources/island2.png"));
            island3 = ImageIO.read(getClass().getResource("Resources/island3.png"));
            myPlane = ImageIO.read(getClass().getResource("Resources/myplane_1.png"));
            enemyImg = ImageIO.read(getClass().getResource("Resources/enemy1_1.png"));


            // MAKE ISLANDS
            allSprites.addIsland(new WingmanIsland(island1, 100, 100, speed, generator,this));
            allSprites.addIsland(new WingmanIsland(island2, 200, 400, speed, generator,this));
            allSprites.addIsland(new WingmanIsland(island3, 300, 200, speed, generator,this));

            //Make HP Bars 
            hp = new HealthBar(1,4,50,GameExe.geth()-50);
            hp2 = new HealthBar(2,4,GameExe.getw()-150, GameExe.geth()-50);
            
            //MAKE USER PLANE
            m = new UserPlane("1", myPlane, 300, 360, 4, hp, allSprites,
                    KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
            allSprites.addUserPlane(m);

            if (users == 2) {
                n = new UserPlane("2", myPlane, 300, 360, 4, hp2, allSprites,
                        KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q);
                allSprites.addUserPlane(n);
            }

            //MAKE ENEMIES
            allSprites.addEnemy(new Enemy("3", enemyImg, 1, generator, allSprites, gameEvents, 1));
            allSprites.addEnemy(new Enemy("3", enemyImg, 1, generator, allSprites, gameEvents, 1));
            allSprites.addEnemy(new Enemy("3", enemyImg, 1, generator, allSprites, gameEvents, 4));


            gameEvents.addObserver(m);

            if (users == 2) {
                gameEvents.addObserver(n);
            }
            //addKeyListener(gameEvents);
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }
    }

    public void drawBackGroundWithTileImage() {
        int TileWidth = sea.getWidth(this);
        int TileHeight = sea.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);

        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(sea, j * TileWidth,
                        i * TileHeight + (move % TileHeight), TileWidth,
                        TileHeight, this);
            }
        }
        move += speed;
    }

    public void drawDemo() {
        drawBackGroundWithTileImage();
        allSprites.update();
        allSprites.draw();
    }
}
