package com.geek.design.builder;

/**
 * @program: ResourcePoolConfig
 * @description:
 * @author: wangf-q
 * @date: 2023-01-06 16:28
 **/
public class ResourcePoolConfigOriginal {
    private static final int DEFAULT_MAX_TOTAL = 8;
    private static final int DEFAULT_MAX_IDLE = 8;
    private static final int DEFAULT_MIN_IDLE = 0;

    private String name;
    private int maxTotal = DEFAULT_MAX_TOTAL;
    private int maxIdle = DEFAULT_MAX_IDLE;
    private int minIdle = DEFAULT_MIN_IDLE;

    public ResourcePoolConfigOriginal(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name should not be empty");
        }
        this.name = name;
    }

    public void setMaxTotal(int maxTotal) {
        if (maxTotal <= 0) {
            throw new IllegalArgumentException("maxTotal should be positive.");
        }
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(int maxIdle) {
        if (maxIdle < 0) {
            throw new IllegalArgumentException("maxIdle should not be negative.");
        }
        this.maxIdle = maxIdle;
    }

    public void setMinIdle(int minIdle) {
        if (minIdle < 0) {
            throw new IllegalArgumentException("minIdle should not be negative.");
        }
        this.minIdle = minIdle;
    }

    public static void main(String[] args) {
        ResourcePoolConfigOriginal config = new ResourcePoolConfigOriginal("dbconnectionpool");
        config.setMaxTotal(16);
        config.setMaxIdle(8);
    }
}
