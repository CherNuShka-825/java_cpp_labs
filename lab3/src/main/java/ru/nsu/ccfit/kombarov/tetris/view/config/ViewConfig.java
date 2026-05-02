package ru.nsu.ccfit.kombarov.tetris.view.config;

public class ViewConfig {
    private final int cellSize;
    private final int previewCellSize;

    public ViewConfig(int cellSize, int previewCellSize) {
        this.cellSize = cellSize;
        this.previewCellSize = previewCellSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getPreviewCellSize() {
        return previewCellSize;
    }

    public int getWindowPadding() {
        return cellSize / 2;
    }

    public int getGap() {
        return cellSize / 2;
    }

    public int getSidePanelWidth() {
        return cellSize * 5;
    }

    public int getSidePanelPadding() {
        return cellSize / 3;
    }

    public int getSidePanelSpacing() {
        return cellSize / 3;
    }

    public int getTitleFontSize() {
        return Math.max(14, cellSize / 2);
    }

    public int getTextFontSize() {
        return Math.max(11, cellSize / 3);
    }

    public int getCellGap() {
        return Math.max(1, getCellSize() / 20);
    }
}