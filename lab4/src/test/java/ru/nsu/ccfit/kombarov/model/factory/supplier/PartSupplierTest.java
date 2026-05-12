package ru.nsu.ccfit.kombarov.model.factory.supplier;

import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.kombarov.model.factory.storage.Storage;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class PartSupplierTest {

    @Test
    void runShouldProduceItemAndPutItToStorage() throws InterruptedException {
        Storage<String> storage = new Storage<>(1);

        AtomicInteger producedCount = new AtomicInteger();
        AtomicInteger onProducedCount = new AtomicInteger();

        PartSupplier<String> supplier = new PartSupplier<>(
                storage,
                () -> "item-" + producedCount.incrementAndGet(),
                () -> 1,
                onProducedCount::incrementAndGet
        );

        Thread thread = new Thread(supplier);

        thread.start();

        assertTimeout(Duration.ofSeconds(1), () -> {
            while (storage.getSize() == 0) {
                Thread.sleep(1);
            }
        });

        thread.interrupt();
        thread.join(1000);

        assertFalse(thread.isAlive());
        assertEquals(1, storage.getSize());
        assertEquals(1, producedCount.get());
        assertEquals(1, onProducedCount.get());

        assertEquals("item-1", storage.take());
    }

    @Test
    void runShouldStopWhenInterruptedDuringSleep() throws InterruptedException {
        Storage<String> storage = new Storage<>(1);

        CountDownLatch sleepStarted = new CountDownLatch(1);
        AtomicInteger producedCount = new AtomicInteger();
        AtomicInteger onProducedCount = new AtomicInteger();

        PartSupplier<String> supplier = new PartSupplier<>(
                storage,
                () -> {
                    producedCount.incrementAndGet();
                    return "item";
                },
                () -> {
                    sleepStarted.countDown();
                    return 10_000;
                },
                onProducedCount::incrementAndGet
        );

        Thread thread = new Thread(supplier);

        thread.start();

        assertTrue(sleepStarted.await(1, TimeUnit.SECONDS));

        thread.interrupt();
        thread.join(1000);

        assertFalse(thread.isAlive());
        assertEquals(0, storage.getSize());
        assertEquals(0, producedCount.get());
        assertEquals(0, onProducedCount.get());
    }
}