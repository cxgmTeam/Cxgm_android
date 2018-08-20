package com.cxgm.app.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelperTest {

    @Test
    public void calculateDiscounted() {
        float f = Helper.calculateDiscounted(99.3f,"-20");
        System.out.println(f);
    }
    @Test
    public void moneyMultiply() {
        float f = Helper.moneyMultiply(14.81f,6f);
        System.out.println(f);
    }
}