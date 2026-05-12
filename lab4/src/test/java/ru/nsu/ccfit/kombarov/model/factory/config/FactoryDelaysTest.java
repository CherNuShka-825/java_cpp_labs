package ru.nsu.ccfit.kombarov.model.factory.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactoryDelaysTest {

    @BeforeEach
    void setUp() {
        FactoryDelays.init(0, 0, 0, 0, 0);
    }

    @Test
    void initShouldSetAllDelays() {
        FactoryDelays.init(100, 200, 300, 400, 500);

        assertEquals(100, FactoryDelays.getBodySupplierDelayMs());
        assertEquals(200, FactoryDelays.getMotorSupplierDelayMs());
        assertEquals(300, FactoryDelays.getAccessorySupplierDelayMs());
        assertEquals(400, FactoryDelays.getDealerDelayMs());
        assertEquals(500, FactoryDelays.getWorkerDelayMs());
    }

    @Test
    void settersShouldUpdateAllDelays() {
        FactoryDelays.setBodySupplierDelayMs(10);
        FactoryDelays.setMotorSupplierDelayMs(20);
        FactoryDelays.setAccessorySupplierDelayMs(30);
        FactoryDelays.setDealerDelayMs(40);
        FactoryDelays.setWorkerDelayMs(50);

        assertEquals(10, FactoryDelays.getBodySupplierDelayMs());
        assertEquals(20, FactoryDelays.getMotorSupplierDelayMs());
        assertEquals(30, FactoryDelays.getAccessorySupplierDelayMs());
        assertEquals(40, FactoryDelays.getDealerDelayMs());
        assertEquals(50, FactoryDelays.getWorkerDelayMs());
    }

    @Test
    void initShouldRejectNegativeBodySupplierDelay() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FactoryDelays.init(-1, 0, 0, 0, 0)
        );

        assertEquals("bodySupplierDelayMs must be non-negative", exception.getMessage());
    }

    @Test
    void initShouldRejectNegativeMotorSupplierDelay() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FactoryDelays.init(0, -1, 0, 0, 0)
        );

        assertEquals("motorSupplierDelayMs must be non-negative", exception.getMessage());
    }

    @Test
    void initShouldRejectNegativeAccessorySupplierDelay() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FactoryDelays.init(0, 0, -1, 0, 0)
        );

        assertEquals("accessorySupplierDelayMs must be non-negative", exception.getMessage());
    }

    @Test
    void initShouldRejectNegativeDealerDelay() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FactoryDelays.init(0, 0, 0, -1, 0)
        );

        assertEquals("dealerDelayMs must be non-negative", exception.getMessage());
    }

    @Test
    void initShouldRejectNegativeWorkerDelay() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FactoryDelays.init(0, 0, 0, 0, -1)
        );

        assertEquals("workerDelayMs must be non-negative", exception.getMessage());
    }

    @Test
    void settersShouldRejectNegativeValues() {
        assertThrows(IllegalArgumentException.class, () -> FactoryDelays.setBodySupplierDelayMs(-1));
        assertThrows(IllegalArgumentException.class, () -> FactoryDelays.setMotorSupplierDelayMs(-1));
        assertThrows(IllegalArgumentException.class, () -> FactoryDelays.setAccessorySupplierDelayMs(-1));
        assertThrows(IllegalArgumentException.class, () -> FactoryDelays.setDealerDelayMs(-1));
        assertThrows(IllegalArgumentException.class, () -> FactoryDelays.setWorkerDelayMs(-1));
    }
}