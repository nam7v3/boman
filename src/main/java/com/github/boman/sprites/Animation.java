package com.github.boman.sprites;

import com.github.boman.entity.Bomber.Atrribute;
import com.github.boman.entity.MoveableEntity.State;
import com.github.boman.game.Duration;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Animation {
    private Map<Object, Image[]> state;
    private Object curState;
    private int index;

    private Image[] curImages;
    private Object defaultState;
    private final Duration waitTime;
    private Duration curWait;
    private static Animation playerAnimation;

    public Animation(Duration waitTime) {
        state = new HashMap<>();
        this.waitTime = waitTime;
        curWait = Duration.of(waitTime);
    }

    public Animation addState(Object key, Image[] images) {
        state.put(key, images);
        return this;
    }

    public void setState(Object key) {
        if (!state.containsKey(key)) {
            curState = defaultState;
        } else if (curState == key) {
            return;
        } else {
            curState = key;
        }
        curImages = state.get(curState);
        index = 0;
    }

    public void setDefaultState(Object defaultState) {
        this.defaultState = defaultState;
    }

    public Image getImage() {
        curWait.minus();
        if (curWait.isNegative()) {
            curWait = Duration.of(waitTime);
            index++;
            if (index >= curImages.length) {
                index = 0;
            }
        }
        return curImages[index];
    }

    public static Animation getPlayerAnimation() {
        if (playerAnimation != null) return playerAnimation;
        playerAnimation = new Animation(Duration.of(5))
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
        playerAnimation.setState(State.Down);
        playerAnimation.setDefaultState(State.Down);
        return playerAnimation;
    }
}
