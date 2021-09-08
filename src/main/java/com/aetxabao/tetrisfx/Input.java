package com.aetxabao.tetrisfx;

public class Input {

    static EGameStatus waitStart(String pressedKey){
        switch (pressedKey){
            case "ENTER": return EGameStatus.GAME_PLAYING;
            case "ESCAPE": return EGameStatus.GAME_QUIT;
            default: return EGameStatus.GAME_INI;
        }
    }

    static EAction readAction(String pressedKey){
        switch (pressedKey){
            case "ESCAPE": return EAction.QUIT_GAME;
            case "W": return EAction.ROTATE_PIECE;
            case "A": return EAction.MOVE_LEFT;
            case "S": return EAction.SINK_PIECE;
            case "D": return EAction.MOVE_RIGHT;
            case "X": return EAction.MOVE_DOWN;
            default: return EAction.NO_ACTION;
        }
    }

    static EGameStatus waitRestart(String pressedKey, EGameStatus previousStatus){
        switch (pressedKey){
            case "ENTER": return EGameStatus.GAME_PLAYING;
            case "ESCAPE": return EGameStatus.GAME_QUIT;
            default: return previousStatus;
        }
    }
}
