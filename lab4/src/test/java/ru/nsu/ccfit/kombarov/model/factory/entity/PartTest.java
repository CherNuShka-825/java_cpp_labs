package ru.nsu.ccfit.kombarov.model.factory.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {

    @Test
    void constructorShouldAssignUniqueIncreasingIds() {
        TestPart first = new TestPart();
        TestPart second = new TestPart();

        assertNotEquals(first.getId(), second.getId());
        assertTrue(second.getId() > first.getId());
    }

    private static final class TestPart extends Part {
    }
}