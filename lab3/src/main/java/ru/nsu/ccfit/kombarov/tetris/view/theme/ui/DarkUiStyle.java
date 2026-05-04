package ru.nsu.ccfit.kombarov.tetris.view.theme.ui;

public class DarkUiStyle implements UiStyle {

    @Override
    public String mainBackgroundStyle() {
        return """
                -fx-background-color: #111118;
                """;
    }

    @Override
    public String panelStyle() {
        return """
                -fx-background-color: #1b1b24;
                -fx-background-radius: 12;
                """;
    }

    @Override
    public String buttonStyle(int fontSize) {
        return """
                -fx-background-color: #252535;
                -fx-text-fill: white;
                -fx-font-size: %dpx;
                -fx-background-radius: 10;
                """.formatted(fontSize);
    }

    @Override
    public String textStyle(int fontSize) {
        return """
                -fx-text-fill: white;
                -fx-font-size: %dpx;
                """.formatted(fontSize);
    }

    @Override
    public String textFieldStyle(int fontSize) {
        return """
                -fx-background-color: #252535;
                -fx-text-fill: white;
                -fx-font-size: %dpx;
                -fx-background-radius: 8;
                -fx-border-color: #3a3a50;
                -fx-border-radius: 8;
                """.formatted(fontSize);
    }
}