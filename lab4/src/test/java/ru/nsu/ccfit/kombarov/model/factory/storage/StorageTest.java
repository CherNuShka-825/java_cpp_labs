package ru.nsu.ccfit.kombarov.model.factory.storage;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void constructorShouldRejectNonPositiveCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new Storage<>(0));
        assertThrows(IllegalArgumentException.class, () -> new Storage<>(-1));
    }

    @Test
    void putAndTakeShouldWorkInFifoOrder() throws InterruptedException {
        Storage<String> storage = new Storage<>(2);

        storage.put("first");
        storage.put("second");

        assertEquals(2, storage.getSize());
        assertEquals(2, storage.getCapacity());

        assertEquals("first", storage.take());
        assertEquals("second", storage.take());

        assertEquals(0, storage.getSize());
    }

    @Test
    void takeShouldWaitUntilItemIsPut() throws Exception {
        Storage<String> storage = new Storage<>(1);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            Future<String> future = executor.submit(storage::take);

            Thread.sleep(50);

            assertFalse(future.isDone());

            storage.put("item");

            assertEquals("item", future.get(1, TimeUnit.SECONDS));
            assertEquals(0, storage.getSize());
        } finally {
            executor.shutdownNow();
        }
    }

    @Test
    void putShouldWaitUntilFreeSpaceAppears() throws Exception {
        Storage<String> storage = new Storage<>(1);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        storage.put("first");

        try {
            Future<?> future = executor.submit(() -> {
                storage.put("second");
                return null;
            });

            Thread.sleep(50);

            assertFalse(future.isDone());
            assertEquals(1, storage.getSize());
            assertEquals("first", storage.take());

            future.get(1, TimeUnit.SECONDS);

            assertEquals(1, storage.getSize());
            assertEquals("second", storage.take());
        } finally {
            executor.shutdownNow();
        }
    }

    @Test
    void waitForFreeSpaceShouldWaitUntilStorageHasFreeSpace() throws Exception {
        Storage<String> storage = new Storage<>(1);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        storage.put("item");

        try {
            Future<?> future = executor.submit(() -> {
                storage.waitForFreeSpace();
                return null;
            });

            Thread.sleep(50);

            assertFalse(future.isDone());

            assertEquals("item", storage.take());

            future.get(1, TimeUnit.SECONDS);
            assertEquals(0, storage.getSize());
        } finally {
            executor.shutdownNow();
        }
    }
}