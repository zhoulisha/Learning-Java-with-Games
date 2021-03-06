/*
 * Pong.java - The main class of the game Pong. 
 */

package pong;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class Pong extends JFrame {
    private ScreenDeck aControl;
    
    public Pong() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game Pong");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        setLayout(new BorderLayout());
        
        aControl = new ScreenDeck();
        add(aControl, BorderLayout.CENTER);
        
        setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Pong();
    }
}
