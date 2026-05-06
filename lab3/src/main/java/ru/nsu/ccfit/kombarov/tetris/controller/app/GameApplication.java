package ru.nsu.ccfit.kombarov.tetris.controller.app;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.ccfit.kombarov.tetris.audio.MusicManager;
import ru.nsu.ccfit.kombarov.tetris.audio.SoundManager;
import ru.nsu.ccfit.kombarov.tetris.config.AppConfig;
import ru.nsu.ccfit.kombarov.tetris.controller.game.GameController;
import ru.nsu.ccfit.kombarov.tetris.controller.game.GameLoop;
import ru.nsu.ccfit.kombarov.tetris.controller.gameover.GameOverHandler;
import ru.nsu.ccfit.kombarov.tetris.controller.highscore.HighScoresManager;
import ru.nsu.ccfit.kombarov.tetris.controller.screen.SceneManager;
import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.model.board.Board;
import ru.nsu.ccfit.kombarov.tetris.model.factory.TetrominoFactory;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameSpeed;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.TetrominoGenerator;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.input.InputHandler;
import ru.nsu.ccfit.kombarov.tetris.view.loop.RenderLoop;
import ru.nsu.ccfit.kombarov.tetris.view.screen.about.AboutScreen;
import ru.nsu.ccfit.kombarov.tetris.view.screen.game.GameScreen;
import ru.nsu.ccfit.kombarov.tetris.view.screen.gameover.GameOverScreen;
import ru.nsu.ccfit.kombarov.tetris.view.screen.highScores.HighScoresScreen;
import ru.nsu.ccfit.kombarov.tetris.view.screen.menu.MainMenuScreen;
import ru.nsu.ccfit.kombarov.tetris.view.screen.pause.PauseMenuScreen;
import ru.nsu.ccfit.kombarov.tetris.view.theme.ThemeManager;
import ru.nsu.ccfit.kombarov.tetris.view.theme.factory.ThemeFactory;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

import java.nio.file.Path;

public class GameApplication {

    private final Stage stage;
    private GameOverHandler gameOverHandler;

    public GameApplication(Stage stage) {
        this.stage = stage;
    }

    public void start() throws FactoryException {
        Board board = new Board(
                AppConfig.BOARD_WIDTH,
                AppConfig.BOARD_HEIGHT
        );

        TetrominoFactory tetrominoFactory =
                new TetrominoFactory(AppConfig.TETROMINO_CONFIG);

        TetrominoGenerator generator =
                new TetrominoGenerator(tetrominoFactory);

        GameModel model =
                new GameModel(board, generator);

        ThemeFactory themeFactory =
                new ThemeFactory(AppConfig.THEME_CONFIG);

        ThemeManager themeManager =
                new ThemeManager(themeFactory, AppConfig.DEFAULT_THEME);

        Theme currentTheme =
                themeManager.getCurrentTheme();

        ViewConfig viewConfig =
                new ViewConfig(
                        AppConfig.APP_BASE_SIZE,
                        AppConfig.BOARD_WIDTH,
                        AppConfig.BOARD_HEIGHT
                );

        MainMenuScreen menuScreen =
                new MainMenuScreen(currentTheme, viewConfig);

        GameScreen gameScreen =
                new GameScreen(model, currentTheme, viewConfig);

        HighScoresScreen highScoresScreen =
                new HighScoresScreen(currentTheme, viewConfig);

        AboutScreen aboutScreen =
                new AboutScreen(currentTheme, viewConfig);

        PauseMenuScreen pauseMenuScreen =
                new PauseMenuScreen(currentTheme, viewConfig);

        MusicManager musicManager =
                new MusicManager(
                        AppConfig.GAME_MUSIC_FILE,
                        AppConfig.MENU_MUSIC_FILE
                );

        SoundManager soundManager =
                new SoundManager(
                        AppConfig.BUTTON_CLICK_SOUND,
                        AppConfig.BLOCK_LOCK_SOUND,
                        AppConfig.LINE_CLEARED_SOUND
                );

        model.setOnBlockLocked(soundManager::playBlockLock);
        model.setOnLineCleared(soundManager::playLineCleared);

        HighScoresManager highScoresManager =
                new HighScoresManager(
                        Path.of(AppConfig.HIGH_SCORES_FILE),
                        highScoresScreen
                );

        GameOverScreen gameOverScreen =
                new GameOverScreen(
                        currentTheme,
                        viewConfig,
                        highScoresManager::addScore
                );

        SceneManager sceneManager =
                new SceneManager(
                        stage,
                        menuScreen,
                        gameScreen,
                        highScoresScreen,
                        aboutScreen,
                        gameOverScreen,
                        pauseMenuScreen
                );

        Runnable render =
                createRenderAction(gameScreen);

        RenderLoop renderLoop =
                new RenderLoop(render);

        renderLoop.start();

        GameSpeed speed =
                new GameSpeed();

        GameLoop gameLoop =
                new GameLoop(model, speed, render);

        GameController controller =
                new GameController(model, gameLoop);

        gameOverHandler =
                new GameOverHandler(
                        model,
                        highScoresManager,
                        gameOverScreen,
                        sceneManager,
                        musicManager::playMenuMusic
                );

        InputHandler input =
                new InputHandler(controller, render);

        input.attachTo(sceneManager.getGameScene());

        input.setEscapeCallback(
                createPauseAction(controller, musicManager, sceneManager, render)
        );

        menuScreen.setOnNewGame(
                withButtonSound(
                        soundManager,
                        createNewGameAction(controller, musicManager, sceneManager, render)
                )
        );

        menuScreen.setOnHighScores(
                withButtonSound(soundManager, sceneManager::showHighScores)
        );

        menuScreen.setOnAbout(
                withButtonSound(soundManager, sceneManager::showAbout)
        );

        menuScreen.setOnExit(
                withButtonSoundAndDelay(
                        soundManager,
                        createExitApplicationAction(gameLoop, renderLoop, musicManager)
                )
        );

        highScoresScreen.setOnBack(
                withButtonSound(soundManager, sceneManager::showMenu)
        );

        aboutScreen.setOnBack(
                withButtonSound(soundManager, sceneManager::showMenu)
        );

        gameOverScreen.setOnMenu(
                withButtonSound(soundManager, sceneManager::showMenu)
        );

        pauseMenuScreen.setOnContinue(
                withButtonSound(
                        soundManager,
                        createContinueAction(controller, musicManager, sceneManager, render)
                )
        );

        pauseMenuScreen.setOnMainMenu(
                withButtonSound(
                        soundManager,
                        createExitToMenuAction(gameLoop, musicManager, render)
                )
        );

        stage.setOnCloseRequest(event ->
                stopApplication(gameLoop, renderLoop, musicManager)
        );

        musicManager.playMenuMusic();
        sceneManager.showMenu();
    }

    private Runnable createRenderAction(GameScreen gameScreen) {
        return () -> {
            double time = System.nanoTime() / 1_000_000_000.0;

            gameScreen.render(time);

            if (gameOverHandler != null) {
                gameOverHandler.check();
            }
        };
    }

    private Runnable createNewGameAction(
            GameController controller,
            MusicManager musicManager,
            SceneManager sceneManager,
            Runnable render
    ) {
        return () -> {
            controller.startNewGame();

            musicManager.playGameMusic();

            sceneManager.showGame();
            render.run();
        };
    }

    private Runnable createPauseAction(
            GameController controller,
            MusicManager musicManager,
            SceneManager sceneManager,
            Runnable render
    ) {
        return () -> {
            controller.pause();

            musicManager.mute();

            sceneManager.showPauseMenu();
            render.run();
        };
    }

    private Runnable createContinueAction(
            GameController controller,
            MusicManager musicManager,
            SceneManager sceneManager,
            Runnable render
    ) {
        return () -> {
            controller.resume();

            musicManager.fadeUp();

            sceneManager.showGame();
            render.run();
        };
    }

    private Runnable createExitToMenuAction(
            GameLoop gameLoop,
            MusicManager musicManager,
            Runnable render
    ) {
        return () -> {
            gameLoop.stop();

            gameOverHandler.exitToMenuWithHighScoreCheck();

            musicManager.playMenuMusic();

            render.run();
        };
    }

    private Runnable createExitApplicationAction(
            GameLoop gameLoop,
            RenderLoop renderLoop,
            MusicManager musicManager
    ) {
        return () -> {
            stopApplication(gameLoop, renderLoop, musicManager);
            Platform.exit();
        };
    }

    private Runnable withButtonSound(
            SoundManager soundManager,
            Runnable action
    ) {
        return () -> {
            soundManager.playButtonClick();
            action.run();
        };
    }

    private void stopApplication(
            GameLoop gameLoop,
            RenderLoop renderLoop,
            MusicManager musicManager
    ) {
        gameLoop.stop();
        renderLoop.stop();
        musicManager.stop();
    }

    private Runnable withButtonSoundAndDelay(
            SoundManager soundManager,
            Runnable action
    ) {
        return () -> {
            soundManager.playButtonClick();

            PauseTransition delay = new PauseTransition(Duration.millis(150));
            delay.setOnFinished(event -> action.run());
            delay.play();
        };
    }
}