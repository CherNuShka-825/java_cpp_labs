package ru.nsu.ccfit.kombarov.tetris.audio;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

public class MusicManager {

    private static final double MUSIC_VOLUME = 0.1;
    private static final double MUTED_VOLUME = 0.0;

    private static final int FADE_IN_DURATION_MS = 700;

    private final String gameMusicPath;
    private final String menuMusicPath;

    private MediaPlayer player;
    private MusicTrack currentTrack;
    private Timeline volumeFade;

    public MusicManager(String gameMusicPath, String menuMusicPath) {
        this.gameMusicPath = gameMusicPath;
        this.menuMusicPath = menuMusicPath;
    }

    public void playGameMusic() {
        switchTo(MusicTrack.GAME, gameMusicPath);
    }

    public void playMenuMusic() {
        switchTo(MusicTrack.MENU, menuMusicPath);
    }

    public void stop() {
        stopFade();

        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
        }

        currentTrack = null;
    }

    public void mute() {
        stopFade();

        if (player != null) {
            player.setVolume(MUTED_VOLUME);
        }
    }

    public void fadeUp() {
        if (player == null) {
            return;
        }

        fadeTo(MUSIC_VOLUME, FADE_IN_DURATION_MS);
    }

    public void setMusicVolume() {
        stopFade();

        if (player != null) {
            player.setVolume(MUSIC_VOLUME);
        }
    }

    public void setVolume(double volume) {
        stopFade();

        if (player != null) {
            player.setVolume(clamp(volume));
        }
    }

    private void switchTo(MusicTrack track, String musicPath) {
        stopFade();

        if (currentTrack == track && player != null) {
            player.setVolume(MUSIC_VOLUME);
            player.play();
            return;
        }

        if (player != null) {
            player.stop();
            player.dispose();
        }

        player = createPlayer(musicPath);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(MUSIC_VOLUME);
        player.play();

        currentTrack = track;
    }

    private void fadeTo(double targetVolume, int durationMs) {
        stopFade();

        volumeFade = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(player.volumeProperty(), player.getVolume())
                ),
                new KeyFrame(
                        Duration.millis(durationMs),
                        new KeyValue(player.volumeProperty(), clamp(targetVolume))
                )
        );

        volumeFade.play();
    }

    private void stopFade() {
        if (volumeFade != null) {
            volumeFade.stop();
            volumeFade = null;
        }
    }

    private MediaPlayer createPlayer(String musicPath) {
        URL url = getClass().getClassLoader().getResource(musicPath);

        if (url == null) {
            throw new IllegalStateException("Music file not found: " + musicPath);
        }

        Media media = new Media(url.toExternalForm());
        return new MediaPlayer(media);
    }

    private double clamp(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }

    private enum MusicTrack {
        GAME,
        MENU
    }
}