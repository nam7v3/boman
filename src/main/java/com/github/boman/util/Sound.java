package com.github.boman.util;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.io.File;

public class Sound {
    private File file;
    private boolean infiniteLoop;
    Media media;
    AudioClip audioClip;
    public Sound(File file, boolean infiniteLoop) {
        this.file = file;
        this.infiniteLoop = infiniteLoop;
        media = new Media(file.toURI().toString());
        audioClip = new AudioClip(media.getSource());
        if (infiniteLoop) {
            audioClip.setCycleCount(AudioClip.INDEFINITE);
        }
    }

    public void playSound() {
        audioClip.play();
    }

    public void stopSound() {
        audioClip.stop();
    }

    public void setVolumeSound(double volume) {
        audioClip.setVolume(volume);
    }
    public static Sound bgm = new Sound(SoundEffects.bgm, true);
    public static Sound bomberDied = new Sound(SoundEffects.bomberDied, false);
    public static Sound  = new Sound(SoundEffects.bgm, true);
    public static Sound bgm = new Sound(SoundEffects.bgm, true);
    public static Sound bgm = new Sound(SoundEffects.bgm, true);
    public static Sound bgm = new Sound(SoundEffects.bgm, true);
    public static Sound bgm = new Sound(SoundEffects.bgm, true);
}