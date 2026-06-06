package ru.nsu.ccfit.kombarov.tetris.view.theme.themes;

import ru.nsu.ccfit.kombarov.tetris.view.theme.backgroundRender.BackgroundRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.cellRender.CellRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;
import ru.nsu.ccfit.kombarov.tetris.view.theme.ui.UiStyle;

public class Theme {

    private ColorPalette palette;
    private CellRenderer cellRenderer;
    private BackgroundRenderer background;
    private UiStyle uiStyle;

    public ColorPalette getPalette() {
        return palette;
    }

    public void setPalette(ColorPalette palette) {
        this.palette = palette;
    }

    public CellRenderer getCellRenderer() {
        return cellRenderer;
    }

    public void setCellRenderer(CellRenderer cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    public BackgroundRenderer getBackground() {
        return background;
    }

    public void setBackground(BackgroundRenderer background) {
        this.background = background;
    }

    public UiStyle getUiStyle() {
        return uiStyle;
    }

    public void setUiStyle(UiStyle uiStyle) {
        this.uiStyle = uiStyle;
    }
}