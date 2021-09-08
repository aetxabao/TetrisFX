package com.aetxabao.tetrisfx;

public class Board {
    private int width;
    private int height;
    private Piece piece;
    private ETetromino[][] matrix;
    private Piece nextPiece;
    private int lines = 0;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new ETetromino[height][width];
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                matrix[i][j] = ETetromino.X;
            }
        }
        piece = new Piece();
        piece.setX(width / 2);
        nextPiece = new Piece();
        nextPiece.setX(width / 2);
        // COMMENT TESTING INIT
        //init();
    }

    private void init(){
        lines = 6;
        for(int i = height-1; i>=height-4; i--){
            for(int j = 0; j<width; j++){
                matrix[i][j] = ETetromino.I;
            }
        }
        for(int i = height-1; i>=height-4; i--){
            matrix[i][5] = ETetromino.X;
        }
        piece.setTetromino(ETetromino.I);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ETetromino[][] getBoardWithPiece() {
        ETetromino[][] m = new ETetromino[height][];
        for (int i = 0; i < height; i++) {
            m[i] = matrix[i].clone();
        }
        //Piece
        int[] pos = piece.getPos();
        int[][] coords = piece.getCoords();
        int y, x;
        for(int[] coord : coords){
            if (pos[0] + coord[0] >= 0){
                y = pos[0]+coord[0];
                x = pos[1]+coord[1];
                m[y][x] = piece.getTetromino();
            }
        }
        return m;
    }

    public Piece getNextPiece(){
        return nextPiece;
    }

    public int getLines(){
        return lines;
    }

    public void pieceDoes(EAction action){
        if (action== EAction.SINK_PIECE) {
            while (canPieceDo(EAction.MOVE_DOWN)) {
                pieceDoes(EAction.MOVE_DOWN);
            }
        }else{
            piece.does(action);
        }
    }

    public boolean canPieceDo(EAction action){
        Piece testPiece = piece.clone();
        if (action==EAction.SINK_PIECE) action = EAction.MOVE_DOWN;
        testPiece.does(action);
        return couldHave(testPiece);
    }

    private boolean couldHave(Piece testPiece){
        int[] pos = testPiece.getPos();
        int[][] coords = testPiece.getCoords();
        int y, x;
        for(int[] coord : coords){
            y = pos[0]+coord[0];
            x = pos[1]+coord[1];
            if (y>=height) return false;
            if (x<0 || x>=width) return false;
            if (y<0) continue;
            if (matrix[y][x].getCode() != ETetromino.X.getCode() ) return false;
        }
        return true;
    }

    public void mergePiece(){
        int[] pos = piece.getPos();
        int[][] coords = piece.getCoords();
        int y, x;
        for(int[] coord : coords){
            y = pos[0]+coord[0];
            x = pos[1]+coord[1];
            if (y>=0) {
                matrix[y][x] = piece.getTetromino();
            }
        }
    }

    public boolean newPiece(){
        if (couldHave(nextPiece)){
            piece = nextPiece;
            nextPiece = new Piece();
            nextPiece.setX(width / 2);
            return true;
        }else{
            return false;
        }
    }

    public void removeLines(){
        boolean isLine;
        for(int i=0; i<height; i++){
            isLine = true;
            for(int j=0; j<width; j++){
                if (matrix[i][j].getCode() == ETetromino.X.getCode()){
                    isLine = false;
                    break;
                }
            }
            if (isLine){
                lines++;
                for (int k=i; k>0; k--) {
                    matrix[k] = matrix[k-1].clone();
//                    for (int l = 0; l < width; l++) {
//                        matrix[k][l] = matrix[k-1][l];
//                    }
                }
                for (int l = 0; l < width; l++) {
                    matrix[0][l] = ETetromino.X;
                }
            }
        }
    }

}
