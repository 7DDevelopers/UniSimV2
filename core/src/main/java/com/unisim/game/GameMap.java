package com.unisim.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.tools.javac.Main;
import com.unisim.game.Stages.MainStage;

/**
 * Represents a tiled map, and handles its rendering.
 */
public class GameMap extends Actor {
    TiledMap tiledMap;
    TiledMap winterMap;
    TiledMapRenderer tiledMapRenderer;
    MainStage game;

    public GameMap() {
        tiledMap = new TmxMapLoader().load("TEAM5ENG1map.tmx");
        winterMap = new TmxMapLoader().load("WINTERmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }
    public void winterSeasonMap(){
        tiledMapRenderer = new OrthogonalTiledMapRenderer(winterMap);
    }
    public void defaultMap(){
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        tiledMapRenderer.setView(main.camera);
        tiledMapRenderer.render();
        batch.begin();
    }
}
