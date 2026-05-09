package ru.nsu.ccfit.kombarov.tetris;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.ccfit.kombarov.tetris.config.LogConfig;
import ru.nsu.ccfit.kombarov.tetris.controller.app.GameApplication;
import ru.nsu.ccfit.kombarov.tetris.exceptions.TetrisException;

import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LogConfig.setup();
        logger.info("Tetris started");
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            GameApplication app = new GameApplication(stage);
            app.start();
        } catch (Exception e) {
            showError(e);
            throw new TetrisException("Startup failed", e);
        }
    }

    private void showError(Exception e) {
        e.printStackTrace();

        javafx.scene.control.Alert alert =
                new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText("Application failed to start");
        alert.setContentText(e.getMessage());

        alert.showAndWait();

        javafx.application.Platform.exit();
    }
}