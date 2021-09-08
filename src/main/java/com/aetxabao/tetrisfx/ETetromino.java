package com.aetxabao.tetrisfx;

import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.*;

public enum ETetromino {

    X(0),I(1),O(2),T(3),L(4),J(5),S(6),Z(7);

    private final String[] symbols = {" ", "I", "O", "T", "L", "J", "S", "Z"};

    private final Color[] colors = {WHITE, TURQUOISE, YELLOW, PURPLE, ORANGE, BLUE, GREEN, RED};

    private int code;

    ETetromino(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public Color getColor() {
        return colors[code];
    }

    @Override
    public String toString(){
        return symbols[code];
    }

}
