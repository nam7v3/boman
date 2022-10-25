package com.github.boman.util;

import java.util.ArrayList;
import java.util.List;

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
    private List<Sound> sounds;
    public static SoundEffects instance = new SoundEffects();
    private SoundEffects() {
        sounds = new ArrayList<>();
        sounds.add(new Sound("bgm.wav", true));
        sounds.add(new Sound("bomberDied.wav", false));
        sounds.add(new Sound("bombExploded.wav", false));
        sounds.add(new Sound("bombPlanted.wav", false));
        sounds.add(new Sound("gameOver.wav", false));
        sounds.add(new Sound("itemPicked.wav", false));
        sounds.add(new Sound("win.wav", false));
        sounds.add(new Sound("mainMenu.wav", true));
    }

    public void playSound(SoundIndex index) {
        sounds.get(index.value).audioClip.play();
    }

    public void stopSound(SoundIndex index) {
        sounds.get(index.value).audioClip.stop();
    }

    public void setVolumeSound(double volume) {
        for (Sound sound: sounds) {
            sound.audioClip.setVolume(volume);
        }
    }
}
