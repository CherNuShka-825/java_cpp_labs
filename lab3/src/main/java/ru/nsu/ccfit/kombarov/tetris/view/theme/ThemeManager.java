package ru.nsu.ccfit.kombarov.tetris.view.theme;

import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.view.theme.factory.ThemeFactory;
import ru.nsu.ccfit.kombarov.tetris.view.theme.themes.Theme;

import java.util.Set;

public class ThemeManager {

    private final ThemeFactory factory;
    private Theme currentTheme;

    public ThemeManager(ThemeFactory factory, String startTheme) {
        this.factory = factory;

        try {
            this.currentTheme = factory.create(startTheme);
        } catch (FactoryException e) {
            throw new IllegalStateException("Cannot load theme: " + startTheme, e);
        }
    }

    public void setTheme(String name) {
        try {
            currentTheme = factory.create(name);
        } catch (FactoryException e) {
            throw new IllegalStateException("Cannot switch theme: " + name, e);
        }
    }

    public Theme getCurrentTheme() {
        return currentTheme;
    }

    public Set<String> getAvailableThemes() {
        return factory.getAvailableThemes();
    }
}