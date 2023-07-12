package com.snail.pattern.SimpleFactory;

import java.util.Scanner;

public class Calculator {
    /**
     * 问题：
     * 1. 变量名随意
     * 2. 没有对0进行处理
     */
    public static double calculateV1() {
        System.out.print("输入数字A: ");
        final Scanner scanner = new Scanner(System.in);
        double A = Double.parseDouble(scanner.nextLine());

        System.out.print("输入运算符(+、-、*、/): ");
        final String operator = scanner.nextLine();

        System.out.print("输入数字B: ");
        double B = Double.parseDouble(scanner.nextLine());

        switch (operator) {
            case "+":
                return A + B;
            case "-":
                return A - B;
            case "*":
                return A * B;
            case "/":
                return A / B;
            default:
                throw new RuntimeException("Operator Can Not Support: " + operator);
        }
    }

    /**
     * 问题
     * 1. 扩展性较差，如果需要加一个指数操作，对原有代码有影响
     */
    public static double calculateV2() {
        try {
            System.out.print("输入数字A: ");
            final Scanner scanner = new Scanner(System.in);
            double numA = Double.parseDouble(scanner.nextLine());

            System.out.print("输入运算符(+、-、*、/): ");
            final String operator = scanner.nextLine();

            System.out.print("输入数字B: ");
            double numB = Double.parseDouble(scanner.nextLine());
            switch (operator) {
                case "+":
                    return numA + numB;
                case "-":
                    return numA - numB;
                case "*":
                    return numA * numB;
                case "/":
                    return numA / numB;
                default:
                    throw new RuntimeException("Operator Can Not Support: " + operator);
            }

        } catch (RuntimeException e) {
            System.out.println("输入有错： " + e.getMessage());
            throw e;
        }
    }

    /**
     * 应用面向对象的封装性
     * 业务逻辑和界面逻辑分离
     */
    public static double calculateV3() {
        try {
            System.out.print("输入数字A: ");
            final Scanner scanner = new Scanner(System.in);
            double numA = Double.parseDouble(scanner.nextLine());

            System.out.print("输入运算符(+、-、*、/): ");
            final String operator = scanner.nextLine();

            System.out.print("输入数字B: ");
            double numB = Double.parseDouble(scanner.nextLine());
            return Operation.getResult(numA, numB, operator);

        } catch (RuntimeException e) {
            System.out.println("输入有错： " + e.getMessage());
            throw e;
        }
    }

    static class Operation {
        public static double getResult(double numA, double numB, String operator) {
            switch (operator) {
                case "+":
                    return numA + numB;
                case "-":
                    return numA - numB;
                case "*":
                    return numA * numB;
                case "/":
                    return numA / numB;
                default:
                    throw new RuntimeException("Operator Can Not Support: " + operator);
            }
        }
    }

    interface Operator {
        double calculate(double numA, double numB);
    }

    static class AddOperator implements Operator {
        @Override
        public double calculate(double numA, double numB) {
            return numA + numB;
        }
    }

    static class SubOperator implements Operator {
        @Override
        public double calculate(double numA, double numB) {
            return numA - numB;
        }
    }

    static class MultipleOperator implements Operator {
        @Override
        public double calculate(double numA, double numB) {
            return numA * numB;
        }
    }

    static class DivideOperator implements Operator {
        @Override
        public double calculate(double numA, double numB) {
            return numA * numB;
        }
    }

    public static void main(String[] args) {
        calculateV1();
    }


}
