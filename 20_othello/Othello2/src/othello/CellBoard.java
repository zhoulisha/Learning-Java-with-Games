/*
 * CellBoard.java -- A class that defines the cellBoard of 8x8 cells.
 */
package othello;

import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class CellBoard extends AbsSprite {

    private CellSprite[][] cellBoard;
    private PieceSprite[][] pieceBoard;
    private int moveX = -1, moveY = -1;
    private int curRow = -1, curCol = -1;
    private int newRow, newCol;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public CellBoard() {
        super();
        initSprite();
    }

    @Override
    public void initSprite() {
        int startX = Consts.GAPW;
        int startY = Consts.GAPW;
        cellBoard = new CellSprite[Consts.MAXCELL][Consts.MAXCELL];
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                CellSprite aCell = new CellSprite();
                aCell.setX(startX);
                aCell.setY(startY);
                cellBoard[row][col] = aCell;
                startX += (Consts.CELLW + Consts.GAPW);
            }
            startX = Consts.GAPW;
            startY += (Consts.CELLH + Consts.GAPW);
        }
        setActive(true);
        setVisible(true);
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(Consts.BGCOLOR);
        g2d.fillRect(0, 0, Consts.BOARDW, Consts.BOARDH);
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                cellBoard[row][col].paintSprite(g2d);
            }
        }
    }

    @Override
    public void updateSprite() {
        if (isValidMove()) {
            enterNewCell();
            exitOldCell();
        }
    }

    public void setMoveXY(int x, int y) {
        this.moveX = x;
        this.moveY = y;
    }

    private boolean isValidMove() {
        boolean validMove = false;
        if ((moveX >= 0) && (moveX <= Consts.BOARDW)
                && (moveY >= 0) && (moveY <= Consts.BOARDH)) {
            newRow = getRow(moveY);
            newCol = getCol(moveX);
            if ((newRow != curRow) || (newCol != curCol)) {
                validMove = true;
            }
        }
        return validMove;
    }

    private void enterNewCell() {
        // if the pieceBoard does not have a piece
        if (pieceBoard[newRow][newCol] == null) {
            cellBoard[newRow][newCol].setColor(Consts.MOUSEENTERC);
        }
    }

    private void exitOldCell() {
        // exit from the previous cell
        // prevent curRow == -1 and curCol == -1
        if ((curRow >= 0) && (curRow < Consts.MAXCELL)
                && (curCol >= 0) && (curCol < Consts.MAXCELL)) {
            cellBoard[curRow][curCol].setColor(Consts.CELLCOLOR);
        }
        curRow = newRow;
        curCol = newCol;
    }

    public void setPieceBoard(PieceSprite[][] pieceBoard) {
        this.pieceBoard = pieceBoard;
    }

    public int getRow(int mouseY) {
        int numRow = 0;
        if (mouseY < Consts.VALIDMAXSIZE) { // won't make index of 8
            numRow = mouseY / (Consts.CELLH + Consts.GAPW);
        }
        return numRow;
    }

    public int getCol(int mouseX) {
        int numCol = 0;
        if (mouseX < Consts.VALIDMAXSIZE) { // verify x value is valid
            numCol = mouseX / (Consts.CELLW + Consts.GAPW);
        }
        return numCol;
    }
}