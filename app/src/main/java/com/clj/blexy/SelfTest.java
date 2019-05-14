package com.clj.blexy;

import java.math.BigInteger;

public class SelfTest {

    public static void main(String[] args) {
        System.out.println(new BigInteger("1a", 16));
        Integer final_int = Integer.parseInt(new BigInteger("1a",16).toString()) + Integer.parseInt(new BigInteger("1a",16).toString());
        System.out.println(final_int);

    }
}
