package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Wall {
    boolean Collision;
    Texture image;

    Wall()
    {
        Collision = true;
        image = new Texture ( "wall.png" );

    }
    void Dispose(){
        image.dispose ();
    }
}
