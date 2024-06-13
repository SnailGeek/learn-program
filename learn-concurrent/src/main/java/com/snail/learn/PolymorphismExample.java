package com.snail.learn;

class Animal {
    void makeSound() {
        System.out.println("sound");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Bark");
    }
}

public class PolymorphismExample {
    public static void main(String[] args) {
        Animal myDog = new Dog(); // 运⾏时多态， myDog的实际类型是Dog
        myDog.makeSound(); // 运⾏时确定调⽤Dog类的makeSound⽅法
    }
}