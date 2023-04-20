
package com.snail.beanTag;

import org.springframework.core.style.ToStringCreator;

public class MockBusinessObjectIndex {
    private String dependency1;
    private int dependency2;

    public MockBusinessObjectIndex(String dependency1, int dependency2) {
        this.dependency1 = dependency1;
        this.dependency2 = dependency2;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("dependency1", dependency1)
                .append("dependency2", dependency2)
                .toString();
    }
}
