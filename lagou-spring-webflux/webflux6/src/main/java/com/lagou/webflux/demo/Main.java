package com.lagou.webflux.demo;

public class Main {
    public static void main(String[] args) {
        Subject subject = new MySubject();
        Observer o1 = new MyObserver1();
        Observer o2 = new MyObserver2();
        subject.registerObserver(o1);
        subject.registerObserver(o2);

        subject.notifyObservers("事件1");
        subject.notifyObservers("事件2");

        System.out.println("-------------------------");
        subject.unregisterObserver(o1);

        subject.notifyObservers("事件3");


    }
}