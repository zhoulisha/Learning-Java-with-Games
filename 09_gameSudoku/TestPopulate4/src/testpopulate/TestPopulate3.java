/*
 * TestPopulate3.java - The main class of the game Sudoku.
 */
package testpopulate;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class TestPopulate3 extends JFrame {

    public TestPopulate3() {
        setTitle("Game Sudoku");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameCanvas gameCv = new GameCanvas();
        add(gameCv);

        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TestPopulate3();
    }
}
