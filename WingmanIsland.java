
import java.awt.Color;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jrubin
 */
public class WingmanIsland extends BackgroundSprite {

GameExe dest; 
int width,height;
    
WingmanIsland(Image img,int x, int y, int speed, Random gen, GameExe h){
    super(img, x, y, speed, gen);
    dest=h; 
    width=img.getWidth(h);
    height= img.getHeight(h);
}
    /**
     * Update the island based on its speed, if island goes out of frame reset it
     */
    public void update() {
        y += speed;
        if (y >=dest.geth()) {
            y = -100;
            x = Math.abs(gen.nextInt() % (dest.getw() - 30));
        }
    }
    /**
     * Draw island 
     * @param obs - Image observer to draw plane.
     */
    public void draw(ImageObserver obs) {
        WingmanExe.g2.drawImage(img, x, y, obs);
    }
    
    public void drawMap(){
        GameExe.gMap.setColor(Color.blue);
        GameExe.gMap.fillRect((int)x, (int)y,width,height);
    }
}
