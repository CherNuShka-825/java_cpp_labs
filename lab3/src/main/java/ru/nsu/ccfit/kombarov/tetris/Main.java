package ru.nsu.ccfit.kombarov.tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.ccfit.kombarov.tetris.config.AppConfig;
import ru.nsu.ccfit.kombarov.tetris.controller.GameController;
import ru.nsu.ccfit.kombarov.tetris.controller.GameLoop;
import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrisException;
import ru.nsu.ccfit.kombarov.tetris.model.board.Board;
import ru.nsu.ccfit.kombarov.tetris.model.facrory.TetrominoFactory;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameModel;
import ru.nsu.ccfit.kombarov.tetris.model.game.GameSpeed;
import ru.nsu.ccfit.kombarov.tetris.model.tetromino.TetrominoGenerator;
import ru.nsu.ccfit.kombarov.tetris.view.layout.GameLayout;
import ru.nsu.ccfit.kombarov.tetris.view.input.InputHandler;
import ru.nsu.ccfit.kombarov.tetris.view.loop.RenderLoop;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.factory.ThemeFactory;
import ru.nsu.ccfit.kombarov.tetris.view.theme.ThemeManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Board board = new Board(
                    AppConfig.BOARD_WIDTH,
                    AppConfig.BOARD_HEIGHT
            );

            TetrominoFactory tetrominoFactory =
                    new TetrominoFactory(AppConfig.TETROMINO_CONFIG);

            TetrominoGenerator generator =
                    new TetrominoGenerator(tetrominoFactory);

            GameModel model = new GameModel(board, generator);

            ThemeFactory themeFactory =
                    new ThemeFactory(AppConfig.THEME_CONFIG);

            ThemeManager themeManager =
                    new ThemeManager(themeFactory, AppConfig.DEFAULT_THEME);

            ViewConfig viewConfig = new ViewConfig(
                    AppConfig.CELL_SIZE,
                    AppConfig.PREVIEW_CELL_SIZE
            );

            GameLayout root =
                    new GameLayout(model, themeManager.getCurrentTheme(), viewConfig);

            GameSpeed speed = new GameSpeed();

            Runnable render = () -> {
                double time = System.nanoTime() / 1_000_000_000.0;
                root.render(time);
            };

            RenderLoop renderLoop = new RenderLoop(render);
            renderLoop.start();

            GameLoop loop = new GameLoop(model, speed, render);

            GameController controller = new GameController(model, loop);

            Scene scene = new Scene(root);

            InputHandler input = new InputHandler(controller, render);
            input.attachTo(scene);

            stage.setScene(scene);
            stage.setTitle(AppConfig.WINDOW_TITLE);
            stage.show();

            controller.startNewGame();

        } catch (FactoryException e) {
            throw new TetrisException("Startup failed", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}