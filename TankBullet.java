
import java.awt.Color;
import java.awt.image.ImageObserver;
import javax.imageio.ImageIO;
import java.math.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jrubin
 */
public class TankBullet extends ProjectileSprite {

    int angle;
    double xs, ys, dx, dy;

    TankBullet(int nx, int ny, double nxspeed, double nyspeed, boolean user,
            CurrentSprites curr, int ShooterID, int ang) {
        super(nx, ny, 0, 0, user, curr, ShooterID);

        try {
            img = ImageIO.read(getClass().getResource("TResources/Shell.png"));
        } catch (Exception e) {
            System.out.println("No bullet resources are found");
        }
        angle = ang;
        xs = nxspeed;
        ys = nyspeed;
        dx = nx;
        dy = ny;
        width = 15;
        height = 15;

    }

    boolean update() {
        if (dy > -10 && dy < GameExe.geth() && dx > -10 && dx < GameExe.getw()) {
            dx += xs;
            dy -= ys;
        } else {
            return false;
        }
        Wall[] walls = sprites.getWalls();
        int len = sprites.getWallLen();
        for (int i = 0; i < len; i++) {
            if (walls[i].collision((int) dx, (int) dy, width, height)) {
                walls[i].decHP();
                return false;
            }
        }
        UserTank[] tanks = sprites.getTanks();
        len = sprites.getTanksLen();
        for (int i = 0; i < len; i++) {
            if (Integer.parseInt(tanks[i].planeID)!=this.ID) {
                if (tanks[i].collision((int) dx, (int) dy, width, height)) {
                    
                    tanks[i].reducehp();
                    if(tanks[i].hp.getHealth()==0){
                        tanks[ID-1].PlusScore();
                    }
                    tanks[ID - 1].PlusScore();
                    
                    return false;
                }
            }
        }


        return true;
    }

    public void draw(ImageObserver obs) {
        GameExe.g2.drawImage(img, (int) dx, (int) dy, (int) dx + 24, (int) dy + 24, angle * 24, 0, (angle + 1) * 24, 24, obs);
    }
    public void drawMap(ImageObserver obs) {
        GameExe.gMap.setColor(Color.RED);
        GameExe.gMap.fillRect((int)dx, (int)dy,24,24);
    }
    
}
