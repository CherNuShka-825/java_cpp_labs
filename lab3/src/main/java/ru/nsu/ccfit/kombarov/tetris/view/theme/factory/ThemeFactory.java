package ru.nsu.ccfit.kombarov.tetris.view.theme.factory;

import ru.nsu.ccfit.kombarov.tetris.exceptions.FactoryException;
import ru.nsu.ccfit.kombarov.tetris.view.theme.Theme;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class ThemeFactory {

    private final Properties config = new Properties();

    public ThemeFactory(String configFile) throws FactoryException {
        try (InputStream is = ThemeFactory.class.getClassLoader().getResourceAsStream(configFile)) {
            if (is == null) {
                throw new FactoryException("Config file not found: " + configFile);
            }
            config.load(is);
        } catch (IOException e) {
            throw new FactoryException("Failed to load config", e);
        }
    }

    public Theme create(String name) throws FactoryException {
        String className = config.getProperty(name);

        if (className == null) {
            throw new FactoryException("Unknown theme: " + name);
        }

        try {
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.getDeclaredConstructor().newInstance();
            return (Theme) obj;

        } catch (ClassNotFoundException e) {
            throw new FactoryException("Class not found: " + className, e);

        } catch (ReflectiveOperationException e) {
            throw new FactoryException("Cannot create theme: " + className, e);

        } catch (ClassCastException e) {
            throw new FactoryException("Class is not Theme: " + className, e);
        }
    }

    public Set<String> getAvailableThemes() {
        return config.stringPropertyNames();
    }
}