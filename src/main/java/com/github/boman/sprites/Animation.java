package com.github.boman.sprites;

import com.github.boman.entity.Bomber.Atrribute;
import com.github.boman.entity.MoveableEntity.State;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Animation {
    private Map<Object, Image[]> state;
    private Iterator<Image> iterator;
    private Object curState;
    private Object defaultState;
    private static Animation playerAnimation;

    public Animation() {
        state = new HashMap<>();
    }

    public Animation addState(Object key, Image[] images) {
        state.put(key, images);
        return this;
    }

    public void setState(Object key) {
        if (!state.containsKey(key)) {
            curState = defaultState;
        } else {
            curState = key;
        }
        iterator = Arrays.stream(state.get(key)).iterator();
    }

    public void setDefaultState(Object defaultState) {
        this.defaultState = defaultState;
    }

    public Image getImage() {
        if (curState == null || !iterator.hasNext()) {
            setState(curState);
        }
        return iterator.next();
    }

    public static Animation getPlayerAnimation() {
        if (playerAnimation != null) return playerAnimation;
        playerAnimation = new Animation()
                .addState(
                        State.Up, new Image[]{
                                Sprite.playerUp,
                                Sprite.playerUp1,
                                Sprite.playerUp2,
                        })
                .addState(
                        State.Down, new Image[]{
                                Sprite.playerDown,
                                Sprite.playerDown1,
                                Sprite.playerDown2,
                        }
                )
                .addState(
                        State.Left, new Image[]{
                                Sprite.playerLeft,
                                Sprite.playerLeft1,
                                Sprite.playerLeft2,
                        }
                )
                .addState(
                        State.Right, new Image[]{
                                Sprite.playerRight,
                                Sprite.playerRight1,
                                Sprite.playerRight2,
                        }
                )
                .addState(
                        State.Standing, new Image[]{
                                Sprite.playerDown
                        }
                )
                .addState(
                        Atrribute.Dead, new Image[]{
                                Sprite.playerDead1,
                                Sprite.playerDead2,
                                Sprite.playerDead3,
                        }
                );
        playerAnimation.setState(State.Standing);
        playerAnimation.setDefaultState(State.Standing);
        return playerAnimation;
    }
}
