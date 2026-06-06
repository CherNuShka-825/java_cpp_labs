package ru.nsu.ccfit.kombarov.tetris.view.theme.themes;

import ru.nsu.ccfit.kombarov.tetris.view.theme.backgroundRender.SolidBackgroundRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.cellRender.PulsingCellRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.DefaultPalette;

public class PulsingTheme extends Theme {

    public PulsingTheme() {
        ColorPalette palette = new DefaultPalette();

        setPalette(palette);
        setCellRenderer(new PulsingCellRenderer(palette));
        setBackground(new SolidBackgroundRenderer(palette));
    }
}