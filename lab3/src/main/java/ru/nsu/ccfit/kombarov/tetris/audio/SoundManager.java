package ru.nsu.ccfit.kombarov.tetris.audio;

import javafx.scene.media.AudioClip;

import java.net.URL;

public class SoundManager {

    private static final double DEFAULT_VOLUME = 0.1;

    private final AudioClip buttonClick;
    private final AudioClip blockLock;

    public SoundManager(String buttonClickPath, String blockLockPath) {
        this.buttonClick = loadClip(buttonClickPath);
        this.blockLock = loadClip(blockLockPath);

        this.buttonClick.setVolume(DEFAULT_VOLUME);
        this.blockLock.setVolume(DEFAULT_VOLUME);
    }

    public void playButtonClick() {
        buttonClick.play();
    }

    public void playBlockLock() {
        blockLock.play();
    }

    public void setVolume(double volume) {
        double clamped = clamp(volume);

        buttonClick.setVolume(clamped);
        blockLock.setVolume(clamped);
    }

    private AudioClip loadClip(String path) {
        URL url = getClass().getClassLoader().getResource(path);

        if (url == null) {
            throw new IllegalStateException("Sound file not found: " + path);
        }

        return new AudioClip(url.toExternalForm());
    }

    private double clamp(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}