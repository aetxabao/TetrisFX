package com.aetxabao.tetrisfx;

/*
mvn package
/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home/bin/jpackage --name TetrisFX --icon src/main/resources/com/aetxabao/tetrisfx/icon.icns --input target --main-jar TetrisFX-1.0-SNAPSHOT.jar --main-class com.aetxabao.tetrisfx.Main
 */
public class Main {
    public static void main(String[] args) {
        Game.main(args);
    }
}
