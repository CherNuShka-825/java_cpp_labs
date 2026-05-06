package ru.nsu.ccfit.kombarov.tetris.view.config;

public class ViewConfig {

    private static final int PREVIEW_SIZE_IN_CELLS = 6;

    private final int baseSize;
    private final int boardWidth;
    private final int boardHeight;

    public ViewConfig(int baseSize, int boardWidth, int boardHeight) {
        this.baseSize = baseSize;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public int getCellSize() {
        return baseSize;
    }

    public int getPreviewCellSize() {
        return baseSize * 4 / 5;
    }

    public int getPreviewSizeInCells() {
        return PREVIEW_SIZE_IN_CELLS;
    }

    public int getPreviewCanvasSize() {
        return getPreviewCellSize() * PREVIEW_SIZE_IN_CELLS;
    }

    public int getWindowPadding() {
        return baseSize / 2;
    }

    public int getGap() {
        return baseSize / 2;
    }

    public int getSidePanelWidth() {
        return baseSize * 5;
    }

    public int getSidePanelPadding() {
        return baseSize / 3;
    }

    public int getSidePanelSpacing() {
        return baseSize / 3;
    }

    public int getScreenSpacing() {
        return baseSize / 3;
    }

    public int getButtonWidth() {
        return baseSize * 4;
    }

    public int getButtonHeight() {
        return baseSize * 3 / 4;
    }

    public int getTextFieldWidth() {
        return getButtonWidth();
    }

    public int getTitleFontSize() {
        return Math.max(18, baseSize / 2);
    }

    public int getLargeTitleFontSize() {
        return Math.max(28, baseSize * 4 / 2);
    }

    public int getTextFontSize() {
        return Math.max(14, baseSize / 3);
    }

    public int getButtonFontSize() {
        return Math.max(14, baseSize / 2);
    }

    public int getWindowWidth() {
        return boardWidth * getCellSize()
                + getSidePanelWidth()
                + getGap()
                + getWindowPadding() * 2;
    }

    public int getWindowHeight() {
        return boardHeight * getCellSize()
                + getWindowPadding() * 2;
    }
}