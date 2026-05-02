package ru.nsu.ccfit.kombarov.tetris.view.theme;

import ru.nsu.ccfit.kombarov.tetris.view.theme.background.BackgroundRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.cell.CellRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;

public class Theme {

    private ColorPalette palette;
    private CellRenderer cellRenderer;
    private BackgroundRenderer background;

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
}