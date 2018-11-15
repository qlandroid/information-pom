package org.mybatis.generator;

public class TestMain {
    public static void main(String[] args) {
        int total = 0;
        for (int i = 0; i < 360; i++) {
            total += i;
        }

        System.out.println("一共金额:" + total);
    }
}
