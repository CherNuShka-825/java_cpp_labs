package ru.nsu.ccfit.kombarov.model.factory.entity;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Part {

    private static final AtomicLong NEXT_ID = new AtomicLong(1);
    private final long id;

    protected Part() {
        this.id = NEXT_ID.getAndIncrement();
    }

    public long getId() {
        return id;
    }
}