package ru.nsu.ccfit.kombarov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.ccfit.kombarov.model.factory.Factory;
import ru.nsu.ccfit.kombarov.model.factory.config.FactoryConfig;
import ru.nsu.ccfit.kombarov.model.factory.config.FactoryConfigLoader;
import ru.nsu.ccfit.kombarov.presenter.FactoryPresenter;
import ru.nsu.ccfit.kombarov.view.FactoryView;

public class Main extends Application {
    private Factory factory;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FactoryConfigLoader loader = new FactoryConfigLoader();
        FactoryConfig config = loader.load(
                "ru/nsu/ccfit/kombarov/model/config/config.properties");

        factory = new Factory(config);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource(
                "ru/nsu/ccfit/kombarov/view/factory-view.fxml"));
        Parent root = fxmlLoader.load();
        FactoryView view = fxmlLoader.getController();

        if (view == null) {
            throw new IllegalStateException("Controller not found in FXML");
        }

        new FactoryPresenter(factory, view);

        factory.start();

        primaryStage.setTitle("Factory Monitoring System");
        primaryStage.setScene(new Scene(root, 800, 700));

        primaryStage.setOnCloseRequest(event -> {
            factory.shutdown();
            System.exit(0);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}