package com.lagou.webflux.demo;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MySubject implements Subject {
    final Set<Observer> observers = new CopyOnWriteArraySet<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        observers.forEach(o -> o.observe(event));
    }
}
