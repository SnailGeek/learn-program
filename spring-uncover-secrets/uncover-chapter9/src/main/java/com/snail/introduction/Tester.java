package com.snail.introduction;

public class Tester implements ITester {
    private boolean busyAsTester;

    @Override
    public boolean isBusyAsTester() {
        return this.busyAsTester;
    }

    @Override
    public void testSoftware() {
        System.out.println("I will ensure the quality.");
    }

    public void setBusyAsTester(boolean busyAsTester) {
        this.busyAsTester = busyAsTester;
    }
}
