package ru.nsu.ccfit.kombarov.model.factory.statistics;

import java.util.concurrent.atomic.AtomicLong;

public final class FactoryStatistics {

    private final AtomicLong producedBodies = new AtomicLong();
    private final AtomicLong producedMotors = new AtomicLong();
    private final AtomicLong producedAccessories = new AtomicLong();
    private final AtomicLong producedAutos = new AtomicLong();
    private final AtomicLong soldAutos = new AtomicLong();

    public void incrementProducedBodies() {
        producedBodies.incrementAndGet();
    }

    public void incrementProducedMotors() {
        producedMotors.incrementAndGet();
    }

    public void incrementProducedAccessories() {
        producedAccessories.incrementAndGet();
    }

    public void incrementProducedAutos() {
        producedAutos.incrementAndGet();
    }

    public void incrementSoldAutos() {
        soldAutos.incrementAndGet();
    }

    public long getProducedAutos() {
        return producedAutos.get();
    }

    public long getSoldAutos() {
        return soldAutos.get();
    }
}