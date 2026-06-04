package ru.nsu.ccfit.kombarov.model.factory.config;

import java.util.concurrent.atomic.AtomicInteger;

public final class FactoryDelays {

    private static final AtomicInteger bodySupplierDelayMs = new AtomicInteger();
    private static final AtomicInteger motorSupplierDelayMs = new AtomicInteger();
    private static final AtomicInteger accessorySupplierDelayMs = new AtomicInteger();
    private static final AtomicInteger dealerDelayMs = new AtomicInteger();
    private static final AtomicInteger workerDelayMs = new AtomicInteger();

    public static void init(
            int bodySupplierDelayMs,
            int motorSupplierDelayMs,
            int accessorySupplierDelayMs,
            int dealerDelayMs,
            int workerDelayMs
    ) {
        FactoryDelays.bodySupplierDelayMs.set(
                requireNonNegative(bodySupplierDelayMs, "bodySupplierDelayMs")
        );

        FactoryDelays.motorSupplierDelayMs.set(
                requireNonNegative(motorSupplierDelayMs, "motorSupplierDelayMs")
        );

        FactoryDelays.accessorySupplierDelayMs.set(
                requireNonNegative(accessorySupplierDelayMs, "accessorySupplierDelayMs")
        );

        FactoryDelays.dealerDelayMs.set(
                requireNonNegative(dealerDelayMs, "dealerDelayMs")
        );

        FactoryDelays.workerDelayMs.set(
                requireNonNegative(workerDelayMs, "workerDelayMs")
        );
    }

    public static int getBodySupplierDelayMs() {
        return bodySupplierDelayMs.get();
    }

    public static void setBodySupplierDelayMs(int delayMs) {
        bodySupplierDelayMs.set(
                requireNonNegative(delayMs, "bodySupplierDelayMs")
        );
    }

    public static int getMotorSupplierDelayMs() {
        return motorSupplierDelayMs.get();
    }

    public static void setMotorSupplierDelayMs(int delayMs) {
        motorSupplierDelayMs.set(
                requireNonNegative(delayMs, "motorSupplierDelayMs")
        );
    }

    public static int getAccessorySupplierDelayMs() {
        return accessorySupplierDelayMs.get();
    }

    public static void setAccessorySupplierDelayMs(int delayMs) {
        accessorySupplierDelayMs.set(
                requireNonNegative(delayMs, "accessorySupplierDelayMs")
        );
    }

    public static int getDealerDelayMs() {
        return dealerDelayMs.get();
    }

    public static void setDealerDelayMs(int delayMs) {
        dealerDelayMs.set(
                requireNonNegative(delayMs, "dealerDelayMs")
        );
    }

    public static int getWorkerDelayMs() {
        return workerDelayMs.get();
    }

    public static void setWorkerDelayMs(int delayMs) {
        workerDelayMs.set(
                requireNonNegative(delayMs, "workerDelayMs")
        );
    }

    private static int requireNonNegative(int value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(
                    name + " must be non-negative"
            );
        }

        return value;
    }
}