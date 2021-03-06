/*
 * AbsGameCanvas.java -- A abstract class that specifies a game canvas.
 */
package breakout;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public abstract class AbsGameCanvas extends JPanel implements Runnable {

    private final ArrayList<AbsSpriteImage> spriteAry;
    private Thread animThread;
    private boolean playing;
    private boolean gameOver;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AbsGameCanvas() {
        gameOver = false;
        spriteAry = new ArrayList<>();
        initComponent();
        startGame();
    }

    public abstract void initComponent();

    public abstract void paintCurrScore(Graphics2D g2d);

    public abstract void announceTermination(Graphics2D g2d);

    private void initAnimation() {
        if (animThread == null) {
            animThread = new Thread(this);
            animThread.start();
        }
    }

    @Override
    public void run() {
        try {
            while (isPlaying()) {
                updateComponent();
                repaint();
                Thread.sleep(8);
            }
        } catch (InterruptedException ex) {
        }
    }

    public void updateComponent() {
        for (AbsSprite element : spriteAry) {
            if (element.isActive()) {
                (element).updateSprite();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (AbsSprite element : spriteAry) {
            if (element.isVisible()) {
                (element).paintSprite(g2d);
            }
        }
        paintCurrScore(g2d);
        if (gameOver) {
            announceTermination(g2d);
        }
    }

    public void startGame() {
        setPlaying(true);
        initAnimation();
    }

    public void pauseGame() {
        setPlaying(false);
    }

    public void resumeGame() {
        setPlaying(true);
    }

    public void stopGame() {
        setPlaying(false);
        animThread = null;
        gameOver = true;
    }

    public void renewGame() {
        stopGame();
        // remove NewGameListener and PaddleMovedListener
        removeMouseListener(getMouseListeners()[1]);
        removeMouseMotionListener(getMouseMotionListeners()[1]);
        gameOver = false;
        initComponent();
        startGame();
    }

    public ArrayList<AbsSpriteImage> getSpriteAry() {
        return spriteAry;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
