package ru.nsu.ccfit.kombarov.tetris.view.theme.ui;

public class GlamourUiStyle implements UiStyle {

    @Override
    public String mainBackgroundStyle() {
        return """
                -fx-background-color: linear-gradient(to bottom, #2b0f24, #120812);
                """;
    }

    @Override
    public String panelStyle() {
        return """
                -fx-background-color: #3a1832;
                -fx-background-radius: 16;
                -fx-border-color: #ff79c6;
                -fx-border-radius: 16;
                -fx-border-width: 2;
                """;
    }

    @Override
    public String buttonStyle(int fontSize) {
        return """
                -fx-background-color: linear-gradient(to bottom, #ff79c6, #c91875);
                -fx-text-fill: white;
                -fx-font-size: %dpx;
                -fx-font-weight: bold;
                -fx-background-radius: 14;
                -fx-border-color: #ffd1ec;
                -fx-border-radius: 14;
                -fx-border-width: 1;
                """.formatted(fontSize);
    }

    @Override
    public String textStyle(int fontSize) {
        return """
                -fx-text-fill: #ffe6f5;
                -fx-font-size: %dpx;
                """.formatted(fontSize);
    }

    @Override
    public String textFieldStyle(int fontSize) {
        return """
                -fx-background-color: #4a1f40;
                -fx-text-fill: #ffe6f5;
                -fx-prompt-text-fill: #d98abe;
                -fx-font-size: %dpx;
                -fx-background-radius: 12;
                -fx-border-color: #ff79c6;
                -fx-border-radius: 12;
                -fx-border-width: 2;
                """.formatted(fontSize);
    }
}