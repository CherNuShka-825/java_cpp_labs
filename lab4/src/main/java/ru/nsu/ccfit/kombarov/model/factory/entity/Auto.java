package ru.nsu.ccfit.kombarov.model.factory.entity;

import java.util.concurrent.atomic.AtomicLong;

public final class Auto {

    private static final AtomicLong NEXT_ID = new AtomicLong(1);

    private final long id; //todo почему не volatile
    private final Body body;
    private final Motor motor;
    private final Accessory accessory;

    public Auto(Body body, Motor motor, Accessory accessory) {
        this.id = NEXT_ID.getAndIncrement();
        this.body = body;
        this.motor = motor;
        this.accessory = accessory;
    }

    public long getId() {
        return id;
    }

    public Body getBody() {
        return body;
    }

    public Motor getMotor() {
        return motor;
    }

    public Accessory getAccessory() {
        return accessory;
    }
}