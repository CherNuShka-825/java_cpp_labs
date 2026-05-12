package ru.nsu.ccfit.kombarov.model.factory.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutoTest {

    @Test
    void constructorShouldAssignUniqueIncreasingIds() {
        Auto first = new Auto(new Body(), new Motor(), new Accessory());
        Auto second = new Auto(new Body(), new Motor(), new Accessory());

        assertNotEquals(first.getId(), second.getId());
        assertTrue(second.getId() > first.getId());
    }

    @Test
    void constructorShouldSaveParts() {
        Body body = new Body();
        Motor motor = new Motor();
        Accessory accessory = new Accessory();

        Auto auto = new Auto(body, motor, accessory);

        assertSame(body, auto.getBody());
        assertSame(motor, auto.getMotor());
        assertSame(accessory, auto.getAccessory());
    }
}