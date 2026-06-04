package ru.nsu.ccfit.kombarov.model.factory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.kombarov.model.factory.config.FactoryConfig;
import ru.nsu.ccfit.kombarov.model.factory.config.FactoryDelays;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTest {

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(Path.of("sales.log"));
    }

    @Test
    void constructorShouldInitializeFactoryComponents() throws IOException {
        FactoryConfig config = createConfig();

        Factory factory = new Factory(config);

        assertNotNull(factory.getStatistics());
        assertNotNull(factory.getBodyStorage());
        assertNotNull(factory.getMotorStorage());
        assertNotNull(factory.getAccessoryStorage());
        assertNotNull(factory.getAutoStorage());
        assertNotNull(factory.getThreadPool());

        assertEquals(2, factory.getBodyStorage().getCapacity());
        assertEquals(3, factory.getMotorStorage().getCapacity());
        assertEquals(4, factory.getAccessoryStorage().getCapacity());
        assertEquals(5, factory.getAutoStorage().getCapacity());

        assertEquals(0, factory.getBodyStorage().getSize());
        assertEquals(0, factory.getMotorStorage().getSize());
        assertEquals(0, factory.getAccessoryStorage().getSize());
        assertEquals(0, factory.getAutoStorage().getSize());

        assertEquals(1000, FactoryDelays.getBodySupplierDelayMs());
        assertEquals(1100, FactoryDelays.getMotorSupplierDelayMs());
        assertEquals(1200, FactoryDelays.getAccessorySupplierDelayMs());
        assertEquals(1300, FactoryDelays.getDealerDelayMs());
        assertEquals(1400, FactoryDelays.getWorkerDelayMs());

        assertEquals(0, factory.getStatistics().getSoldAutos());
        assertEquals(0, factory.getThreadPool().getQueueSize());

        factory.shutdown();
    }

    @Test
    void startAndShutdownShouldNotThrow() {
        assertTimeout(Duration.ofSeconds(2), () -> {
            Factory factory = new Factory(createConfig());

            factory.start();
            Thread.sleep(50);
            factory.shutdown();

            Thread.sleep(50);
        });
    }

    private FactoryConfig createConfig() {
        return new FactoryConfig(
                2,
                3,
                4,
                5,

                1,
                1,
                1,

                1,
                1,

                1000,
                1100,
                1200,
                1300,
                1400,

                false
        );
    }
}