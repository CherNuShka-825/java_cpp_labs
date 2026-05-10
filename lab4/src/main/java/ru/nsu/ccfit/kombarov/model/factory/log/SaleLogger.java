package ru.nsu.ccfit.kombarov.model.factory.log;

import ru.nsu.ccfit.kombarov.model.factory.entity.Auto;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public final class SaleLogger implements Closeable {
    private final boolean enabled;
    private final PrintWriter writer;

    public SaleLogger(Path path, boolean enabled) throws IOException {
        this.enabled = enabled;

        if (enabled) {
            this.writer = new PrintWriter(Files.newBufferedWriter(
                    path,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            ));
        } else {
            this.writer = null;
        }
    }

    public synchronized void logSale(int dealerId, Auto auto) {
        if (!enabled) {
            return;
        }

        writer.printf(
                "%s: Dealer %d: Auto %d (Body: %d, Motor: %d, Accessory: %d)%n",
                LocalDateTime.now(),
                dealerId,
                auto.getId(),
                auto.getBody().getId(),
                auto.getMotor().getId(),
                auto.getAccessory().getId()
        );
        writer.flush();
    }

    @Override
    public synchronized void close() {
        if (writer != null) {
            writer.close();
        }
    }
}