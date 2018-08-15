package com.cxgm.app.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringHelperTest {

    @Test
    public void getRMBFormat() {
        String s = StringHelper.getRMBFormat(34.80f);
        System.out.println(s);
        assertEquals(s,"¥ 34.80");
    }
}