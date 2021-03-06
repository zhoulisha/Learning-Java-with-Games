/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package worm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private int currScore;
    private Worm worm;
    private Treat treat;
    private boolean overlap;
    private int numOfEat;

    public GameCanvas() {
        super();
        setSleepTime(300);
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    @Override
    public void initComponent() {
        getSpriteAry().clear();
        new AllClips();
        overlap = true;
        numOfEat = 0;
        initWorm();
        initTreat();
    }

    public void initWorm() {
        worm = new Worm();
        worm.initSprite();
        worm.setGameCanvas(this);
        getSpriteAry().add(worm);
    }

    public void initTreat() {
        treat = new Treat();
        while (overlap) { // init treat until no overlap with worm
            treat.initSprite();
            overlap = detectOverlap();
        }
        getSpriteAry().add(treat);
        worm.setTreat(treat);
    }

    public boolean detectOverlap() {
        boolean overL = false;
        Rectangle treatRect = new Rectangle(treat.getX(), treat.getY(),
                Consts.TREAT_WIDTH, Consts.TREAT_HEIGHT);
        ArrayList wBody = worm.getWormBody();
        Rectangle wUnitRect;
        Point wUnitP;
        for (Object wBody1 : wBody) {
            wUnitP = (Point) wBody1;
            wUnitRect = new Rectangle(wUnitP.x, wUnitP.y,
                    2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
            if (treatRect.intersects(wUnitRect)) {
                overL = true;
            }
        }
        return overL;
    }
    
    @Override
    public void paintCurrScore(Graphics2D g2d) {
        currScore = numOfEat * Consts.SCORE_UNIT; // adding a canculating expression
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g2d.setColor(Color.red);
        g2d.drawString("Score: " + currScore, 20, 20);
    }

    @Override
    public void announceTermination(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.drawRect(Consts.MAXX / 2 - 110, Consts.MAXY / 2 - 50, 200, 100);
        g2d.drawString("Game Terminates", 160, Consts.MAXY / 2);
        g2d.setColor(Color.BLUE);
        g2d.drawString("Start A New Game If Clicking The Mouse",
                60, Consts.MAXY / 2 + 30);
        // in addition, stop game and prepare renew game
        stopGame();
        addMouseListener(new NewGameListener());
    }

    // A listener for re-starting the game.
    // Assume it is based on a mouse click
    class NewGameListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (true) { // some condition
                renewGame();
            }
        }
    }

    // an inner class for implementing the key event adapter
    // for catching the key control
    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent evt) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    worm.setDirection(Consts.SOUTH);
                    break;
                case KeyEvent.VK_UP:
                    worm.setDirection(Consts.NORTH);
                    break;
                case KeyEvent.VK_RIGHT:
                    worm.setDirection(Consts.EAST);
                    break;
                case KeyEvent.VK_LEFT:
                    worm.setDirection(Consts.WEST);
                    break;
                default:
            }
        }
    }

    public int getNumOfEat() {
        return numOfEat;
    }

    public void setNumOfEat(int numOfEat) {
        this.numOfEat = numOfEat;
    }
}
