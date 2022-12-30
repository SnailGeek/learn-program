package com.geek.design.singleton;

import java.util.concurrent.atomic.AtomicLong;

public enum IdGeneratorEnum {
    INSTANCE;

    private AtomicLong id = new AtomicLong(0);

    public long getId() {
        return id.incrementAndGet();
    }
}
