package ru.nsu.ccfit.kombarov.tetris.view.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.nsu.ccfit.kombarov.tetris.view.config.ViewConfig;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

public class UiFactory {

    private static final String BOLD_STYLE = "-fx-font-weight: bold;";

    private final Theme theme;
    private final ViewConfig config;

    public UiFactory(Theme theme, ViewConfig config) {
        this.theme = theme;
        this.config = config;
    }

    public Button createButton(String text) {
        Button button = new Button(text);

        button.setPrefWidth(config.getButtonWidth());
        button.setPrefHeight(config.getButtonHeight());
        button.setStyle(theme.getUiStyle().buttonStyle(config.getButtonFontSize()));

        return button;
    }

    public Label createLabel() {
        return createLabel("");
    }

    public Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle(theme.getUiStyle().textStyle(config.getTextFontSize()));

        return label;
    }

    public Label createTitleLabel(String text) {
        Label label = new Label(text);

        label.setStyle(
                theme.getUiStyle().textStyle(config.getTitleFontSize())
                        + BOLD_STYLE
        );

        return label;
    }

    public Label createLargeTitleLabel(String text) {
        Label label = new Label(text);

        label.setStyle(
                theme.getUiStyle().textStyle(config.getLargeTitleFontSize())
                        + BOLD_STYLE
        );

        return label;
    }

    public TextField createTextField(String text) {
        TextField textField = new TextField(text);

        textField.setMaxWidth(config.getTextFieldWidth());
        textField.setStyle(theme.getUiStyle().textFieldStyle(config.getTextFontSize()));

        return textField;
    }
}