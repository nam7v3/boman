package com.github.boman.game;

import com.github.boman.entity.Entity;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 *  Handle game logic.
 */
public class BomanEngine {
    private List<Entity> entities;
    private boolean started;

    public BomanEngine(){
        this.entities = new ArrayList<>();
    }

    public void update(Duration t){
        for(Entity entity: entities){
            entity.update(t);
        }
    }

    public void add(Entity e){
        this.entities.add(e);
    }
    public List<Entity> getEntities() {
        return entities;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
