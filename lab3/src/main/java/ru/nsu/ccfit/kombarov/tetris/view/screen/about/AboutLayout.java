package ru.nsu.ccfit.kombarov.tetris.view.screen.about;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;
import ru.nsu.ccfit.kombarov.tetris.view.ui.UiFactory;

public class AboutLayout extends VBox {

    private final Button backButton;

    public AboutLayout(Theme theme, ViewConfig config) {
        UiFactory uiFactory = new UiFactory(theme, config);

        this.backButton = uiFactory.createButton("Back");

        setAlignment(Pos.CENTER);
        setSpacing(config.getScreenSpacing());
        setStyle(theme.getUiStyle().mainBackgroundStyle());

        Label title = uiFactory.createTitleLabel("ABOUT");

        Label text = uiFactory.createLabel("""
                TETO-POTATO
                """);

        getChildren().addAll(
                title,
                text,
                backButton
        );
    }

    public Button getBackButton() {
        return backButton;
    }
}