
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.awt.event.*;
import java.util.*;
/**
 * 
 * @author Jrubin
 */
public class GameEvents extends Observable implements KeyListener{
        int type;
        Object event;
        boolean GameOver;
    
    /**
     * Changes value to 2 if it observes a string message and notifies UserPlane
     * of this change in value
     * @param msg - message from observable on what the change is
     */    
    public void setValue(String msg) {
        System.out.println("HELLO");
        type = 2;
        event = msg;
        setChanged();
        // trigger notification
        notifyObservers(this);
    }
    
    static Set<Integer> pressed = new HashSet<Integer>();
    /**
     * Overwrites KeyListener. When a key is pressed, add it to the hash set
     * @param e -key event
     * 
     */
    public synchronized void keyPressed(KeyEvent e) {
        type=1;
        event = e;
        setChanged();
        pressed.add(e.getKeyCode());
        notifyObservers(this);
    }
    /**
     * Overwrites KeyListener. When a key is released, remove it from hash set.
     * @param e -key event 
     */
    public synchronized void keyReleased(KeyEvent e) {
        type=1;
        event = e;
        setChanged();
        pressed.remove(e.getKeyCode());
        notifyObservers(this);
    }
    /**
     * OverWrites KeyListener. Unused in this class
     * @param event 
     */
    public void keyTyped(KeyEvent event){
    }
   /**
    * Checks if Hash Set contains specified key. If it does, that key is being pressed. 
    * @param c - KeyCode to check against those currently pressed 
    * @return true if HashSet contains c, false if it doesn't
    */
    public static boolean keyMatch(Integer c){
        if(pressed.contains(c)){
            return true;
        }else 
            return false;
    }
    
    /**
     * Sets gameOver to true 
     */
    public void GameOver() {
         GameOver=true;
    }
    /**
     * Returns weather or not game is over
     * @return yes if game over, no if not
     */
    public boolean isGameOver() {
        return GameOver;
    }
}
