package com.github.boman.util;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.io.File;
import java.util.Objects;

public class Sound {
    protected File file;
    protected boolean infiniteLoop;
    protected Media media;
    protected AudioClip audioClip;
    public Sound(String fileName, boolean infiniteLoop) {
        File file = new File(Objects.requireNonNull(Sound.class.getResource(fileName)).getFile());
        this.infiniteLoop = infiniteLoop;
        media = new Media(file.toURI().toString());
        audioClip = new AudioClip(media.getSource());
        if (infiniteLoop) {
            audioClip.setCycleCount(AudioClip.INDEFINITE);
        }
    }
}