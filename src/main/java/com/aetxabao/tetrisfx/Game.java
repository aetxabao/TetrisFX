package com.aetxabao.tetrisfx;

import static com.aetxabao.tetrisfx.Conf.*;
import static com.aetxabao.tetrisfx.Renderer.G_CANVAS_WIDTH;
import static com.aetxabao.tetrisfx.Renderer.G_CANVAS_HEIGHT;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.animation.AnimationTimer;

import javafx.scene.image.Image;

public class Game extends Application {

    private static EGameStatus gameStatus = EGameStatus.GAME_INI;
    private static Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);


    private static String pressedKey = "NONE";
    private static GraphicsContext gc;

    private long deltaTime;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "TetrisFX" );
        theStage.setResizable(false);

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Image icon16 = new Image(Game.class.getResourceAsStream("icon16.png"));
        Image icon32 = new Image(Game.class.getResourceAsStream("icon32.png"));
        Image icon64 = new Image(Game.class.getResourceAsStream("icon64.png"));
        Image icon128 = new Image(Game.class.getResourceAsStream("icon128.png"));
        theStage.getIcons().addAll(icon16, icon32, icon64, icon128);

        Canvas canvas = new Canvas( G_CANVAS_WIDTH, G_CANVAS_HEIGHT);
        root.getChildren().add( canvas );

        gc = canvas.getGraphicsContext2D();

        final long startNanoTime = System.nanoTime();

        deltaTime = startNanoTime;

        theScene.setOnKeyReleased(e -> {
            pressedKey = e.getCode().toString();
       });

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                switch (gameStatus){
                    case GAME_INI:
                        init_game();
                        break;
                    case GAME_PLAYING:
                        if ((currentNanoTime - deltaTime) / 1000000000.0 > (1.0/GAME_SPEED) ){
                            deltaTime = currentNanoTime;
                            game_loop(true);
                        }else{
                            game_loop(false);
                        }
                        break;
                    case GAME_WON:
                    case GAME_LOST:
                        finish_game();
                        break;
                    case GAME_QUIT:
                        theStage.close();
                        break;
                }
            }
        }.start();

        theStage.show();
    }

    static void game_loop(boolean update) {
//        init_game();
//        while (gameStatus == EGameStatus.GAME_PLAYING) {
            render_game();
            process_input();
            if (update) update_game();
//        }
//        finish_game();
    }

    static void init_game() {
        Renderer.drawInit(gc);
        gameStatus = Input.waitStart(pressedKey);
    }

    static void render_game() {
        Renderer.drawBoard(gc, board);
    }

    static void process_input() {
        EAction action = Input.readAction(pressedKey);
        if (action == EAction.QUIT_GAME){
            gameStatus = EGameStatus.GAME_QUIT;
            return;
        }
        if (board.canPieceDo(action)) {
            board.pieceDoes(action);
        }
        pressedKey = "NONE";//reset
    }

    static void update_game() {
        if (board.canPieceDo(EAction.MOVE_DOWN)){
            board.pieceDoes(EAction.MOVE_DOWN);
        }else{
            board.mergePiece();
            board.removeLines();
            if (board.getLines() == TARGET_LINES){
                gameStatus = EGameStatus.GAME_WON;
            }
            if (!board.newPiece()){
                gameStatus = EGameStatus.GAME_LOST;
            }
        }
    }

    static void finish_game() {
        switch (gameStatus){
            case GAME_WON:
                Renderer.drawYouWon(gc, board);
                break;
            case GAME_LOST:
                Renderer.drawYouLost(gc, board);
                break;
        }
        if ( (gameStatus = Input.waitRestart(pressedKey,gameStatus)) == EGameStatus.GAME_PLAYING){
//            gameStatus = EGameStatus.GAME_INI;
            board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
            pressedKey = "NONE";
        }
    }

}
