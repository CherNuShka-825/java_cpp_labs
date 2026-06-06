package ru.nsu.ccfit.kombarov.tetris.view.theme.themes;

import ru.nsu.ccfit.kombarov.tetris.view.theme.backgroundRender.SolidBackgroundRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.backgroundRender.StarfallBackgroundRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.cellRender.DefaultCellRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.cellRender.PulsingCellRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.DefaultPalette;
import ru.nsu.ccfit.kombarov.tetris.view.theme.ui.DarkUiStyle;
import ru.nsu.ccfit.kombarov.tetris.view.theme.ui.GlamourUiStyle;

public class DefaultTheme extends Theme {

    public DefaultTheme() {
        ColorPalette palette = new DefaultPalette();

        setPalette(palette);
        setCellRenderer(new PulsingCellRenderer(palette));
        setBackground(new StarfallBackgroundRenderer(palette));
        setUiStyle(new DarkUiStyle());
    }
}