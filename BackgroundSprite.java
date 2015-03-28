
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

public class BackgroundSprite extends Observable {

    Image img;
    int x, y, speed;
    Random gen;
    /**
     * Class to hold all background sprites 
     * @param img - image of the sprite to be drawn
     * @param x - x position of sprite 
     * @param y - y position of sprite 
     * @param speed - speed of sprite 
     * @param gen  - destination to draw sprite 
     */
    BackgroundSprite(Image img, int x, int y, int speed, Random gen) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.gen = gen;
    }
    
    public void update(){
        
    }
    /**
     * Draws sprite 
     * @param obs - draw destination of sprite 
     */
    public void draw(ImageObserver obs) {
        WingmanExe.g2.drawImage(img, x, y, obs);
    }
}
