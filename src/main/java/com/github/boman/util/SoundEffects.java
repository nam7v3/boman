package com.github.boman.util;

import java.io.File;
import java.util.Objects;

public class SoundEffects {
    public static File bgm = new File(Objects.requireNonNull(SoundEffects.class.getResource("bgm.wav")).getFile());
    public static File bomberDied = new File(Objects.requireNonNull(SoundEffects.class.getResource("bomberDied.wav")).getFile());
    public static File bombExploded = new File(Objects.requireNonNull(SoundEffects.class.getResource("bombExploded.wav")).getFile());
    public static File bombPlanted = new File(Objects.requireNonNull(SoundEffects.class.getResource("bombPlanted.wav")).getFile());
    public static File gameOver = new File(Objects.requireNonNull(SoundEffects.class.getResource("gameOver.wav")).getFile());
    public static File itemPicked = new File(Objects.requireNonNull(SoundEffects.class.getResource("itemPicked.wav")).getFile());
    public static File win = new File(Objects.requireNonNull(SoundEffects.class.getResource("win.wav")).getFile());
}