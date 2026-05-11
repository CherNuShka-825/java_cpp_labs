package ru.nsu.ccfit.kombarov.model.factory.config;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public final class FactoryConfigLoader {

    public FactoryConfig load(String configFile) throws IOException {
        Properties properties = new Properties();

        try (InputStream is = FactoryConfig.class.getClassLoader().getResourceAsStream(configFile)) {
            if (is == null) {
                throw new IOException("Config file not found: " + configFile);
            }
            properties.load(is);
        }

        return new FactoryConfig(
                getInt(properties, "BodyStorageSize"),
                getInt(properties, "MotorStorageSize"),
                getInt(properties, "AccessoryStorageSize"),
                getInt(properties, "AutoStorageSize"),

                getInt(properties, "BodySuppliers"),
                getInt(properties, "MotorSuppliers"),
                getInt(properties, "AccessorySuppliers"),

                getInt(properties, "Workers"),
                getInt(properties, "Dealers"),

                getInt(properties, "BodySupplierDelay"),
                getInt(properties, "MotorSupplierDelay"),
                getInt(properties, "AccessorySupplierDelay"),
                getInt(properties, "DealerDelay"),
                getInt(properties, "WorkerDelay"),

                getBoolean(properties, "LogSale")
        );
    }

    private int getInt(Properties properties, String key) {
        String value = properties.getProperty(key);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Missing property: " + key
            );
        }

        return Integer.parseInt(value);
    }

    private boolean getBoolean(Properties properties, String key) {
        String value = properties.getProperty(key);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Missing property: " + key
            );
        }

        return Boolean.parseBoolean(value);
    }
}