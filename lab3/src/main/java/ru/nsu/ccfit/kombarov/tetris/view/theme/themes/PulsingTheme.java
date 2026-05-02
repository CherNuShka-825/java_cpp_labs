package ru.nsu.ccfit.kombarov.tetris.view.theme.themes;

import ru.nsu.ccfit.kombarov.tetris.view.theme.Theme;
import ru.nsu.ccfit.kombarov.tetris.view.theme.background.SolidBackground;
import ru.nsu.ccfit.kombarov.tetris.view.theme.cell.PulsingCellRenderer;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.ColorPalette;
import ru.nsu.ccfit.kombarov.tetris.view.theme.palette.DefaultPalette;

public class PulsingTheme extends Theme {

    public PulsingTheme() {
        ColorPalette palette = new DefaultPalette();

        setPalette(palette);
        setCellRenderer(new PulsingCellRenderer(palette));
        setBackground(new SolidBackground(palette));
    }
}