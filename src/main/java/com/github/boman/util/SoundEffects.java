package com.github.boman.util;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SoundEffects {
    public enum SoundIndex {
        BGM(0),
        BOMBER_DIED(1),
        BOMB_EXPLODED(2),
        BOMB_PLANTED(3),
        GAME_OVER(4),
        ITEM_PICKED(5),
        WIN(6),
        MAIN_MENU(7);
        private int value;

        private SoundIndex(int value) {
            this.value = value;
        }
    }

    private List<AudioClip> sounds;
    public static SoundEffects instance = new SoundEffects();

    private SoundEffects() {
        sounds = new ArrayList<>();
        sounds.add(loadAudioClip("bgm.wav", true));
        sounds.add(loadAudioClip("bomberDied.wav", false));
        sounds.add(loadAudioClip("bombExploded.wav", false));
        sounds.add(loadAudioClip("bombPlanted.wav", false));
        sounds.add(loadAudioClip("gameOver.wav", false));
        sounds.add(loadAudioClip("itemPicked.wav", false));
        sounds.add(loadAudioClip("win.wav", false));
        sounds.add(loadAudioClip("mainMenu.wav", true));
    }

    public AudioClip loadAudioClip(String filename, boolean infinite) {
        File file = new File(Objects.requireNonNull(getClass().getResource(filename)).getFile());
        Media media = new Media(file.toURI().toString());
        AudioClip audioClip = new AudioClip(media.getSource());
        if (infinite) {
            audioClip.setCycleCount(AudioClip.INDEFINITE);
        }
        return audioClip;
    }

    public void playSound(SoundIndex index) {
        sounds.get(index.value).play();
    }

    public void stopSound(SoundIndex index) {
        sounds.get(index.value).stop();
    }

    public void setVolumeSound(double volume) {
        for (AudioClip audioClip : sounds) {
            audioClip.setVolume(volume);
        }
    }

    public void stopAllSound() {
        for (AudioClip audioClip: sounds) {
            audioClip.stop();
        }
    }
}
