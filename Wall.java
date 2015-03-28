
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Random;
import javax.imageio.ImageIO;

public class Wall extends CharSprite {

    boolean invis;
    HealthBar hp;
    int respawn;
    CurrentSprites sprites;

    Wall(Image image, int nx, int ny, CurrentSprites curr) {
        super(image, 0);
        x = nx;
        y = ny;
        width = image.getWidth(null);
        height = image.getHeight(null);
        invis = false;
        planeID = "3";
        hp = new HealthBar(3, 4, 0, 0);
        respawn = 0;
        sprites=curr;
    }

    public void reset() {
        width = 0;
        height = 0;
        invis = true;
        respawn = 1000;
        hp = new HealthBar(3, 4, 0, 0);
        try {
            img = ImageIO.read(getClass().getResource("TResources/wall2.png"));
        } catch (Exception e) {
            System.out.println("image not found");
        }

    }

    void update() {
        if (explode) {
            explode();
        } else if (respawn > 0) {
            respawn--;
        } else {
            boolean safe=true;
            UserTank[] t=sprites.getTanks();
            int len = sprites.getTanksLen();
            for(int i=0;i<len;i++){
                if(t[i].safeColl(x, y, 32, 32)){
                    safe=false;
                }
            }
            if (respawn == 0 && invis &&safe) {
                invis = false;
                width = 32;
                height = 32;
            }
        }

    }

    public void draw(ImageObserver obs) {
        if (!invis) {
            GameExe.g2.drawImage(img, x, y, obs);
        }
    }
    public void drawMap(){
        GameExe.gMap.setColor(Color.BLUE);
        GameExe.gMap.fillRect(x, y,width,height);
    }

    void decHP() {
        if (!hp.reduceHealth()) {
            this.explode();
        }

    }
}
