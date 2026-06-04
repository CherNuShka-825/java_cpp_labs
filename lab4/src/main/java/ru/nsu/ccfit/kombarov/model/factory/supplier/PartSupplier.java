package ru.nsu.ccfit.kombarov.model.factory.supplier;

import ru.nsu.ccfit.kombarov.model.factory.storage.Storage;

public final class PartSupplier<T> implements Runnable {

    private final Storage<T> storage;
    private final Producer<T> producer;
    private final DelayProvider delayProvider;
    private final Runnable onProduced;

    public PartSupplier(
            Storage<T> storage,
            Producer<T> producer,
            DelayProvider delayProvider,
            Runnable onProduced
    ) {
        this.storage = storage;
        this.producer = producer;
        this.delayProvider = delayProvider;
        this.onProduced = onProduced;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(delayProvider.getDelayMs());

                storage.waitForFreeSpace();

                T item = producer.produce();
                storage.put(item);

                onProduced.run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @FunctionalInterface
    public interface Producer<T> {
        T produce();
    }

    @FunctionalInterface
    public interface DelayProvider {
        int getDelayMs();
    }
}