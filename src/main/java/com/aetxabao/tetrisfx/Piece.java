package com.aetxabao.tetrisfx;

import java.util.Random;

public class Piece {

    private final int[][][] SHAPES = new int[][][] {//shape,y,x
            { {  0,  0 },    {  0,  0 },    {  0,  0 },    { 0,  0 }   },//X
            { { -3,  0 },    { -2,  0 },    { -1,  0 },    { 0,  0 }   },//I
            { { -1,  0 },    { -1, -1 },    {  0, -1 },    { 0,  0 }   },//O
            { { -1, -1 },    { -1,  0 },    { -1,  1 },    { 0,  0 }   },//T
            { { -2, -1 },    { -1, -1 },    {  0, -1 },    { 0,  0 }   },//L
            { { -2,  0 },    { -1,  0 },    {  0,  0 },    { 0, -1 }   },//J
            { { -1,  1 },    { -1,  0 },    {  0,  0 },    { 0, -1 }   },//S
            { { -1, -1 },    { -1,  0 },    {  0,  0 },    { 0,  1 }   },//Z
    };

    private ETetromino tetromino;
    private int[] pos = new int[] {0,0};//y,x
    private int[][] coords = new int[4][2];

    public Piece(){
        var r = new Random();
        int code = Math.abs(r.nextInt()) % 7 + 1;
        tetromino = ETetromino.values()[code];
        setTetromino(tetromino);
    }

    public void setTetromino(ETetromino t){
        tetromino = t;
        for(int i=0; i<4; i++)
            for(int j=0; j<2; j++)
                coords[i][j] = SHAPES[tetromino.getCode()][i][j];
    }

    public ETetromino getTetromino() {
        return tetromino;
    }

    @Override
    public Piece clone(){
        Piece piece = new Piece();
        piece.tetromino = this.tetromino;
        piece.pos = this.pos.clone();
        for(int i=0; i<4; i++){
            piece.coords[i] = this.coords[i].clone();
        }
        return piece;
    }

    public int[] getPos() {
        return pos;
    }

    public void setX(int x) {
        pos[1] = x;
    }

    public void does(EAction action){
        switch (action){
            case MOVE_DOWN:
                pos[0] += 1;
                break;
            case MOVE_LEFT:
                pos[1] -= 1;
                break;
            case MOVE_RIGHT:
                pos[1] += 1;
                break;
            case ROTATE_PIECE:
                rotate();
                break;
        }
    }

    private void rotate(){
        for(int i=0; i<4; i++){
            int y = coords[i][0];
            coords[i][0] = coords[i][1];
            coords[i][1] = -y;
        }
    }

    public int[][] getCoords() {
        return coords;
    }

    public ETetromino[][] getETetrominos() {
        ETetromino[][] matrix = new ETetromino[4][4];
        //clean
        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                matrix[i][j] = ETetromino.X;
            }
        }
        //values
        for (int[] coord : coords) {
            matrix[coord[0]+3][coord[1]+1] = tetromino;
        }
        return matrix;
    }

    @Override
    public String toString(){
        ETetromino[][] matrix = getETetrominos();
        //string
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                sb.append(matrix[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
