package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Point {
    Texture image;
    boolean colision;

    Point(){
        image = new Texture ( "point.png" );
        colision = false;
    }
}
