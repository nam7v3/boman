package com.github.boman.sprites;

import com.github.boman.entity.Bomb;
import com.github.boman.entity.Bomber.Atrribute;
import com.github.boman.entity.Brick;
import com.github.boman.entity.Enemy;
import com.github.boman.entity.Fire;
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
    private static Animation enemyAnimation;
    private static Animation bombAnimation;

    public static Animation fireAnimation;


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

    public boolean animationDone(){
        return index == curImages.length - 1;
    }

    public Duration getCurWait() {
        return curWait;
    }

    public void setCurWait(Duration curWait) {
        this.curWait = curWait;
    }

    public static Animation getPlayerAnimation() {
        Animation playerAnimation = new Animation(Duration.of(5))
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

    public static Animation getEnemyAnimation() {
        Animation enemyAnimation = new Animation(Duration.of(20))
                .addState(
                        State.Left, new Image[]{
                                Sprite.balloomLeft1,
                                Sprite.balloomLeft2,
                                Sprite.balloomLeft3,
                        }
                )
                .addState(
                        State.Standing, new Image[]{
                                Sprite.balloomLeft1
                        }
                )
                .addState(
                        State.Right, new Image[]{
                                Sprite.balloomRight1,
                                Sprite.balloomRight2,
                                Sprite.balloomRight3,
                        }
                )
                .addState(Enemy.Attribute.Dead, new Image[]{
                        Sprite.balloomDead,
                });
        enemyAnimation.setDefaultState(State.Left);
        enemyAnimation.setState(State.Left);
        return enemyAnimation;
    }

    public static Animation getBombAnimation() {
        if (bombAnimation != null) {
            return bombAnimation;
        }
        bombAnimation = new Animation(Duration.of(5))
                .addState(Bomb.Attribute.Pending, new Image[]{
                        Sprite.bomb,
                        Sprite.bomb1,
                        Sprite.bomb2
                })
                .addState(Bomb.Attribute.Exploded, new Image[]{
                        Sprite.bombExploded,
                        Sprite.bombExploded1,
                        Sprite.bombExploded2
                });
        bombAnimation.setDefaultState(Bomb.Attribute.Pending);
        bombAnimation.setDefaultState(Bomb.Attribute.Pending);
        return bombAnimation;
    }

    public static Animation getFireAnimation() {
        Animation fireAnimation = new Animation(Duration.of(5))
                .addState(Fire.State.HLeft, new Image[]{
                        Sprite.explosionHorizontalLeftLast,
                        Sprite.explosionHorizontalLeftLast1,
                        Sprite.explosionHorizontalLeftLast2
                })
                .addState(Fire.State.HRight, new Image[]{
                        Sprite.explosionHorizontalRightLast,
                        Sprite.explosionHorizontalRightLast1,
                        Sprite.explosionHorizontalRightLast2
                })
                .addState(Fire.State.VDown, new Image[]{
                        Sprite.explosionVerticalDownLast,
                        Sprite.explosionVerticalDownLast1,
                        Sprite.explosionVerticalDownLast2
                })
                .addState(Fire.State.VUp, new Image[]{
                        Sprite.explosionVerticalTopLast,
                        Sprite.explosionVerticalTopLast1,
                        Sprite.explosionVerticalTopLast2
                })
                .addState(Fire.State.Horizontal, new Image[]{
                        Sprite.explosionHorizontal,
                        Sprite.explosionHorizontal1,
                        Sprite.explosionHorizontal2
                })
                .addState(Fire.State.Vertical, new Image[]{
                        Sprite.explosionVertical,
                        Sprite.explosionVertical1,
                        Sprite.explosionVertical2,
                })
                .addState(Fire.State.Middle, new Image[]{
                        Sprite.bombExploded,
                        Sprite.bombExploded1,
                        Sprite.bombExploded2,
                });
        fireAnimation.setDefaultState(Fire.State.Middle);
        return fireAnimation;
    }

    public static Animation getBrickAnimation() {
        Animation brickAnimation = new Animation(Duration.of(5))
                .addState(Brick.Attribute.Normal, new Image[]{
                        Sprite.brick,
                })
                .addState(Brick.Attribute.Breaking, new Image[]{
                        Sprite.brickExploded,
                        Sprite.brickExploded1,
                        Sprite.brickExploded2,
                });
        brickAnimation.setDefaultState(Brick.Attribute.Normal);
        brickAnimation.setState(Brick.Attribute.Normal);
        return brickAnimation;
    }
}