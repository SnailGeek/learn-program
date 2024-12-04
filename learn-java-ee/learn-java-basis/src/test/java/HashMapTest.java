import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class HashMapTest {

    @Test
    void testInit() {
        HashMap<A, Integer> map = new HashMap<>();
        map.put(new A(), 1);
        HashMap<A, Integer> map2 = new HashMap<>(3);
    }

    @Test
    void testReSizeForSizeBiggerThanThreshold() {
        HashMap<Integer, Integer> map = new HashMap<>(4);
        for (int i = 0; i < 4; i++) {
            map.put(i, i);
        }
    }

    @Test
    void testReSizeForLinkNodeBiggerThan8AndSmallerThan64() {
        HashMap<A, Integer> map = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            map.put(new A(i), i);
        }
    }


    class A {
        private int num;

        public A() {
        }

        public A(int num) {
            this.num = num;
        }

        @Override
        public int hashCode() {
            return 100;
        }


        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
