package com.snail.learn;

public class Main {
    public static void main(String[] args) {
        String str = "console-consumer-1142";
        System.out.println(Math.abs(str.hashCode()) % 50);
    }
}