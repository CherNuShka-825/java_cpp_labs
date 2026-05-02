package ru.nsu.ccfit.kombarov.tetris.view.theme.themes;

import ru.nsu.ccfit.kombarov.tetris.view.theme.Theme;
import ru.nsu.ccfit.kombarov.tetris.view.theme.background.SolidBackground;
import ru.nsu.ccfit.kombarov.tetris.view.theme.cell.DefaultCellRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.DefaultPalette;

public class DefaultTheme extends Theme {

    public DefaultTheme() {
        ColorPalette palette = new DefaultPalette();

        setPalette(palette);
        setCellRenderer(new DefaultCellRenderer(palette));
        setBackground(new SolidBackground(palette));
    }
}