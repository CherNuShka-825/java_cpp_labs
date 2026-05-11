package ru.nsu.ccfit.kombarov.model.factory.config;

import java.util.concurrent.atomic.AtomicInteger;

public final class FactoryDelays {

    private final AtomicInteger bodySupplierDelayMs;
    private final AtomicInteger motorSupplierDelayMs;
    private final AtomicInteger accessorySupplierDelayMs;
    private final AtomicInteger dealerDelayMs;
    private final AtomicInteger workerDelayMs;

    public FactoryDelays(
            int bodySupplierDelayMs,
            int motorSupplierDelayMs,
            int accessorySupplierDelayMs,
            int dealerDelayMs,
            int workerDelayMs
    ) {
        this.bodySupplierDelayMs = new AtomicInteger(requirePositive(bodySupplierDelayMs, "bodySupplierDelayMs"));
        this.motorSupplierDelayMs = new AtomicInteger(requirePositive(motorSupplierDelayMs, "motorSupplierDelayMs"));
        this.accessorySupplierDelayMs = new AtomicInteger(requirePositive(accessorySupplierDelayMs, "accessorySupplierDelayMs"));
        this.dealerDelayMs = new AtomicInteger(requirePositive(dealerDelayMs, "dealerDelayMs"));
        this.workerDelayMs = new AtomicInteger(requirePositive(workerDelayMs, "workerDelayMs"));
    }

    public int getBodySupplierDelayMs() {
        return bodySupplierDelayMs.get();
    }

    public void setBodySupplierDelayMs(int delayMs) {
        bodySupplierDelayMs.set(requirePositive(delayMs, "bodySupplierDelayMs"));
    }

    public int getMotorSupplierDelayMs() {
        return motorSupplierDelayMs.get();
    }

    public void setMotorSupplierDelayMs(int delayMs) {
        motorSupplierDelayMs.set(requirePositive(delayMs, "motorSupplierDelayMs"));
    }

    public int getAccessorySupplierDelayMs() {
        return accessorySupplierDelayMs.get();
    }

    public void setAccessorySupplierDelayMs(int delayMs) {
        accessorySupplierDelayMs.set(requirePositive(delayMs, "accessorySupplierDelayMs"));
    }

    public int getDealerDelayMs() {
        return dealerDelayMs.get();
    }

    public void setDealerDelayMs(int delayMs) {
        dealerDelayMs.set(requirePositive(delayMs, "dealerDelayMs"));
    }

    public int getWorkerDelayMs() {
        return workerDelayMs.get();
    }

    public void setWorkerDelayMs(int delayMs) {
        workerDelayMs.set(requirePositive(delayMs, "workerDelayMs"));
    }

    private static int requirePositive(int value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be positive");
        }

        return value;
    }
}