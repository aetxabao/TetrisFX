package com.aetxabao.tetrisfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static com.aetxabao.tetrisfx.Conf.*;

public class Renderer {

    public static final int G_BORDER_WIDTH = 2;//8;//4;
    public static final int G_LINE_WIDTH = 2;
    public static final int G_BOX_SIZE = 30;

    public static final int G_CANVAS_WIDTH = 2*G_BORDER_WIDTH + BOARD_WIDTH*(G_BOX_SIZE + G_LINE_WIDTH);
    public static final int G_CANVAS_HEIGHT = 2*G_BORDER_WIDTH + (6/2+BOARD_HEIGHT)*(G_BOX_SIZE + G_LINE_WIDTH);

    public static final String[] G_INIT_TXTS = {
            "########################",
            "#  TETRIS JAVAFX GAME  #",
            "########################",
            "",
            "",
            "    Piece actions",
            "    --------------",
            "    'W' rotate piece",
            "    'A' move left",
            "    'S' sink to bottom",
            "    'D' move right",
            "    'X' move down",
            "",
            "",
            "    Game options",
            "    --------------",
            "    'Esc' quit",
            "    'Enter' start",
            "",
    };

    public static final String[] G_YOUWON_TXTS = {
            "      *************",
            "      * YOU  WON! *",
            "      *************",
            "",
            "      'Esc' quit",
            "      'Enter' start",};

    public static final String[] G_YOULOST_TXTS = {
            "      ~~~~~~~~~~~~~",
            "      ~ You  lost ~",
            "      ~~~~~~~~~~~~~",
            "",
            "      'Esc' quit",
            "      'Enter' start",};

    public static void clearScreen(GraphicsContext gc) {
        double x,y,w,h, r;
        gc.setFill(Color.WHITESMOKE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(G_BORDER_WIDTH);
        x = G_BORDER_WIDTH/2;
        y = G_BORDER_WIDTH/2;
        w = BOARD_WIDTH*(G_BOX_SIZE + G_LINE_WIDTH)+ G_BORDER_WIDTH;
        h = BOARD_HEIGHT*(G_BOX_SIZE + G_LINE_WIDTH)+ G_BORDER_WIDTH;
        r = 0;
        gc.fillRect(x, y, w ,h);
        gc.strokeRoundRect(x, y, w ,h, r, r);
        y = G_CANVAS_HEIGHT - G_BORDER_WIDTH/2 - (6/2)*(G_BOX_SIZE + G_LINE_WIDTH);
        h = (6/2)*(G_BOX_SIZE + G_LINE_WIDTH);
        r = 0;
        gc.fillRect(x, y, w ,h);
        gc.strokeRoundRect(x, y, w ,h, r, r);
    }

    public static void drawInit(GraphicsContext gc) {
        clearScreen(gc);
        gc.setFill( Color.BLACK );
        gc.setFont( Font.font( "Courier New", FontWeight.NORMAL, 18 ) );
        double ax = 30;
        double ay = 50;
        double y = 25;
        int j = 1;
        for(String txt : G_INIT_TXTS){
            gc.fillText( txt, ax, ay+j*y );
            j++;
        }
    }

    public static void drawBoard(GraphicsContext gc, Board board) {
        clearScreen(gc);
        int height = board.getHeight();
        int width = board.getWidth();
        ETetromino[][] m = board.getBoardWithPiece();
        int x, y, n, ay = 0;
        Color c;
        String t;
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                if (m[i][j].toString().trim().length()>0){
                    y = G_BORDER_WIDTH + G_LINE_WIDTH /2+i*(G_BOX_SIZE + G_LINE_WIDTH);
                    x = G_BORDER_WIDTH + G_LINE_WIDTH /2+j*(G_BOX_SIZE + G_LINE_WIDTH);
                    c = m[i][j].getColor();
                    gc.setFill(c);
                    gc.fillRect(x, y, G_BOX_SIZE, G_BOX_SIZE);
                    gc.strokeRoundRect(x, y, G_BOX_SIZE, G_BOX_SIZE, 0, 0);
                }
            }
        }
        // next piece
        m = board.getNextPiece().getETetrominos();
        c = board.getNextPiece().getTetromino().getColor();
        t = board.getNextPiece().getTetromino().toString();
        if (t.equals("I")){
            ay = 0;
        }else if (t.equals("L") || t.equals("J") ){
            ay = 1*(G_BOX_SIZE + G_LINE_WIDTH) / 2;
        }else{
            ay = 2*(G_BOX_SIZE + G_LINE_WIDTH) / 2;
        }
        for(int i = 0; i<m.length; i++){
            for(int j = 0; j<m[0].length; j++){
                if (m[i][j].toString().trim().length()>0){
                    y = G_CANVAS_HEIGHT - 5*(G_BOX_SIZE + G_LINE_WIDTH)/2 - ay;
                    y += (G_BORDER_WIDTH + G_LINE_WIDTH/2+i*(G_BOX_SIZE + G_LINE_WIDTH)) / 2;
                    x = 1*(G_BOX_SIZE + G_LINE_WIDTH)/2;
                    x += (G_BORDER_WIDTH + G_LINE_WIDTH /2+j*(G_BOX_SIZE + G_LINE_WIDTH)) / 2;
                    gc.setFill(c);
                    gc.fillRect(x, y, G_BOX_SIZE/2, G_BOX_SIZE/2);
                    gc.strokeRoundRect(x, y, G_BOX_SIZE/2, G_BOX_SIZE/2, 0, 0);
                }
            }
        }
        // text
        gc.setFill( Color.BLACK );
        gc.setFont( Font.font( "Courier New", FontWeight.NORMAL, 18 ) );
        x = 6*(G_BOX_SIZE + G_LINE_WIDTH)/2;
        y = G_CANVAS_HEIGHT - 4*(G_BOX_SIZE + G_LINE_WIDTH)/2;
        n = board.getLines();
        gc.fillText( "Next: " + t, x, y );
        y += 2*(G_BOX_SIZE + G_LINE_WIDTH)/2;
        gc.fillText( "Lines: " + n, x, y );
    }

    private static void drawResult(GraphicsContext gc, Board board, String[] txts) {
        drawBoard(gc, board);
        // frame
        gc.setFill(Color.WHITESMOKE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(G_BORDER_WIDTH);
        double x = G_CANVAS_WIDTH/4 + G_BORDER_WIDTH/2;
        double y = G_CANVAS_HEIGHT/4;
        double w = G_CANVAS_WIDTH/2+ G_BORDER_WIDTH;
        double h = G_CANVAS_HEIGHT/3 + G_BORDER_WIDTH/2;
        double r = 5;
        gc.fillRect(x, y, w, h);
        gc.strokeRoundRect(x, y, w, h, r, r);
        // text
        gc.setFill( Color.BLACK );
        gc.setFont( Font.font( "Courier New", FontWeight.NORMAL, 18 ) );
        double ax = 30;
        double ay = 25;
        int j = 1;
        for(String txt : txts){
            gc.fillText( txt, ax, y+j*ay );
            j++;
        }
    }

    public static void drawYouWon(GraphicsContext gc, Board board) {
        drawResult(gc, board, G_YOUWON_TXTS);
    }

    public static void drawYouLost(GraphicsContext gc, Board board) {
        drawResult(gc, board, G_YOULOST_TXTS);
    }
}
