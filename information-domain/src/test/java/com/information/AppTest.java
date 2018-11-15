package com.information;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 33; i++) {
            list.add(i);
        }

        List<Integer> bList = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            bList.add(i);
        }

        Random ra = new Random();
        String s = "";
        for (int i = 0; i < 6; i++) {
            int n = ra.nextInt(32 - i);
            Integer integer = list.get(n);
            s += integer + ",";
            list.remove(n);
        }

        String bL = "";
        int n = ra.nextInt(15);
        Integer integer = bList.get(n);

        System.out.println(s +"----" + bList.get(integer));


    }
}
