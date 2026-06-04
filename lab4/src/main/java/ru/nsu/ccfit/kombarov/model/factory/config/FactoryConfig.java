package ru.nsu.ccfit.kombarov.model.factory.config;

public final class FactoryConfig {

    private final int bodyStorageSize;
    private final int motorStorageSize;
    private final int accessoryStorageSize;
    private final int autoStorageSize;

    private final int bodySuppliers;
    private final int motorSuppliers;
    private final int accessorySuppliers;

    private final int workers;
    private final int dealers;

    private final int bodySupplierDelayMs;
    private final int motorSupplierDelayMs;
    private final int accessorySupplierDelayMs;
    private final int dealerDelayMs;
    private final int workerDelayMs;

    private final boolean logSale;

    public FactoryConfig(
            int bodyStorageSize,
            int motorStorageSize,
            int accessoryStorageSize,
            int autoStorageSize,

            int bodySuppliers,
            int motorSuppliers,
            int accessorySuppliers,

            int workers,
            int dealers,

            int bodySupplierDelayMs,
            int motorSupplierDelayMs,
            int accessorySupplierDelayMs,
            int dealerDelayMs,
            int workerDelayMs,

            boolean logSale
    ) {
        this.bodyStorageSize = bodyStorageSize;
        this.motorStorageSize = motorStorageSize;
        this.accessoryStorageSize = accessoryStorageSize;
        this.autoStorageSize = autoStorageSize;

        this.bodySuppliers = bodySuppliers;
        this.motorSuppliers = motorSuppliers;
        this.accessorySuppliers = accessorySuppliers;

        this.workers = workers;
        this.dealers = dealers;

        this.bodySupplierDelayMs = bodySupplierDelayMs;
        this.motorSupplierDelayMs = motorSupplierDelayMs;
        this.accessorySupplierDelayMs = accessorySupplierDelayMs;
        this.dealerDelayMs = dealerDelayMs;
        this.workerDelayMs = workerDelayMs;

        this.logSale = logSale;
    }

    public int getBodyStorageSize() {
        return bodyStorageSize;
    }

    public int getMotorStorageSize() {
        return motorStorageSize;
    }

    public int getAccessoryStorageSize() {
        return accessoryStorageSize;
    }

    public int getAutoStorageSize() {
        return autoStorageSize;
    }

    public int getBodySuppliers() {
        return bodySuppliers;
    }

    public int getMotorSuppliers() {
        return motorSuppliers;
    }

    public int getAccessorySuppliers() {
        return accessorySuppliers;
    }

    public int getWorkers() {
        return workers;
    }

    public int getDealers() {
        return dealers;
    }

    public int getBodySupplierDelayMs() {
        return bodySupplierDelayMs;
    }

    public int getMotorSupplierDelayMs() {
        return motorSupplierDelayMs;
    }

    public int getAccessorySupplierDelayMs() {
        return accessorySupplierDelayMs;
    }

    public int getDealerDelayMs() {
        return dealerDelayMs;
    }

    public int getWorkerDelayMs() {
        return workerDelayMs;
    }

    public boolean isLogSale() {
        return logSale;
    }
}