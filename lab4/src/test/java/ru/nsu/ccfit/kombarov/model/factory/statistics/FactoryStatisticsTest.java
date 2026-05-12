package ru.nsu.ccfit.kombarov.model.factory.statistics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactoryStatisticsTest {

    @Test
    void statisticsShouldStartWithZeroValues() {
        FactoryStatistics statistics = new FactoryStatistics();

        assertEquals(0, statistics.getProducedAutos());
        assertEquals(0, statistics.getSoldAutos());
    }

    @Test
    void incrementsShouldUpdateAvailableCounters() {
        FactoryStatistics statistics = new FactoryStatistics();

        statistics.incrementProducedBodies();
        statistics.incrementProducedMotors();
        statistics.incrementProducedAccessories();

        statistics.incrementProducedAutos();
        statistics.incrementProducedAutos();

        statistics.incrementSoldAutos();

        assertEquals(2, statistics.getProducedAutos());
        assertEquals(1, statistics.getSoldAutos());
    }
}