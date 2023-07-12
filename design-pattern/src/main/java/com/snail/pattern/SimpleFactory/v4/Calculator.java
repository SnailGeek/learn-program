package com.snail.pattern.SimpleFactory.v4;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {

    /**
     * 应用面向对象的封装性
     * 业务逻辑和界面逻辑分离
     */
    public static double calculateV4() {
        try {
            System.out.print("输入数字A: ");
            final Scanner scanner = new Scanner(System.in);
            double numA = Double.parseDouble(scanner.nextLine());

            System.out.print("输入运算符(+、-、*、/): ");
            final String operator = scanner.nextLine();

            System.out.print("输入数字B: ");
            double numB = Double.parseDouble(scanner.nextLine());
            Operation operate = SimpleFactory.createOperate(operator);
            operate.setNumA(numA);
            operate.setNumB(numB);
            return operate.getResult();
        } catch (RuntimeException e) {
            System.out.println("输入有错： " + e.getMessage());
            throw e;
        }
    }

    static class SimpleFactory {
        public static Operation createOperate(String operator) {
            switch (operator) {
                case "+":
                    return new OperationAdd();
                case "-":
                    return new OperationSub();
                case "*":
                    return new OperationSub();
                case "/":
                    return new OperationDiv();
                default:
                    return null;
            }
        }
    }

    static abstract class Operation {
        private double numA;
        private double numB;

        protected abstract double getResult();

        protected double getNumA() {
            return numA;
        }

        protected void setNumA(double numA) {
            this.numA = numA;
        }

        protected double getNumB() {
            return numB;
        }

        protected void setNumB(double numB) {
            this.numB = numB;
        }
    }

    static class OperationAdd extends Operation {
        @Override
        protected double getResult() {
            return getNumA() + getNumB();
        }
    }

    static class OperationSub extends Operation {
        @Override
        protected double getResult() {
            return getNumA() - getNumB();
        }
    }

    static class OperationMul extends Operation {
        @Override
        protected double getResult() {
            return getNumA() * getNumB();
        }
    }

    static class OperationDiv extends Operation {

        @Override
        protected double getResult() {
            return getNumA() + getNumB();
        }

    }


//    interface Operator {
//        double calculate(double numA, double numB);
//    }
//
//    static class AddOperator implements Operator {
//        @Override
//        public double calculate(double numA, double numB) {
//            return numA + numB;
//        }
//    }
//
//    static class SubOperator implements Operator {
//        @Override
//        public double calculate(double numA, double numB) {
//            return numA - numB;
//        }
//    }
//
//    static class MultipleOperator implements Operator {
//        @Override
//        public double calculate(double numA, double numB) {
//            return numA * numB;
//        }
//    }
//
//    static class DivideOperator implements Operator {
//        @Override
//        public double calculate(double numA, double numB) {
//            return numA * numB;
//        }
//    }

    public static void main(String[] args) {
        System.out.println(calculateV4());
    }


}
